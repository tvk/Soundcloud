package com.senselessweb.soundcloud.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senselessweb.soundcloud.web.service.DisplayDataService;

/**
 * Web controller interface for the display widget.
 *  
 * @author thomas
 */
@Controller
@RequestMapping("/displayData/*")
public class DisplayDataController
{
	
	/**
	 * The DisplayDataService
	 */
	@Autowired DisplayDataService displayDataService;

	/**
	 * Returns (immediately) the current display data.
	 * 
	 * @return The current display data.
	 */
	@RequestMapping("/getInitialData")
	@ResponseBody
	public Map<String, String> getInitialData()
	{
		return this.displayDataService.getDisplayData().getProperties();
	}
	

	/**
	 * Returns the display data as soon as it gets updated.
	 * 
	 * @return The display data
	 */
	@RequestMapping("/waitForData")
	@ResponseBody
	public Map<String, String> waitForData()
	{
		return this.displayDataService.waitForDisplayData().getProperties();
	}
	
	
}
