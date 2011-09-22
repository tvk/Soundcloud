package com.senselessweb.soundcloud.storage.mongodb.service;

import junit.framework.Assert;

import org.junit.Test;

import com.senselessweb.soundcloud.storage.mongodb.ApplicationContextTestBase;
import com.senselessweb.storage.PersistencyService;


public class PersistencyStorageImplTest extends ApplicationContextTestBase
{

	@Test
	public void storeAndRestoreAProperty()
	{
		final String key = "test.myFirstProperty";
		final String value = "hello world";
		
		final PersistencyService persistencyService = (PersistencyService) this.context.getBean("persistencyService");
		persistencyService.put(key, value);
		
		Assert.assertEquals(value, persistencyService.get(key));
	}
}
