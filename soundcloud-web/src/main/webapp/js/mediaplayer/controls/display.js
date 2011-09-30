
function Display(displayElement)
{
	displayElement.append('<div id="display-title"></div><div style="clear:both;"></div>');
	displayElement.append('<div id="display-genre"></div>');
	displayElement.append('<div id="display-bitrate"></div>');
	
	$.getJSON('controller/displayData/getInitialData', function(data) {
		setData(data);
		waitForData();
	});	
}

function waitForData()
{
	$.getJSON('controller/displayData/waitForData', function(data) {
		setData(data);
		waitForData();
	});
}

function setData(data)
{
	var title = (data.artist != undefined ? data.artist + " : " : '') + (data.title != undefined ? data.title : '');
	if (title == '' && data.source != undefined) title = data.source; 
	$('#display-title').text(title);
	
	if (data.genre != undefined) $('#display-genre').text('Genre: ' + data.genre);
	else $('#display-genre').html('&nbsp;'); 
		
	if (data.bitrate != undefined) $('#display-bitrate').text((data.bitrate / 1000) + ' kbps');
	else $('#display-bitrate').html('&nbsp;'); 
}