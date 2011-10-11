/**
 * 
 */
package com.senselessweb.soundcloud.web.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.mediasupport.service.MessageListener;
import com.senselessweb.soundcloud.mediasupport.service.Playlist.ChangeEvent;
import com.senselessweb.soundcloud.mediasupport.service.impl.MessageMediator;

/**
 * This abstract class solves the problem that a unscoped service tries to 
 * delegate a message to a scoped {@link Service}.
 * 
 * Furthermore this class can be used as an adapter for the {@link MessageListener}.
 *
 * @author thomas
 */
public class AbstractMessageAdapter implements MessageListener
{

	/**
	 * The messageMediator
	 */
	@Autowired MessageMediator messageMediator;


	/**
	 * Registers this controller as a message listener
	 */
	@PostConstruct public void registerMessageListener()
	{
		this.messageMediator.addMessageListener(this);
	}

	/**
	 * Registers this controller as a message listener
	 */
	@PreDestroy public void unregisterMessageListener()
	{
		this.messageMediator.removeMessageListener(this);
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	@Override
	public void stateChanged(State newState) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#error(java.lang.String)
	 */
	@Override
	public void error(String message) { /* unused */ }
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#tag(java.lang.String, java.lang.String)
	 */
	@Override
	public void tag(String tag, String value) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#newSource(com.senselessweb.soundcloud.domain.sources.MediaSource)
	 */
	@Override
	public void newSource(MediaSource source) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#playlistChanged(com.senselessweb.soundcloud.mediasupport.service.Playlist.ChangeEvent, int)
	 */
	@Override
	public void playlistChanged(ChangeEvent event, int current) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#endOfStream()
	 */
	@Override
	public void endOfStream() { /* unused */ }

}
