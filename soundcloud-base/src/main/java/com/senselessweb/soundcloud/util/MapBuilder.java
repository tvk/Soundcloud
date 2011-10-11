package com.senselessweb.soundcloud.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class that creates a map.
 * 
 * @author thomas
 *
 * @param <K> The key type
 * @param <V> The value type.
 */
public class MapBuilder<K, V>
{

	/**
	 * The map
	 */
	private final Map<K, V> map = new HashMap<K, V>();
	
	/**
	 * Adds an element to the map in creation.
	 * 
	 * @param key The key.
	 * @param value The value.
	 * 
	 * @return This {@link MapBuilder}.
	 */
	public MapBuilder<K, V> with(final K key, final V value)
	{
		this.map.put(key, value);
		return this;
	}
	
	/**
	 * Builds the map.
	 * 
	 * @return A new map containing all the element that have been added recently.
	 */
	public Map<K, V> build()
	{
		return new HashMap<K, V>(this.map);
	}
}
