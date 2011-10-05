package com.senselessweb.soundcloud.storage.mongodb.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
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
	 * @see com.senselessweb.storage.RadioStationStorageService#createRadioStation(RadioLibraryItem)
	 */
	@Override
	public RadioLibraryItem createRadioStation(final RadioLibraryItem streamSource)
	{
		// Remove stations with the same name and location
		this.mongoTemplate.remove(new Query(
				Criteria.where("name").is(streamSource.getName())), collectionName);
		
		// Store the new station
		this.mongoTemplate.insert(streamSource, collectionName);
		
		// Return the new entity with id.
		return streamSource;
	}

	
	/**
	 * @see com.senselessweb.storage.RadioStationStorageService#getAllRadioStations()
	 */
	@Override
	public Collection<RadioLibraryItem> getAllRadioStations()
	{
		return this.mongoTemplate.findAll(RadioLibraryItem.class, collectionName);
	}

	
	/**
	 * @see com.senselessweb.storage.RadioStationStorageService#getRadioStation(java.lang.String)
	 */
	@Override
	public RadioLibraryItem getRadioStation(final String id)
	{
		return this.mongoTemplate.findById(id, RadioLibraryItem.class, collectionName);
	}


	/**
	 * @see com.senselessweb.storage.RadioStationStorageService#deleteRadioStation(java.lang.String)
	 */
	@Override
	public void deleteRadioStation(final String id)
	{
		this.mongoTemplate.remove(new Query(Criteria.where("id").is(id)), collectionName);
	}

}
