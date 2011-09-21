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
	 * Play the next song of the current playlist.
	 */
	public void next();
	

	/**
	 * Play the previous song of the current playlist.
	 */
	public void previous();
	
	
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
	
	
	/**
	 * Returns the current playlist.
	 * 
	 * @return The current playlist. Never null.
	 */
	public Playlist getCurrentPlaylist();
	
	
	/**
	 * Return the volume control that can be used to control the volume.
	 * 
	 * @return The {@link VolumeControl}. 
	 */
	public VolumeControl getVolumeControl();
	
	
	/**
	 * Returns the equalizer control.
	 * 
	 * @return The {@link Equalizer}.
	 */
	public Equalizer getEqualizer();


	/**
	 * Shutdown the music service and deallocate all resources.
	 */
	public void shutdown();


}
