package com.senselessweb.soundcloud.web.controller.library;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.domain.library.LocalFile;
import com.senselessweb.soundcloud.domain.library.LocalFolder;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.library.service.local.LocalLibraryService;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.soundcloud.mediasupport.service.Playlist;

/**
 * Web controller interface for the local music library
 *
 * @author thomas
 */
@Controller
@RequestMapping("/library/local/*")
public class LocalLibraryController
{
	
	/**
	 * The localLibraryService
	 */
	private final LocalLibraryService localLibraryService;
	
	/**
	 * The mediaPlayer
	 */
	private final MediaPlayer mediaPlayer;

	/**
	 * The playlist
	 */
	private final Playlist playlist;
	
	@Autowired
	public LocalLibraryController(final LocalLibraryService localLibraryService,
		final MediaPlayer mediaPlayer, final Playlist playlist) 
	{
		this.localLibraryService = localLibraryService;
		this.mediaPlayer = mediaPlayer;
		this.playlist = playlist;
	}
		
	/**
	 * Returns the content of a folder
	 * 
	 * @param folder The folder
	 * 
	 * @return The content of that folder
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/getFolder")
	@ResponseBody
	public LocalFolder getFolder(@RequestParam(required=false) String folder) throws UnsupportedEncodingException
	{
		return this.localLibraryService.getFolder(folder != null ? java.net.URLDecoder.decode(folder, "UTF-8") : null);
	}

	/**
	 * Plays a file
	 * 
	 * @param file The file
	 */
	@RequestMapping("/playFile")
	@ResponseStatus(HttpStatus.OK)
	public void playFile(@RequestParam String file)
	{
		this.mediaPlayer.stop();
		this.playlist.set(this.localLibraryService.getFile(file).asMediaSources());
		this.mediaPlayer.play();
	}
	
	/**
	 * Plays all entries of a folder
	 * 
	 * @param folder The folder
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/playFolder")
	@ResponseStatus(HttpStatus.OK)
	public void playFolder(@RequestParam String folder) throws UnsupportedEncodingException
	{
		final List<MediaSource> playlist = new ArrayList<MediaSource>();
		for (final LocalFile file : this.localLibraryService.getFiles(java.net.URLDecoder.decode(folder, "UTF-8")))
			playlist.addAll(file.asMediaSources());
		
		this.mediaPlayer.stop();
		this.playlist.set(playlist);
		this.mediaPlayer.play();
	}

	/**
	 * Enqueues a file to the playlist
	 * 
	 * @param file The file
	 */
	@RequestMapping("/enqueueFile")
	@ResponseStatus(HttpStatus.OK)
	public void enqueueFile(@RequestParam String file)
	{
		for (final MediaSource mediaSource : this.localLibraryService.getFile(file).asMediaSources())
			this.playlist.add(mediaSource);
	}
	
	/**
	 * Enqueues all entries of a folder
	 * 
	 * @param folder The folder
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/enqueueFolder")
	@ResponseStatus(HttpStatus.OK)
	public void enqueueFolder(@RequestParam String folder) throws UnsupportedEncodingException
	{
		final List<MediaSource> playlist = new ArrayList<MediaSource>();		
		for (final LocalFile file : this.localLibraryService.getFiles(java.net.URLDecoder.decode(folder, "UTF-8")))
			playlist.addAll(file.asMediaSources());
			
		this.playlist.addAll(playlist);
	}
	
	
}
