/**
 * 
 */
package com.senselessweb.storage.library;

import java.io.File;
import java.util.Set;

import com.senselessweb.soundcloud.domain.library.LocalFile;
import com.senselessweb.soundcloud.domain.sources.MediaSource;

/**
 * 
 *
 * @author thomas
 */
public interface LocalLibraryStorageService
{

	/**
	 * Returns the stored localfile or a new one if no one exists yet.
	 * 
	 * @param input The input file.
	 * 
	 * @return The local file. 
	 */
	public LocalFile getOrCreate(File input);
	
	/**
	 * Returns the local file by id.
	 * 
	 * @param id The id.
	 * 
	 * @return The local file or null if there is no such file.
	 */
	public LocalFile get(String id);

	/**
	 * Returns a local file by media source.
	 * 
	 * @param mediaSource The {@link MediaSource}.
	 * 
	 * @return The local file or null if there is no such file.
	 */
	public LocalFile get(MediaSource mediaSource);

	/**
	 * Returns all keywords for a given path
	 * 
	 * @param basePath The base path
	 * @param path The actual path
	 * 
	 * @return All keywords for that path
	 */
	public Set<String> getKeywords(final String basePath, final String path);

}
