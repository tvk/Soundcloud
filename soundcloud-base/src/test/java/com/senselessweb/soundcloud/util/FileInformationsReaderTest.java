/**
 * 
 */
package com.senselessweb.soundcloud.util;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.senselessweb.soundcloud.domain.library.FileInformations;
import com.senselessweb.soundcloud.util.FileInformationsReader;


/**
 * Testcases for the {@link FileInformationsReader}
 *
 * @author thomas
 */
public class FileInformationsReaderTest
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
		final FileInformations fileInformations = FileInformationsReader.read(testfile);
		
		Assert.assertEquals("Asmara All Stars", fileInformations.getArtist());
		Assert.assertEquals("Amajo", fileInformations.getTitle());
		Assert.assertEquals("Eritrea's Got Soul", fileInformations.getAlbum());
		Assert.assertEquals("1/13", fileInformations.getTracknumber());
	}

}
