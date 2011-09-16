package com.senselessweb.soundcloud.web.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senselessweb.soundcloud.mediasupport.domain.MediaSource;
import com.senselessweb.soundcloud.mediasupport.domain.StreamSource;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

@Controller
public class TestController
{
	
	@Autowired MediaPlayer mediaPlayer;

	@RequestMapping("/playTestFile")
	public String playTestFile() throws Exception
	{
		final MediaSource source = new StreamSource(new URL("http://ubuntu.hbr1.com:19800/trance.ogg"));
		this.mediaPlayer.getCurrentPlaylist().add(source);
		this.mediaPlayer.play();
		
		return "ok";
	}
	
}
