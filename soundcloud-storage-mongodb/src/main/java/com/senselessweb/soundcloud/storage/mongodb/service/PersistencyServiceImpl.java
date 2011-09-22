package com.senselessweb.soundcloud.storage.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.storage.mongodb.support.SimpleEntry;
import com.senselessweb.storage.PersistencyService;

@Service("persistencyService")
public class PersistencyServiceImpl implements PersistencyService
{
	
	@Autowired MongoTemplate mongoTemplate;
	
	private String collectionName = "persistencyServiceCollection";

	public void put(String key, String value)
	{
		this.mongoTemplate.remove(new Query(Criteria.where("key").is(key)), this.collectionName);
		this.mongoTemplate.insert(new SimpleEntry(key, value), this.collectionName);
	}

	public String get(String key)
	{
		final SimpleEntry tuple = this.mongoTemplate.findOne(
				new Query(Criteria.where("key").is(key)), SimpleEntry.class, this.collectionName);
		return tuple != null ? tuple.getValue() : null;
	}

	public boolean contains(String key)
	{
		return this.mongoTemplate.findOne(new Query(Criteria.where("key=" + key)), SimpleEntry.class, this.collectionName) != null;
	}
	
}