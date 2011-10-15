package com.senselessweb.soundcloud.domain.sources;

/**
 * Represents a media source. A media source is something that can be 
 * played by the media player.
 * 
 * A media source usually contains only informations that are necessary 
 * for the playback and the playlist.
 * 
 * @author thomas
 */
public interface MediaSource
{
	
	/**
	 * Returns a title for this media source.
	 * 
	 * @return The title.
	 */
	public String getTitle();
	
}
