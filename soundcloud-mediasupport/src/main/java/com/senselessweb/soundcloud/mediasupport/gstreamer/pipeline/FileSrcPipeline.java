package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import java.io.File;

import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * Pipeline bridge that uses a file source.
 * 
 * @author thomas
 */
public class FileSrcPipeline extends AbstractPipeline
{

	/**
	 * Constructor
	 * 
	 * @param file The file that is used as source.
	 * @param volume The {@link VolumeControl}.
	 * @param equalizer The current {@link EqualizerBridge}.
	 */
	public FileSrcPipeline(final File file, final VolumeBridge volume, final EqualizerBridge equalizer)
	{
		super(createDefaultPipeline("filesrc"), volume, equalizer);
		this.pipeline.getElementByName("src").set("location", file.getAbsolutePath());
	}
}
