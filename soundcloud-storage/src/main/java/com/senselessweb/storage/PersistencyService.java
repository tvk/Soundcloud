package com.senselessweb.storage;


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
	 * @param key The key under which the value is stored.
	 * @param value The value
	 */
	public void put(String key, String value);
	
	/**
	 * Returns the value that is stored for the given key.
	 * 
	 * @param key The key
	 * 
	 * @return The value or null if there is no value for that key.
	 */
	public String get(String key);
	
	/**
	 * Checks whether there is a value stored for the given key.
	 * 
	 * @param key The key
	 * 
	 * @return True if there is a value for that key, false otherwise.
	 */
	public boolean contains(String key);
}
