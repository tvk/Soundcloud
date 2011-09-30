package com.senselessweb.soundcloud.web.service.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.MediaSource;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.mediasupport.service.MessageListener;
import com.senselessweb.soundcloud.web.service.Event;
import com.senselessweb.soundcloud.web.service.EventQueue;

/**
 * Default event queue implementation.
 * 
 * @author thomas
 */
@Service
public class EventQueueImpl implements EventQueue, MessageListener
{

	/**
	 * The message queue
	 */
	private final BlockingQueue<Event> queue = new LinkedBlockingQueue<Event>();
	
	/**
	 * @param mediaPlayer The media player
	 */
	@Autowired public EventQueueImpl(final MediaPlayer mediaPlayer)
	{
		mediaPlayer.addMessageListener(this);
	}

	/**
	 * @see com.senselessweb.soundcloud.web.service.EventQueue#getNextEvent()
	 */
	public Event getNextEvent()
	{
		try
		{
			return this.queue.take();
		} 
		catch (final InterruptedException e)
		{
			throw new RuntimeException("Interrupted while waiting for the next event : " + e, e);
		}
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	public void stateChanged(final State newState)
	{
		this.queue.add(new Event("stateChanged", "newState", newState.name()));
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#error(java.lang.String)
	 */
	public void error(final String message)
	{
		this.queue.add(new Event("error", "message", message));
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#tag(java.lang.String, java.lang.String)
	 */
	public void tag(final String tag, final String value)
	{
		// Unused as long as the DisplayDataController handles all tag request
		// this.queue.add(new Event("tag", tag, value));
	}

	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#newSource(com.senselessweb.soundcloud.domain.MediaSource)
	 */
	public void newSource(final MediaSource source)
	{
		// Unused
	}
}
