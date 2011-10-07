package com.senselessweb.soundcloud.mediasupport.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.mediasupport.service.Playlist;
import com.senselessweb.soundcloud.mediasupport.service.impl.MessageMediator;

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
	 * The messageMediator
	 */
	private final MessageMediator messageMediator;

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
	 * Constructor
	 *
	 * @param messageMediator The message mediator.
	 */
	public DefaultPlaylist(final MessageMediator messageMediator)
	{
		this.messageMediator = messageMediator;
	}


	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#add(MediaSource)
	 */
	@Override
	public void add(final MediaSource mediaSource)
	{
		this.playlist.add(mediaSource);
		if (this.current == -1) this.current = 0;
		
		log.debug("Added " + mediaSource);
		this.messageMediator.playlistChanged(ChangeEvent.OTHER);		
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.Playlist#set(java.util.Collection)
	 */
	@Override
	public void set(final Collection<? extends MediaSource> playlist)
	{
		this.playlist.clear();
		this.playlist.addAll(playlist);
		
		this.current = this.playlist.isEmpty() ? -1 : 0;
		this.messageMediator.playlistChanged(ChangeEvent.OTHER);
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
			this.messageMediator.playlistChanged(ChangeEvent.NEXT);
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
			this.messageMediator.playlistChanged(ChangeEvent.PREVIOUS);
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
