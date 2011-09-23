package com.senselessweb.soundcloud.storage.mongodb.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.StreamSource;
import com.senselessweb.storage.RadioStationStorageService;

/**
 * Implementation of the {@link RadioStationStorageService} that is based on mongoDB.
 * 
 * @author thomas
 */
@Service
public class RadioStationStorageServiceImpl implements RadioStationStorageService
{

	/**
	 * The mongotemplate
	 */
	@Autowired MongoTemplate mongoTemplate;
	
	/**
	 * The collection used by this service.
	 */
	private static final String collectionName = "radioStationCollection";

	
	/**
	 * @see com.senselessweb.storage.RadioStationStorageService#storeRadioStation(StreamSource)
	 */
	public void storeRadioStation(final StreamSource streamSource)
	{
		// Remove stations with the same name and location
		this.mongoTemplate.remove(new Query(
				Criteria.where("name").is(streamSource.getName()).and("url").is(streamSource.getUrl())), collectionName);
		
		// Store the new station
		this.mongoTemplate.insert(streamSource, collectionName);
	}

	
	/**
	 * @see com.senselessweb.storage.RadioStationStorageService#getAllRadioStations()
	 */
	public Collection<StreamSource> getAllRadioStations()
	{
		return this.mongoTemplate.findAll(StreamSource.class, collectionName);
	}

}
