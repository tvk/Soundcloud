package com.senselessweb.soundcloud.library.service.radio.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.library.LibraryItem;
import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
import com.senselessweb.soundcloud.library.service.LibraryService;
import com.senselessweb.soundcloud.library.service.radio.RemoteRadioLibraryService;

/**
 * Implementation of the {@link LibraryService} that uses the icecast directory
 * service to find radio stations.
 * 
 * @author thomas
 *
 */
@Service
public class IcecastRadioService extends AbstractRadioService implements RemoteRadioLibraryService
{

	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(IcecastRadioService.class);

	/**
	 * The url where the icecast radio xml file is available.
	 */
	private static final String icecastUrl = "http://dir.xiph.org/yp.xml";
	
	/**
	 * The items
	 */
	private Collection<? extends LibraryItem> items;
	
	/**
	 * Initializes the library
	 * 
	 * @throws Exception
	 */
	public IcecastRadioService() throws Exception
	{
		try
		{
			this.items = loadItems();
		}
		catch (final Exception e)
		{
			log.error("Could not load icecast stations", e);
			this.items = Collections.emptySet();
		}
	}
	

	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#getItems()
	 */
	@Override
	public Collection<? extends LibraryItem> getItems()
	{
		return Collections.unmodifiableCollection(this.items);
	}
	
	/**
	 * Loads all items from the remote xml file.
	 * 
	 * @return The items. 
	 * 
	 * @throws Exception
	 */
	private static Collection<LibraryItem> loadItems() throws Exception
	{
		try
		{
			log.debug("Loading icecast stations from " + icecastUrl);
			final Collection<LibraryItem> items = new HashSet<LibraryItem>();
			final Document doc = Jsoup.connect(icecastUrl).get();
			
			final Map<String, Set<String>> urlsByServerName = new HashMap<String, Set<String>>();
			final Map<String, Set<String>> genresByServerName = new HashMap<String, Set<String>>();
			
			for (final Element entry : doc.select("entry"))
			{
				final String serverName = entry.select("server_name").text();
				final String url = entry.select("listen_url").text();
				final String genre = entry.select("genre").text();
				
				if (!urlsByServerName.containsKey(serverName)) urlsByServerName.put(serverName, new HashSet<String>());
				urlsByServerName.get(serverName).add(url);
				
				if (!genresByServerName.containsKey(serverName)) genresByServerName.put(serverName, new HashSet<String>());
				genresByServerName.get(serverName).add(genre);
			}
			
			int i = 0;		
			for (final String serverName : urlsByServerName.keySet())
			{
				items.add(new RadioLibraryItem(String.valueOf(i), serverName, urlsByServerName.get(serverName), genresByServerName.get(serverName)));			
				i++;
			}
			
			log.debug("Received " + items.size() + " items");
			return items;
		} 
		catch (final IOException e)
		{
			throw new RuntimeException("Could not load icecast stations", e);
		}
	}

}
