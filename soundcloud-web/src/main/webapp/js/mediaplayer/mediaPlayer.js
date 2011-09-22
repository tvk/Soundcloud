
var playbackControl;

var playlistControl;

var volumeControl;

var equalizerControl;

function MediaPlayer(playbackControlElement, volumeControlElement, equalizerControlElement)
{
	this.playbackControl = new PlaybackControl(playbackControlElement);
	this.volumeControl = new VolumeControl(volumeControlElement);
	this.equalizerControl = new EqualizerControl(equalizerControlElement);
	this.playlistControl = new PlaylistControl();
}