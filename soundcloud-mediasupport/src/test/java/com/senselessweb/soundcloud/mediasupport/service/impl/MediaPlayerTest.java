package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.senselessweb.soundcloud.mediasupport.service.Equalizer.Band;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;


/**
 * Testcases for the {@link MediaPlayer}.
 * 
 * @author thomas
 */
public class MediaPlayerTest
{
	
	/**
	 * A demo file
	 */
	private File file;
	
	/**
	 * A demo internet radio stream
	 */
	private URL url; 
	
	/**
	 * The media player to test
	 */
	private MediaPlayer mediaPlayer;
	
	/**
	 * Initialize the {@link MediaPlayer} and create some source files.
	 * 
	 * @throws MalformedURLException
	 */
	@Before
	public void setUp() throws MalformedURLException
	{
		this.mediaPlayer = new MediaPlayerImpl();
		
		this.url = new URL("http://ubuntu.hbr1.com:19800/trance.ogg");
		this.file = new File("src/test/resources/soundfiles/test.mp3");
	}
	
	/**
	 * Try to play some sources
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPlayDifferentSources() throws Exception
	{
		this.mediaPlayer.play(this.file);
		Thread.sleep(5000);
		
		this.mediaPlayer.play(this.url);
		Thread.sleep(5000);
	}

	/**
	 * Try to set the volume
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetVolume() throws Exception
	{
		this.mediaPlayer.play(this.file);
		Thread.sleep(5000);
		
		this.mediaPlayer.getVolumeControl().setVolume(0.1);
		Thread.sleep(5000);
		
		this.mediaPlayer.getVolumeControl().setVolume(1.0);
		Thread.sleep(5000);
	}
	
	/**
	 * Try to use the equalizer
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEqualizer() throws Exception
	{
		this.mediaPlayer.play(this.url);
		Thread.sleep(5000);

		this.mediaPlayer.getEqualizer().setValue(Band.BAND5, -24.0);
		Thread.sleep(5000);
		
		this.mediaPlayer.getEqualizer().setValue(Band.BAND5, 12.0);
		Thread.sleep(5000);
	}
	
	/**
	 * Shut the mediaplayer down
	 */
	@After
	public void tearDown()
	{
		this.mediaPlayer.shutdown();
	}
}
