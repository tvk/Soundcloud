

/**
 * The display control
 */
var display;

/**
 * The playback control
 */
var playbackControl;

/**
 * The playlist control
 */
var playlistControl;

/**
 * The volume control
 */
var volumeControl;

/**
 * The message mediator
 */
var messageMediator;

/**
 * Constructor
 * 
 * @param displayElement
 * @param playlistControlElement
 * @param playbackControlElement
 * @param volumeControlElement
 * 
 * @returns {MediaPlayer}
 */
function MediaPlayer(displayElement, playlistControlElement, playbackControlElement, volumeControlElement)
{
	this.display = new Display(displayElement);
	this.playbackControl = new PlaybackControl(playbackControlElement);
	this.volumeControl = new VolumeControl(volumeControlElement);
	this.playlistControl = new Playlist(playlistControlElement);
	
	// Creating a message mediator and append all the listeners
	this.messageMediator = new MessageMediator(new Array(this, this.playbackControl, this.playlistControl));
	
	$("body").append('<div id="errorDialog" style="display:none;"><div class="content"></div></div>');
}

/**
 * Listens to error events and displays them.
 * 
 * @param data 
 */
MediaPlayer.prototype.processMessage = function(data)
{
	if (data.type == 'error')
	{
		$('#errorDialog .content').text(data.properties['message']);
		$('#errorDialog').dialog({modal:true, buttons : { Ok: function() { $(this).dialog("close"); }}});
	}
};