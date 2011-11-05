package com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Bus;
import org.gstreamer.Element;
import org.gstreamer.Format;
import org.gstreamer.GstObject;
import org.gstreamer.Pipeline;
import org.gstreamer.Tag;
import org.gstreamer.TagList;

import com.google.common.collect.Sets;
import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.PanoramaBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.mediasupport.service.MessageListenerService;
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
	 * The volume control
	 */
	private final VolumeBridge volume;
	
	/**
	 * The equalizer control
	 */
	private final EqualizerBridge equalizer;
	
	/**
	 * The audiopanorama control
	 */
	private final PanoramaBridge panoramaBridge;
	
	/**
	 * The messageListener
	 */
	final MessageListenerService messageListener;
	
	/**
	 * The duration of this pipeline.
	 */
	private long duration = -1;
	
	/**
	 * Constructor
	 * 
	 * @param pipeline The {@link Pipeline} to use.
	 * @param volume The {@link VolumeControl}.
	 * @param equalizer The {@link EqualizerBridge}.
	 * @param panoramaBridge The {@link PanoramaBridge}. 
	 * @param messageListener The {@link MessageListenerService}.
	 */
	public AbstractPipeline(final Pipeline pipeline, final VolumeBridge volume, final EqualizerBridge equalizer, 
			final PanoramaBridge panoramaBridge, final MessageListenerService messageListener)
	{
		this.pipeline = pipeline;
		this.messageListener = messageListener;
		
		this.volume = volume;
		this.volume.initElement(this.pipeline.getElementByName("volume"));
		
		this.equalizer = equalizer;
		this.equalizer.initElement(this.pipeline.getElementByName("equalizer"));
		
		this.panoramaBridge = panoramaBridge;
		this.panoramaBridge.initElement(this.pipeline.getElementByName("audiopanorama"));
		
		final BusMessageListener busMessageListener = new BusMessageListener(messageListener);
		this.pipeline.getBus().connect(busMessageListener.errorMessageListener);
		this.pipeline.getBus().connect(busMessageListener.warnMessageListener);
		this.pipeline.getBus().connect(busMessageListener.infoMessageListener);
		this.pipeline.getBus().connect(busMessageListener.tagMessageListener);
		this.pipeline.getBus().connect(busMessageListener.eosMessageListener);
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
		final String pipeline = src + " name=src ! " +
				"decodebin2 buffer-duration=3 ! " +
				"audioconvert ! " +
				"equalizer-10bands name=equalizer ! " +
				"audiopanorama name=audiopanorama ! " +
				"volume name=volume ! " +
				"alsasink";
		
		log.debug("Creating pipeline: " + pipeline);
		return Pipeline.launch(pipeline);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#play()
	 */
	@Override
	public void play()
	{
		final org.gstreamer.State previousState = this.pipeline.getState();
		if (previousState == org.gstreamer.State.PLAYING) return;
		
		log.debug("Playing " + this);
		this.pipeline.play();
		
		if (previousState != org.gstreamer.State.PAUSED) this.queryDuration();
	}

	/**
	 * Queries the duration and notifies the listeners. Should be called after pipeline.play() is called. 
	 */
	private void queryDuration()
	{
		final long startTime = System.currentTimeMillis();
		while (true)
		{
			final long duration = AbstractPipeline.this.pipeline.queryDuration(TimeUnit.SECONDS); 
			if (duration > 0)
			{
				// If duration is successfully found, notify the listeners and break
				this.duration = duration;
				AbstractPipeline.this.messageListener.durationChanged(duration);
				break;
			}
			else if (System.currentTimeMillis() > startTime + 1000)
			{
				// .. but wait outmost one second
				break;
			}
			
			try
			{
				Thread.sleep(20);
			} 
			catch (final InterruptedException e)
			{
				throw new RuntimeException("Error while querying duration", e);
			}
		}
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
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#getDuration()
	 */
	@Override
	public long getDuration()
	{
		return this.duration;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#getPosition()
	 */
	@Override
	public long getPosition()
	{
		if (this.getState() == State.STOPPED) return -1;
		
		final long startTime = System.currentTimeMillis();
		while (this.pipeline.queryPosition(Format.TIME) == -1 && System.currentTimeMillis() > startTime + 1000)
		{
			try
			{
				Thread.sleep(5);
			} catch (final InterruptedException e) {
				throw new RuntimeException("Error while querying duration", e);
			}
		}
		return this.pipeline.queryPosition(TimeUnit.SECONDS);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#gotoPosition(long)
	 */
	@Override
	public void gotoPosition(final long position)
	{
		this.pipeline.seek(position, TimeUnit.SECONDS);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge#getState()
	 */
	@Override
	public State getState()
	{
		switch (this.pipeline.getState())
		{
			case PAUSED: return State.PAUSED;
			case PLAYING: return State.PLAYING;
			default: return State.STOPPED;
		}
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
	 * The messageListener
	 */
	final MessageListenerService messageListener;
	
	/**
	 * Constructor
	 * 
	 * @param messageListener The {@link MessageListenerService} to redirect some messages to.
	 */
	BusMessageListener(final MessageListenerService messageListener)
	{
		this.messageListener = messageListener;
	}
	
	/**
	 * End of stream message listener
	 */
	Bus.EOS eosMessageListener = new Bus.EOS() {
		
		@Override
		public void endOfStream(final GstObject source)
		{
			AbstractPipeline.log.debug("EOS: " + source);
			BusMessageListener.this.messageListener.endOfStream();
		}
	};
	
	/**
	 * Error message listener
	 */
	Bus.ERROR errorMessageListener = new Bus.ERROR() {
		
		@Override
		public void errorMessage(final GstObject source, final int code, final String message)
		{
			AbstractPipeline.log.error(source + ", " + code + ", " + message);
			BusMessageListener.this.messageListener.error(message);
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
	
	/**
	 * Tag message listener
	 */
	Bus.TAG tagMessageListener = new Bus.TAG() {
		
		/**
		 * The tags that should be sent to the message listener
		 */
		private final Set<String> tagsToFilter = Sets.newHashSet(
				Tag.ARTIST.getId(), Tag.TITLE.getId(), Tag.ALBUM.getId(), 
				Tag.GENRE.getId(), Tag.BITRATE.getId());
		
		
		@Override
		public void tagsFound(final GstObject source, final TagList tagList)
		{			
			AbstractPipeline.log.info("Tags found : " + StringUtils.abbreviate(tagList.toString(), 400));		
			
			for (final String tag: Sets.intersection(this.tagsToFilter, new HashSet<String>(tagList.getTagNames())))
			{
				final Object o = tagList.getValue(tag);
				BusMessageListener.this.messageListener.tag(tag, o.toString());
			}
		}
	};
	
}


