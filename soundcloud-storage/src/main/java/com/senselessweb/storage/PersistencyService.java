package com.senselessweb.storage;

import java.util.Map;


/**
 * A PersistencyService is used by the MediaPlayer to store and restore 
 * the current settings after the application has been restarted. 
 *  
 * @author thomas
 */
public interface PersistencyService
{

	/**
	 * Stores a value in the {@link PersistencyService}.
	 * 
	 * @param prefix The prefix for the key. 
	 * @param key The key under which the value is stored.
	 * 
	 * @param value The value
	 */
	public void put(String prefix, String key, Object value);
	
	/**
	 * Returns the value that is stored for the given key.
	 * 
	 * @param prefix The prefix for the key. 
	 * @param key The key
	 * 
	 * @return The value or null if there is no value for that key.
	 */
	public Object get(String prefix, String key);
	
	/**
	 * Checks whether a value is stored for the given key.
	 * 
	 * @param prefix The prefix for the key. 
	 * @param key The key
	 * 
	 * @return True if a value is stored for that key, false otherwise.
	 */
	public boolean contains(String prefix, String key);
	
	/**
	 * Returns all value that are stored for the given prefix.
	 * 
	 * @param prefix The prefix for the key. 
	 * 
	 * @return All values for that prefix.
	 */
	public Map<String, Object> getAll(String prefix);
	
}
