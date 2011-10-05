package com.senselessweb.soundcloud.domain.library;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.senselessweb.soundcloud.util.IdentityUtils;

/**
 * Base class for {@link LibraryItem}s.
 * 
 * @author thomas
 */
public abstract class AbstractLibraryItem implements LibraryItem
{

	/**
	 * The id. May be null if this item has no id yet.
	 */
	private final String id;
	
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
	 * @param id The id
	 * @param name The name 
	 * @param genres The genres
	 * @param bitrate The bitrate
	 */
	public AbstractLibraryItem(final String id, final String name, final Collection<String> genres, final int bitrate)
	{
		if (StringUtils.isBlank(name)) 
			throw new IllegalArgumentException("Param name must not be null");
		
		this.id = id;
		this.name = name;
		this.genres = genres;
		this.bitrate = bitrate;
	}

	/**
	 * Constructor
	 * 
	 * @param name The name 
	 * @param genres The genres
	 * @param bitrate The bitrate
	 */
	public AbstractLibraryItem(final String name, final Collection<String> genres, final int bitrate)
	{
		this(null, name, genres, bitrate);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getGenres()
	 */
	@Override
	public Collection<String> getGenres()
	{
		return this.genres;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getId()
	 */
	@Override
	public String getId()
	{
		return this.id;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getName()
	 */
	@Override
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getBitrate()
	 */
	@Override
	public int getBitrate()
	{
		return this.bitrate;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (!(obj instanceof AbstractLibraryItem)) return false;
		final AbstractLibraryItem other = (AbstractLibraryItem) obj;
		return IdentityUtils.areEqual(this.name, other.name, this.bitrate, other.bitrate, this.genres, other.genres);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
}
