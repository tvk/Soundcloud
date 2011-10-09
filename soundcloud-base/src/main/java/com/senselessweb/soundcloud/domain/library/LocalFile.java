package com.senselessweb.soundcloud.domain.library;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.CharUtils;
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
	 * The path to the file.
	 */
	private final String longTitle;
	
	/**
	 * The tracknumber or -1 if it is unknown
	 */
	private final int tracknumber;

	/**
	 * Constructor
	 * 
	 * @param id The id of this item.
	 * @param path The path to the file.
	 * @param fileInformations The file informations container
	 */
	public LocalFile(final String id, final String path, final FileInformations fileInformations)
	{
		super(id, createShortTitle(path, fileInformations), 
				StringUtils.isBlank(fileInformations.getGenre()) ? Collections.<String>emptySet() : Collections.singleton(fileInformations.getGenre()), 
						-1);
		
		if (StringUtils.isBlank(path)) 
			throw new IllegalArgumentException("Param path must not be null");
		
		this.path = path;
		this.longTitle = createLongTitle(path, fileInformations);
		this.tracknumber = readTracknumber(fileInformations);
	}

	/**
	 * Creates a short title for this item.
	 * 
	 * @param path The path of this item
	 * @param fileInformations The {@link FileInformations} of this item
	 * 
	 * @return The short title.
	 */
	private static String createShortTitle(final String path, final FileInformations fileInformations)
	{
		if (!StringUtils.isBlank(fileInformations.getTitle()))
			return fileInformations.getTitle();
		else
			return FilenameUtils.getBaseName(path);
	}
	
	/**
	 * Creates a long title for this item.
	 * 
	 * @param path The path of this item
	 * @param fileInformations The {@link FileInformations} of this item
	 * 
	 * @return The long title.
	 */
	private static String createLongTitle(final String path, final FileInformations fileInformations)
	{
		if (!StringUtils.isBlank(fileInformations.getTitle()))
		{
			String title = "";

			if (!StringUtils.isBlank(fileInformations.getTracknumber()))
				title += fileInformations.getTracknumber() + " - ";

			if (!StringUtils.isBlank(fileInformations.getArtist()))
				title += fileInformations.getArtist() + " - ";

			title += fileInformations.getTitle();

			if (!StringUtils.isBlank(fileInformations.getAlbum()))
				title += " (" + fileInformations.getAlbum() + ")";
			
			return title;
		}
		else
			return FilenameUtils.getBaseName(path);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.library.LibraryItem#getLongTitle()
	 */
	@Override
	public String getLongTitle()
	{
		return this.longTitle;
	}
	
	/**
	 * Returns the tracknumber
	 * 
	 * @param fileInformations The fileInformations
	 * @return The tracknumber. -1 if it is unknown.
	 */
	public static int readTracknumber(final FileInformations fileInformations)
	{
		if (StringUtils.isBlank(fileInformations.getTracknumber())) return -1;
		
		final StringBuilder sb = new StringBuilder();
		final String raw = fileInformations.getTracknumber();
		
		for (int i = 0; i < raw.length(); i++)
			if (CharUtils.isAsciiNumeric(raw.charAt(i))) 
				sb.append(raw.charAt(i));
		
		return sb.length() == 0 ? -1 : Integer.parseInt(sb.toString());
	}
	
	/**
	 * Returns the tracknumber
	 *
	 * @return The tracknumber
	 */
	public int getTracknumber()
	{
		return this.tracknumber;
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
		// If both have a track number, compare those.
		if (this.getTracknumber() >= 0 && o.getTracknumber() >= 0)
			return this.getTracknumber() - o.getTracknumber();
		
		// Otherwise compare the filenames
		return this.path.compareTo(o.path);
	}
	
}
