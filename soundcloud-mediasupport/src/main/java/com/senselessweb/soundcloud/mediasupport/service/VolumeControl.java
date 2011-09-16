package com.senselessweb.soundcloud.mediasupport.service;

/**
 * Controls the current playback volume.
 * 
 * @author thomas
 */
public interface VolumeControl
{

	/**
	 * Sets the volume.
	 * 
	 * @param volume The volume. Must be a value between 0.0 and 1.0.
	 */
	public void setVolume(double volume);
	
	
	/**
	 * Returns the current volume.
	 * 
	 * @return The current volume. Always a value between 0.0 and 1.0.
	 */
	public double getVolume();
	
	
	/**
	 * Sets the mute value.
	 * 
	 * @param mute The mute value. If true, the volume is set to mute. 
	 */
	public void setMute(boolean mute);
	

	/**
	 * Returns the mute value.
	 * 
	 * @return The mute value. If true, the volume is set to mute. 
	 */
	public boolean getMute();
	
}
