package com.senselessweb.soundcloud.mediasupport.gstreamer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.sources.FileSource;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.domain.sources.StreamSource;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.PanoramaBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline.FileSrcPipeline;
import com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline.StreamSourcePipeline;
import com.senselessweb.soundcloud.mediasupport.service.impl.MessageMediator;

/**
 * Pipeline factory
 * 
 * @author thomas
 *
 */
@Service
public class PipelineBuilder
{
	
	/**
	 * The volume to use
	 */
	private final VolumeBridge volume;

	/**
	 * The equalizer to use
	 */
	private final EqualizerBridge equalizer;
	
	/**
	 * The panorama to use
	 */
	private final PanoramaBridge panoramaBridge;
	
	/**
	 * The messageListener to use
	 */
	private final MessageMediator messageMediator;
	
	@Autowired
	public PipelineBuilder(final VolumeBridge volume, final EqualizerBridge equalizer, 
			final PanoramaBridge panoramaBridge, final MessageMediator messageMediator) 
	{
		this.equalizer = equalizer;
		this.messageMediator = messageMediator;
		this.panoramaBridge = panoramaBridge;
		this.volume = volume;
	}
	
	/**
	 * Returns the correct pipeline for the given {@link MediaSource}.
	 * 
	 * @param mediaSource The {@link MediaSource}. Must not be null.
	 * 
	 * @return The {@link PipelineBridge} for the given {@link MediaSource}.
	 */
	public PipelineBridge createPipeline(final MediaSource mediaSource)
	{
		if (mediaSource instanceof FileSource)
		{
			return new FileSrcPipeline(((FileSource) mediaSource).getFile(), 
					this.volume, this.equalizer, this.panoramaBridge, this.messageMediator);
		}
		else if (mediaSource instanceof StreamSource)
		{
			return new StreamSourcePipeline(((StreamSource) mediaSource).getUrl(), 
					this.volume, this.equalizer, this.panoramaBridge, this.messageMediator);
		}
		else
		{
			throw new IllegalStateException("There is no suitable pipeline for the mediasource \"" + mediaSource + "\"");
		}
			
	}
}
