package com.senselessweb.soundcloud.mediasupport.gstreamer.elements;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Element;

/**
 * Abstract element bridge that contains an {@link Element} and the properties that need 
 * to be set to that element.
 * The idea of this class is that the properties are stored while the actual element may 
 * be exchanged using the {@link AbstractElementBridge#initElement(Element)} method. 
 * 
 * @author thomas
 *
 */
abstract class AbstractElementBridge
{
	
	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(AbstractElementBridge.class);

	/**
	 * The actual element. May be null.
	 */
	private Element element;
	
	/**
	 * The properties to set to the object.
	 */
	private Map<String, Object> properties = new HashMap<String, Object>();
	
	/**
	 * Initializes the element and sets all stored properties to that element.
	 * 
	 * @param element The {@link Element}.
	 */
	public void initElement(final Element element)
	{
		this.element = element;
		for (final Map.Entry<String, Object> entry : this.properties.entrySet())
		{
			this.element.set(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Sets a property to the element. The property is stored in case the {@link Element} is replaced.
	 * 
	 * @see Element#set(String, Object)
	 * 
	 * @param key The key 
	 * @param value The value
	 */
	protected void set(final String key, final Object value)
	{
		log.debug("Setting [" + key + ":" + value + "] to " + this.getClass().getSimpleName() + " (Element: " + this.element + ")");
		this.properties.put(key, value);
		if (this.element != null) this.element.set(key, value);
	}
	
	/**
	 * Returns the currently stored value for a given key.
	 * 
	 * @param <T> The type of property to return.
	 * @param key The key.
	 * @param defaultValue A default value in case there is no value stored for the given key.
	 * 
	 * @return The stored value or the default value.
	 */
	@SuppressWarnings("unchecked")
	protected <T> T get(final String key, final T defaultValue)
	{
		if (!this.properties.containsKey(key)) return defaultValue;
		else return (T) this.properties.get(key);
	}
	
}
