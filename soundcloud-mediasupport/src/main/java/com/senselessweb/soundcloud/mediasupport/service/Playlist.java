package com.senselessweb.soundcloud.mediasupport.service;

import java.util.List;

import com.senselessweb.soundcloud.domain.MediaSource;

/**
 * Represents a playlist
 * 
 * @author thomas
 */
public interface Playlist
{

	/**
	 * Jump to the next {@link MediaSource}. Is called when the 
	 * last {@link MediaSource} ended.
	 * 
	 * @return If there was a next song to jump to. 
	 */
	public boolean next();

	
	/**
	 * Jump to the previous {@link MediaSource}. 
	 * 
	 * @return If there was a previous song to jump to. 
	 */
	public boolean previous();
	
	
	/**
	 * Return the current playlist entry.
	 * 
	 * @return The current playlist entry. May be null if there 
	 * is no next entry.
	 */
	public MediaSource getCurrent();
	
	
	/**
	 * Adds a new entry to the playlist.
	 * 
	 * @param mediaSource The {@link MediaSource} to add.
	 */
	public void add(MediaSource mediaSource);
	
	
	/**
	 * Returns the current playlist entries.
	 * 
	 * @return A copy of the current playlist entries.
	 */
	public List<MediaSource> getAll();

	
	
}
