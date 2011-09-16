package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import org.gstreamer.Element;
import org.gstreamer.Pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
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
	 * The actual pipeline
	 */
	protected final Pipeline pipeline;
	
	/**
	 * The current volume control
	 */
	private final VolumeBridge volume;
	
	/**
	 * Constructor
	 * 
	 * @param pipeline The {@link Pipeline} to use.
	 * @param volume The current {@link VolumeControl}.
	 */
	public AbstractPipeline(final Pipeline pipeline, final VolumeBridge volume)
	{
		this.pipeline = pipeline;
		
		this.volume = volume;
		this.volume.initElement(pipeline.getElementByName("volume"));
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
	public void play()
	{
		this.pipeline.play();
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#stop()
	 */
	public void stop()
	{
		this.pipeline.stop();
	}
}
