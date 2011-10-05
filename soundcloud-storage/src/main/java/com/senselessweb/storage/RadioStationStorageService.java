package com.senselessweb.storage;

import java.util.Collection;

import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
import com.senselessweb.soundcloud.domain.sources.StreamSource;



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
	public RadioLibraryItem createRadioStation(RadioLibraryItem streamSource);
	
	/**
	 * Returns all stored radio stations.
	 * 
	 * @return All stored radio stations.
	 */
	public Collection<RadioLibraryItem> getAllRadioStations();
	
	/**
	 * Find the radio station with the given id.
	 * 
	 * @param id The id
	 * 
	 * @return The radio station with that id or null if there is no such station.
	 */
	public RadioLibraryItem getRadioStation(String id);


	/**
	 * Deletes the radio station with the given id.
	 * 
	 * @param id The id
	 */ 
	public void deleteRadioStation(String id);
}
