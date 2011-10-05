package com.senselessweb.soundcloud.library.service;

import java.util.Collection;

import com.senselessweb.soundcloud.library.domain.LibraryItem;

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
	
	
	
}
