package com.senselessweb.soundcloud.domain.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.domain.sources.StreamSource;
import com.senselessweb.soundcloud.util.IdentityUtils;

/**
 * IcecastRadioItem
 * 
 * @author thomas
 */
public class RadioLibraryItem extends AbstractLibraryItem
{

	/**
	 * The url
	 */
	private final String url; 
	
	/**
	 * Constructor
	 * 
	 * @param id The id of this item.
	 * @param shortTitle The shortTitle
	 * @param url The url
	 * @param genres The genres
	 */
	public RadioLibraryItem(final String id, final String shortTitle, final String url, 
			final Collection<String> genres)
	{
		super(id, shortTitle, genres, Lists.asList(shortTitle, url, genres.toArray(new String[0]))); 
		
		if (StringUtils.isBlank(url)) 
			throw new IllegalArgumentException("Param url must not be null");
		
		this.url = url;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#asMediaSources()
	 */
	@Override
	public Collection<? extends MediaSource> asMediaSources()
	{
		try
		{
			return Collections2.transform(this.getStreamUrls(), new Function<String, StreamSource>() {
				@Override
				public StreamSource apply(final String url)
				{
					return new StreamSource(RadioLibraryItem.this.getShortTitle(), url);
				}
			});
		} 
		catch (final IOException e)
		{
			throw new RuntimeException("Could not get stream urls", e);
		}
	}
	
	/**
	 * Checks if the uri of this item points to a playlist and returns the single 
	 * entries in that case.
	 * 
	 * @return The stream urls.
	 * 
	 * @throws IOException
	 */
	private Collection<String> getStreamUrls() throws IOException
	{
		final List<String> result = new ArrayList<String>(); 
		
		final URLConnection conn = new URL(this.url).openConnection();
		final String contentType = conn.getHeaderField("Content-Type");
		
		
		if ("audio/x-mpegurl".equals(contentType))
		{
			final InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			try
			{
				final BufferedReader reader = new BufferedReader(isr);
				
				for (String line = reader.readLine(); line != null; line = reader.readLine())
					result.add(line);
			}
			finally
			{
				isr.close();
			}
		}
		else
		{
			result.add(this.url);
		}
		return result;
	}

	
	/**
	 * Returns the url.
	 * 
	 * @return The url.
	 */
	public String getUrl()
	{
		return this.url;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getLongTitle()
	 */
	@Override
	public String getLongTitle()
	{
		return this.getShortTitle();
	}
	
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (!(obj instanceof RadioLibraryItem)) return false;
		final RadioLibraryItem other = (RadioLibraryItem) obj;
		return IdentityUtils.areEqual(this.url, other.url);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this.url, true);
	}
	
}
