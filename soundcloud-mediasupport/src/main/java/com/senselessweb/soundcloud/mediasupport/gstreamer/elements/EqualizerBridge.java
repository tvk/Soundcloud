package com.senselessweb.soundcloud.mediasupport.gstreamer.elements;

import com.senselessweb.soundcloud.mediasupport.service.Equalizer;
import com.senselessweb.storage.PersistencyService;

/**
 * Bridge that forwards to the equalizer pipeline element.
 * 
 * @author thomas
 */
public class EqualizerBridge extends AbstractElementBridge implements Equalizer
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
	public EqualizerBridge(final PersistencyService persistencyService)
	{
		this.persistencyService = persistencyService;
		
		if (this.persistencyService != null)
		{
			for (final Band band : Band.values())
			{
				final String value = this.persistencyService.get("equalizer." + band.name());
				if (value != null) this.setValue(band, Double.valueOf(value));
			}
		}
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Equalizer#setValue(com.senselessweb.soundcloud.mediasupport.service.Equalizer.Band, double)
	 */
	@Override
	public void setValue(final Band band, final double value)
	{
		this.set(band.name().toLowerCase(), value);
		if (this.persistencyService != null) this.persistencyService.put("equalizer." + band.name(), String.valueOf(value));
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Equalizer#getValue(com.senselessweb.soundcloud.mediasupport.service.Equalizer.Band)
	 */
	@Override
	public double getValue(final Band band)
	{
		return this.get(band.name().toLowerCase(), 0.0);
	}

}
