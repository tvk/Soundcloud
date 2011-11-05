package com.senselessweb.soundcloud.mediasupport.service;

import com.senselessweb.soundcloud.mediasupport.service.impl.MessageMediator;


/**
 * {@link MessageListener} that is designed as a service. Beans that implement
 * this class are automatically added to the {@link MessageMediator} that is
 * responsible for delegating the messages. 
 * 
 * @author thomas
 */
public interface MessageListenerService extends MessageListener
{


	// Marker interface
}
