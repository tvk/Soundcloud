package com.senselessweb.storage;

import java.util.Collection;

import com.senselessweb.soundcloud.domain.StreamSource;



/**
 * Service that stores streaming radio stations.
 * 
 * @author thomas
 */
public interface RadioStationStorageService
{

	/**
	 * Stores a RadioStation
	 * 
	 * @param streamSource
	 */
	public void storeRadioStation(StreamSource streamSource);
	
	/**
	 * Returns all stored radio stations.
	 * 
	 * @return All stored radio stations.
	 */
	public Collection<StreamSource> getAllRadioStations();
}
