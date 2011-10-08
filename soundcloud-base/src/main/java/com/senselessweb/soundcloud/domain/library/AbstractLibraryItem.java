package com.senselessweb.soundcloud.domain.library;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	 * The short title of this item.
	 */
	private final String shortTitle;
	
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
	 * @param shortTitle The name 
	 * @param genres The genres
	 * @param bitrate The bitrate
	 */
	public AbstractLibraryItem(final String id, final String shortTitle, final Collection<String> genres, final int bitrate)
	{
		if (StringUtils.isBlank(shortTitle)) 
			throw new IllegalArgumentException("Param shortTitle must not be null");
		
		this.id = id;
		this.shortTitle = shortTitle;
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
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getShortTitle()
	 */
	@Override
	public String getShortTitle()
	{
		return this.shortTitle;
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
		return IdentityUtils.areEqual(this.shortTitle, other.shortTitle, this.bitrate, other.bitrate, this.genres, other.genres);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this, true);
	}
}
