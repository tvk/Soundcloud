/**
 * 
 */
package com.senselessweb.soundcloud.mediasupport.gstreamer.elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senselessweb.storage.PersistencyService;

/**
 * The panorama bridge controls the audiopanorama element. The balance can be controlled
 * using this element.
 *
 * @author thomas
 */
@Service
public class PanoramaBridge extends AbstractElementBridge
{
	@Autowired
	public PanoramaBridge(final PersistencyService persistencyService) 
	{
		super(persistencyService);
	}

	/**
	 * Sets the panorama value.
	 * 
	 * @param value The panorama value. -1.0 means balancing fully to the left, 1.0 means full
	 * balancing to the right. 
	 */
	public void setPanorama(final double value)
	{
		if (value < -1.0 || value > 1.0) throw new IllegalArgumentException("Value " + value + " is not allowed");
		this.set("panorama", value);
	}
	
	/**
	 * Returns the panorama value
	 * 
	 * @return The panorama value. Always between -1.0 and 1.0
	 */
	public double getPanorama()
	{
		return this.get("panorama", 0.0);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.elements.AbstractElementBridge#getPrefix()
	 */
	@Override
	protected String getPrefix()
	{
		return "panorama";
	}
}
