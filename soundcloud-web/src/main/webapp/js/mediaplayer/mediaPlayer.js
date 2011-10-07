
var display;

var playbackControl;

var playlistControl;

var volumeControl;

var equalizerControl;

var messageMediator;

function MediaPlayer(displayElement, playlistControlElement, playbackControlElement, volumeControlElement, equalizerControlElement)
{
	this.display = new Display(displayElement);
	this.playbackControl = new PlaybackControl(playbackControlElement);
	this.volumeControl = new VolumeControl(volumeControlElement);
	this.equalizerControl = new EqualizerControl(equalizerControlElement);
	this.playlistControl = new PlaylistControl(playlistControlElement);
	
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