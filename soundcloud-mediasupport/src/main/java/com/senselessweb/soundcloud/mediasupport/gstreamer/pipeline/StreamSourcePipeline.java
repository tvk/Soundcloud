package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.MessageListenerService;
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
	 * @param equalizer The current {@link EqualizerBridge}.
	 * @param messageListener The {@link MessageListenerService}.
	 */
	public StreamSourcePipeline(final String url, final VolumeBridge volume, final EqualizerBridge equalizer, 
			final MessageListenerService messageListener)
	{
		super(createDefaultPipeline("souphttpsrc iradio-mode=true "), volume, equalizer, messageListener);
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
