package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.mediasupport.service.MessageListener;
import com.senselessweb.soundcloud.mediasupport.service.MessageListenerService;
import com.senselessweb.soundcloud.mediasupport.service.Playlist.ChangeEvent;

/**
 * Message Mediator that contains all the message listeners.
 * 
 * @author thomas
 */
@Service
public class MessageMediator implements MessageListenerService
{
	
	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(MessageMediator.class);
	
	/**
	 * The autowired listeners
	 */
	@Autowired Collection<MessageListenerService> messageListenerServices;
	
	/**
	 * The attached listeners
	 */
	private final Collection<MessageListener> messageListeners = new HashSet<MessageListener>();
	
	/**
	 * Attaches a new {@link MessageListener}.
	 * 
	 * @param listener The new listener
	 */
	public void addMessageListener(final MessageListener listener)
	{
		this.messageListeners.add(listener);
	}

	/**
	 * Removes a new {@link MessageListener}.
	 * 
	 * @param listener The listener to remove
	 */
	public void removeMessageListener(final MessageListener listener)
	{
		this.messageListeners.remove(listener);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	@Override
	public void stateChanged(final State newState)
	{
		log.debug("State changed event. New state: " + newState);
		for (final MessageListener listener : this.allMessageListeners())
			listener.stateChanged(newState);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#error(java.lang.String)
	 */
	@Override
	public void error(final String message)
	{
		log.debug("Error event. Message: " + message);
		for (final MessageListener listener : this.allMessageListeners())
			listener.error(message);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#tag(java.lang.String, java.lang.String)
	 */
	@Override
	public void tag(final String tag, final String value)
	{
		for (final MessageListener listener : this.allMessageListeners())
			listener.tag(tag, value);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#newSource(com.senselessweb.soundcloud.domain.sources.MediaSource)
	 */
	@Override
	public void newSource(final MediaSource source)
	{
		for (final MessageListener listener : this.allMessageListeners())
			listener.newSource(source);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#durationChanged(long)
	 */
	@Override
	public void durationChanged(long duration)
	{
		for (final MessageListener listener : this.allMessageListeners())
			listener.durationChanged(duration);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#positionChanged(long)
	 */
	@Override
	public void positionChanged(long position)
	{
		for (final MessageListener listener : this.allMessageListeners())
			listener.positionChanged(position);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#playlistChanged(ChangeEvent, int)
	 */
	@Override
	public void playlistChanged(final ChangeEvent event, final int current)
	{
		for (final MessageListener listener : this.allMessageListeners())
			listener.playlistChanged(event, current);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#endOfStream()
	 */
	@Override
	public void endOfStream()
	{
		for (final MessageListener listener : this.allMessageListeners())
			listener.endOfStream();
	}
		
	/**
	 * Returns all {@link MessageListener}s
	 * 
	 * @return All {@link MessageListener}s
	 */
	private Collection<? extends MessageListener> allMessageListeners()
	{
		final Set<MessageListener> all = new HashSet<MessageListener>();
		all.addAll(this.messageListeners);
		all.addAll(this.messageListenerServices);
		return all;
	}


}
