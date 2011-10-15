package com.senselessweb.soundcloud.library.service.radio.impl;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.senselessweb.soundcloud.domain.library.LibraryItem;
import com.senselessweb.soundcloud.library.service.LibraryService;

/**
 * Base class for radio service implementations.
 * 
 * @author thomas
 */
public abstract class AbstractRadioService implements LibraryService
{

	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#getItems(String)
	 */
	@Override
	public Collection<? extends LibraryItem> getItems(final String keyword)
	{
		if (StringUtils.isBlank(keyword)) return Collections.unmodifiableCollection(this.getItems());
		
		return Collections2.filter(this.getItems(), new Predicate<LibraryItem>() 
		{
			/** @see com.google.common.base.Predicate#apply(java.lang.Object) */
			@Override
			public boolean apply(final LibraryItem input)
			{
				for (final String k : input.getKeywords())
					if (k.toLowerCase().contains(keyword.trim().toLowerCase())) return true;
				return false;
			}
		});
	}
	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#findById(java.lang.String)
	 */
	@Override
	public LibraryItem findById(final String id)
	{
		for (final LibraryItem item : this.getItems(null))
			if (id.equals(item.getId())) return item;

		return null;
	}

}
