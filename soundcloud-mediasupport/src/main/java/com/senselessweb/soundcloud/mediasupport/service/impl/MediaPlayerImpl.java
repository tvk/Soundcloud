package com.senselessweb.soundcloud.mediasupport.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Pipeline;

import com.senselessweb.soundcloud.mediasupport.domain.MediaSource;
import com.senselessweb.soundcloud.mediasupport.gstreamer.EndOfStreamListener;
import com.senselessweb.soundcloud.mediasupport.gstreamer.GstreamerSupport;
import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline.PipelineFactory;
import com.senselessweb.soundcloud.mediasupport.service.Equalizer;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.soundcloud.mediasupport.service.Playlist;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * {@link MediaPlayer} implementation based on a gstreamer {@link Pipeline}.
 * 
 * @author thomas
 */
public class MediaPlayerImpl implements MediaPlayer, EndOfStreamListener
{

	/**
	 * Logger
	 */
	static final Log log = LogFactory.getLog(MediaPlayerImpl.class);
	
	/**
	 * The current playlist
	 */
	private Playlist playlist = new DefaultPlaylist();
	
	/**
	 * The currently used pipeline. Is rebuilt everytime a new song is played.  
	 */
	private PipelineBridge pipeline; 
	
	/**
	 * The volume brigde. Is reused for every build pipeline.
	 */
	private final VolumeBridge volume = new VolumeBridge();
	
	/**
	 * The equalizer brigde. Is reused for every build pipeline.
	 */
	private final EqualizerBridge equalizer = new EqualizerBridge();
	
	
	/**
	 * Constructor
	 */
	public MediaPlayerImpl()
	{
		GstreamerSupport.initGst();
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#play()
	 */
	@Override
	public synchronized void play()
	{
		if (this.pipeline == null)
		{
			final MediaSource next = this.playlist.getNext();
			if (next != null) this.pipeline = PipelineFactory.createPipeline(next, this.volume, this.equalizer, this);
		}
		
		if (this.pipeline != null) this.pipeline.play();
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#stop()
	 */
	@Override
	public synchronized void stop()
	{
		if (this.pipeline != null) this.pipeline.stop();
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#pause()
	 */
	@Override
	public synchronized void pause()
	{
		if (this.pipeline != null) this.pipeline.pause();
	}
	

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#next()
	 */
	@Override
	public void next()
	{
		final MediaSource next = this.playlist.getNext();
		if (next != null)
		{
			log.debug("Starting next source: " + next);
			if (this.pipeline != null) this.pipeline.stop();
			this.pipeline = PipelineFactory.createPipeline(next, this.volume, this.equalizer, this);
			this.pipeline.play();
		}
		else
		{
			log.debug("next() was called, but no next song available");
		}
	}	
	

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.gstreamer.EndOfStreamListener#streamEnded()
	 */
	@Override
	public void streamEnded()
	{
		this.pipeline = null;
		this.next();
	}	

	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getCurrentPlaylist()
	 */
	@Override
	public Playlist getCurrentPlaylist()
	{
		return this.playlist;
	}

	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getVolumeControl()
	 */
	@Override
	public VolumeControl getVolumeControl()
	{
		return this.volume;
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getEqualizer()
	 */
	@Override
	public Equalizer getEqualizer()
	{
		return this.equalizer;
	}

	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#shutdown()
	 */
	@Override
	public synchronized void shutdown()
	{
		log.debug("Shutdown called");
		if (this.pipeline != null) this.pipeline.stop();
		GstreamerSupport.shutdown();
	}
}
