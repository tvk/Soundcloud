package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.io.File;
import java.net.URL;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;

import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.VolumeBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline.FileSrcPipeline;
import com.senselessweb.soundcloud.mediasupport.gstreamer.pipeline.StreamSourcePipeline;
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
			
			public void run()
			{
				log.debug("Calling Gst.main()");
				Gst.main();
			}
		});
	}


	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#play(java.io.File)
	 */
	public synchronized void play(final File file)
	{
		if (file == null || !file.isFile()) throw new IllegalArgumentException("File " + file + " does not exist.");
		
		this.stop();
		this.pipeline = new FileSrcPipeline(file, this.volume);
		this.pipeline.play();
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#play(java.net.URL)
	 */
	public synchronized void play(final URL url)
	{
		if (url == null) throw new IllegalArgumentException("url must not be null.");
		
		this.stop();
		this.pipeline = new StreamSourcePipeline(url, this.volume);
		this.pipeline.play();
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#stop()
	 */
	public synchronized void stop()
	{
		if (this.pipeline != null) this.pipeline.stop();
	}

	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getVolumeControl()
	 */
	public synchronized VolumeControl getVolumeControl()
	{
		return this.volume;
	}
	
}
