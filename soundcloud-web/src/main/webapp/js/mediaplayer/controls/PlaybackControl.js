
/**
 * The playbackControlElement
 */
var playbackControlElement;

/**
 * Creates a new playback control element
 * 
 * @param playbackControlElement The playback control element
 */
function PlaybackControl(playbackControlElement)
{
	this.playbackControlElement = playbackControlElement;
	var _this = this;
	
	$.getJSON('controller/playback/getData', function(data) {
		_this.initPlaybackControlMenu(data);
	});
}

/**
 * Initializes the control buttons.
 * 
 * @param data The initial data
 */
PlaybackControl.prototype.initPlaybackControlMenu = function(data)
{
	// The previous button
	this.appendButton('previous', 'ui-icon-seek-prev');
	
	// The pause button
	this.appendCheckbox('pause', 'ui-icon-pause', data.state == 'PAUSED');
	
	// The play button
	this.appendCheckbox('play', 'ui-icon-play', data.state == 'PLAYING');
	
	// The stop button
	this.appendCheckbox('stop', 'ui-icon-stop', data.state == 'STOPPED');
	
	// The next button
	this.appendButton('next', 'ui-icon-seek-next');
};

/**
 * Appends a new button
 * 
 * @param command The command of this button. Is used as servlet request.
 * @param icon The icon to use.
 */
PlaybackControl.prototype.appendButton = function(command, icon)
{
	this.playbackControlElement.append('<button class="' + command + ' playback-control-button"></button>');
	$('.' + command, this.playbackControlElement).button({icons: {primary: icon}, text: false}).click(function() {
		$(this).blur();
		$.ajax({ url: "controller/playback/" + command });
	});
};

/**
 * Appends a new checkbox. Note that all appended checkboxes are attached to the group 'state'
 * 
 * @param command The command of this button. Is used as servlet request.
 * @param icon The icon to use.
 */
PlaybackControl.prototype.appendCheckbox = function(command, icon, initalState)
{
	this.playbackControlElement.append('<input type="radio" name="state" id="button-' + command + '" ' + (initalState ? 'checked="true"' : '') + '/>');
	this.playbackControlElement.append('<label for="button-' + command + '" class="playback-control-button"></label>');

	$('#button-' + command, this.playbackControlElement).button({icons: {primary: icon}, text: false}).click(function(evt) {
			if (evt.originalEvent != undefined)
			{
				$(this).blur();
				$.ajax({ url: "controller/playback/" + command });				
			}
	});	
};

/**
 * Listens to stateChanged events and changes the current active button
 * 
 * @param data 
 */
PlaybackControl.prototype.processMessage = function(data)
{
	if (data.type == 'stateChanged')
	{
		var newState = data.properties['newState'];
		if (newState == 'PLAYING') $('#button-play', this.playbackControlElement).click();
		if (newState == 'STOPPED') $('#button-stop', this.playbackControlElement).click();
		if (newState == 'PAUSED') $('#button-pause', this.playbackControlElement).click();
	}

};
