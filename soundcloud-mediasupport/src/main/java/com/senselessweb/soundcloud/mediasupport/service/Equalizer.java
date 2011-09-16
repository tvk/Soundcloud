package com.senselessweb.soundcloud.mediasupport.service;

/**
 * Represents the 10band equalizer that is used by the {@link MediaPlayer}.
 * 
 * @author thomas
 *
 */
public interface Equalizer
{

	/**
	 * The different bands.
	 * 
	 * @author thomas
	 */
	public enum Band
	{
		/** gain for 29 Hz */
		BAND0,
		/** gain for 59 Hz */
		BAND1,
		/** gain for 119 Hz */
		BAND2,
		/** gain for 237 Hz */
		BAND3,
		/** gain for 474 Hz */
		BAND4,
		/** gain for 947 Hz */
		BAND5,
		/** gain for 1889 Hz */
		BAND6,
		/** gain for 3770 Hz */
		BAND7,
		/** gain for 7523 Hz */
		BAND8,
		/** gain for 15011 Hz */
		BAND9;
	}
	
	/**
	 * Sets the value for a given band.
	 * 
	 * @param band The band.
	 * 
	 * @param value The value to set in db. Must be a value between -24.0 - 12.0.
	 */
	public void setValue(Band band, double value);
	
	/**
	 * Returns the value for the given band.
	 * 
	 * @param band The band.
	 * 
	 * @return The value for that band. 
	 */
	public double getValue(Band band);
}
