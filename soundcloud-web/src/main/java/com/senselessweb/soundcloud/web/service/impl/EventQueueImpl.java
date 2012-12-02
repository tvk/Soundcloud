package com.senselessweb.soundcloud.web.service.impl;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.mediasupport.service.Playlist.ChangeEvent;
import com.senselessweb.soundcloud.mediasupport.service.impl.MessageMediator;
import com.senselessweb.soundcloud.web.service.Event;
import com.senselessweb.soundcloud.web.service.EventQueue;

/**
 * Default event queue implementation.
 * 
 * @author thomas
 */
@Service
@Scope(proxyMode=ScopedProxyMode.INTERFACES, value="session")
public class EventQueueImpl extends AbstractMessageAdapter implements EventQueue, Serializable
{
	
	@Autowired
	public EventQueueImpl(final MessageMediator messageMediator) 
	{
		super(messageMediator);
	}

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = -9044435692250334497L;
	
	/**
	 * The message queue
	 */
	private final BlockingQueue<Event> queue = new LinkedBlockingQueue<Event>();
	
	
	/**
	 * @see com.senselessweb.soundcloud.web.service.EventQueue#getNextEvent()
	 */
	@Override
	public Event getNextEvent()
	{
		// Throw anything into the queue so that a second waiting thread stops waiting 
		final Event poison = new Event("poison-" + Thread.currentThread().getId());
		this.queue.add(poison);
		
		try
		{
			final Event event = this.queue.take();
			
			// Won't consume my own poison :)
			if (!event.equals(poison)) return event;
			
			// Continue waiting
			return this.queue.take();
		} 
		catch (final InterruptedException e)
		{
			throw new RuntimeException("Interrupted while waiting for the next event : " + e, e);
		}
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	@Override
	public void stateChanged(final State newState)
	{
		this.queue.add(new Event("stateChanged", "newState", newState.name()));
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#error(java.lang.String)
	 */
	@Override
	public void error(final String message)
	{
		this.queue.add(new Event("error", "message", message));
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#playlistChanged(ChangeEvent, int)
	 */
	@Override
	public void playlistChanged(final ChangeEvent event, final int current)
	{
		this.queue.add(new Event("playlistChanged", "event", event.name(), "current", String.valueOf(current)));
	}
	
	/**
	 * @see com.senselessweb.soundcloud.web.service.impl.AbstractMessageAdapter#durationChanged(long)
	 */
	@Override
	public void durationChanged(final long duration)
	{
		this.queue.add(new Event("durationChanged", "duration", String.valueOf(duration)));
	}
	
}
