package com.senselessweb.soundcloud.mediasupport.gstreamer;

/**
 * Listener interface the gets informed when a stream ends
 * 
 * @author thomas
 *
 */
public interface EndOfStreamListener
{

	/**
	 * Is called when a stream ends.
	 */
	public void streamEnded();
}
