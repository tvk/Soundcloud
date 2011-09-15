package com.senselessweb.soundcloud.mediasupport.model;

import java.io.File;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

import com.google.common.collect.Sets;

/**
 * List of supported media types.
 * 
 * @author thomas
 *
 */
public enum MediaType
{

	/** 
	 * Mp3 
	 */
	MP3(Sets.newHashSet("mp3")),
	
	/** 
	 * Wave 
	 */
	WAVE(Sets.newHashSet("wav")), 
	
	/**
	 * OGG
	 */
	OGG(Sets.newHashSet("ogg"));
	
	
	/**
	 * Filename extensions of this mediatype
	 */
	private final Set<String> fileExtensions;
	
	/**
	 * Constructor
	 * 
	 * @param fileExtensions Filename extensions of this mediatype
	 */
	private MediaType(final Set<String> fileExtensions)
	{
		this.fileExtensions = fileExtensions;
	}
	
	/**
	 * Returns the {@link MediaType} for a fileexten
	 * 
	 * @param fileExtension
	 * @return
	 */
	public static MediaType getMediaType(final File file)
	{
		if (file == null || !file.isFile()) throw new IllegalArgumentException("File \"" + file + "\" is not a file");
		final String fileExtension = FilenameUtils.getExtension(file.getName()).toLowerCase();
		
		for (final MediaType mediaType : MediaType.values())
		{
			if (mediaType.fileExtensions.contains(fileExtension)) return mediaType;
		}
		return null;
	}
}
