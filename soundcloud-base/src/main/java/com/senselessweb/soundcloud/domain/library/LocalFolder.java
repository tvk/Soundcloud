package com.senselessweb.soundcloud.domain.library;

import java.util.Collection;

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
	private final Collection<String> subfolders;
	
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
	 */
	public LocalFolder(final String name, final String path, final Collection<String> subfolders, final Collection<LocalFile> files)
	{
		this.name = name;
		this.path = path;
		this.subfolders = subfolders;
		this.files = files;
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
	public Collection<String> getSubfolders()
	{
		return this.subfolders;
	}
}
