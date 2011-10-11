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
	 * The prefix
	 */
	private final String prefix;
	
	/**
	 * The key
	 */
	private final String key;
	
	/**
	 * The value
	 */
	private final Object value;

	/**
	 * Constructor
	 * 
	 * @param prefix The prefix
	 * @param key The key
	 * @param value The value
	 */
	public SimpleEntry(final String prefix, final String key, final Object value)
	{
		this.prefix = prefix;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Returns the prefix
	 *
	 * @return The prefix
	 */
	public String getPrefix()
	{
		return this.prefix;
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
	public Object getValue()
	{
		return this.value;
	}
	
	/**
	 * Creates a simple {@link Query} to search for objects of this class.
	 *  
	 * @param prefix The prefix
	 * 
	 * @return The query to search for this key.
	 */
	public static Query createQuery(final String prefix)
	{
		return new Query(Criteria.where("prefix").is(prefix));
	}

	/**
	 * Creates a simple {@link Query} to search for objects of this class.
	 * 
	 * @param prefix The prefix
	 * @param key The key to search for.
	 * 
	 * @return The query to search for this key.
	 */
	public static Query createQuery(String prefix, String key)
	{
		return new Query(Criteria.where("key").is(key).and("prefix").is(prefix));

	}
}
