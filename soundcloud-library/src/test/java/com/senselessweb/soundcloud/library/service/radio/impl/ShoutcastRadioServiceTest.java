/**
 * 
 */
package com.senselessweb.soundcloud.library.service.radio.impl;

import junit.framework.Assert;

import org.junit.Test;


/**
 * Testcases for the {@link ShoutcastRadioService}
 *
 * @author thomas
 */
public class ShoutcastRadioServiceTest
{

	/**
	 * The shoutcastRadioService to test
	 */
	private final ShoutcastRadioService shoutcastRadioService = new ShoutcastRadioService();
	
	/**
	 * Tries to fetch some items.
	 */
	@Test
	public void fetchItems() 
	{
		// Fetch items by keyword
		Assert.assertTrue(this.shoutcastRadioService.getItems("pop").size() > 10);

		// Fetch items without keyword
		Assert.assertTrue(this.shoutcastRadioService.getItems().size() > 10);
	}
}
