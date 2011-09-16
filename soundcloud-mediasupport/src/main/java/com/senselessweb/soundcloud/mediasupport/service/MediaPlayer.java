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
	 * Pauses the playback.
	 */
	public void pause();
	
	
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
