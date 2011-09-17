
var playbackControl;

var playlistControl;

function MediaPlayer()
{
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
