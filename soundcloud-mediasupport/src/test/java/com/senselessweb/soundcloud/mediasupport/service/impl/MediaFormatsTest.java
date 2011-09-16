package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.gstreamer.Bus;
import org.gstreamer.Format;
import org.gstreamer.Gst;
import org.gstreamer.GstObject;
import org.gstreamer.Message;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.junit.Before;
import org.junit.Test;


public class MediaFormatsTest
{
	
	@Before
	public void before()
	{
		Gst.init();
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			
			public void run()
			{
				Gst.main();
			}
		});		
	}

	@Test
	public void playOgg() throws Exception
	{
		
		// WOW Somehow works with all formsts
		

		Pipeline pipeline = Pipeline.launch("souphttpsrc name=src ! decodebin ! audioconvert ! equalizer-10bands name=equalizer ! alsasink");
        pipeline.getElementByName("src").set("location", 
			"http://ubuntu.hbr1.com:19800/trance.ogg");

        pipeline.play();
        
        Thread.sleep(15000);
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
	
	@Test
	public void tryToRecognizeEndOfFile() throws Exception
	{
		Pipeline pipeline = Pipeline.launch("filesrc name=src ! decodebin ! audioconvert ! equalizer-10bands name=equalizer ! alsasink");
        pipeline.getElementByName("src").set("location", 
			"/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test3.wav");

        pipeline.getBus().connect(new Bus.MESSAGE() {
			
			public void busMessage(Bus bus, Message message)
			{
				System.out.println("" + bus + message.getStructure());
				
			}
		});
        pipeline.getBus().connect(new Bus.EOS() {
			
			public void endOfStream(GstObject source)
			{
				System.out.println(source);
			}
		});
        pipeline.play();
        Thread.sleep(10000);
	}
	
	@Test
	public void jumpForwardAndBackward() throws Exception
	{
		Pipeline pipeline = Pipeline.launch("filesrc name=src ! decodebin ! audioconvert ! equalizer-10bands name=equalizer ! alsasink");
        pipeline.getElementByName("src").set("location", 
			"/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test2.mp3");
		
        pipeline.play();
        Thread.sleep(5000);
        
        System.out.println(pipeline.queryPosition());
        System.out.println(pipeline.queryDuration());
        
        pipeline.seek(60, TimeUnit.SECONDS);

        Thread.sleep(5000);
        
        System.out.println(pipeline.queryPosition());
        System.out.println(pipeline.queryDuration());

        pipeline.seek(30, TimeUnit.SECONDS);

        Thread.sleep(5000);
	}
	

	
}