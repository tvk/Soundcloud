package com.senselessweb.soundcloud.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.mediasupport.service.Equalizer;
import com.senselessweb.soundcloud.mediasupport.service.Equalizer.Band;

/**
 * Web controller interface for the {@link Equalizer}
 * 
 * @author thomas
 */
@Controller
@RequestMapping("/equalizer/*")
public class EqualizerController
{

	/**
	 * The Equalizer
	 */
	private final Equalizer equalizer;
	
	@Autowired
	public EqualizerController(final Equalizer equalizer) 
	{
		this.equalizer = equalizer;
	}

	/**
	 * Returns the current equalizer values.
	 * 
	 * @param model The current model.
	 * 
	 * @return A map with all {@link Equalizer.Band}s and their values.
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public Model getData(final Model model)
	{
		for (final Band band : Equalizer.Band.values())
			model.addAttribute(band.name(), this.equalizer.getValue(band));

		return model;
	}

	/**
	 * Sets the equalizer values.
	 * 
	 * @param values A map with the new value for each {@link Equalizer.Band}
	 */
	@RequestMapping("/setData")
	@ResponseStatus(HttpStatus.OK)
	public void setData(final @RequestParam Map<String, String> values)
	{
		for (final Map.Entry<String, String> entry : values.entrySet()) 
			this.equalizer.setValue(Band.valueOf(entry.getKey()), Double.valueOf(entry.getValue()));
	}
}
