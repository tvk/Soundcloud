package com.senselessweb.soundcloud.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

@Controller
@RequestMapping("/volume/*")
public class VolumeController
{
	
	@Autowired MediaPlayer mediaPlayer;

	@RequestMapping("/setVolume")
	@ResponseStatus(HttpStatus.OK)
	public void setVolume(final @RequestParam("volume") double volume)
	{
		this.mediaPlayer.getVolumeControl().setVolume(volume);
	}
	

	@RequestMapping("/setMute")
	@ResponseStatus(HttpStatus.OK)
	public void setMute(final @RequestParam("mute") boolean mute)
	{
		this.mediaPlayer.getVolumeControl().setMute(mute);
	}
	
	
	
	@RequestMapping("/getData")
	@ResponseBody
	public Model getData(final Model model)
	{
		model.addAttribute("volume", this.mediaPlayer.getVolumeControl().getVolume());
		model.addAttribute("mute", this.mediaPlayer.getVolumeControl().getMute());
		return model;
	}
}
