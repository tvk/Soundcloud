package com.senselessweb.soundcloud.storage.mongodb.support;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Simple that consists only of a string key and value an can be stored in mongodb.
 * 
 * @author thomas
 */
public class SimpleEntry
{

	/**
	 * The key
	 */
	private final String key;
	
	/**
	 * The value
	 */
	private final String value;

	/**
	 * Constructor
	 * 
	 * @param key The key
	 * @param value The value
	 */
	public SimpleEntry(final String key, final String value)
	{
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Returns the key.
	 * 
	 * @return The key
	 */
	public String getKey()
	{
		return this.key;
	}
	
	/**
	 * Returns the value
	 * 
	 * @return The value
	 */
	public String getValue()
	{
		return this.value;
	}
	
	/**
	 * Creates a simple {@link Query} to search for objects of this class.
	 *  
	 * @param key The key to search for.
	 * 
	 * @return The query to search for this key.
	 */
	public static Query createQuery(final String key)
	{
		return new Query(Criteria.where("key").is(key));
	}
}
