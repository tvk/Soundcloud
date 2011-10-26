package com.senselessweb.soundcloud.domain.sources;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.senselessweb.soundcloud.util.IdentityUtils;

/**
 * A media source represented by a stream.
 * 
 * @author thomas
 *
 */
public class StreamSource extends AbstractMediaSource
{
	
	/**
	 * The id of this media source
	 */
	private String _id;
	
	/**
	 * The url 
	 */
	private final String url;
	
	/**
	 * @param name The name of this stream source.
	 * @param url The url of this stream
	 */
	public StreamSource(final String name, final String url)
	{
		super(name);

		if (url == null) 
			throw new IllegalArgumentException("url must not be null");
		
		this.url = url;
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
	 * Returns the url.
	 * 
	 * @return The url. Never null.
	 */
	public String getUrl()
	{
		return this.url;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this, true);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof StreamSource)) return false;
		final StreamSource other = (StreamSource) obj;
		return IdentityUtils.areEqual(this.url, other.url);	
	}

}
