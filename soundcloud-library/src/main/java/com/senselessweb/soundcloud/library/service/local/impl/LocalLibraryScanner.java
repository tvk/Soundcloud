/**
 * 
 */
package com.senselessweb.soundcloud.library.service.local.impl;

import java.util.LinkedList;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.library.LocalFolder;
import com.senselessweb.soundcloud.domain.library.LocalSubfolder;
import com.senselessweb.soundcloud.library.service.local.LocalLibraryService;

/**
 * Scans the local library to keep the items up2date. 
 *
 * @author thomas
 */
@Service
public class LocalLibraryScanner implements ApplicationListener<ContextRefreshedEvent>
{

	/**
	 * The log
	 */
	static final Log log = LogFactory.getLog(LocalLibraryScanner.class);
	
	
	/**
	 * The localLibraryService
	 */
	private final LocalLibraryService localLibraryService;
	
	/**
	 * Indicates if the scanner is still alive.
	 */
	boolean alive = true;

	@Autowired
	public LocalLibraryScanner(final LocalLibraryService localLibraryService) 
	{
		this.localLibraryService = localLibraryService;
	}
	

	/**
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	@Async public void onApplicationEvent(final ContextRefreshedEvent event)
	{
		final LinkedList<String> folders = new LinkedList<String>();
		
		while (this.alive)
		{
			final String next = folders.pollFirst();
			log.debug("Scanning " + next);
			final LocalFolder localFolder = LocalLibraryScanner.this.localLibraryService.getFolder(next);
			for (final LocalSubfolder subfolder : localFolder.getSubfolders())
				folders.add((next != null ? next + "/" : "") + subfolder.getName());
			
			try
			{
				Thread.sleep(5000);
			} 
			catch (final InterruptedException e)
			{
				throw new RuntimeException("Scanner was interrupted", e);
			}
		}
	}
	
	/**
	 * Sends a signal to stop scanning.
	 */
	@PreDestroy void stopScanning()
	{
		this.alive = false;
	}
	
}
