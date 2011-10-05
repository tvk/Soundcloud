package com.senselessweb.soundcloud.library.domain.radio;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.senselessweb.soundcloud.domain.MediaSource;
import com.senselessweb.soundcloud.domain.StreamSource;
import com.senselessweb.soundcloud.library.domain.AbstractLibraryItem;

/**
 * IcecastRadioItem
 * 
 * @author thomas
 */
public class IcecastRadioItem extends AbstractLibraryItem
{

	/**
	 * The url
	 */
	private final String url; 
	
	/**
	 * The current song. 
	 */
	private final String currentSong;
	
	/**
	 * Constructor
	 * 
	 * @param serverName The name
	 * @param url The url
	 * @param bitrate The bitrate. Supply -1 if no bitrate is known.
	 * @param genres The genres
	 * @param currentSong The current playing song.
	 */
	public IcecastRadioItem(final String serverName, final String url, final int bitrate, 
			final Collection<String> genres, final String currentSong)
	{
		super(serverName, genres, bitrate); 
		
		if (StringUtils.isBlank(url)) 
			throw new IllegalArgumentException("Param url must not be null");
		
		this.url = url;
		this.currentSong = currentSong;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.domain.LibraryItem#getMediaSources()
	 */
	@Override
	public Collection<? extends MediaSource> getMediaSources()
	{
		return Collections.singleton(
				new StreamSource(this.getName(), this.url, 
						this.getGenres().toArray(new String[this.getGenres().size()])));
	}

	
	/**
	 * Returns the current playing song.
	 * 
	 * @return The current song. May be null.
	 */
	public String getCurrentSong()
	{
		return this.currentSong;
	}
	
}
