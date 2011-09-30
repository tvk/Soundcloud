package com.senselessweb.soundcloud.web.controller.library;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.domain.StreamSource;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.storage.RadioStationStorageService;

/**
 * Web controller interface for the radio library.
 * 
 * @author thomas
 */
@Controller
@RequestMapping("/library/radio/*")
public class RadioLibraryController
{
	
	/**
	 * The radioStationStorageService
	 */
	@Autowired RadioStationStorageService radioStationStorageService;

	/**
	 * The mediaPlayer
	 */
	@Autowired MediaPlayer mediaPlayer;

	
	/**
	 * Returns all user radio stations
	 * 
	 * @param model The current model.
	 * 
	 * @return All user radio stations.
	 */
	@RequestMapping("/getUserStations")
	@ResponseBody
	public Collection<StreamSource> getUserStations(final Model model)
	{
		return this.radioStationStorageService.getAllRadioStations();
	}
	
	
	/**
	 * Adds a new user radio station to the library
	 * 
	 * @param name The name of the new station
	 * @param url The url of the new station 
	 * @param genres The genres of the new station
	 * 
	 * @return The created {@link StreamSource} 
	 */
	@RequestMapping("/create")
	@ResponseBody
	public StreamSource create(final @RequestParam String name, final @RequestParam String url, final @RequestParam String genres)
	{
		final StreamSource streamSource = new StreamSource(name, url, genres != null ? genres.split(",") : null);
		return this.radioStationStorageService.createRadioStation(streamSource);
	}
	
	/**
	 * Plays the user station with the given id.
	 * 
	 * @param id The id.
	 */
	@RequestMapping("/play")
	@ResponseStatus(HttpStatus.OK)
	public void play(final @RequestParam String id)
	{
		final StreamSource station = this.radioStationStorageService.getRadioStation(id);
		this.mediaPlayer.stop();
		this.mediaPlayer.getCurrentPlaylist().set(Collections.singleton(station));
		this.mediaPlayer.play();
	}
	
	/**
	 * Deletes the user station with the given id.
	 * 
	 * @param id The id.
	 */
	@RequestMapping("/delete")
	@ResponseStatus(HttpStatus.OK)
	public void delete(final @RequestParam String id)
	{
		this.radioStationStorageService.deleteRadioStation(id);
	}
}
