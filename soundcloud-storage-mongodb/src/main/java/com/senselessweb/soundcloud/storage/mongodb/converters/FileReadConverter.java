/**
 * 
 */
package com.senselessweb.soundcloud.storage.mongodb.converters;

import java.io.File;

import org.springframework.core.convert.converter.Converter;

import com.mongodb.DBObject;

/**
 * 
 *
 * @author thomas
 */
public class FileReadConverter implements Converter<DBObject, File>
{

	/**
	 * Constructor
	 *
	 */
	public FileReadConverter()
	{
		//  Auto-generated constructor stub
	}
	/**
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public File convert(DBObject source)
	{
		return new File(source.get("path").toString());
	}
}
