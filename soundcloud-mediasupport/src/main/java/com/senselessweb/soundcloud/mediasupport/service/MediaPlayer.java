package com.senselessweb.soundcloud.mediasupport.service;


/**
 * Service interface that defines the methods the mediaplayer offers.
 * 
 * @author thomas
 *
 */
public interface MediaPlayer
{
	
	/**
	 * The current playback state
	 * 
	 * @author thomas
	 */
	public enum State
	{
		/** Playing */
		PLAYING,
		
		/** Paused */
		PAUSED,
		
		/** Stopped */
		STOPPED
	}
	
	/**
	 * Starts the playback
	 */
	public void play();
	
	/**
	 * Stops the playback.
	 */
	public void stop();
	
	/**
	 * Pauses the playback.
	 */
	public void pause();
	
	
	/**
	 * Returns the current playback state
	 * 
	 * @return The current playback state. Never null.
	 */
	public State getState();
	

}
