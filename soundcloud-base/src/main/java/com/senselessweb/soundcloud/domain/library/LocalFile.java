package com.senselessweb.soundcloud.domain.library;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.senselessweb.soundcloud.domain.sources.FileSource;
import com.senselessweb.soundcloud.domain.sources.MediaSource;

/**
 * Library item that represents a local file.
 * 
 * @author thomas
 */
public class LocalFile extends AbstractLibraryItem implements Comparable<LocalFile>
{

	/**
	 * The path to the file.
	 */
	private final String path;
	
	/**
	 * Constructor
	 * 
	 * @param id The id of this item.
	 * @param path The path to the file.
	 * @param name The name
	 * @param bitrate The bitrate. Supply -1 if no bitrate is known.
	 * @param genres The genres
	 */
	public LocalFile(final String id, final String path, final String name, final int bitrate, 
			final Collection<String> genres)
	{
		super(id, name, genres, bitrate);
		
		if (StringUtils.isBlank(path)) 
			throw new IllegalArgumentException("Param path must not be null");
		this.path = path;
	}

	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#asMediaSources()
	 */
	@Override
	public Collection<? extends MediaSource> asMediaSources()
	{
		return Collections.singleton(new FileSource(new File(this.path)));
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final LocalFile o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
}
