package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import java.net.URL;

import com.senselessweb.soundcloud.mediasupport.gstreamer.MessageListener;
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
	 * @param eosListener The {@link MessageListener} gets notified when the strem ends.
	 */
	public StreamSourcePipeline(final URL url, final VolumeBridge volume, final EqualizerBridge equalizer, final MessageListener eosListener)
	{
		super(createDefaultPipeline("souphttpsrc"), volume, equalizer, eosListener);
		this.pipeline.getElementByName("src").set("location", url.toString());
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#resetInErrorCase()
	 */
	@Override
	public boolean resetInErrorCase()
	{
		return true;
	}
	
}
