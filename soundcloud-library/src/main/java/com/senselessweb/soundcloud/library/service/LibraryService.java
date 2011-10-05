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
	public Collection<? extends LibraryItem> getAllItems(); 
	

	/**
	 * Returns some randomized items of this library.
	 * 
	 * @param limit The maximum number of items to return.  
	 * 
	 * @return The items.
	 */
	public Collection<? extends LibraryItem> getRandomItems(int limit); 
	

	/**
	 * Returns all items of this library of that genre.
	 * 
	 * @param genre The genre. May be a substring of the real genre. 
	 * 
	 * @return The items.
	 */
	public Collection<? extends LibraryItem> findByGenre(String genre); 
	

	/**
	 * Returns all items of this library of that name.
	 * 
	 * @param name The name. May be a substring of the real name. 
	 * 
	 * @return The items.
	 */
	public Collection<? extends LibraryItem> findByName(String name); 
	
	
	/**
	 * Finds an item by id.
	 * 
	 * @param id The id.
	 * 
	 * @return The item or null if there is no such item.
	 */
	public LibraryItem findById(String id);
	
}
