package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.io.File;
import java.net.URL;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.EqualizerBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline.FileSrcPipeline;
import com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline.StreamSourcePipeline;
import com.senselessweb.soundcloud.mediasupport.service.Equalizer;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.soundcloud.mediasupport.service.VolumeControl;

/**
 * {@link MediaPlayer} implementation based on a gstreamer {@link Pipeline}.
 * 
 * @author thomas
 */
public class MediaPlayerImpl implements MediaPlayer
{

	/**
	 * Logger
	 */
	static final Log log = LogFactory.getLog(MediaPlayerImpl.class);
	
	/**
	 * The currently used pipeline  
	 */
	private PipelineBridge pipeline; 
	
	/**
	 * The volume brigde
	 */
	private final VolumeBridge volume = new VolumeBridge();
	
	/**
	 * The equalizer brigde
	 */
	private final EqualizerBridge equalizer = new EqualizerBridge();
	
	
	/**
	 * Constructor
	 */
	public MediaPlayerImpl()
	{
		this.init();
	}

	/**
	 * Initializes the gstreamer media framework
	 */
	private void init()
	{
		log.debug("Calling Gst.init()");
		Gst.init("MediaPlayer", new String[0]);
		
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			
			@Override
			public void run()
			{
				log.debug("Calling Gst.main()");
				Gst.main();
				log.debug("Gst.main() terminated");
			}
		});
	}


	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#play(java.io.File)
	 */
	@Override
	public synchronized void play(final File file)
	{
		if (file == null || !file.isFile()) throw new IllegalArgumentException("File " + file + " does not exist.");
		
		this.stop();
		this.pipeline = new FileSrcPipeline(file, this.volume, this.equalizer);
		this.pipeline.play();
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#play(java.net.URL)
	 */
	@Override
	public synchronized void play(final URL url)
	{
		if (url == null) throw new IllegalArgumentException("url must not be null.");
		
		this.stop();
		this.pipeline = new StreamSourcePipeline(url, this.volume, this.equalizer);
		this.pipeline.play();
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
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getVolumeControl()
	 */
	@Override
	public synchronized VolumeControl getVolumeControl()
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
		Gst.quit();
	}
	
}
