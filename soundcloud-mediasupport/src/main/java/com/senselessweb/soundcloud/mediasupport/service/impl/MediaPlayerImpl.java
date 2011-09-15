package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.io.File;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Gst;

import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

public class MediaPlayerImpl implements MediaPlayer
{

	private static final Log log = LogFactory.getLog(MediaPlayerImpl.class);
	
	private PipelineBridge pipeline; 
	
	public MediaPlayerImpl()
	{
		this.init();
		this.pipeline = new PipelineBridge();
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


	public synchronized void play(final File file)
	{
		if (!file.isFile()) throw new IllegalArgumentException("File " + file + " does not exist.");
		this.pipeline.play(file);
	}
	
	public synchronized void stop()
	{
		//if (this.pipeline != null) this.pipeline.stop();
	}
	
	public void setVolume(final double volume)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
