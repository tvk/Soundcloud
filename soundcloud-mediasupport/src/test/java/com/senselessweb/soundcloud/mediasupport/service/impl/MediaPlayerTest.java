package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;


public class MediaPlayerTest
{

	@Test
	public void testPlayDifferentSources() throws Exception
	{
		final MediaPlayer mediaPlayer = new MediaPlayerImpl();

		final File file = new File("src/test/resources/soundfiles/test.mp3");
		mediaPlayer.play(file);
		Thread.sleep(5000);
		mediaPlayer.getVolumeControl().setVolume(0.1);
		Thread.sleep(5000);
		
		final URL url = new URL("http://ubuntu.hbr1.com:19800/trance.ogg");
		mediaPlayer.play(url);
		Thread.sleep(5000);
		mediaPlayer.getVolumeControl().setVolume(1.0);
		Thread.sleep(5000);
	}
}
