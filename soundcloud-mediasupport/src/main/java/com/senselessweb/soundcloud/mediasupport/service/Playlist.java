package com.senselessweb.soundcloud.mediasupport.service;

import java.util.Collection;
import java.util.List;

import com.senselessweb.soundcloud.domain.sources.MediaSource;

/**
 * Represents a playlist
 * 
 * @author thomas
 */
public interface Playlist
{
	
	/**
	 * Events that are fired when the playlist changes
	 * 
	 * @see MessageListener 
	 *
	 * @author thomas
	 */
	public enum ChangeEvent
	{
		/** Current playlist index has changed */
		CURRENT_CHANGED,

		/** All other events */
		OTHER
	}

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
	 * Jumps to the title with the given index.
	 * 
	 * @param index The index to jump to.
	 */
	public void gotoTitle(int index);

	
	/**
	 * Removes the title with the given index.
	 * 
	 * @param index The index to remove.
	 */
	public void remove(int index);
	
	
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
	 * Adds all the given media sources to the playlist.
	 * 
	 * @param playlist The {@link MediaSource}s to add.
	 */
	public void addAll(Collection<MediaSource> playlist);

	
	/**
	 * Replaces the current playlist by the given sources.
	 * 
	 * @param playlist The new playlist entries.
	 */
	public void set(Collection<? extends MediaSource> playlist);
	
	
	/**
	 * Returns the current playlist entries.
	 * 
	 * @return A copy of the current playlist entries.
	 */
	public List<MediaSource> getAll();
	
}
