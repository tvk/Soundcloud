/**
 * 
 */
package com.senselessweb.soundcloud.storage.mongodb.converters;

import java.io.File;

import org.springframework.core.convert.converter.Converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 *
 * @author thomas
 */
public class FileWriteConverter implements Converter<File, DBObject>
{

	/**
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public DBObject convert(File source)
	{
		return new BasicDBObject("path", source.getAbsolutePath());
	}
}
