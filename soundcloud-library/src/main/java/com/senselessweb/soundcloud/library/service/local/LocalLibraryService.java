package com.senselessweb.soundcloud.library.service.local;

import java.util.Collection;

import com.senselessweb.soundcloud.domain.library.LocalFile;
import com.senselessweb.soundcloud.domain.library.LocalFolder;
import com.senselessweb.soundcloud.domain.sources.MediaSource;

/**
 * Service interface for the local music library.
 *
 * @author thomas
 */
public interface LocalLibraryService 
{

	/**
	 * Returns a {@link LocalFolder} object representing the content of the given folder.
	 * 
	 * @param folder The folder name. Relative to the base folder.
	 * 
	 * @return The content of the folder.
	 */
	public LocalFolder getFolder(String folder);
	
	/**
	 * Returns all files in a given folder and its subdirectories
	 * 
	 * @param folder The folder name. Relative to the base folder.
	 * 
	 * @return All items of that folder
	 */
	public Collection<LocalFile> getFiles(String folder);
	
	/**
	 * Returns a single item.
	 * 
	 * @param id The id of that item.
	 * 
	 * @return The file.
	 */
	public LocalFile getFile(String id);
	

	/**
	 * Returns a single item by media source.
	 * 
	 * @param mediaSource The {@link MediaSource}.
	 * 
	 * @return The local file that belongs to that {@link MediaSource} or null if 
	 * there is no such item. 
	 */
	public LocalFile getFile(MediaSource mediaSource);	
}
