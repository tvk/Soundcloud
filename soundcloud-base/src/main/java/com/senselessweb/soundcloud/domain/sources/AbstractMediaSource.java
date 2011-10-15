/**
 * 
 */
package com.senselessweb.soundcloud.domain.sources;

/**
 * Abstract base class for {@link MediaSource}s.
 *
 * @author thomas
 */
public abstract class AbstractMediaSource implements MediaSource 
{

	/**
	 * The title
	 */
	private final String title;
	
	/**
	 * Constructor
	 * 
	 * @param title The title
	 */
	public AbstractMediaSource(final String title)
	{
		this.title = title;
	}
	
	/**
	 * @see com.senselessweb.soundcloud.domain.sources.MediaSource#getTitle()
	 */
	@Override
	public String getTitle()
	{
		return this.title;
	}
}
