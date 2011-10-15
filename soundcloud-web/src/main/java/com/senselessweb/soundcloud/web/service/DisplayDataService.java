package com.senselessweb.soundcloud.web.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Returns the data to display.
 * 
 * @author thomas
 */
public interface DisplayDataService
{


	/**
	 * Display data container.
	 * 
	 * @author thomas
	 */
	class DisplayData implements Serializable
	{
		
		/**
		 * The serialVersionUID
		 */
		private static final long serialVersionUID = 7773075459199856642L;
		
		/**
		 * The properties
		 */
		private final Map<String, String> properties = new HashMap<String, String>();
		
		/**
		 * Sets or replaces a value
		 * 
		 * @param key The key
		 * @param value The value
		 */
		public void set(final String key, final String value)
		{
			this.properties.put(key, value);
		}
		
		/**
		 * Returns the properties.
		 * 
		 * @return The properties.
		 */
		public Map<String, String> getProperties()
		{
			return this.properties;
		}

		/**
		 * Removes all current properties.
		 */
		public void clear()
		{
			this.properties.clear();
		}
	}
	
	/**
	 * Returns the current display data.
	 * 
	 * @return The current display data.
	 */
	public DisplayData getDisplayData();
	
	/**
	 * Waits for the next display data. Blocks the current thread until an update is available.
	 * 
	 * @return The refreshed display data.
	 */
	public DisplayData waitForDisplayData();
}

