package com.senselessweb.soundcloud.mediasupport.gstreamer;

import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Gst;

/**
 * Static support methods to control the gstreamer framework
 * 
 * @author thomas
 */
public class GstreamerSupport
{
	/**
	 * Logger
	 */
	static final Log log = LogFactory.getLog(GstreamerSupport.class);
	
	
	/**
	 * Initializes the gstreamer media framework
	 */
	public static void initGst()
	{
		log.debug("Calling Gst.init()");
		Gst.init("MediaPlayer", new String[0]);
		log.debug("Gst initialized: " + Gst.getVersionString());
		
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
	 * Shutdown the gstreamer media framework
	 */	
	public static void shutdown()
	{
		Gst.quit();
	}
}
