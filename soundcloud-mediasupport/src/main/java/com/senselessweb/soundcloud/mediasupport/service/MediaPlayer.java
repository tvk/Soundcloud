package com.senselessweb.soundcloud.mediasupport.service;

import java.io.File;

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
	 * Stops the playback.
	 */
	public void stop();
	
	
	/**
	 * Sets the sound volume.
	 * 
	 * @param volume The volume. Must be a value between 0.0 and 1.0.
	 */
	public void setVolume(double volume);
}
