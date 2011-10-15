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
		Assert.assertTrue(this.icecastRadioService.getItems().size() > 2);
	}
}
