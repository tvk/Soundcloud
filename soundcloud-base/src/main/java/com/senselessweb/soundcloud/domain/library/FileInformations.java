package com.senselessweb.soundcloud.domain.library;

/**
 * Simple container class for collected file informations. Note
 * that all fields may be null if no information could be found.
 *
 * @author thomas
 */
public class FileInformations
{
	
	/**
	 * The title of the file. May be null.
	 */
	private final String title;
	
	/**
	 * The artist of the file. May be null.
	 */
	private final String artist;
	
	/**
	 * The album of the file. May be null.
	 */
	private final String album;
	
	/**
	 * The tracknumber of the file. May be null.
	 */
	private final String tracknumber;
	
	/**
	 * The genre of the file. May be null.
	 */
	private final String genre;
	
	/**
	 * The bitrate of the file. May be null.
	 */
	private final String bitrate;
	

	/**
	 * Constructor
	 * 
	 * @param title The title of the file
	 * @param artist The artist of the file 
	 * @param album The album of the file 
	 * @param tracknumber The tracknumber of the file 
	 * @param genre The genre of the file 
	 * @param bitrate The bitrate of the file 
	 */
	public FileInformations(final String title, final String artist, final String album, final String tracknumber, 
			final String genre, final String bitrate)
	{
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.tracknumber = tracknumber;
		this.genre = genre;
		this.bitrate = bitrate;
	}
	
	/**
	 * Returns the title
	 *
	 * @return The title
	 */
	public String getTitle()
	{
		return this.title;
	}
	
	/**
	 * Returns the artist
	 *
	 * @return The artist
	 */
	public String getArtist()
	{
		return this.artist;
	}
	
	/**
	 * Returns the album
	 *
	 * @return The album
	 */
	public String getAlbum()
	{
		return this.album;
	}
	
	/**
	 * Returns the genre
	 *
	 * @return The genre
	 */
	public String getGenre()
	{
		return this.genre;
	}
	
	/**
	 * Returns the tracknumber
	 *
	 * @return The tracknumber
	 */
	public String getTracknumber()
	{
		return this.tracknumber;
	}
	
	/**
	 * Returns the bitrate
	 *
	 * @return The bitrate
	 */
	public String getBitrate()
	{
		return this.bitrate;
	}
}