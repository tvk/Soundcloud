/**
 * 
 */
package com.senselessweb.soundcloud.library.service.local.impl;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.senselessweb.soundcloud.domain.library.FileInformations;


/**
 * Testcases for the {@link FileReader}
 *
 * @author thomas
 */
public class FileReaderTest
{
	
	/**
	 * A test file.
	 */
	private static final File testfile = new File(
			"src/test/resources/filereader-testfiles/Amajo.mp3");
	
	/**
	 * Tries to get the informations of a simple mp3 file.
	 */
	@Test
	public void testReading()
	{
		final FileInformations fileInformations = FileReader.read(testfile);
		
		Assert.assertEquals("Asmara All Stars", fileInformations.getArtist());
		Assert.assertEquals("Amajo", fileInformations.getTitle());
		Assert.assertEquals("Eritrea's Got Soul", fileInformations.getAlbum());
		Assert.assertEquals("1/13", fileInformations.getTracknumber());
	}

}
