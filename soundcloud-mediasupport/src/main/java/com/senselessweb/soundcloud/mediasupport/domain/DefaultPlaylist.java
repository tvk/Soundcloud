package com.senselessweb.soundcloud.mediasupport.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	private static final Log log = LogFactory.getLog(DefaultPlaylist.class);

	/**
	 * The list of media sources in this playlist.
	 */
	private final List<MediaSource> playlist = new ArrayList<MediaSource>();
	
	/**
	 * An index pointing to the current source in the playlist. This is only -1
	 * when the playlist is empty. Otherwise this is always a valid entry inside the playlist.
	 */
	private int current = -1;



	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#add(com.senselessweb.soundcloud.mediasupport.domain.MediaSource)
	 */
	@Override
	public void add(final MediaSource mediaSource)
	{
		this.playlist.add(mediaSource);
		if (this.current == -1) this.current = 0;
		
		log.debug("Added " + mediaSource);
		log.debug("Playlist is now: " + this);
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#next()
	 */
	@Override
	public boolean next()
	{
		if (this.current < this.playlist.size() - 1) 
		{
			this.current++;
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
			this.current--;
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
		return this.current != -1 ? this.playlist.get(this.current) : null;
	}
	

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#getAll()
	 */
	@Override
	public List<MediaSource> getAll()
	{
		return Collections.unmodifiableList(this.playlist);
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("Playlist[");
		int i = 0;
		for (final MediaSource source : this.playlist)
		{
			if (i == 0) sb.append(",");
			sb.append(i == this.current ? "<" + source + ">" :  source);
		}
		return sb.append("]").toString();
	}

}
