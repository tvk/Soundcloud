package com.senselessweb.soundcloud.domain.library;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;

/**
 * Represents a local folder.
 *
 * @author thomas
 */
public class LocalFolder
{

	/**
	 * The name
	 */
	private final String name;

	/**
	 * The path
	 */
	private final String path;
	
	/**
	 * The subfolders
	 */
	private final Collection<LocalSubfolder> subfolders;
	
	/**
	 * The files
	 */
	private final Collection<LocalFile> files;
	

	/**
	 * Constructor
	 *
	 * @param name The name of this folder
	 * @param path The relative path
	 * @param subfolders The subfolders
	 * @param files The files in this folder
	 * @param keywords Keywords of this folder
	 */
	public LocalFolder(final String name, final String path, final Collection<LocalSubfolder> subfolders, 
			final Collection<LocalFile> files, final Collection<String> keywords)
	{
		this.name = name;
		this.subfolders = subfolders;
		this.files = files;
		
		try
		{
			this.path = FilenameUtils.normalize(URLEncoder.encode(path, "UTF-8"));
		} 
		catch (final UnsupportedEncodingException e) { throw new RuntimeException(e); }		
	}
	
	/**
	 * Returns the name
	 *
	 * @return The name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Returns the path
	 *
	 * @return The path
	 */
	public String getPath()
	{
		return this.path;
	}
	
	/**
	 * Returns the files
	 *
	 * @return The files
	 */
	public Collection<LocalFile> getFiles()
	{
		return this.files;
	}
	
	/**
	 * Returns the subfolders
	 *
	 * @return The subfolders
	 */
	public Collection<LocalSubfolder> getSubfolders()
	{
		return this.subfolders;
	}
	
	
}
