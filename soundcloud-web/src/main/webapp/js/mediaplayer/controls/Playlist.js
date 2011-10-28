
var playlistControlElement;

function Playlist(_playlistControlElement)
{
	this.playlistControlElement = _playlistControlElement;
	var _this = this;
	
	$.getJSON('controller/playlist/getData', function(data) {
		_this.initPlaylistControl(data);
		_this.playlistControlElement.scrollTo($('.playlistEntry.current', _this.playlistControlElement), 200);	
	});
};

Playlist.prototype.initPlaylistControl= function(data)
{
	$('div', this.playlistControlElement).remove();	
	for (var i = 0; i < data.length; i++)
	{
		this.playlistControlElement.append(
				'<div class="playlistEntry' + (data[i].current ? ' current' : '') + '">' + 
					'<div class="playlist-delete"><button value="' + i + '" id="playlist-delete-' + i + '"/></div>' + 
					'<div class="playlist-play"><button value="' + i + '" id="playlist-play-' + i + '"/></div>' + 
					'<div class="title">' + (i+1) + '&nbsp;&nbsp;&nbsp;' + data[i].title + '</div>' + 
				'</div><div style="clear:both;"></div>');
		
		
		$('#playlist-play-' + i).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() {
			$.ajax('controller/playlist/gotoTitle?index=' + $(this).val());
		});
		
		$('#playlist-delete-' + i).button({icons: {primary: 'ui-icon-cancel'}, text: false}).click(function() {
			$.ajax('controller/playlist/removeTitle?index=' + $(this).val());
		});		
	}
};

/**
 * Listens to stateChanged events and changes the current active button
 * 
 * @param data 
 */
Playlist.prototype.processMessage = function(data)
{
	if (data.type == 'playlistChanged')
	{
		var event = data.properties['event'];
		if (event == 'CURRENT_CHANGED')
		{
			var newCurrent = $('div:nth-child(' + ((parseInt(data.properties['current']) + 1) * 2 - 1) + ')', this.playlistControlElement);
			$('.playlistEntry.current', this.playlistControlElement).removeClass('current');
			newCurrent.addClass('current');
			this.playlistControlElement.scrollTo(newCurrent, 200);	
		}
		if (event == 'OTHER')
		{
			var _this = this;
			$.getJSON('controller/playlist/getData', function(data) {
				_this.initPlaylistControl(data);
			});
		}
	}

};