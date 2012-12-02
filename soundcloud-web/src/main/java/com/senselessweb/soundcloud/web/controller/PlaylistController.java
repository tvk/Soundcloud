package com.senselessweb.soundcloud.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.soundcloud.mediasupport.service.Playlist;

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
	private final MediaPlayer mediaPlayer;

	/**
	 * The playlist
	 */
	private final Playlist playlist;
	
	@Autowired
	public PlaylistController(final MediaPlayer mediaPlayer, final Playlist playlist) 
	{
		this.mediaPlayer = mediaPlayer;
		this.playlist = playlist;
	}
	
	/**
	 * Returns the current playlist
	 * 
	 * @return The current playlist encapsulated in {@link PlaylistContainer}s. 
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public List<PlaylistContainer> getData()
	{
		final List<PlaylistContainer> result = new ArrayList<PlaylistContainer>();
		for (final MediaSource mediaSource : this.playlist.getAll())
			result.add(new PlaylistContainer(mediaSource.getTitle(), mediaSource.equals(this.playlist.getCurrent())));
		return result;
	}
	
	/**
	 * Jumps to the title specified by that index.
	 * 
	 * @param index The index.
	 */
	@RequestMapping("/gotoTitle")
	@ResponseStatus(HttpStatus.OK)
	public void gotoTitle(@RequestParam int index)
	{
		this.mediaPlayer.stop();
		this.playlist.gotoTitle(index);
		this.mediaPlayer.play();
	}
	
	/**
	 * Removes the title specified by that index.
	 * 
	 * @param index The index.
	 */
	@RequestMapping("/removeTitle")
	@ResponseStatus(HttpStatus.OK)
	public void removeTitle(@RequestParam int index)
	{
		this.playlist.remove(index);
	}
	
}

/**
 * Simple container for a playlist entry
 * 
 * @author thomas
 */
class PlaylistContainer
{
	
	/**
	 * The title
	 */
	private final String title;
	
	/**
	 * True if this is the current entry
	 */
	private final boolean isCurrent;
	
	/**
	 * Constructor
	 * 
	 * @param title The title
	 * @param isCurrent True if this is the current entry
	 */
	public PlaylistContainer(final String title, final boolean isCurrent)
	{
		this.title = title;
		this.isCurrent = isCurrent;
	}
	
	/**
	 * @return True if this is the current entry
	 */ 
	public boolean isCurrent()
	{
		return this.isCurrent;
	}
	
	/**
	 * Returns a title for the playlist.
	 * 
	 * @return The title.
	 */
	public String getTitle()
	{
		return this.title;
	}
}

