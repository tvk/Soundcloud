package com.senselessweb.soundcloud.mediasupport.gstreamer;

import com.senselessweb.soundcloud.mediasupport.service.MediaPlayer.State;


/**
 * Encapsulates the gstreamer pipeline and contains basic methods to 
 * control the playback.
 * 
 * A pipeline represents always only one source. You have to recreate the 
 * pipeline for every new source.
 * 
 * Use the {@link PipelineBuilder} to create pipelines.
 * 
 * @author thomas
 *
 */
public interface PipelineBridge
{

	/**
	 * Starts the playback.
	 */
	public void play();

	
	/**
	 * Stops the playback.
	 */
	public void stop();

	
	/**
	 * Pauses the playback.
	 */
	public void pause();
	
	
	/**
	 * Returns the current playback state
	 * 
	 * @return The current playback state. Never null.
	 */
	public State getState();
	
	
	/**
	 * If an error occurs during the playback, should this pipeline just 
	 * be recreated? This may be helpful when playing stream sources (that
	 * somtimes just crashes wihtout reason...)
	 * 
	 * @return True if this source should be resetted in error case, false otherwise.
	 */
	public boolean resetInErrorCase();
	
	/**
	 * Returns whether the current pipeline can be set to {@link State#PAUSED} state.
	 * 
	 * @return True if this pipeline can be set to pause state, false otherwise.
	 */
	public boolean isPausable();


	
}
