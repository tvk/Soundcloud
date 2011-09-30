package com.senselessweb.soundcloud.storage.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.storage.mongodb.support.SimpleEntry;
import com.senselessweb.storage.PersistencyService;

/**
 * Implementation of the {@link PersistencyService} that uses mongodb as storage backend.
 * 
 * @author thomas
 */
@Service("persistencyService")
public class PersistencyServiceImpl implements PersistencyService
{

	/**
	 * The mongotemplate
	 */
	@Autowired MongoTemplate mongoTemplate;
	
	/**
	 * The collection used by this service.
	 */
	private static final String collectionName = "persistencyServiceCollection";

	
	/**
	 * @see com.senselessweb.storage.PersistencyService#put(java.lang.String, java.lang.String)
	 */
	@Override
	public void put(String key, String value)
	{
		this.mongoTemplate.remove(SimpleEntry.createQuery(key), PersistencyServiceImpl.collectionName);
		this.mongoTemplate.insert(new SimpleEntry(key, value), PersistencyServiceImpl.collectionName);
	}

	
	/**
	 * @see com.senselessweb.storage.PersistencyService#get(java.lang.String)
	 */
	@Override
	public String get(String key)
	{
		final SimpleEntry tuple = this.mongoTemplate.findOne(
				SimpleEntry.createQuery(key), SimpleEntry.class, PersistencyServiceImpl.collectionName);
		return tuple != null ? tuple.getValue() : null;
	}

	
	/**
	 * @see com.senselessweb.storage.PersistencyService#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String key)
	{
		return this.mongoTemplate.findOne(
				SimpleEntry.createQuery(key), SimpleEntry.class, PersistencyServiceImpl.collectionName) != null;
	}
	
}