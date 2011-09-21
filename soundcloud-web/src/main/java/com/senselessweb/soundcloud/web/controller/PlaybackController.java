package com.senselessweb.soundcloud.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

/**
 * Web controller interface for the playback control
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
	@ResponseStatus(HttpStatus.OK)
	public void play()
	{
		this.mediaPlayer.play();
	}

	/**
	 * Pauses the playback
	 */
	@RequestMapping("/pause") 
	@ResponseStatus(HttpStatus.OK)
	public void pause()
	{
		this.mediaPlayer.pause();
	}
	
	/**
	 * Stops the playback
	 */
	@RequestMapping("/stop") 
	@ResponseStatus(HttpStatus.OK)
	public void stop()
	{
		this.mediaPlayer.stop();
	}
	
	/**
	 * Jumps to the previous song
	 */
	@RequestMapping("/previous")
	@ResponseStatus(HttpStatus.OK)
	public void previous()
	{
		this.mediaPlayer.previous();
	}
	
	/**
	 * Jumps to the next song
	 */
	@RequestMapping("/next") 
	@ResponseStatus(HttpStatus.OK)
	public void next()
	{
		this.mediaPlayer.next();
	}
	
	
	
}
