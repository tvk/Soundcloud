package com.senselessweb.soundcloud.mediasupport.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.senselessweb.soundcloud.mediasupport.service.Playlist;

/**
 * Default playlist implementation
 * 
 * @author thomas
 */
public class DefaultPlaylist implements Playlist
{
	

	/**
	 * Logger
	 */
	static final Log log = LogFactory.getLog(DefaultPlaylist.class);
	
	/**
	 * ID Generator for the playlist entries
	 */
	private final AtomicInteger entryIdGenerator = new AtomicInteger();

	/**
	 * The list of media sources in this playlist.
	 */
	private final List<PlaylistEntry> playlist = new ArrayList<PlaylistEntry>();
	
	/**
	 * A pointer pointing to the current source in the playlist. This is only null 
	 * when the playlist is empty. Otherwise this is always a valid entry inside the playlist.
	 */
	private PlaylistEntry current = null;



	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#add(com.senselessweb.soundcloud.mediasupport.domain.MediaSource)
	 */
	@Override
	public void add(final MediaSource mediaSource)
	{
		final PlaylistEntry entry = new PlaylistEntry(this.entryIdGenerator.incrementAndGet(), mediaSource); 
		this.playlist.add(entry);
		if (this.current == null) this.current = entry; 
		
		log.debug("Added " + mediaSource);
		log.debug("Playlist is now: " + this);
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#next()
	 */
	@Override
	public boolean next()
	{
		final int currentIndex = this.playlist.indexOf(this.current);
		if (currentIndex >= 0 && currentIndex < this.playlist.size() - 1)
		{
			this.current = this.playlist.get(currentIndex + 1);
			return true;
		}
		else
		{
			return false;
		}
	}
	

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#previous()
	 */
	@Override
	public boolean previous()
	{
		final int currentIndex = this.playlist.indexOf(this.current);
		if (currentIndex > 0)
		{
			this.current = this.playlist.get(currentIndex - 1);
			return true;
		}
		else
		{
			return false;
		}
	}	

	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#getCurrent()
	 */
	@Override
	public MediaSource getCurrent()
	{
		return this.current != null ? this.current.getMediaSource() : null;
	}
	


	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#getAll()
	 */
	@Override
	public List<MediaSource> getAll()
	{
		return new ArrayList<MediaSource>(
				Collections2.transform(this.playlist, new Function<PlaylistEntry, MediaSource>() {
			
			@Override
			public MediaSource apply(final PlaylistEntry input)
			{
				return input.getMediaSource();
			}
		}));
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("Playlist[");
		boolean first = true;
		for (final PlaylistEntry source : this.playlist)
		{
			if (!first) sb.append(",");
			sb.append(source.equals(this.current) ? "<" + source + ">" :  source);
			first = false;
		}
		return sb.append("]").toString();
	}


}


/**
 * A playlist entry.
 * 
 * @author thomas
 */
class PlaylistEntry
{
	
	/**
	 * A unique entry id
	 */
	private final int id;
	
	/**
	 * The mediasource
	 */
	private final MediaSource mediaSource;
	
	/**
	 * Constructor 
	 * 
	 * @param id The unique id inside this playlist
	 * @param mediaSource The {@link MediaSource}
	 */
	public PlaylistEntry(final int id, final MediaSource mediaSource)
	{
		this.id = id;
		this.mediaSource = mediaSource;
	}
	
	/**
	 * Returns the mediasource
	 * 
	 * @return The mediasource
	 */
	public MediaSource getMediaSource()
	{
		return this.mediaSource;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.id + ":" + this.mediaSource;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		return (obj instanceof PlaylistEntry) && this.id == ((PlaylistEntry) obj).id;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
}

