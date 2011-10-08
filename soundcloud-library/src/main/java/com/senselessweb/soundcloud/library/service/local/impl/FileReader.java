/**
 * 
 */
package com.senselessweb.soundcloud.library.service.local.impl;

import java.io.File;

import org.apache.commons.lang3.CharUtils;
import org.farng.mp3.MP3File;

import com.senselessweb.soundcloud.domain.library.FileInformations;

/**
 * Utility class to get file informations.
 *
 * @author thomas
 */
class FileReader
{

	/**
	 * Tries to read the {@link FileInformations} from a given file.
	 * 
	 * @param input The file.
	 * 
	 * @return The {@link FileInformations}.
	 */
	public static FileInformations read(final File input)
	{
		final String title, album, artist, genre, tracknumber;
		
		try
		{
			final MP3File mp3 = new MP3File(input);
			if (mp3.getID3v2Tag() != null)
			{
				title = format(mp3.getID3v2Tag().getSongTitle());
				album = format(mp3.getID3v2Tag().getAlbumTitle());
				artist = format(mp3.getID3v2Tag().getLeadArtist());
				genre = format(mp3.getID3v2Tag().getSongGenre());
				tracknumber = format(mp3.getID3v2Tag().getTrackNumberOnAlbum());
			}
			else if (mp3.getID3v1Tag() != null)
			{
				title = format(mp3.getID3v1Tag().getTitle());
				album = format(mp3.getID3v1Tag().getAlbum());
				artist = format(mp3.getID3v1Tag().getArtist());
				genre = format(mp3.getID3v1Tag().getSongGenre());
				tracknumber = format(mp3.getID3v1Tag().getTrackNumberOnAlbum());
			}
			else
			{
				title = null;
				album = null;
				artist = null;
				genre = null;
				tracknumber = null;
			}
			
		} 
		catch (final Exception e)
		{
			return new FileInformations(null, null, null, null, null, null);
		}
		
		return new FileInformations(title, artist, album, tracknumber, genre, null);
	}
	
	/**
	 * Formats the string. Removes unreadable characters and trailing and leading
	 * empty spaces.
	 * 
	 * @param s The string to format.
	 * 
	 * @return The formatted string or null if the string was blank. 
	 */
	private static String format(final String s)
	{
		if (s == null) return null;
		final StringBuilder formatted = new StringBuilder();
		
		for (int i = 0; i < s.length(); i++)
			if (CharUtils.isAsciiPrintable(s.charAt(i))) 
				formatted.append(s.charAt(i));
		
		return formatted.toString().trim().length() == 0 ? null : formatted.toString().trim();
	}
}


