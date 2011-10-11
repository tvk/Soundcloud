package com.senselessweb.soundcloud.mediasupport.gstreamer;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Gst;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service that controls the init and shutdown procedure of the media player.
 * 
 * @author thomas
 */
@Service
public class GstreamerSupportService implements ApplicationListener<ContextRefreshedEvent>
{
	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(GstreamerSupportService.class);
	

	/**
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	@Async public void onApplicationEvent(final ContextRefreshedEvent event)
	{
		log.debug("Calling Gst.init()");
		Gst.init("MediaPlayer", new String[0]);
		log.debug("Gst initialized: " + Gst.getVersionString());
		
		log.debug("Calling Gst.main()");
		Gst.main();
		log.debug("Gst.main() terminated");
	}

	
	/**
	 * Shutdown the gstreamer media framework
	 */	
	@PreDestroy public void shutdown()
	{
		Gst.deinit();
		Gst.quit();
	}

}
