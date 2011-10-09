package com.senselessweb.soundcloud.domain.library;

import java.util.Collection;
import java.util.Collections;

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
	 * The keywords
	 */
	private final Collection<String> keywords;
	
	
	
	/**
	 * Constructor
	 * 
	 * @param id The id
	 * @param shortTitle The name 
	 * @param genres The genres
	 * @param keywords The keywords
	 */
	public AbstractLibraryItem(final String id, final String shortTitle, final Collection<String> genres, 
			final Collection<String> keywords)
	{
		if (StringUtils.isBlank(shortTitle)) 
			throw new IllegalArgumentException("Param shortTitle must not be null");
		
		this.id = id;
		this.shortTitle = shortTitle;
		this.genres = genres;
		this.keywords = keywords;
	}

	/**
	 * Constructor
	 * 
	 * @param name The name 
	 * @param genres The genres
	 * @param keywords The keywords
	 */
	public AbstractLibraryItem(final String name, final Collection<String> genres, final Collection<String> keywords)
	{
		this(null, name, genres, keywords);
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
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getKeywords()
	 */
	@Override
	public Collection<String> getKeywords()
	{
		return Collections.unmodifiableCollection(this.keywords);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (!(obj instanceof AbstractLibraryItem)) return false;
		final AbstractLibraryItem other = (AbstractLibraryItem) obj;
		return IdentityUtils.areEqual(this.shortTitle, other.shortTitle, this.genres, other.genres);
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
