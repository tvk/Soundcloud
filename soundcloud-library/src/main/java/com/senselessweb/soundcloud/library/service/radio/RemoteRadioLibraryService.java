package com.senselessweb.soundcloud.library.service.radio;

import java.util.Collection;

import com.senselessweb.soundcloud.domain.library.LibraryItem;
import com.senselessweb.soundcloud.library.service.LibraryService;

/**
 * Radio service interface for remote entries.
 * 
 * @author thomas
 */
public interface RemoteRadioLibraryService extends LibraryService
{
	// Marker interface, no usage yet.
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#getItems()
	 */
	@Override
	public Collection<? extends LibraryItem> getItems();
}
