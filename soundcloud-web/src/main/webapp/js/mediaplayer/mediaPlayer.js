
var playbackControl;

var playlistControl;

var volumeControl;

var equalizerControl;

function MediaPlayer(volumeControlElement, equalizerControlElement)
{
	this.volumeControl = new VolumeControl(volumeControlElement);
	this.equalizerControl = new EqualizerControl(equalizerControlElement);
	this.playbackControl = new PlaybackControl();
	this.playlistControl = new PlaylistControl();
}

MediaPlayer.prototype.getPlaybackControl = function()
{
	return this.playbackControl;
};


MediaPlayer.prototype.getPlaylistControl = function()
{
	return this.playlistControl;
};
