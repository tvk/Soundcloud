package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Element;
import org.gstreamer.Pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * Bridge that contains the gstreamer {@link Pipeline} and contains references 
 * to some pipeline {@link Element}s.
 * 
 * @author thomas
 *
 */
public abstract class AbstractPipeline implements PipelineBridge
{

	/**
	 * Logger
	 */
	static final Log log = LogFactory.getLog(AbstractPipeline.class);
	
	/**
	 * The actual pipeline
	 */
	protected final Pipeline pipeline;
	
	/**
	 * The current volume control
	 */
	private final VolumeBridge volume;
	
	/**
	 * The current equalizer control
	 */
	private final EqualizerBridge equalizer;
	
	/**
	 * Constructor
	 * 
	 * @param pipeline The {@link Pipeline} to use.
	 * @param volume The current {@link VolumeControl}.
	 * @param equalizer The current {@link EqualizerBridge}.
	 */
	public AbstractPipeline(final Pipeline pipeline, final VolumeBridge volume, final EqualizerBridge equalizer)
	{
		this.pipeline = pipeline;
		
		this.volume = volume;
		this.volume.initElement(pipeline.getElementByName("volume"));
		
		this.equalizer = equalizer;
		this.equalizer.initElement(pipeline.getElementByName("equalizer"));
	}
	
	/**
	 * Creates a pipeline containing the default pipeline elements.
	 * 
	 * @param src The name of the source {@link Element} to use. Read the dokumentation about 
	 * gstreamer plugins to find out what sources are supported. The source element is added 
	 * to the pipeline under the name "src".   
	 * 
	 * @return The created pipeline.
	 */
	protected static Pipeline createDefaultPipeline(final String src)
	{
		return Pipeline.launch(
				src + " name=src ! " +
				"decodebin ! " +
				"audioconvert ! " +
				"equalizer-10bands name=equalizer ! " +
				"volume name=volume ! " +
				"alsasink");
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#play()
	 */
	@Override
	public void play()
	{
		log.debug("Playing " + this);
		this.pipeline.play();
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#stop()
	 */
	@Override
	public void stop()
	{
		log.debug("Stopping " + this);
		this.pipeline.stop();
	}
}
