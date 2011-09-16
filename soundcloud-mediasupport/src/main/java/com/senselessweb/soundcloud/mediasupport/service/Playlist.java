package com.senselessweb.soundcloud.mediasupport.service;

import com.senselessweb.soundcloud.mediasupport.domain.MediaSource;

/**
 * Represents a playlist
 * 
 * @author thomas
 */
public interface Playlist
{

	/**
	 * Adds a {@link MediaSource} to the end of the playlist
	 * 
	 * @param mediaSource The {@link MediaSource} to add. Must not be null.
	 */
	public void add(final MediaSource mediaSource);
	
	
	/**
	 * Returns the next {@link MediaSource} to play.
	 * 
	 * @return The next {@link MediaSource} to play. May be null.
	 */
	public MediaSource getNext();
}
