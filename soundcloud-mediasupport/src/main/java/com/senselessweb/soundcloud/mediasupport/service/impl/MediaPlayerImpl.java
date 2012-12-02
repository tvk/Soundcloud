package com.senselessweb.soundcloud.mediasupport.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Pipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senselessweb.soundcloud.domain.sources.MediaSource;
import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBridge;
import com.senselessweb.soundcloud.mediasupport.gstreamer.PipelineBuilder;
import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer;
import com.senselessweb.soundcloud.mediasupport.service.MessageListenerService;
import com.senselessweb.soundcloud.mediasupport.service.Playlist;
import com.senselessweb.soundcloud.mediasupport.service.Playlist.ChangeEvent;

/**
 * {@link MediaPlayer} implementation based on a gstreamer {@link Pipeline}.
 * 
 * @author thomas
 */
@Service
public class MediaPlayerImpl implements MediaPlayer, MessageListenerService
{

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(MediaPlayerImpl.class);
	
	/**
	 * The current playlist
	 */
	private final Playlist playlist;	
	
	/**
	 * The preconfigured pipeline builder. 
	 */
	private final PipelineBuilder pipelineBuilder;
	
	/**
	 * The attached {@link MessageListenerService}s.
	 */
	private final MessageMediator messageMediator;

	/**
	 * The currently used pipeline. Is rebuilt everytime a new song is played.  
	 */
	private PipelineBridge pipeline; 
	
	/**
	 * The current mediasource
	 */
	private MediaSource current;
	
	@Autowired
	public MediaPlayerImpl(final Playlist playlist, final PipelineBuilder pipelineBuilder, 
			final MessageMediator messageMediator) 
	{
		this.playlist = playlist;
		this.pipelineBuilder = pipelineBuilder;
		this.messageMediator = messageMediator;
	}
	
	/**
	 * The timestamp when the pipeline was restarted the last time
	 */
	private long lastPipelineReset = 0;
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#play()
	 */
	@Override
	public synchronized void play()
	{
		if (this.pipeline == null && this.playlist.getCurrent() != null)
		{
			this.current = this.playlist.getCurrent();
			this.pipeline = this.pipelineBuilder.createPipeline(this.current);
		}
		
		if (this.pipeline != null) 
		{
			this.pipeline.play();
			this.messageMediator.stateChanged(State.PLAYING);
			this.messageMediator.newSource(this.current);
		}
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#stop()
	 */
	@Override
	public synchronized void stop()
	{
		if (this.pipeline != null) 
		{
			this.pipeline.stop();
			this.pipeline = null;
			this.messageMediator.stateChanged(State.STOPPED);
		}
	}
	
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#pause()
	 */
	@Override
	public synchronized void pause()
	{
		if (this.pipeline != null) 
		{
			if (this.pipeline.isPausable())
			{
				if (this.pipeline.getState() == State.PAUSED)
				{
					// Restart playback if pipeline is already paused.
					this.play();
				}
				else
				{
					this.pipeline.pause();
					this.messageMediator.stateChanged(State.PAUSED);
				}
			}
			else
			{
				log.debug("Current pipeline can't be set to pause state, redirecting to stop()");
				this.stop();
			}
		}
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getState()
	 */
	@Override
	public synchronized State getState()
	{
		return this.pipeline != null ? this.pipeline.getState() : State.STOPPED;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getDuration()
	 */
	@Override
	public long getDuration()
	{
		return this.pipeline == null ? -1 : this.pipeline.getDuration();
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#getPosition()
	 */
	@Override
	public long getPosition()
	{
		return this.pipeline == null ? -1 : this.pipeline.getPosition();
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#gotoPosition(long)
	 */
	@Override
	public void gotoPosition(final long position)
	{
		if (this.pipeline != null)
		{
			this.pipeline.gotoPosition(position);
			this.messageMediator.positionChanged(this.getPosition());
		}
	}
	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MediaPlayer#isSeekSupported()
	 */
	@Override
	public boolean isSeekSupported()
	{
		return this.pipeline != null && this.pipeline.isSeekSupported();
	}

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#error(java.lang.String)
	 */
	@Override
	public void error(String message)
	{
		this.stop();
		
		if (this.pipeline.resetInErrorCase())
		{
			
			// Restart the pipeline only every 3 seconds 
			if (this.lastPipelineReset < System.currentTimeMillis() - 3*1000)
			{
				this.lastPipelineReset = System.currentTimeMillis();
				
				log.debug("Trying to restart the pipeline");
				this.pipeline = this.pipelineBuilder.createPipeline(this.current);
				this.play();				
			}
			else
			{
				// Otherwise try to go to the next playlist entry
				if (this.playlist.next()) this.play();
			}
		}
		else 
		{
			this.messageMediator.error(message);
		}
	}


	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#endOfStream()
	 */
	@Override
	public void endOfStream()
	{
		this.stop();
		this.pipeline = null;
		
		if (this.playlist.next()) this.play();
	}
	

	
	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#stateChanged(com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State)
	 */
	@Override
	public void stateChanged(State newState) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#tag(java.lang.String, java.lang.String)
	 */
	@Override
	public void tag(String tag, String value) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#newSource(com.senselessweb.soundcloud.domain.sources.MediaSource)
	 */
	@Override
	public void newSource(MediaSource source) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListenerService#playlistChanged(com.senselessweb.soundcloud.mediasupport.service.Playlist.ChangeEvent, int)
	 */
	@Override
	public void playlistChanged(ChangeEvent event, int current) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#durationChanged(long)
	 */
	@Override
	public void durationChanged(long duration) { /* unused */ }

	/**
	 * @see com.senselessweb.soundcloud.mediasupport.service.MessageListener#positionChanged(long)
	 */
	@Override
	public void positionChanged(long position) { /* unused */ }
}
