package com.senselessweb.soundcloud.storage.mongodb.service;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.senselessweb.soundcloud.domain.sources.FileSource;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.domain.sources.StreamSource;
import com.senselessweb.soundcloud.storage.mongodb.ApplicationContextTestBase;
import com.senselessweb.storage.PersistencyService;


/**
 * Testcases for the {@link PersistencyServiceImpl}.
 * 
 * @author thomas
 */
public class PersistencyServiceImplTest extends ApplicationContextTestBase
{

	/**
	 * Tries to store and restore a simple value. 
	 */
	@Test
	public void storeAndRestoreAProperty()
	{
		final PersistencyService persistencyService = (PersistencyService) this.context.getBean("persistencyService");
		
		final String prefix = "test";
		final String key = "myFirstProperty";
		final String value = "hello world";
		
		Assert.assertTrue(persistencyService.getAll(prefix).isEmpty());
		
		persistencyService.put(prefix, key, value);		
		Assert.assertEquals(value, persistencyService.get(prefix, key));
	}
	
	/**
	 * Tries to store and restore a complex bean.
	 */
	@Test
	public void testComplexBean()
	{
		final PersistencyService persistencyService = (PersistencyService) this.context.getBean("persistencyService");
		
		final MediaSource fileMediaSource = new FileSource("file source", new File("src/test/resources/empty"));
		final MediaSource urlMediaSource = new StreamSource("radio source", "http://wdr.de");
		final MediaSource urlMediaSource2 = new StreamSource("stream source", "http://radio.de");
		
		final ComplexBean bean3 = new ComplexBean(Lists.newArrayList(fileMediaSource, urlMediaSource, urlMediaSource2), "bean3");
		
		persistencyService.put("prefix", "key", bean3);
		
		Assert.assertEquals(bean3, persistencyService.get("prefix", "key"));
	}
	
}

/**
 * A complex bean for testing
 *
 * @author thomas
 */
class ComplexBean 
{

	/**
	 * The list
	 */
	final List<MediaSource> list;
	
	/**
	 * The name
	 */
	final String name;
	
	/**
	 * Constructor
	 * 
	 * @param list A list
	 * @param name The name
	 */
	public ComplexBean(final List<MediaSource> list, final String name)
	{
		this.name = name;
		this.list = list;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		final ComplexBean other = (ComplexBean) obj;
		return this.name.equals(other.name) && this.list.equals(other.list);
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return -1;
	}
}
