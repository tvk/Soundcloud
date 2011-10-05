package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.senselessweb.soundcloud.domain.sources.FileSource;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.domain.sources.StreamSource;
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
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(MediaPlayerTest.class);
	
	/**
	 * A demo file
	 */
	private MediaSource file;
	
	/**
	 * A second demo file
	 */
	private MediaSource wavFile;
	
	/**
	 * A demo internet radio stream
	 */
	private MediaSource url; 
	
	/**
	 * The media player to test
	 */
	private MediaPlayer mediaPlayer;
	
	/**
	 * Initialize the {@link MediaPlayer} and create some source files.
	 */
	@Before
	public void setUp() 
	{
		this.mediaPlayer = new MediaPlayerImpl();
		
		this.url = new StreamSource("Trance", "http://ubuntu.hbr1.com:19800/trance.ogg", new String[0]);
		this.file = new FileSource(new File("src/test/resources/soundfiles/test.mp3"));
		this.wavFile = new FileSource(new File("src/test/resources/soundfiles/test3.wav"));
	}
	
	/**
	 * Try to play some sources
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPlayDifferentSources() throws Exception
	{
		this.mediaPlayer.getCurrentPlaylist().add(this.file);
		this.mediaPlayer.getCurrentPlaylist().add(this.url);
		
		this.mediaPlayer.play();
		Thread.sleep(5000);
		
		this.mediaPlayer.next();
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
		this.mediaPlayer.getCurrentPlaylist().add(this.file);
		this.mediaPlayer.getCurrentPlaylist().add(this.url);
		this.mediaPlayer.play();
		Thread.sleep(5000);
		
		this.mediaPlayer.getVolumeControl().setVolume(0.5);
		Thread.sleep(5000);
		
		this.mediaPlayer.getVolumeControl().setVolume(0.1);
		Thread.sleep(5000);
		
		this.mediaPlayer.next();
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
		this.mediaPlayer.getCurrentPlaylist().add(this.url);
		this.mediaPlayer.play();
		Thread.sleep(5000);

		this.mediaPlayer.getEqualizer().setValue(Band.BAND5, -24.0);
		Thread.sleep(5000);
		
		this.mediaPlayer.getEqualizer().setValue(Band.BAND5, 12.0);
		Thread.sleep(5000);
	}
	
	/**
	 * Simple test of the playlist functionality
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testPlaylistSimple() throws Exception
	{
		this.mediaPlayer.getCurrentPlaylist().add(this.wavFile);
		this.mediaPlayer.getCurrentPlaylist().add(this.file);
		
		this.mediaPlayer.play();
		log.debug("Playlist: " + this.mediaPlayer.getCurrentPlaylist());
		Thread.sleep(10000);
		log.debug("Playlist: " + this.mediaPlayer.getCurrentPlaylist());
	}
	
	
	/**
	 * Test the playlist functionality
	 * 
	 * @throws Exception 
	 */
	@Test
	public void testPlaylist() throws Exception
	{
		this.mediaPlayer.getCurrentPlaylist().add(this.file);
		this.mediaPlayer.getCurrentPlaylist().add(this.wavFile);
		
		// Should play file
		this.mediaPlayer.play();
		log.debug("Playlist: " + this.mediaPlayer.getCurrentPlaylist());
		Thread.sleep(5000);
		
		// Jump to wavFile
		this.mediaPlayer.next();
		log.debug("Playlist: " + this.mediaPlayer.getCurrentPlaylist());
		Thread.sleep(2000);
		
		// Should do nothing
		this.mediaPlayer.next();
		log.debug("Playlist: " + this.mediaPlayer.getCurrentPlaylist());
		Thread.sleep(5000);
		
		// Should play file
		this.mediaPlayer.getCurrentPlaylist().add(this.file);
		log.debug("Playlist: " + this.mediaPlayer.getCurrentPlaylist());
		this.mediaPlayer.next();
		log.debug("Playlist: " + this.mediaPlayer.getCurrentPlaylist());
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
