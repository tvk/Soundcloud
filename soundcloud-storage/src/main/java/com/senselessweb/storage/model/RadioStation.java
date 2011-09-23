package com.senselessweb.storage.model;

public class RadioStation
{

	private final String name;
	
	private final String url;
	
	private final String[] genres;
	
	public RadioStation(final String name, final String url, final String[] genres)
	{
		this.name = name;
		this.url = url;
		this.genres =  genres;
	}
}
