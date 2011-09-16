package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import java.net.URL;

import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * Pipeline bridge that uses a file source.
 * 
 * @author thomas
 */
public class StreamSourcePipeline extends AbstractPipeline
{

	/**
	 * Constructor
	 * 
	 * @param url The url that is used as source.
	 * @param volume The {@link VolumeControl}.
	 */
	public StreamSourcePipeline(final URL url, final VolumeBridge volume)
	{
		super(createDefaultPipeline("souphttpsrc"), volume);
		this.pipeline.getElementByName("src").set("location", url.toString());
	}
}
