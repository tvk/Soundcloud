package com.senselessweb.soundcloud.mediasupport.service;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;


/**
 * Generic message listener that can be attached to the media player.
 * 
 * @author thomas
 */
public interface MessageListener
{

	/**
	 * Is called when the playback state changes.
	 * 
	 * @param newState The new playback state.
	 */
	public void stateChanged(State newState);

	/**
	 * Is called when an error appears.
	 * 
	 * @param message The error message.
	 */
	public void error(String message);
	
	
}
