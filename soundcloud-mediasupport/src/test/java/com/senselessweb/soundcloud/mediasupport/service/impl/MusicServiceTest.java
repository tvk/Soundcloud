package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.io.File;
import java.util.concurrent.Executors;

import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.gstreamer.elements.DecodeBin;
import org.gstreamer.elements.DecodeBin2;
import org.junit.Test;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;


public class MusicServiceTest
{
	
	@Test
	public void playPipe() throws Exception
	{
		Gst.init();
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			
			public void run()
			{
				Gst.main();
			}
		});
		
		Pipeline pipe = new Pipeline();
		
		final Element source = ElementFactory.make("filesrc", "filesrc");
		source.set("location", "/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test.mp3");
		
		final Element decoder = ElementFactory.make("mad", "mad");
		final Element audioconvert = ElementFactory.make("audioconvert", "audioconvert");
		final Element equalizer = ElementFactory.make("equalizer-10bands", "equalizer");
		final Element volume = ElementFactory.make("volume", "volume");
		final Element sink = ElementFactory.make("alsasink", "alsasink");
		
		
		
		pipe.addMany(source, decoder, audioconvert, equalizer, volume, sink);
		
		if (!Element.linkMany(source, decoder, audioconvert, equalizer, volume, sink)) 
		{
            throw new RuntimeException("failed to link pipeline elements");
        }
		
		pipe.play();
		
		Thread.sleep(5000);
		volume.set("volume", 0.1);
		
		Thread.sleep(5000);
		volume.set("volume", 1.0);
		
		Thread.sleep(5000);
		equalizer.set("band5", -24.0);
		
		Thread.sleep(5000);
		equalizer.set("band5", 12.0);
		
		Thread.sleep(5000);
		pipe.stop();
		
		/*
		source.set("location", "/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test3.wav");
		pipe.play();
		Thread.sleep(5000);
		*/
	}
	
	@Test
	public void playPipe2() throws Exception
	{
		Gst.init();
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			
			public void run()
			{
				Gst.main();
			}
		});

        Pipeline pipeline;

    
        pipeline = new Pipeline("Processing pipeline");

        Element source = ElementFactory.make("filesrc", "src");
        Element decoder = ElementFactory.make("vorbisdec", "vorbisdec");
		Element audioconvert = ElementFactory.make("audioconvert", "audioconvert");
		Element equalizer = ElementFactory.make("equalizer-10bands", "equalizer");
        Element sink = ElementFactory.make("alsasink", "audiosink");


        source.set("location", "/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test.mp3");

        pipeline.addMany(source, decoder, audioconvert, equalizer, sink);
        if (!Element.linkMany(source, decoder, audioconvert, equalizer, sink)) {
            throw new RuntimeException("failed to link pipeline elements");
        }
    

        pipeline.play();
        Thread.sleep(5000);
        pipeline.setState(State.NULL);
	}
	
	@Test
	public void playPipe3() throws Exception
	{
		Gst.init();


        Pipeline pipeline;

        boolean I_want_to_do_it_the_hard_way = true;

        if (I_want_to_do_it_the_hard_way) {
           pipeline = new Pipeline("Processing pipeline");

            Element source = ElementFactory.make("filesrc", "src");
            Element decoder = ElementFactory.make("decodebin", "decoder");
            Element sink = ElementFactory.make("pulsesink", "audiosink");


            source.set("location", "/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test.mp3");

            pipeline.addMany(source, decoder);
            
            if (!source.link(decoder)) {
                throw new RuntimeException("failed to link pipeline elements");
            }

            pipeline.setState(State.PAUSED);

            sink.setState(State.PAUSED);
            
            pipeline.add(sink);

            if (!decoder.link(sink)) {
                throw new RuntimeException("failed to link pipeline elements");
            }
        } else {
            pipeline = Pipeline.launch("filesrc name=src ! decodebin ! pulsesink");
            pipeline.getElementByName("src").set("location", "/tmp/heli.flac");
        }


        pipeline.setState(State.PLAYING);
        Gst.main();
        pipeline.setState(State.NULL);
	}

	@Test
	public void playAnMp3() throws Exception
	{
		MediaPlayer player = new MediaPlayerImpl();
		
		player.play(new File("/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test4.ogg"));
		Thread.sleep(10000);
		
		player.play(new File("/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test.mp3"));	
		Thread.sleep(10000);

		player.play(new File("/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test2.mp3"));
		Thread.sleep(10000);

	}
	
	@Test
	public void playPipe4()
	{
		Gst.init();


        Pipeline pipeline;

        boolean I_want_to_do_it_the_hard_way = true;

        if (I_want_to_do_it_the_hard_way) {
           pipeline = new Pipeline("Processing pipeline");

            Element source = ElementFactory.make("filesrc", "src");
            Element decoder = ElementFactory.make("decodebin", "decoder");
            Element sink = ElementFactory.make("pulsesink", "audiosink");


            source.set("location", "/home/thomas/development/eclipse-workspace/soundcloud-mediasupport/src/test/resources/soundfiles/test4.ogg");

            pipeline.addMany(source, decoder);
            
            if (!source.link(decoder)) {
                throw new RuntimeException("failed to link pipeline elements");
            }

            pipeline.setState(State.PAUSED);

            sink.setState(State.PAUSED);
            
            pipeline.add(sink);

            if (!decoder.link(sink)) {
                throw new RuntimeException("failed to link pipeline elements");
            }
        } else {
            pipeline = Pipeline.launch("filesrc name=src ! decodebin ! pulsesink");
            pipeline.getElementByName("src").set("location", "/tmp/heli.flac");
        }


        pipeline.setState(State.PLAYING);
        Gst.main();
        pipeline.setState(State.NULL);
	}
}
