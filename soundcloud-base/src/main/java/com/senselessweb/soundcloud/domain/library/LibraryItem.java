package com.senselessweb.soundcloud.domain.library;

import java.util.Collection;

import com.senselessweb.soundcloud.domain.sources.MediaSource;

/**
 * Base interface for all library items.
 * 
 * @author thomas
 */
public interface LibraryItem
{

	/**
	 * Returns a collection of {@link MediaSource}s for this library 
	 * item (i.e. the items of an album or the single files of a playlist)
	 *  
	 * @return The {@link MediaSource}s. 
	 */
	public Collection<? extends MediaSource> asMediaSources();
	
	/**
	 * Returns the genres of this item.
	 * 
	 * @return The genres.
	 */
	public Collection<String> getGenres();
	
	/**
	 * Returns the id of this item.
	 * 
	 * @return The id.
	 */
	public String getId();
	
	/**
	 * Returns a long title for this item (contains for example the
	 * tracknumber and the artist)
	 * 
	 * @return The long title.
	 */
	public String getLongTitle();
	
	/**
	 * Returns a short title for this item.
	 * 
	 * @return The short title.
	 */
	public String getShortTitle();

	
	/**
	 * Returns a collection of keywords for this item.
	 * 
	 * @return A collection of keywords.
	 */
	public Collection<String> getKeywords();
	
}
