

function RadioLibrary(radioLibraryElement)
{
	radioLibraryElement.append('<ol></ol>');
	$.getJSON('controller/library/radio/getUserStations', function(data) {
		initUserLibrary(radioLibraryElement, data);
		$('ol', radioLibraryElement).finder({ animate : true });
	});
}

function initUserLibrary(radioLibraryElement, data)
{
	$('ol', radioLibraryElement).append('<li><a>My stations</a><ul id="userstations"></ul></li>');
	for (var i = 0; i < data.length; i++)
	{
		$('ol li ul#userstations', radioLibraryElement).append(
			'<li id="userstation-' + i + '"><a>' + data[i].name + '</a>' +
				'<ul>' +
					'<ins><table class="description">' + 
						'<tr><td colspan="2"><h2>' + data[i].name + '</h2></td></tr>' + 
						'<tr><td class="title">URL:</td><td>' + data[i].url + '</td></tr>' + 
						'<tr><td class="title">Genres:</td><td>' + data[i].genres + '</td></tr>' +
						'<tr><td colspan="2">' + 
							'<button name="play" class="button playbutton">Play</button>' +
							'<button name="delete" class="button deletebutton">Delete</button>' + 
						'</td></tr>' +
					'</table></ins>' +
				'</ul>' +
			'</li>');
		
		$('#userstation-' + i + ' .playbutton', radioLibraryElement).button(
				{icons: {primary: 'ui-icon-play'}, text: false}).click(function() {
					console.log("test");
					$(this).blur();
					$.ajax({
						  url: "controller/playback/" + command,
						  error: function(jqXHR, textStatus, errorThrown) {
							  
						  }
						});
				});
		
		
		$('#userstation-' + i + ' .deletebutton', radioLibraryElement).button(
				{icons: {primary: 'ui-icon-delete'}, text: false}).click(function() {
						$(this).blur();
						$.ajax({ url: "controller/playback/" + command });
				});
	}
}