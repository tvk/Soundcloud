package com.senselessweb.soundcloud.library.domain;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * Base class for {@link LibraryItem}s.
 * 
 * @author thomas
 */
public abstract class AbstractLibraryItem implements LibraryItem
{

	/**
	 * The name of this item.
	 */
	private final String name;
	
	/**
	 * The genres
	 */
	private final Collection<String> genres;
	
	/**
	 * The bitrate
	 */
	private final int bitrate;
	
	
	
	/**
	 * Constructor
	 * 
	 * @param name The name 
	 * @param genres The genres
	 * @param bitrate The bitrate
	 */
	public AbstractLibraryItem(final String name, final Collection<String> genres, final int bitrate)
	{
		if (StringUtils.isBlank(name)) 
			throw new IllegalArgumentException("Param name must not be null");
		
		this.name = name;
		this.genres = genres;
		this.bitrate = bitrate;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.domain.LibraryItem#getGenres()
	 */
	@Override
	public Collection<String> getGenres()
	{
		return this.genres;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.domain.LibraryItem#getName()
	 */
	@Override
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.domain.LibraryItem#getBitrate()
	 */
	@Override
	public int getBitrate()
	{
		return this.bitrate;
	}
}
