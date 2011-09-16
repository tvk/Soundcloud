package com.senselessweb.soundcloud.mediasupport.domain;

import java.net.URL;

import com.senselessweb.soundcloud.mediasupport.util.IdentityUtils;

/**
 * A media source represented by a stream.
 * 
 * @author thomas
 *
 */
public class StreamSource implements MediaSource
{

	/**
	 * The url 
	 */
	private final URL url;
	
	/**
	 * @param url The url of this stream
	 */
	public StreamSource(final URL url)
	{
		if (url == null) 
			throw new IllegalArgumentException("url must not be null");
		this.url = url;
	}
	
	/**
	 * Returns the url.
	 * 
	 * @return The url. Never null.
	 */
	public URL getUrl()
	{
		return this.url;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "StreamSource[" + this.url + "]";
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

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}	
}
