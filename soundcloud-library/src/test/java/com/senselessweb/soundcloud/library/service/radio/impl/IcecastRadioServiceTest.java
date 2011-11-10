package com.senselessweb.soundcloud.library.service.radio.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.senselessweb.soundcloud.library.service.radio.RemoteRadioLibraryService;
import com.senselessweb.soundcloud.library.service.radio.impl.IcecastRadioService;


/**
 * Testcases for the {@link IcecastRadioService}.
 * 
 * @author thomas
 */
@Ignore
public class IcecastRadioServiceTest
{
	
	/**
	 * The service to test
	 */
	private RemoteRadioLibraryService remoteRadioService; 
	
	/**
	 * The {@link ApplicationContext}.
	 */
	protected ApplicationContext context;
	
	
	/**
	 * Initializes the {@link ApplicationContext}. 
	 */
	@Before
	public final void setupSpringApplicationContext()
	{
		 this.context = new ClassPathXmlApplicationContext("soundcloud-library-applicationcontext.xml");
		 this.remoteRadioService = this.context.getBean(RemoteRadioLibraryService.class);
		 
	}
	

	/**
	 * Try to get all stations
	 */
	@Test
	public void tryToGetAllRadioStations()
	{
		Assert.assertTrue(this.remoteRadioService.getItems().size() > 2);
		Assert.assertTrue(this.remoteRadioService.getItems().size() > 2);
		Assert.assertTrue(this.remoteRadioService.getItems().size() > 2);
	}
}
