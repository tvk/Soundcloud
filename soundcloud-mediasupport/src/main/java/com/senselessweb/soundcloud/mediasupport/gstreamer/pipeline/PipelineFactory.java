package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import com.senselessweb.soundcloud.mediasupport.domain.FileSource;
import com.senselessweb.soundcloud.mediasupport.domain.MediaSource;
import com.senselessweb.soundcloud.mediasupport.domain.StreamSource;
import com.senselessweb.soundcloud.mediasupport.gstreamer.EndOfStreamListener;
import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * Pipeline factory
 * 
 * @author thomas
 *
 */
public class PipelineFactory
{

	/**
	 * Returns the correct pipeline for the given {@link MediaSource}.
	 * 
	 * @param mediaSource The {@link MediaSource}. Must not be null.
	 * @param volume The {@link VolumeControl}.
	 * @param equalizer The current {@link EqualizerBridge}.
	 * @param eosListener The {@link EndOfStreamListener} gets notified when the strem ends.
	 * 
	 * @return The {@link PipelineBridge} for the given {@link MediaSource}.
	 */
	public static PipelineBridge createPipeline(final MediaSource mediaSource, 
			final VolumeBridge volume, final EqualizerBridge equalizer, final EndOfStreamListener eosListener)
	{
		if (mediaSource instanceof FileSource)
		{
			return new FileSrcPipeline(((FileSource) mediaSource).getFile(), volume, equalizer, eosListener);
		}
		else if (mediaSource instanceof StreamSource)
		{
			return new StreamSourcePipeline(((StreamSource) mediaSource).getUrl(), volume, equalizer, eosListener);
		}
		else
		{
			throw new IllegalStateException("There is no suitable pipeline for the mediasource \"" + mediaSource + "\"");
		}
			
	}
}
