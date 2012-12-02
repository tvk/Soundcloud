package com.senselessweb.soundcloud.storage.mongodb.service;

import java.util.HashMap;
import java.util.Map;

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
	private final MongoTemplate mongoTemplate;
	
	/**
	 * The collection used by this service.
	 */
	private static final String collectionName = "persistencyService";

	@Autowired
	public PersistencyServiceImpl(final MongoTemplate mongoTemplate) 
	{
		this.mongoTemplate = mongoTemplate;
	}
	
	/**
	 * @see com.senselessweb.storage.PersistencyService#put(String, String, Object)
	 */
	@Override
	public void put(final String prefix, final String key, final Object value)
	{
		this.mongoTemplate.remove(SimpleEntry.createQuery(prefix, key), collectionName);
		this.mongoTemplate.insert(new SimpleEntry(prefix, key, value), collectionName);
	}

	
	/**
	 * @see com.senselessweb.storage.PersistencyService#get(String, String)
	 */
	@Override
	public Object get(final String prefix, final String key)
	{
		final SimpleEntry tuple = this.mongoTemplate.findOne(
				SimpleEntry.createQuery(prefix, key), SimpleEntry.class, collectionName);
		return tuple != null ? tuple.getValue() : null;
	}
	
	/**
	 * @see com.senselessweb.storage.PersistencyService#contains(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean contains(String prefix, String key)
	{
		return this.get(prefix, key) != null;
	}
	
	/**
	 * @see com.senselessweb.storage.PersistencyService#getAll(java.lang.String)
	 */
	@Override
	public Map<String, Object> getAll(String prefix)
	{
		final Map<String, Object> result = new HashMap<String, Object>();
		for (final SimpleEntry entry : this.mongoTemplate.find(SimpleEntry.createQuery(prefix), SimpleEntry.class, collectionName))
		{
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

}