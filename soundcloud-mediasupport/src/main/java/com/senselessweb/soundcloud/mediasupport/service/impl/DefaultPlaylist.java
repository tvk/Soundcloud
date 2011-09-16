package com.senselessweb.soundcloud.mediasupport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.senselessweb.soundcloud.mediasupport.domain.MediaSource;
import com.senselessweb.soundcloud.mediasupport.service.Playlist;

/**
 * Default playlist implementation
 * 
 * @author thomas
 */
public class DefaultPlaylist implements Playlist
{
	
	/**
	 * ID Generator for the playlist entries
	 */
	private final AtomicInteger entryIdGenerator = new AtomicInteger();

	/**
	 * The list of media sources in this playlist.
	 */
	private final List<PlaylistEntry> playlist = new ArrayList<PlaylistEntry>();
	
	/**
	 * A pointer pointing to the current source in the playlist.
	 */
	private PlaylistEntry current = null;

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#add(com.senselessweb.soundcloud.mediasupport.domain.MediaSource)
	 */
	@Override
	public void add(final MediaSource mediaSource)
	{
		this.playlist.add(new PlaylistEntry(this.entryIdGenerator.incrementAndGet(), mediaSource));
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#getNext()
	 */
	@Override
	public MediaSource getNext()
	{
		if (this.playlist.isEmpty()) return null;
		
		final PlaylistEntry next;
		if (this.current == null) 
		{
			// No source returned yet
			next = this.playlist.get(0);
		}
		else
		{
			final int currentIndex = this.playlist.indexOf(this.current);
			if (currentIndex == -1 || currentIndex == this.playlist.size() - 1) 
			{
				// The current was the last source in the playlist
				return null;
			}
			else
			{
				// Return the next source
				next = this.playlist.get(currentIndex + 1);
			}
		}
		
		this.current = next;
		return next == null ? null : next.getMediaSource();
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
		return "[" + this.id + ":" + this.mediaSource + "]";
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

