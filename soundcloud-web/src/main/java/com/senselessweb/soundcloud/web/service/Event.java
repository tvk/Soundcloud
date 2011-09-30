package com.senselessweb.soundcloud.web.service;

/**
 * Event
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
	 * The new state
	 */
	private final String newState;

	/**
	 * The message
	 */
	private final String message;

	/**
	 * @param type
	 * @param newState
	 * @param message
	 */
	public Event(final String type, final String newState, final String message)
	{
		this.type = type;
		this.newState = newState;
		this.message = message;
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
	 * Returns the message.
	 * 
	 * @return The message.
	 */
	public String getMessage()
	{
		return this.message;
	}
	
	/**
	 * Returns the new state.
	 * 
	 * @return The new state.
	 */
	public String getNewState()
	{
		return this.newState;
	}
}
