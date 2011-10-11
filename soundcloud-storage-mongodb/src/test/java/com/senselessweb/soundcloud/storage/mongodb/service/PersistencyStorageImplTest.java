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
		
		final String prefix = "test";
		final String key = "myFirstProperty";
		final String value = "hello world";
		
		Assert.assertTrue(persistencyService.getAll(prefix).isEmpty());
		
		persistencyService.put(prefix, key, value);		
		Assert.assertEquals(value, persistencyService.get(prefix, key));
	}
}
