package com.senselessweb.soundcloud.util;

import java.io.File;
import java.io.FileFilter;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

import com.google.common.collect.Sets;

/**
 * Contains static multi purpose {@link FileFilter}.
 *
 * @author thomas
 */
public class FileFilters
{

	/**
	 * {@link FileFilter} that returns only directories.
	 */
	public static final FileFilter directoryFilter = new FileFilter()
	{
		@Override
		public boolean accept(final File pathname)
		{
			return pathname != null && pathname.isDirectory() && !pathname.getName().equals("System Volume Information");
		}
	};

	/**
	 * {@link FileFilter} that returns only files.
	 */
	public static final FileFilter fileFilter = new FileFilter()
	{
		@Override
		public boolean accept(final File pathname)
		{
			return pathname != null && pathname.isFile();
		}
	};

	/**
	 * {@link FileFilter} that returns only supported media files.
	 */
	public static final FileFilter mediaFileFilter = new FileFilter()
	{
		/**
		 * The supported media types.
		 */
		private final Set<String> supportedTypes = Sets.newHashSet("mp3", "wav", "m4a", "ogg", "wma");
		
		@Override
		public boolean accept(final File pathname)
		{
			return fileFilter.accept(pathname) && this.supportedTypes.contains(FilenameUtils.getExtension(pathname.getName()).toLowerCase());
		}
	};
	
}
