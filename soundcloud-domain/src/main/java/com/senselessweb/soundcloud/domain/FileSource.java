package com.senselessweb.soundcloud.domain;

import java.io.File;

import com.senselessweb.soundcloud.util.IdentityUtils;

/**
 * A media source represented by a file.
 * 
 * @author thomas
 *
 */
public class FileSource implements MediaSource
{

	/**
	 * The file
	 */
	private final File file;
	
	/**
	 * @param file The file of this media source
	 */
	public FileSource(final File file)
	{
		if (file == null || !file.isFile()) 
			throw new IllegalArgumentException("File \"" + file + "\" must be an existing file");
		this.file = file;
	}
	
	/**
	 * Returns the file.
	 * 
	 * @return The file. Never null.
	 */
	public File getFile()
	{
		return this.file;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "FileSource[" + this.file + "]";
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof FileSource)) return false;
		final FileSource other = (FileSource) obj;
		return IdentityUtils.areEqual(this.file, other.file);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/**
	 * @see com.senselessweb.soundcloud.domain.MediaSource#getTitle()
	 */
	@Override
	public String getTitle()
	{
		return this.file.getName();
	}
}
