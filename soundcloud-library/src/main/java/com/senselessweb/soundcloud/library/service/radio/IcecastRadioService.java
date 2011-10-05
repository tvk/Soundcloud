package com.senselessweb.soundcloud.library.service.radio;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.library.domain.LibraryItem;
import com.senselessweb.soundcloud.library.domain.radio.IcecastRadioItem;
import com.senselessweb.soundcloud.library.service.LibraryService;

/**
 * Implementation of the {@link LibraryService} that uses the icecast directory
 * service to find radio stations.
 * 
 * @author thomas
 *
 */
public class IcecastRadioService implements LibraryService
{


	/**
	 * The url where the icecast radio xml file is available.
	 */
	private static final String icecastUrl = "http://dir.xiph.org/yp.xml";
	
	/**
	 * All items.
	 */
	private final Set<IcecastRadioItem> items = new HashSet<IcecastRadioItem>();
	
	/**
	 * Initializes the library
	 * 
	 * @throws Exception
	 */
	public IcecastRadioService() throws Exception
	{
		this.loadItems();
	}
	

	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#getAllItems()
	 */
	@Override
	public Collection<? extends LibraryItem> getAllItems()
	{
		return Collections.unmodifiableCollection(this.items);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#findByName(java.lang.String)
	 */
	@Override
	public Collection<? extends LibraryItem> findByName(final String name)
	{
		return Collections2.filter(this.items, new Predicate<IcecastRadioItem>() {
			@Override
			public boolean apply(final IcecastRadioItem input)
			{
				return input.getName().contains(name);
			}
		});
	}

	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#findByGenre(java.lang.String)
	 */
	@Override
	public Collection<? extends LibraryItem> findByGenre(final String genre)
	{
		return Collections2.filter(this.items, new Predicate<IcecastRadioItem>() {
			@Override
			public boolean apply(final IcecastRadioItem input)
			{
				return !Collections2.filter(input.getGenres(), new Predicate<String>() {
					@Override
					public boolean apply(final String input)
					{
						return input.contains(genre);
					}
				}).isEmpty();
			}
		});
	}
	
	/**
	 * Loads all items from the remote xml file.
	 * 
	 * @throws Exception
	 */
	private void loadItems() throws Exception
	{
		
		final Document doc = Jsoup.connect(icecastUrl).get();
		for (final Element element : doc.select("entry"))
		{
			int bitrate = -1;
			try 
			{
				bitrate = Integer.valueOf(element.select("bitrate").text());
			}
			catch (final NumberFormatException e) { /* do nothing */ }
			
			
			final Collection<String> genres = Lists.newArrayList(element.select("genre").text().split(","));
			final String name = element.select("server_name").text(); 
			
			final IcecastRadioItem item = new IcecastRadioItem(
					name,
					element.select("listen_url").text(),
					bitrate, genres,
					element.select("current_song").text());
			
			this.items.add(item);
		}
	}

	
}
