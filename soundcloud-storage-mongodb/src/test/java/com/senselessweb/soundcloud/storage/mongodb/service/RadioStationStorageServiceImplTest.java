package com.senselessweb.soundcloud.storage.mongodb.service;

import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.domain.StreamSource;
import com.senselessweb.soundcloud.storage.mongodb.ApplicationContextTestBase;
import com.senselessweb.storage.RadioStationStorageService;

/**
 * Testcases for the {@link RadioStationStorageServiceImpl}
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
		final RadioStationStorageService service = this.context.getBean(RadioStationStorageService.class);
		
		Assert.assertTrue(service.getAllRadioStations().isEmpty());
		
		final StreamSource radio1 = new StreamSource("WDR 2", "https://www.wdr2-radio.de", new String[] {"pop", "schrott", "gelaber", "werbung"});
		final StreamSource radio2 = new StreamSource("WDR 4", "https://www.wdr4-radio.de", new String[] {"volksmusik", "schrott", "gelaber"});
		final StreamSource radio2Clone = new StreamSource("WDR 4", "https://www.wdr4-radio.de", new String[] {"volksmusik", "schrott", "unsinn"});
		
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
		
	}
}
