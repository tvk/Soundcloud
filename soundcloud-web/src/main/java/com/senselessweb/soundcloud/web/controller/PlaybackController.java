package com.senselessweb.soundcloud.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

/**
 * Controller for the playback control
 * 
 * @author thomas
 */
@Controller
@RequestMapping("/playback/*")
public class PlaybackController
{
	
	/**
	 * The mediaplayer
	 */
	@Autowired MediaPlayer mediaPlayer;

	/**
	 * Starts the playback
	 */
	@RequestMapping("/play")
	public void play()
	{
		this.mediaPlayer.play();
	}

	/**
	 * Pauses the playback
	 */
	@RequestMapping("/pause")
	public void pause()
	{
		this.mediaPlayer.pause();
	}
	
	/**
	 * Stops the playback
	 */
	@RequestMapping("/stop")
	public void stop()
	{
		this.mediaPlayer.stop();
	}
	
	/**
	 * Jumps to the previous song
	 */
	@RequestMapping("/previous")
	public void previous()
	{
		this.mediaPlayer.previous();
	}
	
	/**
	 * Jumps to the next song
	 */
	@RequestMapping("/next")
	public void next()
	{
		this.mediaPlayer.next();
	}
	
	
	
}
