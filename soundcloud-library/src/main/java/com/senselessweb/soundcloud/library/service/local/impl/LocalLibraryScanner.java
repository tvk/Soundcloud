/**
 * 
 */
package com.senselessweb.soundcloud.library.service.local.impl;

import java.util.LinkedList;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LocalLibraryScanner
{

	/**
	 * The log
	 */
	static final Log log = LogFactory.getLog(LocalLibraryScanner.class);
	
	
	/**
	 * The localLibraryService
	 */
	@Autowired LocalLibraryService localLibraryService;
	
	/**
	 * Indicates if the scanner is still alive.
	 */
	boolean alive = true;
	
	/**
	 * Starts the scanning
	 * 
	 * @throws InterruptedException
	 */
	/*@PostConstruct*/ void startScanning() throws InterruptedException
	{
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			
			@Override
			public void run()
			{
				final LinkedList<String> folders = new LinkedList<String>();
				
				while (LocalLibraryScanner.this.alive)
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
		});
	}
	
	/**
	 * Sends a signal to stop scanning.
	 */
	@PreDestroy void stopScanning()
	{
		this.alive = false;
	}
	
}
