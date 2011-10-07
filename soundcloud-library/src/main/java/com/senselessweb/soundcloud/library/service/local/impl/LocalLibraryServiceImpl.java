package com.senselessweb.soundcloud.library.service.local.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.domain.library.LocalFile;
import com.senselessweb.soundcloud.domain.library.LocalFolder;
import com.senselessweb.soundcloud.library.service.local.LocalLibraryService;
import com.senselessweb.soundcloud.util.FileFilters;

/**
 * Default implementation of the {@link LocalLibraryService}
 *
 * @author thomas
 */
@Service
public class LocalLibraryServiceImpl implements LocalLibraryService
{
	
	/**
	 * The root folder
	 * 
	 * TODO Make this configurable
	 */
	private static final String root = "/home/thomas/Musik";
	
	/**
	 * Map of generated ids by a file object
	 */
	private final Map<File, String> idsByPathname = new HashMap<File, String>();
	
	/**
	 * Map of local files by id.
	 */
	final Map<String, LocalFile> localFilesById = new HashMap<String, LocalFile>();
	
	/**
	 * Sequence generator for the unique file ids.
	 */
	private final AtomicInteger fileIdSequence = new AtomicInteger();

	/**
	 * @see com.senselessweb.soundcloud.library.service.local.LocalLibraryService#getFolder(java.lang.String)
	 */
	@Override
	public LocalFolder getFolder(final String folder)
	{
		final File dir = StringUtils.isBlank(folder) ? new File(root) : new File(root, folder);
		
		final List<String> subfolders = Lists.newArrayList(Collections2.transform(Lists.newArrayList(dir.listFiles(FileFilters.directoryFilter)), new Function<File, String>() {
			/** @see com.google.common.base.Function#apply(java.lang.Object) */
			@Override public String apply(final File input) { return input.getName(); }
		}));
		Collections.sort(subfolders);
		
		final List<LocalFile> files = Lists.newArrayList(Collections2.transform(Lists.newArrayList(dir.listFiles(FileFilters.mediaFileFilter)), new Function<File, LocalFile>() {
			/** @see com.google.common.base.Function#apply(java.lang.Object) */
			@Override public LocalFile apply(final File input) { 
				final LocalFile localFile = new LocalFile(createId(input), input.getAbsolutePath(), input.getName(), -1, Collections.<String>emptySet());
				LocalLibraryServiceImpl.this.localFilesById.put(localFile.getId(), localFile);
				return localFile;
			}
		}));
		Collections.sort(files);
		
		return new LocalFolder(dir.getName(), dir.getAbsolutePath().substring(root.length()), subfolders, files);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.local.LocalLibraryService#getFiles(java.lang.String)
	 */
	@Override
	public Collection<LocalFile> getFiles(final String folder)
	{
		final List<LocalFile> result = new ArrayList<LocalFile>();
		final LocalFolder localFolder = this.getFolder(folder);
		
		result.addAll(localFolder.getFiles());
		for (final String subfolder : localFolder.getSubfolders())
			result.addAll(this.getFiles(folder + "/" + subfolder));
		
		return result;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.local.LocalLibraryService#getFile(java.lang.String)
	 */
	@Override
	public LocalFile getFile(final String id) 
	{
		return this.localFilesById.get(id);
	}
	
	/**
	 * Returns a valid and unique id for each file.
	 * 
	 * @param file The file.
	 * 
	 * @return The file.
	 */
	String createId(final File file)
	{
		if (!this.idsByPathname.containsKey(file)) this.idsByPathname.put(file, String.valueOf(this.fileIdSequence.getAndIncrement())); 
		return this.idsByPathname.get(file);
	}


}
