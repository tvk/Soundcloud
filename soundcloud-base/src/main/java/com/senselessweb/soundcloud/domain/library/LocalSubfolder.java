/**
 * 
 */
package com.senselessweb.soundcloud.domain.library;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

/**
 * Simple container that contains the name of a subfolder and 
 * the keywords, but not the actual content.
 *
 * @author thomas
 */
public class LocalSubfolder implements Comparable<LocalSubfolder>
{

	/**
	 * The name of this subfolder
	 */
	private final String name;

	/**
	 * The decoded name of this subfolder 
	 */
	private final String decodedName;

	/**
	 * The keywords of this subfolder
	 */
	private final Set<String> keywords;
	
	/**
	 * Constructor
	 * 
	 * @param name The name of this subfolder
	 * @param keywords The keywords of this subfolder
	 */
	public LocalSubfolder(final String name, final Set<String> keywords)
	{
		this.name = name;
		this.keywords = keywords;
		
		try
		{
			this.decodedName = URLEncoder.encode(name, "UTF-8");
		} 
		catch (final UnsupportedEncodingException e) { throw new RuntimeException(e); }
	}
	
	/**
	 * Returns the name
	 *
	 * @return The name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Returns the keywords
	 *
	 * @return The keywords
	 */
	public Set<String> getKeywords()
	{
		return this.keywords;
	}
	
	/**
	 * Returns the decodedName
	 *
	 * @return The decodedName
	 */
	public String getDecodedName()
	{
		return this.decodedName;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final LocalSubfolder o)
	{
		return this.getName().compareTo(o.getName());
	}
}
