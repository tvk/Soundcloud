/**
 * 
 */
package com.senselessweb.soundcloud.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.mediasupport.gstreamer.elements.PanoramaBridge;

/**
 * Web interface for the balance control.
 *
 * @author thomas
 */
@Controller
@RequestMapping("/balance/*")
public class BalanceController
{
	
	/**
	 * The panoramaBridge
	 */
	private final PanoramaBridge panoramaBridge;

	@Autowired
	public BalanceController(final PanoramaBridge panoramaBridge) 
	{
		this.panoramaBridge = panoramaBridge;
	}
	
	/**
	 * Sets the balancing value.
	 * 
	 * @param value The value. Must be between -1.0 and 1.0.
	 */
	@RequestMapping("/setData")
	@ResponseStatus(HttpStatus.OK)
	public void setData(final @RequestParam("value") double value)
	{
		this.panoramaBridge.setPanorama(value);
	}
	
	/**
	 * Returns the current balancing value.
	 * 
	 * @return The current balancing value. Always a value between -1.0 and 1.0
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public double getData()
	{
		return this.panoramaBridge.getPanorama();
	}
}
