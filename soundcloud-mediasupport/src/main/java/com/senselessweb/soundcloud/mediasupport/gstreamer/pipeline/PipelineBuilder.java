package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import com.senselessweb.soundcloud.domain.sources.FileSource;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.domain.sources.StreamSource;
import com.senselessweb.soundcloud.mediasupport.gstreamer.GStreamerMessageListener;
import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.MessageListener;

/**
 * Pipeline factory
 * 
 * @author thomas
 *
 */
public class PipelineBuilder
{
	
	/**
	 * The volume to use
	 */
	private VolumeBridge volume;
	
	/**
	 * @param volume The volume to use
	 * @return This builder.
	 */
	public PipelineBuilder withVolume(final VolumeBridge volume)
	{
		this.volume = volume;
		return this;
	}

	/**
	 * The equalizer to use
	 */
	private EqualizerBridge equalizer;
	
	/**
	 * @param equalizer The equalizer to use
	 * @return This builder.
	 */
	public PipelineBuilder withEqualizer(final EqualizerBridge equalizer)
	{
		this.equalizer = equalizer;
		return this;
	}
	

	/**
	 * The messageListener to use
	 */
	private GStreamerMessageListener gStreamerMessageListener;
	
	/**
	 * @param gStreamerMessageListener The messageListener to use
	 * @return This builder.
	 */
	public PipelineBuilder withGStreamerMessageListener(final GStreamerMessageListener gStreamerMessageListener)
	{
		this.gStreamerMessageListener = gStreamerMessageListener;
		return this;
	}
	
	

	/**
	 * The messageListener to use
	 */
	private MessageListener messageListener;
	
	/**
	 * @param MessageListener The messageListener to use
	 * @return This builder.
	 */
	public PipelineBuilder withMessageListener(final MessageListener MessageListener)
	{
		this.messageListener = MessageListener;
		return this;
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
					this.volume, this.equalizer, this.gStreamerMessageListener, this.messageListener);
		}
		else if (mediaSource instanceof StreamSource)
		{
			return new StreamSourcePipeline(((StreamSource) mediaSource).getUrl(), 
					this.volume, this.equalizer, this.gStreamerMessageListener, this.messageListener);
		}
		else
		{
			throw new IllegalStateException("There is no suitable pipeline for the mediasource \"" + mediaSource + "\"");
		}
			
	}
}
