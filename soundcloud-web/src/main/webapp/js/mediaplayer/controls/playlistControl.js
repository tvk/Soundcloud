

function PlaylistControl(playlistControlElement)
{
	$.getJSON('controller/playlist/getData', function(data) {
		initPlaylistControl(playlistControlElement, data);
	});
}

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
			//$.ajax('controller/library/radio/play?id=' + $(this).val());
		});
		
		$('#playlist-delete-' + i).button({icons: {primary: 'ui-icon-cancel'}, text: false}).click(function() {
			//$.ajax('controller/library/radio/play?id=' + $(this).val());
		});
	}
}