package com.senselessweb.soundcloud.storage.mongodb.service;

import junit.framework.Assert;

import org.junit.Test;

import com.senselessweb.soundcloud.storage.mongodb.ApplicationContextTestBase;
import com.senselessweb.storage.PersistencyService;


/**
 * Testcases for the {@link PersistencyServiceImpl}.
 * 
 * @author thomas
 */
public class PersistencyStorageImplTest extends ApplicationContextTestBase
{

	/**
	 * Tries to store and restore a simple value. 
	 */
	@Test
	public void storeAndRestoreAProperty()
	{
		final PersistencyService persistencyService = (PersistencyService) this.context.getBean("persistencyService");
		
		final String key = "test.myFirstProperty";
		final String value = "hello world";
		
		Assert.assertFalse(persistencyService.contains(key));
		
		persistencyService.put(key, value);		
		Assert.assertTrue(persistencyService.contains(key));
		Assert.assertEquals(value, persistencyService.get(key));
	}
}
