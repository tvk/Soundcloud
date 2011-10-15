package com.senselessweb.soundcloud.library.service.radio.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
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
	 * The url where the icecast radio xml file is available.
	 */
	private static final String icecastUrl = "http://dir.xiph.org/yp.xml";
	
	/**
	 * The items
	 */
	private final Collection<? extends LibraryItem> items;
	
	/**
	 * Initializes the library
	 * 
	 * @throws Exception
	 */
	public IcecastRadioService() throws Exception
	{
		this.items = loadItems();
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
		final Collection<LibraryItem> items = new HashSet<LibraryItem>();
		final Document doc = Jsoup.connect(icecastUrl).get();
		
		int i = 0;
		for (final Element element : doc.select("entry"))
		{
			
			final Collection<String> genres = Lists.newArrayList(element.select("genre").text().split(" "));
			final String name = element.select("server_name").text(); 
			
			final RadioLibraryItem item = new RadioLibraryItem(
					"remote-" + i, name,
					element.select("listen_url").text(),
					genres);
			
			items.add(item);
			i++;
		}
		return items;
	}

}
