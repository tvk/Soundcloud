package com.senselessweb.soundcloud.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senselessweb.soundcloud.mediasupport.service.Equalizer;
import com.senselessweb.soundcloud.mediasupport.service.Equalizer.Band;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

@Controller
@RequestMapping("/equalizer/*")
public class EqualizerController
{

	@Autowired MediaPlayer mediaPlayer;

	@RequestMapping("/getData")
	@ResponseBody
	public Model getData(final Model model)
	{
		for (final Band band : Equalizer.Band.values())
			model.addAttribute(band.name(), this.mediaPlayer.getEqualizer().getValue(band));

		return model;
	}
}
