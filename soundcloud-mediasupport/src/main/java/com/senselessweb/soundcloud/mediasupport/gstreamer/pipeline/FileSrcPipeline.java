package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import java.io.File;

import com.senselessweb.soundcloud.mediasupport.gstreamer.MessageListener;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * Pipeline bridge that uses a file source.
 * 
 * @author thomas
 */
class FileSrcPipeline extends AbstractPipeline
{

	/**
	 * Constructor
	 * 
	 * @param file The file that is used as source.
	 * @param volume The {@link VolumeControl}.
	 * @param equalizer The current {@link EqualizerBridge}.
	 * @param eosListener The {@link MessageListener} gets notified when the strem ends.
	 */
	public FileSrcPipeline(final File file, final VolumeBridge volume, final EqualizerBridge equalizer, final MessageListener eosListener)
	{
		super(createDefaultPipeline("filesrc"), volume, equalizer, eosListener);
		this.pipeline.getElementByName("src").set("location", file.getAbsolutePath());
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#resetInErrorCase()
	 */
	@Override
	public boolean resetInErrorCase()
	{
		return false;
	}
}
