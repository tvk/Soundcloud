package com.senselessweb.soundcloud.library.service.radio.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.domain.library.LibraryItem;
import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
import com.senselessweb.soundcloud.library.service.radio.UserRadioLibraryService;
import com.senselessweb.storage.RadioStationStorageService;

/**
 * Library service implementation for user stored radio stations.
 *  
 * @author thomas
 */
@Service
public class UserRadioServiceImpl extends AbstractRadioService implements UserRadioLibraryService
{
	
	/**
	 * The RadioStationStorageService
	 */
	@Autowired RadioStationStorageService radioStationStorageService;

	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#getAllItems()
	 */
	@Override
	public Collection<? extends LibraryItem> getAllItems()
	{
		return this.radioStationStorageService.getAllRadioStations();
	}

	/**
	 * @see com.senselessweb.soundcloud.library.service.radio.UserRadioLibraryService#store(java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public RadioLibraryItem store(final String name, final String url, final String[] genres)
	{
		final RadioLibraryItem newItem = new RadioLibraryItem(null, name, url, -1, Lists.newArrayList(genres), null);
		return this.radioStationStorageService.createRadioStation(newItem);
	}

	/**
	 * @see com.senselessweb.soundcloud.library.service.radio.UserRadioLibraryService#delete(java.lang.String)
	 */
	@Override
	public void delete(final String id)
	{
		this.radioStationStorageService.deleteRadioStation(id);
	}

	
}
