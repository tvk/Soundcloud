package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.GStreamerMessageListener;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.MessageListener;
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
	 * @param eosListener The {@link GStreamerMessageListener} gets notified when the strem ends.
	 * @param messageListener The {@link MessageListener}.
	 */
	public StreamSourcePipeline(final String url, final VolumeBridge volume, final EqualizerBridge equalizer, 
			final GStreamerMessageListener eosListener, final MessageListener messageListener)
	{
		super(createDefaultPipeline("souphttpsrc iradio-mode=true "), volume, equalizer, eosListener, messageListener);
		this.pipeline.getElementByName("src").set("location", url);
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
