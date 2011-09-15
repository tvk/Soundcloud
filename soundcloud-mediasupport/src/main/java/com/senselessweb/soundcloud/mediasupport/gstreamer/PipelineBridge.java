package com.senselessweb.soundcloud.mediasupport.gstreamer;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

import com.senselessweb.soundcloud.mediasupport.model.MediaType;
import com.senselessweb.soundcloud.mediasupport.service.impl.MediaPlayerImpl;
import com.senselessweb.soundcloud.mediasupport.util.ArrayBuilder;

/**
 * Encapsulates the gstreamer pipeline.
 * 
 * @author thomas
 *
 */
public class PipelineBridge
{

	/**
	 * Logger
	 */
	private static final Log log = LogFactory.getLog(MediaPlayerImpl.class);
	
	/**
	 * The actual pipeline.
	 */
	private Pipeline pipeline = null;
	
	/**
	 * The current decoder
	 */
	private DecoderBridge decoder = null;
	
	/**
	 * The file source element
	 */
	private Element fileSource = ElementFactory.make("filesrc", "filesrc");

	/**
	 * Plays a file.
	 * 
	 * @param file The file to play.
	 */
	public void play(final File file)
	{
		// Get the media type
		final MediaType mediaType = MediaType.getMediaType(file);
		
		// Check decoder and pipeline
		if (this.pipeline == null || this.decoder == null || !this.decoder.supportsMediaType(mediaType))
		{
			// Check if the DecoderBridge supports this mediatype
			if (!DecoderBridge.isMediaTypeSupported(mediaType))
				throw new IllegalArgumentException("The DecoderBridge does not support media files of type \"" + mediaType + "\"");
			
			// We have to reinitialize the pipeline.
			this.initPipeline(mediaType);
		}
		
		// Stop the pipeline
		this.pipeline.stop();
		
		// Set the filesource
		this.fileSource.set("location", file.getAbsolutePath());
		
		// Start playback
		this.pipeline.play();
	}
	
	/**
	 * Initalizes the pipeline.
	 * 
	 * @param mediaType
	 */
	private void initPipeline(final MediaType mediaType)
	{
		// Initialize the decoder
		this.decoder = new DecoderBridge(mediaType);

		// The audioconverter
		final Element audioconvert = ElementFactory.make("audioconvert", "audioconvert");
		
		// TODO Add equalizer and volume controls (with current cached values!!)
		//final Element equalizer = ElementFactory.make("equalizer-10bands", "equalizer");
		//final Element volume = ElementFactory.make("volume", "volume");
		
		// The audio sink
		final Element sink = ElementFactory.make("alsasink", "alsasink");

		// Create the pipeline elements
		final Element[] elements = new ArrayBuilder<Element>().
			with(this.fileSource).
			with(this.decoder.getDecoders()).
			with(audioconvert).
			with(sink).
			build(Element.class);
		
		// Create the pipeline
		this.pipeline = new Pipeline();
		this.pipeline.setState(State.PAUSED);
        sink.setState(State.PAUSED);
		this.pipeline.addMany(elements);
		
		// Link the elements together
		if (!Element.linkMany(elements)) 
            throw new RuntimeException("Failed to link pipeline elements");
		
		log.debug("Initalized the pipeline");
	}
	
}
