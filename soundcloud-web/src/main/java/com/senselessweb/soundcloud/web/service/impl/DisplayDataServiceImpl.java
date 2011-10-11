package com.senselessweb.soundcloud.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.library.LocalFile;
import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.library.service.local.LocalLibraryService;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;
import com.senselessweb.soundcloud.web.service.DisplayDataService;

/**
 * Default implementation of the {@link DisplayDataService}.
 * 
 * @author thomas
 */
@Service
@Scope(proxyMode=ScopedProxyMode.INTERFACES, value="session")
public class DisplayDataServiceImpl extends AbstractMessageAdapter implements DisplayDataService
{
	
	/**
	 * The localLibraryService
	 */
	@Autowired LocalLibraryService localLibraryService;
			
	/**
	 * The current display data
	 */
	private final DisplayData currentDisplayData = new DisplayData();
	
	/**
	 * Indicates that new data is available that has not been consumed yet. 
	 */
	private boolean newDataAvailable = false;
	
	

	/**
	 * @see com.senselessweb.soundcloud.web.service.DisplayDataService#getDisplayData()
	 */
	@Override
	public DisplayData getDisplayData()
	{
		return this.currentDisplayData;
	}

	/**
	 * @see com.senselessweb.soundcloud.web.service.DisplayDataService#waitForDisplayData()
	 */
	@Override
	public DisplayData waitForDisplayData()
	{
		synchronized (this)
		{
			while(!this.newDataAvailable)
			{
				try
				{
					this.wait();		
				}
				catch (final InterruptedException e) { /* ignored */ }
			}
		} 

		this.newDataAvailable = false;
		return this.currentDisplayData;
	}
	
	/**
	 * Internal notify
	 */
	private void notifyInternal()
	{
		this.newDataAvailable = true;
		synchronized (this)
		{
			this.notify();			
		}		
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#tag(java.lang.String, java.lang.String)
	 */
	@Override
	public void tag(final String tag, final String value) 
	{ 
		this.currentDisplayData.set(tag, value);
		this.notifyInternal();
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	@Override
	public void stateChanged(final State newState) 
	{  
		if (newState == State.STOPPED) this.currentDisplayData.clear();
		this.notifyInternal();
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#newSource(com.senselessweb.soundcloud.domain.sources.MediaSource)
	 */
	@Override
	public void newSource(final MediaSource source)
	{
		final LocalFile localFile = this.localLibraryService.getFile(source);
		this.currentDisplayData.set("source", localFile != null ? localFile.getShortTitle() : source.getTitle());
		this.notifyInternal();
	}
	
}
