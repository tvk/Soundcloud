package com.senselessweb.soundcloud.storage.mongodb.support;

public class SimpleEntry
{
	
	private final String key;
	
	private final String value;

	public SimpleEntry(final String key, final String value)
	{
		this.key = key;
		this.value = value;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public String getValue()
	{
		return value;
	}
}
