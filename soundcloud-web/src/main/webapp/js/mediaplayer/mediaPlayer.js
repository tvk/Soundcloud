
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
	
	this.messageMediator = new MessageMediator(new Array(this.playbackControl));
}