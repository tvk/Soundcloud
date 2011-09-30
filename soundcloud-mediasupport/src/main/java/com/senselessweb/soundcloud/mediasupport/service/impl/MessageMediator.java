package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.mediasupport.service.MessageListener;

/**
 * Message Mediator that contains all the message listeners.
 * 
 * @author thomas
 */
public class MessageMediator implements MessageListener
{
	
	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(MessageMediator.class);
	
	/**
	 * The attached listeners
	 */
	private final Collection<MessageListener> messageListeners = new HashSet<MessageListener>();
	
	/**
	 * Attaches a new listener
	 * 
	 * @param listener The new listener
	 */
	public void addMessageListener(MessageListener listener)
	{
		this.messageListeners.add(listener);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	@Override
	public void stateChanged(final State newState)
	{
		log.debug("State changed event. New state: " + newState);
		for (final MessageListener listener : this.messageListeners)
			listener.stateChanged(newState);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#error(java.lang.String)
	 */
	@Override
	public void error(final String message)
	{
		log.debug("Error event. Message: " + message);
		for (final MessageListener listener : this.messageListeners)
			listener.error(message);
	}

}
