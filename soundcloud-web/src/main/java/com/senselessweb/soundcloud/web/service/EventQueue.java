package com.senselessweb.soundcloud.web.service;

/**
 * The event queue
 * 
 * @author thomas
 */
public interface EventQueue
{
	
	/**
	 * Returns the next event as soon as it is available.
	 * 
	 * @return The next event.
	 */
	public Event getNextEvent();
}
