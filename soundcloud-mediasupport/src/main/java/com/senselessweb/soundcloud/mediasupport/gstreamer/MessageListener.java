package com.senselessweb.soundcloud.mediasupport.gstreamer;

/**
 * Listener interface the gets informed when a stream ends
 * 
 * @author thomas
 *
 */
public interface MessageListener
{

	/**
	 * Is called when a stream ends.
	 */
	public void endofStream();
	
	/**
	 * Is called when an error occured
	 * 
	 * @param errorcode The error code. 
	 * @param message A message describing this error.
	 */
	public void error(int errorcode, String message);
}
