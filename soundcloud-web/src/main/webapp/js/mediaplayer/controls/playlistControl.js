
var playlistControlElement;

function PlaylistControl(_playlistControlElement)
{
	this.playlistControlElement = _playlistControlElement;
	$.getJSON('controller/playlist/getData', function(data) {
		initPlaylistControl(_playlistControlElement, data);
	});
};

function initPlaylistControl(playlistControlElement, data)
{
	for (var i = 0; i < data.length; i++)
	{
		playlistControlElement.append(
				'<div class="playlistEntry' + (data[i].current ? ' current' : '') + '">' + 
				'<div class="playlist-delete"><button id="playlist-delete-' + i + '"/></div>' + 
				'<div class="playlist-play"><button id="playlist-play-' + i + '"/></div>' + 
				'<div class="index">' + (i+1) + '</div>' + 
				'<div class="title">' + data[i].mediaSource.title + '</div>' + 
				'</div><div style="clear:both;"></div>');
		
		$('#playlist-play-' + i).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() {
			//TODO $.ajax('controller/library/radio/play?id=' + $(this).val());
		});
		
		$('#playlist-delete-' + i).button({icons: {primary: 'ui-icon-cancel'}, text: false}).click(function() {
			//TODO $.ajax('controller/library/radio/play?id=' + $(this).val());
		});
	}
}

/**
 * Listens to stateChanged events and changes the current active button
 * 
 * @param data 
 */
PlaylistControl.prototype.processMessage = function(data)
{
	var _playlistControlElement = this.playlistControlElement;
	
	if (data.type == 'playlistChanged')
	{
		var event = data.properties['event'];
		
		// TODO If event is next or previous, it's not necessary to reload the complete list
		$('.playlistEntry', _playlistControlElement).remove();
		$.getJSON('controller/playlist/getData', function(data) {
			initPlaylistControl(_playlistControlElement, data);
		});
	}

};