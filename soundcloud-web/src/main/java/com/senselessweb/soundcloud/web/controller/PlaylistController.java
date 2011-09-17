package com.senselessweb.soundcloud.web.controller;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senselessweb.soundcloud.mediasupport.domain.MediaSource;
import com.senselessweb.soundcloud.mediasupport.domain.StreamSource;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

/**
 * Controls the playlist
 * 
 * @author thomas
 */
@Controller
@RequestMapping("/playlist/*")
public class PlaylistController
{

	/**
	 * The mediaplayer
	 */
	@Autowired MediaPlayer mediaPlayer;

	/**
	 * Adds an url to the playlist
	 * 
	 * @param url The url.
	 * 
	 * @throws MalformedURLException 
	 */
	@RequestMapping("/add")
	public void add(final @RequestParam String url) throws MalformedURLException
	{
		final MediaSource mediaSource = new StreamSource(new URL(url));
		this.mediaPlayer.getCurrentPlaylist().add(mediaSource);
	}
}
