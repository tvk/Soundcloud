package com.senselessweb.soundcloud.web.service.impl;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.library.LocalFile;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.library.service.local.LocalLibraryService;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.web.service.DisplayDataService;

/**
 * Default implementation of the {@link DisplayDataService}.
 * 
 * @author thomas
 */
@Service
@Scope(proxyMode=ScopedProxyMode.INTERFACES, value="session")
public class DisplayDataServiceImpl extends AbstractMessageAdapter implements DisplayDataService, Serializable
{
	
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 7263664298086412798L;

	/**
	 * The log
	 */
	private static final Log log = LogFactory.getLog(DisplayDataServiceImpl.class);
	
	/**
	 * The localLibraryService
	 */
	@Autowired LocalLibraryService localLibraryService;
	
	/**
	 * The mediaplayer
	 */
	@Autowired MediaPlayer mediaplayer;
	
	
	/**
	 * The current display data
	 */
	private static final DisplayData currentDisplayData = new DisplayData();
	
	/**
	 * Indicates that new data is available that has not been consumed yet. 
	 */
	private final LinkedBlockingQueue<Object> newDataAvailable = new LinkedBlockingQueue<Object>();

	/**
	 * @see com.senselessweb.soundcloud.web.service.DisplayDataService#getDisplayData()
	 */
	@Override
	public DisplayData getDisplayData()
	{
		currentDisplayData.set("position", String.valueOf(this.mediaplayer.getPosition()));
		return currentDisplayData;
	}

	/**
	 * @see com.senselessweb.soundcloud.web.service.DisplayDataService#waitForDisplayData()
	 */
	@Override
	public DisplayData waitForDisplayData()
	{
		try
		{
			log.debug("Waiting for new data...");
			this.newDataAvailable.take();
		} 
		catch (final InterruptedException e)
		{
			throw new RuntimeException(e);
		}
		
		// Always resend the current position to keep the frontend up2date
		currentDisplayData.set("position", String.valueOf(this.mediaplayer.getPosition()));
		return currentDisplayData;
	}
	
	/**
	 * Internal notify
	 */
	private void notifyInternal()
	{
		this.newDataAvailable.add(new Object());
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#tag(java.lang.String, java.lang.String)
	 */
	@Override
	public void tag(final String tag, final String value) 
	{ 
		currentDisplayData.set(tag, value);
		this.notifyInternal();
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	@Override
	public void stateChanged(final State newState) 
	{  
		if (newState == State.STOPPED) currentDisplayData.clear();
		currentDisplayData.set("state", newState.toString());
		this.notifyInternal();
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#newSource(com.senselessweb.soundcloud.domain.sources.MediaSource)
	 */
	@Override
	public void newSource(final MediaSource source)
	{
		final LocalFile localFile = this.localLibraryService.getFile(source);
		currentDisplayData.set("source", localFile != null ? localFile.getShortTitle() : source.getTitle());
		currentDisplayData.set("isSeekSupported", String.valueOf(this.mediaplayer.isSeekSupported()));
		this.notifyInternal();
	}
	
	/**
	 * @see com.senselessweb.soundcloud.web.service.impl.AbstractMessageAdapter#durationChanged(long)
	 */
	@Override
	public void durationChanged(final long duration)
	{
		currentDisplayData.set("duration", String.valueOf(duration));
		this.notifyInternal();
	}
	
}
