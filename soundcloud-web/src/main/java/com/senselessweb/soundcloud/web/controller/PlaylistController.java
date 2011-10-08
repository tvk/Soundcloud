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
			result.add(new PlaylistContainer(mediaSource, mediaSource.equals(this.mediaPlayer.getCurrentPlaylist().getCurrent())));
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
	 * True if this is the current entry
	 */
	private final boolean isCurrent;
	
	/**
	 * Constructor
	 * 
	 * @param mediaSource The media source
	 * @param isCurrent True if this is the current entry
	 */
	public PlaylistContainer(final MediaSource mediaSource, final boolean isCurrent)
	{
		this.mediaSource = mediaSource;
		this.isCurrent = isCurrent;
	}
	
	/**
	 * @return The media source
	 */
	public MediaSource getMediaSource()
	{
		return this.mediaSource;
	}
	
	/**
	 * @return True if this is the current entry
	 */ 
	public boolean isCurrent()
	{
		return this.isCurrent;
	}
}

