package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.PanoramaBridge;
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
	 * @param panoramaBridge The {@link PanoramaBridge}. 
	 * @param messageListener The {@link MessageListenerService}.
	 */
	public StreamSourcePipeline(final String url, final VolumeBridge volume, final EqualizerBridge equalizer, 
			final PanoramaBridge panoramaBridge, final MessageListenerService messageListener)
	{
		super(createDefaultPipeline("souphttpsrc iradio-mode=true location="+url), volume, equalizer, panoramaBridge, messageListener);
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#resetInErrorCase()
	 */
	@Override
	public boolean resetInErrorCase()
	{
		return true;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#isPausable()
	 */
	@Override
	public boolean isPausable()
	{
		return false;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#isSeekSupported()
	 */
	@Override
	public boolean isSeekSupported()
	{
		return false;
	}
	
}
