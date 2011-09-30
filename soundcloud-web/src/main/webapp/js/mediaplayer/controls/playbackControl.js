

/**
 * Creates a new playback control element
 * 
 * @param playbackControlElement The playback control element
 */
function PlaybackControl(playbackControlElement)
{
	$.getJSON('controller/playback/getData', function(data) {
		initPlaybackControlMenu(playbackControlElement, data);
	});
}

/**
 * Initializes the control buttons.
 * 
 * @param playbackControlElement The parent element
 * 
 * @param data The initial data
 */
function initPlaybackControlMenu(playbackControlElement, data)
{
	// The previous button
	appendButton(playbackControlElement, 'previous', 'ui-icon-seek-prev');
	
	// The pause button
	appendCheckbox(playbackControlElement, 'pause', 'ui-icon-pause', data.state == 'PAUSED');
	
	// The play button
	appendCheckbox(playbackControlElement, 'play', 'ui-icon-play', data.state == 'PLAYING');
	
	// The stop button
	appendCheckbox(playbackControlElement, 'stop', 'ui-icon-stop', data.state == 'STOPPED');
	
	// The next button
	appendButton(playbackControlElement, 'next', 'ui-icon-seek-next');
}

/**
 * Appends a new button
 * 
 * @param playbackControlElement The parent playback element
 * @param command The command of this button. Is used as servlet request.
 * @param icon The icon to use.
 */
function appendButton(playbackControlElement, command, icon)
{
	playbackControlElement.append('<button class="' + command + ' playback-control-button"></button>');
	$('.' + command, playbackControlElement).button({icons: {primary: icon}, text: false}).click(function() {
		$(this).blur();
		$.ajax({
			  url: "controller/playback/" + command,
			  error: function(jqXHR, textStatus, errorThrown) {
				  
			  }
			});
	});
}

/**
 * Appends a new checkbox. Note that all appended checkboxes are attached to the group 'state'
 * 
 * @param playbackControlElement The parent playback element
 * @param command The command of this button. Is used as servlet request.
 * @param icon The icon to use.
 */
function appendCheckbox(playbackControlElement, command, icon, initalState)
{
	playbackControlElement.append('<input type="radio" name="state" id="button-' + command + '" ' + (initalState ? 'checked="true"' : '') + '/>');
	playbackControlElement.append('<label for="button-' + command + '" class="playback-control-button"></label>');

	$('#button-' + command, playbackControlElement).button({icons: {primary: icon}, text: false}).click(function(evt) {
			if (evt.originalEvent != undefined)
			{
				$(this).blur();
				$.ajax({
					url: "controller/playback/" + command,
					error: function(jqXHR, textStatus, errorThrown) {
						
					}
				});				
			}
	});	
}

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
		if (newState == 'PLAYING') $('#button-play').click();
		if (newState == 'STOPPED') $('#button-stop').click();
		if (newState == 'PAUSED') $('#button-pause').click();
	}

};
