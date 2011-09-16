package com.senselessweb.soundcloud.mediasupport.gstreamer.elements;

import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * Bridge that forwards to the volume pipeline element.
 * 
 * @author thomas
 */
public class VolumeBridge extends AbstractElementBridge implements VolumeControl
{
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.VolumeControl#setVolume(double)
	 */
	@Override
	public void setVolume(final double volume)
	{
		if (volume < 0.0 || volume > 1.0)
			throw new IllegalArgumentException("Illegal volume value: " + volume);
		
		this.set("volume", volume);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.VolumeControl#setMute(boolean)
	 */
	@Override
	public void setMute(final boolean mute)
	{
		this.set("mute", mute);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.VolumeControl#getVolume()
	 */
	@Override
	public double getVolume()
	{
		return this.get("volume", 1.0);
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.VolumeControl#getMute()
	 */
	@Override
	public boolean getMute()
	{
		return this.get("mute", false);
	}
}
