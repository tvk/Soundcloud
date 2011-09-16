package com.senselessweb.soundcloud.mediasupport.gstreamer.elements;

import com.senselessweb.soundcloud.mediasupport.service.Equalizer;

/**
 * Bridge that forwards to the equalizer pipeline element.
 * 
 * @author thomas
 */
public class EqualizerBridge extends AbstractElementBridge implements Equalizer
{

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Equalizer#setValue(com.senselessweb.soundcloud.mediasupport.service.Equalizer.Band, double)
	 */
	@Override
	public void setValue(final Band band, final double value)
	{
		this.set(band.name().toLowerCase(), value);
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