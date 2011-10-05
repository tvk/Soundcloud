package com.senselessweb.soundcloud.library.service.radio;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.senselessweb.soundcloud.library.service.radio.impl.IcecastRadioService;


/**
 * Testcases for the {@link IcecastRadioService}.
 * 
 * @author thomas
 */
public class IcecastRadioServiceTest
{
	
	/**
	 * The service to test
	 */
	private IcecastRadioService icecastRadioService; 
	
	/**
	 * Init the service.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception
	{
		this.icecastRadioService = new IcecastRadioService();
	}

	/**
	 * Try to get all stations
	 */
	@Test
	public void tryToGetAllRadioStations()
	{
		Assert.assertTrue(this.icecastRadioService.getAllItems().size() > 2);
	}
	
	/**
	 * Try to find stations be genre
	 */
	@Test
	public void findRadioStationsByGenre()
	{
		Assert.assertTrue(this.icecastRadioService.findByGenre("80").size() > 2);
	}
}
