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

import com.senselessweb.soundcloud.domain.library.LibraryItem;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.library.service.local.LocalLibraryService;
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
	 * The localLibraryService
	 */
	@Autowired LocalLibraryService localLibraryService;
	
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
		for (final MediaSource mediaSource : this.mediaPlayer.getCurrentPlaylist().getAll())
			result.add(new PlaylistContainer(mediaSource, this.localLibraryService.getFile(mediaSource), 
					mediaSource.equals(this.mediaPlayer.getCurrentPlaylist().getCurrent())));
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
		this.mediaPlayer.getCurrentPlaylist().gotoTitle(index);
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
		this.mediaPlayer.getCurrentPlaylist().remove(index);
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
	 * The media source
	 */
	private final MediaSource mediaSource;
	
	/**
	 * The libraryItem
	 */
	private final LibraryItem libraryItem;
	
	/**
	 * True if this is the current entry
	 */
	private final boolean isCurrent;
	
	/**
	 * Constructor
	 * 
	 * @param mediaSource The media source
	 * @param libraryItem The libraryItem
	 * @param isCurrent True if this is the current entry
	 */
	public PlaylistContainer(final MediaSource mediaSource, final LibraryItem libraryItem, final boolean isCurrent)
	{
		this.mediaSource = mediaSource;
		this.isCurrent = isCurrent;
		this.libraryItem = libraryItem;
	}
	
	/**
	 * @return The media source
	 */
	public MediaSource getMediaSource()
	{
		return this.mediaSource;
	}
	
	/**
	 * Returns the libraryItem
	 *
	 * @return The libraryItem
	 */
	public LibraryItem getLibraryItem()
	{
		return this.libraryItem;
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
		return this.libraryItem != null ? this.libraryItem.getShortTitle() : this.mediaSource.getTitle();
	}
}

