
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
	$('div', playlistControlElement).remove();	
	for (var i = 0; i < data.length; i++)
	{
		playlistControlElement.append(
				'<div class="playlistEntry' + (data[i].current ? ' current' : '') + '">' + 
					'<div class="playlist-delete"><button value="' + i + '" id="playlist-delete-' + i + '"/></div>' + 
					'<div class="playlist-play"><button value="' + i + '" id="playlist-play-' + i + '"/></div>' + 
					'<div class="title">' + (i+1) + ' ' + data[i].title + '</div>' + 
				'</div><div style="clear:both;"></div>');
		
		$('#playlist-play-' + i).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() {
			$.ajax('controller/playlist/gotoTitle?index=' + $(this).val());
		});
		
		$('#playlist-delete-' + i).button({icons: {primary: 'ui-icon-cancel'}, text: false}).click(function() {
			$.ajax('controller/playlist/removeTitle?index=' + $(this).val());
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
		if (event == 'CURRENT_CHANGED')
		{
			var newCurrent = $('div:nth-child(' + ((parseInt(data.properties['current']) + 1) * 2 - 1) + ')', _playlistControlElement);
			$('.playlistEntry.current', _playlistControlElement).removeClass('current');
			newCurrent.addClass('current');
		}
		if (event == 'OTHER')
		{
			$.getJSON('controller/playlist/getData', function(data) {
				initPlaylistControl(_playlistControlElement, data);
			});
		}
	}

};