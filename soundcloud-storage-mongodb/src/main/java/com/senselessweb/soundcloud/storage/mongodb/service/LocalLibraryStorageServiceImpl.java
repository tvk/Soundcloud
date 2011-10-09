/**
 * 
 */
package com.senselessweb.soundcloud.storage.mongodb.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.library.LocalFile;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.util.FileInformationsReader;
import com.senselessweb.storage.library.LocalLibraryStorageService;

/**
 * Default implementation of the LocalLibraryStorageService.
 *
 * @author thomas
 */
@Service
public class LocalLibraryStorageServiceImpl implements LocalLibraryStorageService
{
	
	/**
	 * The collection used by this service.
	 */
	private static final String collectionName = "localLibraryCacheCollection";
	
	/**
	 * The mongoTemplate
	 */
	@Autowired MongoTemplate mongoTemplate;

	/**
	 * @see com.senselessweb.storage.library.LocalLibraryStorageService#getOrCreate(java.io.File)
	 */
	@Override
	public synchronized LocalFile getOrCreate(final File input)
	{
		final LocalFile localFile = this.mongoTemplate.findOne(
				new Query(Criteria.where("path").is(input.getAbsolutePath())), LocalFile.class, collectionName);

		if (localFile != null && input.lastModified() <= localFile.getLastModified())
			return localFile;
		
		final LocalFile newLocalFile = LocalFile.create(input.getAbsolutePath(), FileInformationsReader.read(input));
		this.mongoTemplate.insert(newLocalFile, collectionName);
		return this.mongoTemplate.findOne(
				new Query(Criteria.where("path").is(input.getAbsolutePath())), LocalFile.class, collectionName);
	}

	/**
	 * @see com.senselessweb.storage.library.LocalLibraryStorageService#get(java.lang.String)
	 */
	@Override
	public LocalFile get(final String id)
	{
		return this.mongoTemplate.findById(id, LocalFile.class, collectionName);
	}

	/**
	 * @see com.senselessweb.storage.library.LocalLibraryStorageService#get(com.senselessweb.soundcloud.domain.sources.MediaSource)
	 */
	@Override
	public LocalFile get(final MediaSource mediaSource)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
