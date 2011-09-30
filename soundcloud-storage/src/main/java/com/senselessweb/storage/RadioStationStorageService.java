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
	 * 
	 * @return The new {@link StreamSource} 
	 */
	public StreamSource createRadioStation(StreamSource streamSource);
	
	/**
	 * Returns all stored radio stations.
	 * 
	 * @return All stored radio stations.
	 */
	public Collection<StreamSource> getAllRadioStations();
	
	/**
	 * Find the radio station with the given id.
	 * 
	 * @param id The id
	 * 
	 * @return The radio station with that id or null if there is no such station.
	 */
	public StreamSource getRadioStation(String id);


	/**
	 * Deletes the radio station with the given id.
	 * 
	 * @param id The id
	 */ 
	public void deleteRadioStation(String id);
}
