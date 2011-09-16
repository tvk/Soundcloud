package com.senselessweb.soundcloud.mediasupport.gstreamer;


/**
 * Encapsulates the gstreamer pipeline and contains basic methods to 
 * control the playback.
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
	
}
