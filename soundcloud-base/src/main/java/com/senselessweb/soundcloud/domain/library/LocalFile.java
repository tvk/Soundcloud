package com.senselessweb.soundcloud.domain.library;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

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
	 * The lastModified
	 */
	private final long lastModified;
	
	/**
	 * Constructor
	 * 
	 * @param id The id 
	 * @param shortTitle The sort title 
	 * @param genres The genres
	 * @param keywords The keywords
	 * @param path The path
	 * @param longTitle The long title
	 * @param tracknumber The tracknumber
	 * @param lastModified The last modified
	 *
	 */
	public LocalFile(final String id, final String shortTitle, final Collection<String> genres, final Collection<String> keywords, 
			final String path, final String longTitle, final int tracknumber, final long lastModified)
	{
		super(id, shortTitle, genres, keywords);

		if (StringUtils.isBlank(path)) 
			throw new IllegalArgumentException("Param path must not be null");
		
		this.path = path;
		this.longTitle = longTitle;
		this.tracknumber = tracknumber;
		this.lastModified = lastModified;
	}
	
	/**
	 * Creates a new local file.
	 * 
	 * @param path The path to the file.
	 * @param fileInformations The file informations container
	 * 
	 * @return The new local file. Has no id yet.
	 */
	public static LocalFile create(final String path, final FileInformations fileInformations)
	{
		return new LocalFile(null,
				createShortTitle(path, fileInformations), 
				StringUtils.isBlank(fileInformations.getGenre()) ? Collections.<String>emptySet() : Collections.singleton(fileInformations.getGenre()), 
				createKeywords(path, fileInformations),
				path, 
				createLongTitle(path, fileInformations),
				readTracknumber(fileInformations),
				new File(path).lastModified());
	}
	
	/**
	 * Creates the keywords for this item.
	 * 
	 * @param path The path of this item
	 * @param fileInformations The {@link FileInformations} of this item
	 * 
	 * @return The keywords
	 */
	public static Collection<String> createKeywords(final String path, final FileInformations fileInformations)
	{
		final Collection<String> keywords = new HashSet<String>();
		keywords.add(FilenameUtils.getBaseName(path));
		
		if (!StringUtils.isBlank(fileInformations.getArtist())) keywords.add(fileInformations.getArtist());
		if (!StringUtils.isBlank(fileInformations.getAlbum())) keywords.add(fileInformations.getAlbum());
		if (!StringUtils.isBlank(fileInformations.getGenre())) keywords.add(fileInformations.getGenre());
		if (!StringUtils.isBlank(fileInformations.getTitle())) keywords.add(fileInformations.getTitle());
		
		return keywords;
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
	 * Returns the lastModified
	 *
	 * @return The lastModified
	 */
	public long getLastModified()
	{
		return this.lastModified;
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
