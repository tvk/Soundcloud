package com.senselessweb.soundcloud.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

