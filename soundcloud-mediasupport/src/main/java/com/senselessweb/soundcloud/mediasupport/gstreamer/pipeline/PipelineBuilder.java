package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import com.senselessweb.soundcloud.domain.FileSource;
import com.senselessweb.soundcloud.domain.MediaSource;
import com.senselessweb.soundcloud.domain.StreamSource;
import com.senselessweb.soundcloud.mediasupport.gstreamer.MessageListener;
import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;

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
	private MessageListener messageListener;
	
	/**
	 * @param messageListener The messageListener to use
	 * @return This builder.
	 */
	public PipelineBuilder withMessageListener(final MessageListener messageListener)
	{
		this.messageListener = messageListener;
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
					this.volume, this.equalizer, this.messageListener);
		}
		else if (mediaSource instanceof StreamSource)
		{
			return new StreamSourcePipeline(((StreamSource) mediaSource).getUrl(), 
					this.volume, this.equalizer, this.messageListener);
		}
		else
		{
			throw new IllegalStateException("There is no suitable pipeline for the mediasource \"" + mediaSource + "\"");
		}
			
	}
}
