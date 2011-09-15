package com.senselessweb.soundcloud.mediasupport.gstreamer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;

import com.senselessweb.soundcloud.mediasupport.model.MediaType;
import com.senselessweb.soundcloud.mediasupport.service.impl.MediaPlayerImpl;

/**
 * The {@link DecoderBridge} encapsulates a gstreamer decoder and supports the 
 * selection of the correct decoder for a given {@link MediaType}.
 * 
 * @author thomas
 *
 */
public class DecoderBridge
{

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(MediaPlayerImpl.class);
	
	/**
	 * Map of decoder names by media type
	 */
	private static Map<MediaType, String[]> decoderByMediaType = new HashMap<MediaType, String[]>();
	static 
	{
		// Supported plugins. A full list is available at
		// http://gstreamer.freedesktop.org/documentation/plugins.html
		decoderByMediaType.put(MediaType.MP3, new String[] {"mad"});
		decoderByMediaType.put(MediaType.WAVE, new String[] {"wavparse"});
		decoderByMediaType.put(MediaType.OGG, new String[] {"oggdemux", "vorbisdec"});
	}
	
	/**
	 * The decoder names
	 */
	private final String[] decoderNames;
	
	/**
	 * The decoders. Are initialized when requested.
	 */
	private Element[] decoders = null;
	
	/**
	 * Constructor
	 * 
	 * @param mediaType The {@link MediaType} to create a decoder for. 
	 */
	public DecoderBridge(final MediaType mediaType)
	{
		if (!decoderByMediaType.containsKey(mediaType))
			throw new IllegalArgumentException("No decoder found for mediatype \"" + mediaType + "\"");
		this.decoderNames = decoderByMediaType.get(mediaType);
		log.debug("Initalized decoder \"" + Arrays.toString(this.decoderNames) + "\" for mediatype \"" + mediaType + "\"");
	}
	
	/**
	 * Checks whether the given {@link MediaType} is supported by this {@link DecoderBridge}. This does not
	 * check if the given {@link MediaType} is generally supported. If this is the question, use 
	 * {@link DecoderBridge#supportsMediaType(MediaType)} instead. 
	 * 
	 * @param mediaType The {@link MediaType} to check.
	 * 
	 * @return True if the given media type is supported by this decoder. False otherwise.
	 */
	public boolean supportsMediaType(final MediaType mediaType)
	{
		return this.decoderNames.equals(decoderByMediaType.get(mediaType));
	}
	
	/**
	 * Creates and returns the gstreamer decoder elements to use.
	 * 
	 * @return The decoder {@link Element}s.
	 */
	public synchronized Element[] getDecoders()
	{
		if (this.decoders == null)
		{
			this.decoders = new Element[this.decoderNames.length];
			for (int i = 0; i < this.decoderNames.length; i++)
				this.decoders[i] = ElementFactory.make(this.decoderNames[i], this.decoderNames[i]);
		}
		return this.decoders;
	}

	/**
	 * Checks whether the given media type is supported by the {@link DecoderBridge}.
	 * 
	 * @param mediaType The {@link MediaType} to check.
	 * 
	 * @return True if the given media type is supported by this decoder. False otherwise.
	 */
	public static boolean isMediaTypeSupported(final MediaType mediaType)
	{
		return decoderByMediaType.containsKey(mediaType);
	}
	
}
