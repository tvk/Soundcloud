package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Bus;
import org.gstreamer.Element;
import org.gstreamer.GstObject;
import org.gstreamer.Pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.MessageListener;
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
	 * @param eosListener The {@link MessageListener} gets notified when the strem ends.
	 */
	public AbstractPipeline(final Pipeline pipeline, final VolumeBridge volume, final EqualizerBridge equalizer, 
			final MessageListener eosListener)
	{
		this.pipeline = pipeline;
		
		this.volume = volume;
		this.volume.initElement(this.pipeline.getElementByName("volume"));
		
		this.equalizer = equalizer;
		this.equalizer.initElement(this.pipeline.getElementByName("equalizer"));
		
		final BusMessageListener messageListener = new BusMessageListener();
		this.pipeline.getBus().connect(messageListener.errorMessageListener);
		this.pipeline.getBus().connect(messageListener.warnMessageListener);
		this.pipeline.getBus().connect(messageListener.infoMessageListener);
		
		if (eosListener != null)
		{
			this.pipeline.getBus().connect(new Bus.EOS() {
				@Override
				public void endOfStream(final GstObject source)
				{
					eosListener.endofStream();
				}
			});
			this.pipeline.getBus().connect(new Bus.ERROR() {
				
				@Override
				public void errorMessage(GstObject source, int code, String message)
				{
					eosListener.error(code, message);
				}
			});
		}
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
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#pause()
	 */
	@Override
	public void pause()
	{
		log.debug("Pausing " + this);
		this.pipeline.pause();
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
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PipelineBridge for " + this.pipeline;
	}
}

/**
 * Generic {@link Bus} message listener that forwards all messages to the logger.
 * 
 * @author thomas
 */
class BusMessageListener  
{
	/**
	 * Error message listener
	 */
	Bus.ERROR errorMessageListener = new Bus.ERROR() {
		
		@Override
		public void errorMessage(final GstObject source, final int code, final String message)
		{
			AbstractPipeline.log.error(source + ", " + code + ", " + message);		
		}
	};
	
	/**
	 * Info message listener
	 */
	Bus.INFO infoMessageListener = new Bus.INFO() {
		
		@Override
		public void infoMessage(final GstObject source, final int code, final String message)
		{
			AbstractPipeline.log.info(source + ", " + code + ", " + message);		
		}
	};
	
	/**
	 * Warn message listener
	 */
	Bus.WARNING warnMessageListener = new Bus.WARNING() {
		
		@Override
		public void warningMessage(final GstObject source, final int code, final String message)
		{
			AbstractPipeline.log.warn(source + ", " + code + ", " + message);		
		}
	};
	

	
}


