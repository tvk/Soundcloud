package com.senselessweb.soundcloud.library.service.radio.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
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
	 * A randomizer
	 */
	private final Random random = new Random();


	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#findById(java.lang.String)
	 */
	@Override
	public LibraryItem findById(final String id)
	{
		for (final LibraryItem item : this.getAllItems())
			if (id.equals(item.getId())) return item;

		return null;
	}

	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#getRandomItems(int)
	 */
	@Override
	public Collection<? extends LibraryItem> getRandomItems(final int limit)
	{
		if (this.getAllItems().size() <= limit) return this.getAllItems();
		
		final Set<LibraryItem> result = new HashSet<LibraryItem>();
		final List<LibraryItem> asList = Lists.newArrayList(this.getAllItems());
		while (result.size() < limit)
		{
			result.add(asList.get(this.random.nextInt(this.getAllItems().size() - 1)));
		}
		return result;
	}

	
	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#findByName(java.lang.String)
	 */
	@Override
	public Collection<? extends LibraryItem> findByName(final String name)
	{
		return Collections2.filter(this.getAllItems(), new Predicate<LibraryItem>() {
			@Override
			public boolean apply(final LibraryItem input)
			{
				return input.getLongTitle().contains(name);
			}
		});
	}

	/**
	 * @see com.senselessweb.soundcloud.library.service.LibraryService#findByGenre(java.lang.String)
	 */
	@Override
	public Collection<? extends LibraryItem> findByGenre(final String genre)
	{
		return Collections2.filter(this.getAllItems(), new Predicate<LibraryItem>() {
			@Override
			public boolean apply(final LibraryItem input)
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
}
