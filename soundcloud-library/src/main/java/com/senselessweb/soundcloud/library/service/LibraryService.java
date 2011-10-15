package com.senselessweb.soundcloud.library.service;

import java.util.Collection;

import com.senselessweb.soundcloud.domain.library.LibraryItem;

/**
 * Implementations of this interface manage a media libary.
 *  
 * @author thomas
 */
public interface LibraryService
{

	/**
	 * Returns all items of this library.
	 * 
	 * @return All items.
	 */
	public Collection<? extends LibraryItem> getItems(); 
	
	/**
	 * Returns all items of this library.
	 * 
	 * @param keyword A keyword. Only items that match the given keyword are returned. If null,
	 * the keyword is ignored. 
	 * 
	 * @return All items.
	 */
	public Collection<? extends LibraryItem> getItems(String keyword); 
	
	/**
	 * Finds an item by id.
	 * 
	 * @param id The id.
	 * 
	 * @return The item or null if there is no such item.
	 */
	public LibraryItem findById(String id);
	
}
