
/**
 * The display element
 */
var displayElement;

/**
 * Constructor
 * 
 * @param displayElement The display element
 */
function Display(displayElement)
{
	this.displayElement = displayElement;
	var _this = this;
	
	this.displayElement.append('<div id="display-title"></div><div style="clear:both;"></div>');
	this.displayElement.append('<div id="display-genre"></div>');
	this.displayElement.append('<div id="display-bitrate"></div>');
	
	$.getJSON('controller/displayData/getInitialData', function(data) {
		_this.setData(data);
		_this.waitForData();
	});	
}

/**
 * Sends an ajax request to the server to wait for new data to display
 */
Display.prototype.waitForData = function()
{
	var _this = this;
	$.getJSON('controller/displayData/waitForData', function(data) {
		_this.setData(data);
		_this.waitForData();
	});
};

/**
 * Refreshes the display data
 * 
 * @param data The data as it comes from the server
 */
Display.prototype.setData = function(data)
{
	var title = (data.artist != undefined ? data.artist + " : " : '') + (data.title != undefined ? data.title : '');
	if (title == '' && data.source != undefined) title = data.source; 
	$('#display-title', this.displayElement).text(title);
	
	if (data.genre != undefined) $('#display-genre').text('Genre: ' + data.genre);
	else $('#display-genre', this.displayElement).html('&nbsp;'); 
		
	if (data.bitrate != undefined) $('#display-bitrate').text((data.bitrate / 1000) + ' kbps');
	else $('#display-bitrate', this.displayElement).html('&nbsp;'); 
};