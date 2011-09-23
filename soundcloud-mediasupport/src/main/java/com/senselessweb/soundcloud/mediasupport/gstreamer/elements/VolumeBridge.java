package com.senselessweb.soundcloud.mediasupport.gstreamer.elements;

import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;
import com.senselessweb.storage.PersistencyService;

/**
 * Bridge that forwards to the volume pipeline element.
 * 
 * @author thomas
 */
public class VolumeBridge extends AbstractElementBridge implements VolumeControl
{
	
	/**
	 * The {@link PersistencyService}
	 */
	private final PersistencyService persistencyService;

	
	/**
	 * Constructor
	 * 
	 * @param persistencyService The {@link PersistencyService} to store and restore properties.
	 */
	public VolumeBridge(final PersistencyService persistencyService)
	{
		this.persistencyService = persistencyService;
		
		if (this.persistencyService != null)
		{
			if (this.persistencyService.contains("volume.mute")) 
				this.setMute(Boolean.valueOf(this.persistencyService.get("volume.mute")));
			if (this.persistencyService.contains("volume.volume")) 
				this.setVolume(Double.valueOf(this.persistencyService.get("volume.volume")));
		}
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.VolumeControl#setVolume(double)
	 */
	@Override
	public void setVolume(final double volume)
	{
		if (volume < 0.0 || volume > 1.0)
			throw new IllegalArgumentException("Illegal volume value: " + volume);
		
		this.set("volume", volume);
		if (this.persistencyService != null) this.persistencyService.put("volume.volume", String.valueOf(volume));		
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.VolumeControl#setMute(boolean)
	 */
	@Override
	public void setMute(final boolean mute)
	{
		this.set("mute", mute);
		if (this.persistencyService != null) this.persistencyService.put("volume.mute", String.valueOf(mute));		
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
