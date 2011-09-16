package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.util.concurrent.Executors;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.junit.Test;


public class MediaFormatsTest
{

	@Test
	public void playOgg() throws Exception
	{
		
		// WOW Somehow works with all formsts
		
		Gst.init();
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			
			public void run()
			{
				Gst.main();
			}
		});

		Pipeline pipeline = Pipeline.launch("filesrc name=src ! decodebin ! equalizer-10bands name=equalizer ! alsasink");
        pipeline.getElementByName("src").set("location", 
			"/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test2.mp3");

        pipeline.play();
        
        Thread.sleep(5000);
        System.out.println(pipeline.queryPosition());
        pipeline.stop();
        pipeline.getElementByName("src").set("location", 
			"/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test.mp3");
        pipeline.play();
        
        Thread.sleep(5000);
        System.out.println("Equalizer -> -24.0");
        pipeline.getElementByName("equalizer").set("band5", -24.0);
        
        Thread.sleep(5000);
        System.out.println("Equalizer -> 12.0");
        pipeline.getElementByName("equalizer").set("band5", 12.0);
        
        Thread.sleep(5000);
        pipeline.stop();
        pipeline.getElementByName("src").set("location", 
			"/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test4.ogg");
        pipeline.play();
        
        Thread.sleep(5000);
        pipeline.setState(State.NULL);
	}
	

	
}
