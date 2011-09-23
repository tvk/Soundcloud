package com.senselessweb.soundcloud.web.controller;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.domain.FileSource;
import com.senselessweb.soundcloud.domain.MediaSource;
import com.senselessweb.soundcloud.domain.StreamSource;
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
	 * @param name 
	 * @param url The url.
	 * @param genres 
	 * 
	 * @throws MalformedURLException 
	 */
	@RequestMapping("/addUrl")
	@ResponseStatus(HttpStatus.OK)
	public void addUrl(final @RequestParam String name, final @RequestParam String url, final @RequestParam String genres) throws MalformedURLException
	{
		final MediaSource mediaSource = new StreamSource(name, url, StringUtils.isBlank(genres) ? null : genres.split(","));
		this.mediaPlayer.getCurrentPlaylist().add(mediaSource);
	}
	
	/**
	 * Adds a file to the playlist
	 * 
	 * @param file The file.
	 */
	@RequestMapping("/addFile")
	@ResponseStatus(HttpStatus.OK)
	public void addFile(final @RequestParam File file) 
	{
		final MediaSource mediaSource = new FileSource(file);
		this.mediaPlayer.getCurrentPlaylist().add(mediaSource);
	}
}
