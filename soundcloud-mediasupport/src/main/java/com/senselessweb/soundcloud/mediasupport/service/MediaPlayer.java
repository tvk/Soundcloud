package com.senselessweb.soundcloud.mediasupport.service;

import java.io.File;
import java.net.URL;

/**
 * Service interface that defines the methods the mediaplayer offers.
 * 
 * @author thomas
 *
 */
public interface MediaPlayer
{
	
	/**
	 * Plays the given media file.
	 * 
	 * @param file The media file to play. Must be an existing file.
	 */
	public void play(File file);
	
	
	/**
	 * Plays the mediastream represented by the given {@link URL}.
	 * 
	 * @param url The {@link URL} to play. Must not be null.
	 */
	public void play(URL url);
	
	
	/**
	 * Stops the playback.
	 */
	public void stop();
	
	
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
