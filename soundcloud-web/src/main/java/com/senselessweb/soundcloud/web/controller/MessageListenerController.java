package com.senselessweb.soundcloud.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senselessweb.soundcloud.web.service.Event;
import com.senselessweb.soundcloud.web.service.EventQueue;

/**
 * Comet like controller that blocks the requests until a new message is available.
 * 
 * @author thomas
 */
@Controller
@RequestMapping("/messageListener/*")
public class MessageListenerController
{

	/**
	 * The event queue
	 */
	@Autowired EventQueue eventQueue;
	

	/**
	 * Returns the next event as soon as it is available.
	 * 
	 * @return The next event.
	 */
	@RequestMapping("/getNextEvent")
	@ResponseBody
	public Event getNextEvent()
	{
		return this.eventQueue.getNextEvent();
	}
}
