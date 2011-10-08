package com.senselessweb.soundcloud.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.senselessweb.soundcloud.util.IdentityUtils;

/**
 * Event. Contains a string identifying the event type
 * and a map of string properties.
 * 
 * @author thomas
 */
public class Event
{

	/**
	 * The type
	 */
	private final String type;
	
	/**
	 * The properties
	 */
	private final Map<String, String> properties;

	
	/**
	 * Constructor
	 * 
	 * @param type The type of this event 
	 * @param properties The properties as pairs of keys and values.
	 */
	public Event(final String type, final String... properties)
	{
		if (StringUtils.isBlank(type)) throw new IllegalArgumentException("type must not be null");
		this.type = type;
		
		this.properties = new HashMap<String, String>();
		if (properties != null)
		{
			if (properties.length % 2 != 0)
				throw new IllegalArgumentException("properties \"" + properties + "\" are invalid");
			for (int i = 0; i < properties.length; i += 2)
				this.properties.put(properties[i], properties[i+1]);
		}
	}
	
	/**
	 * Returns the type.
	 * 
	 * @return The type.
	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * Returns the properties
	 * 
	 * @return The properties.
	 */
	public Map<String, String> getProperties()
	{
		return this.properties;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (!(obj instanceof Event)) return false;
		final Event other = (Event) obj;
		return IdentityUtils.areEqual(this.type , other.type, this.properties, other.properties);
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this, true);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.type + "[" + this.properties + "]";
	}
}
