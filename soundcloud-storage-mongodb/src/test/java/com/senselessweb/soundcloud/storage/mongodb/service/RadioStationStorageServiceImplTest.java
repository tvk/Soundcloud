package com.senselessweb.soundcloud.storage.mongodb.service;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
import com.senselessweb.soundcloud.storage.mongodb.ApplicationContextTestBase;
import com.senselessweb.storage.library.UserRadioStorageService;

/**
 * Testcases for the {@link UserRadioStorageServiceImpl}
 * 
 * @author thomas
 */
public class RadioStationStorageServiceImplTest extends ApplicationContextTestBase
{

	/**
	 * Try to store some radio stations
	 * @throws MalformedURLException 
	 */
	@Test
	public void saveRadioStations() throws MalformedURLException
	{
		final UserRadioStorageService service = this.context.getBean(UserRadioStorageService.class);
		
		Assert.assertTrue(service.getAllRadioStations().isEmpty());
		
		final RadioLibraryItem radio1 = new RadioLibraryItem(null, "WDR 2", "https://www.wdr2-radio.de", Lists.newArrayList("pop", "schrott", "gelaber", "werbung"));
		final RadioLibraryItem radio2 = new RadioLibraryItem(null, "WDR 4", "https://www.wdr4-radio.de", Lists.newArrayList("volksmusik", "schrott", "gelaber"));
		final RadioLibraryItem radio2Clone = new RadioLibraryItem(null, "WDR 4", "https://www.wdr4-radio.de", Lists.newArrayList("volksmusik", "schrott", "unsinn"));
		
		// Add a station
		service.createRadioStation(radio1);
		Assert.assertEquals(1, service.getAllRadioStations().size());
		Assert.assertTrue(service.getAllRadioStations().contains(radio1));
		
		// Add a second station
		service.createRadioStation(radio2);
		Assert.assertEquals(2, service.getAllRadioStations().size());
		Assert.assertTrue(service.getAllRadioStations().containsAll(Lists.newArrayList(radio1, radio2)));

		// Add a clone of the second station
		service.createRadioStation(radio2Clone);
		Assert.assertEquals(2, service.getAllRadioStations().size());
		Assert.assertTrue(service.getAllRadioStations().containsAll(Lists.newArrayList(radio1, radio2Clone)));
		
		// Each station should have a unique id
		final Set<String> ids = new HashSet<String>();
		for (final RadioLibraryItem item : service.getAllRadioStations()) ids.add(item.getId());
		Assert.assertEquals(2, ids.size());
		
	}
}
