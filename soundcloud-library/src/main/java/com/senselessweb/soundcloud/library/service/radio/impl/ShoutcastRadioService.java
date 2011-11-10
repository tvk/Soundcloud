/**
 * 
 */
package com.senselessweb.soundcloud.library.service.radio.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.domain.library.LibraryItem;
import com.senselessweb.soundcloud.domain.library.RadioLibraryItem;
import com.senselessweb.soundcloud.library.service.radio.RemoteRadioLibraryService;

/**
 * The shoutcast radio service fetches radio stations from the 
 * <a href="http://www.shoutcast.com">shoutcast radio library</a>.
 *
 * @author thomas
 */
@Service("shoutcastRadioService")
public class ShoutcastRadioService implements RemoteRadioLibraryService
{
	
	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(ShoutcastRadioService.class);
	
	/**
	 * The base request url
	 */
	private static final String requestUrl = "http://www.shoutcast.com/search-ajax/";
	
	/**
	 * A default keyword to search for as the request uri does not support
	 * searching without keyword.
	 */
	private static final String defaultKeyword = "MP3";
	
	/**
	 * The post parameters to send with every request 
	 */
	private static final List<NameValuePair> parameters = Lists.<NameValuePair>newArrayList(
			new BasicNameValuePair("count", "30"),
			new BasicNameValuePair("mode", "listenershead2"),
			new BasicNameValuePair("order", "desc2"));

	
	/**
	 * Cached items by url
	 */
	private final Map<String, RadioLibraryItem> cachedItemsByUrl = new HashMap<String, RadioLibraryItem>();
	
	/**
	 * Cached items by ido
	 */
	private final Map<String, RadioLibraryItem> cachedItemsById = new HashMap<String, RadioLibraryItem>();
	
	/**
	 * Cached results by keyword
	 */
	private final Map<String, Collection<RadioLibraryItem>> cachedResults = new HashMap<String, Collection<RadioLibraryItem>>();
	
	/**
	 * An id counter for new radio library items 
	 */
	private int id = 1;
	
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.radio.RemoteRadioLibraryService#getItems()
	 */
	@Override
	public Collection<RadioLibraryItem> getItems()
	{
		return this.fetchItems(defaultKeyword);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#findById(java.lang.String)
	 */
	@Override
	public LibraryItem findById(final String id)
	{
		return this.cachedItemsById.get(id);
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#getItems(java.lang.String)
	 */
	@Override
	public Collection<RadioLibraryItem> getItems(final String keyword)
	{
		return this.fetchItems(StringUtils.isBlank(keyword) ? defaultKeyword : keyword);
	}
	
	/**
	 * Fetches the radio library items.
	 * 
	 * @param keyword The keyword. Must not be blank as this is not supported by the search api.
	 * 
	 * @return The found radio library items.
	 */
	private synchronized Collection<RadioLibraryItem> fetchItems(final String keyword)
	{
		if (StringUtils.isBlank(keyword)) 
			throw new IllegalArgumentException("keyword must not be blank");
		if (this.cachedResults.containsKey(keyword))
			return this.cachedResults.get(keyword);
		
		try
		{
			// Send the request
			final HttpClient client = new CachingHttpClient();
			final HttpPost post = new HttpPost(requestUrl + keyword);
			post.setEntity(new UrlEncodedFormEntity(parameters));
			
			log.debug("Fetching radio items from " + requestUrl + keyword);
			String response = client.execute(post, new BasicResponseHandler());

			// Parse the response
			final Collection<RadioLibraryItem> result = new ArrayList<RadioLibraryItem>();
			final Document document = Jsoup.parse(response);
			for (final Element element : document.getElementsByClass("dirlist"))
			{
				final String url = element.select("a.clickabletitle").attr("href");
				if (this.cachedItemsByUrl.containsKey(url))
				{
					result.add(this.cachedItemsByUrl.get(url));
				}
				else
				{
					final String id = "shoutcast-" + this.id;
					final String title = element.select("a.clickabletitle").text();
					final Collection<String> genres = Collections2.transform(
							element.select(".metaLayer div.heading div").first().select("a"), new Function<Element, String>() {
								@Override public String apply(final Element input)
								{
									return input.text();
								}
							}); 
					
					final RadioLibraryItem item = new RadioLibraryItem(id, title, Collections.singleton(url), genres);
					this.cachedItemsByUrl.put(url, item);
					this.cachedItemsById.put(id, item);
					result.add(item);
					this.id++;
				}
			}
			
			log.debug("Fetched " + result.size() + " items");
			this.cachedResults.put(keyword, result);
			return result;
		} 
		catch (final Exception e)
		{
			throw new RuntimeException("Could not fetch from " + requestUrl + keyword, e);
		}
	}
}