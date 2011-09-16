package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import java.net.URL;

import com.senselessweb.soundcloud.mediasupport.gstreamer.EndOfStreamListener;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * Pipeline bridge that uses a file source.
 * 
 * @author thomas
 */
class StreamSourcePipeline extends AbstractPipeline
{

	/**
	 * Constructor
	 * 
	 * @param url The url that is used as source.
	 * @param volume The {@link VolumeControl}.
	 * @param equalizer The current {@link EqualizerBridge}.
	 * @param eosListener The {@link EndOfStreamListener} gets notified when the strem ends.
	 */
	public StreamSourcePipeline(final URL url, final VolumeBridge volume, final EqualizerBridge equalizer, final EndOfStreamListener eosListener)
	{
		super(createDefaultPipeline("souphttpsrc"), volume, equalizer, eosListener);
		this.pipeline.getElementByName("src").set("location", url.toString());
	}
}
