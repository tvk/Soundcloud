package com.senselessweb.soundcloud.library.service.radio;

import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
import com.senselessweb.soundcloud.library.service.LibraryService;

/**
 * Radio service interface for user stored entries.
 * 
 * @author thomas
 */
public interface UserRadioLibraryService extends LibraryService
{

	/**
	 * Stores a new radio station.
	 * 
	 * @param name The name of the new radio station.
	 * @param url The url
	 * @param genres The genres
	 * 
	 * @return The new radio station
	 */
	public RadioLibraryItem store(String name, String url, String[] genres);

	/**
	 * Deletes a station.
	 * 
	 * @param id The id of the station to delete.
	 */
	public void delete(String id);
}
