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
	 * Returns the duration of the current song.
	 * 
	 * IMPORTANT: This should called earliest after playback of a track started.
	 * 
	 * @return The duration of the current song in seconds or -1 if no
	 * duration is available.
	 */
	public long getDuration();
	
	/**
	 * Returns the position inside the current song.
	 * 
	 * @return The position inside the current song.
	 */
	public long getPosition();
	
	/**
	 * Returns whether seeking is supported by the current song.
	 * 
	 * @return True if seeking is supported, false otherwise.
	 */
	public boolean isSeekSupported();
	
	/**
	 * Jumps to a given position in the current song. Use isSeekSupported() to find out
	 * if seeking is supported.
	 * 
	 * @param position The position to jump to (in seconds)
	 */
	public void gotoPosition(long position);
	
	
	/**
	 * Returns the current playback state
	 * 
	 * @return The current playback state. Never null.
	 */
	public State getState();
	

}
