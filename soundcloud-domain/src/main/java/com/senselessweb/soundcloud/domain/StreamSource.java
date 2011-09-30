package com.senselessweb.soundcloud.domain;

import com.senselessweb.soundcloud.util.IdentityUtils;

/**
 * A media source represented by a stream.
 * 
 * @author thomas
 *
 */
public class StreamSource implements MediaSource
{
	
	/**
	 * The id of this media source
	 */
	private String _id;
	
	/**
	 * The name of this stream source.
	 */
	private final String name;

	/**
	 * The genres of this stream source.
	 */
	private final String[] genres;

	/**
	 * The url 
	 */
	private final String url;
	
	/**
	 * @param name The name of this stream source.
	 * @param url The url of this stream
	 * @param genres The genres of this stream source.
	 */
	public StreamSource(final String name, final String url, final String[] genres)
	{
		if (name == null) 
			throw new IllegalArgumentException("name must not be null");
		if (url == null) 
			throw new IllegalArgumentException("url must not be null");
		
		this.name = name;
		this.url = url;
		this.genres = genres != null ? genres : new String[0];
	}
	
	/**
	 * Returns the id of this stream source.
	 * 
	 * @return The id.
	 */
	public String getId()
	{
		return this._id;
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return The name.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Returns the url.
	 * 
	 * @return The url. Never null.
	 */
	public String getUrl()
	{
		return this.url;
	}
	
	
	/**
	 * Returns the genres
	 * 
	 * @return The genres
	 */
	public String[] getGenres()
	{
		return this.genres;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "StreamSource[" + this.name + "," + this.url + "]";
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof StreamSource)) return false;
		final StreamSource other = (StreamSource) obj;
		return IdentityUtils.areEqual(this.name, other.name, this.url, other.url, this.genres, other.genres);
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
