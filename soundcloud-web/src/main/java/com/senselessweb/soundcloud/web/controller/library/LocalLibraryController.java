package com.senselessweb.soundcloud.web.controller.library;

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
	@Autowired LocalLibraryService localLibraryService;
	
	/**
	 * The mediaPlayer
	 */
	@Autowired MediaPlayer mediaPlayer;

	/**
	 * Returns the content of a folder
	 * 
	 * @param folder The folder
	 * 
	 * @return The content of that folder
	 */
	@RequestMapping("/getFolder")
	@ResponseBody
	public LocalFolder getFolder(@RequestParam(required=false) String folder)
	{
		return this.localLibraryService.getFolder(folder);
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
		this.mediaPlayer.getCurrentPlaylist().set(this.localLibraryService.getFile(file).asMediaSources());
		this.mediaPlayer.play();
	}
	
	/**
	 * Plays all entries of a folder
	 * 
	 * @param folder The folder
	 */
	@RequestMapping("/playFolder")
	@ResponseStatus(HttpStatus.OK)
	public void playFolder(@RequestParam String folder)
	{
		final List<MediaSource> playlist = new ArrayList<MediaSource>();
		for (final LocalFile file : this.localLibraryService.getFiles(folder))
			playlist.addAll(file.asMediaSources());
		
		this.mediaPlayer.stop();
		this.mediaPlayer.getCurrentPlaylist().set(playlist);
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
			this.mediaPlayer.getCurrentPlaylist().add(mediaSource);
	}
	
	/**
	 * Enqueues all entries of a folder
	 * 
	 * @param folder The folder
	 */
	@RequestMapping("/enqueueFolder")
	@ResponseStatus(HttpStatus.OK)
	public void enqueueFolder(@RequestParam String folder)
	{
		final List<MediaSource> playlist = new ArrayList<MediaSource>();		
		for (final LocalFile file : this.localLibraryService.getFiles(folder))
			playlist.addAll(file.asMediaSources());
			
		this.mediaPlayer.getCurrentPlaylist().addAll(playlist);
	}
	
	
}
