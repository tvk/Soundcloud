package com.senselessweb.soundcloud.web.controller.library;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.senselessweb.soundcloud.domain.library.LibraryItem;
import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
import com.senselessweb.soundcloud.library.service.radio.RemoteRadioLibraryService;
import com.senselessweb.soundcloud.library.service.radio.UserRadioLibraryService;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;

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
	 * The UserRadioLibraryService
	 */
	@Autowired UserRadioLibraryService userRadioLibraryService;

	/**
	 * The RemoteRadioLibraryService
	 */
	@Autowired RemoteRadioLibraryService remoteRadioLibraryService;
	
	/**
	 * The mediaPlayer
	 */
	@Autowired MediaPlayer mediaPlayer;

	
	/**
	 * Returns all user radio stations
	 * 
	 * @return All user radio stations.
	 */
	@RequestMapping("/getUserStations")
	@ResponseBody
	public Collection<? extends LibraryItem> getUserStations()
	{
		return this.userRadioLibraryService.getAllItems();
	}
	
	/**
	 * Returns remote radio stations
	 * 
	 * @param limit The maximum number of radio stations to return
	 * 
	 * @return Some random remote radio stations.
	 */
	@RequestMapping("/getRemoteStations")
	@ResponseBody
	public Collection<? extends LibraryItem> getRemoteStations(@RequestParam(defaultValue="25") int limit)
	{
		return this.remoteRadioLibraryService.getRandomItems(limit);
	}
	
	
	/**
	 * Adds a new user radio station to the library
	 * 
	 * @param name The name of the new station
	 * @param url The url of the new station 
	 * @param genres The genres of the new station
	 * 
	 * @return The created {@link RadioLibraryItem} 
	 */
	@RequestMapping("/create")
	@ResponseBody
	public RadioLibraryItem create(final @RequestParam String name, final @RequestParam String url, final @RequestParam String genres)
	{
		return this.userRadioLibraryService.store(name, url, genres.split(","));
	}
	
	/**
	 * Stores a remote item in the user storage service.
	 * 
	 * @param id The id of the remote item.
	 * 
	 * @return The new stored user item.
	 */
	@RequestMapping("/store")
	@ResponseBody
	public RadioLibraryItem store(final @RequestParam String id)
	{
		final RadioLibraryItem item = (RadioLibraryItem) this.remoteRadioLibraryService.findById(id);
		return this.userRadioLibraryService.store(item.getLongTitle(), item.getUrl(), item.getGenres().toArray(new String[0]));
	}
	
	/**
	 * Plays the user station with the given id.
	 * 
	 * @param type The type. Either remote od my-stations
	 * @param id The id.
	 */
	@RequestMapping("/play")
	@ResponseStatus(HttpStatus.OK)
	public void play(final @RequestParam String type, final @RequestParam String id)
	{
		final LibraryItem station;
		
		if (type.equals("remote")) station = this.remoteRadioLibraryService.findById(id);
		else station = this.userRadioLibraryService.findById(id);
		
		this.mediaPlayer.stop();
		this.mediaPlayer.getCurrentPlaylist().set(station.asMediaSources());
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
		this.userRadioLibraryService.delete(id);
	}
}
