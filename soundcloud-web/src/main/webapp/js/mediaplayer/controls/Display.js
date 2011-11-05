
/**
 * The display element
 */
var displayElement;

/**
 * The last known position inside the track in seconds
 */
var lastKnownPosition;

/**
 * The timestamp when the position has been updated the last time.
 */
var lastPositionUpdate;

/**
 * The duration of the track in seconds
 */
var duration;

/**
 * The current state
 */
var state;

/**
 * Indicates that the user is current using the seek slider. No need to update the slider programatically.
 */
var isSeeking;

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
	this.displayElement.append('<div id="timer"></div>');
	this.displayElement.append('<div id="display-genre"></div>');
	this.displayElement.append('<div id="display-bitrate"></div><div style="clear:both;"></div>');

	this.displayElement.append('<div id="seekControl"></div>');
	$('#seekControl', this.displayElement).slider({'disabled' : true});

	$.getJSON('controller/displayData/getInitialData', function(data) {
		_this.setData(data);
		_this.waitForData();
	});	
	
	this.startTimer();
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
	
	// Get and set media informations
	var title = (data.artist != undefined ? data.artist + " : " : '') + (data.title != undefined ? data.title : '');
	if (title == '' && data.source != undefined) title = data.source; 
	$('#display-title', this.displayElement).text(title);
	
	if (data.genre != undefined) $('#display-genre').text('Genre: ' + data.genre);
	else $('#display-genre', this.displayElement).html('&nbsp;'); 
		
	if (data.bitrate != undefined) $('#display-bitrate').text((data.bitrate / 1000) + ' kbps');
	else $('#display-bitrate', this.displayElement).html('&nbsp;'); 
	
	// Get position and duration if available 
	if (data.position != undefined) 
	{
		this.lastKnownPosition = data.position;
		this.lastPositionUpdate = new Date().getTime();
	}
	if (data.duration != undefined) this.updateDuration(data.duration);
	
	// Update the current state
	if (data.state != undefined) this.state = data.state;
};

/**
 * Starts the timers.
 */
Display.prototype.startTimer = function()
{
	var _this = this; 
	
	// The timer to update the clock display
	$.timer(1000, function (timer) {
		if (_this.state == 'PLAYING') 
		{
			var dt = new Date().getTime() - _this.lastPositionUpdate; 
			var position = parseInt(parseInt(_this.lastKnownPosition) + dt/1000);
			_this.updateTimer(position);
		}
		else if (_this.state == 'STOPPED')
		{
			_this.updateTimer(0);
		}
	});
	
	// The timer to update the seek slider
	$.timer(500, function (timer) {
		if (_this.duration > 0 && _this.isSeeking != true)
		{
			if (_this.state == 'PLAYING') 
			{
				var dt = new Date().getTime() - _this.lastPositionUpdate; 
				$('#seekControl', this.displayElement).slider({'value' : (_this.lastKnownPosition * 1000 + dt)});
			}
			else if (_this.state == 'STOPPED')
			{
				_this.updateTimer(0);
			}
		}
	});
	
};

/**
 * Update the timer display element.
 * 
 * @param time The current time in seconds.
 */
Display.prototype.updateTimer = function(time) 
{
	var hours = parseInt(time / 3600);
	var minutes = parseInt((time % 3600) / 60);
	var seconds = parseInt(time % 60);
	var formattedTime = (hours > 0 ? hours + ":" : "") + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
	
	$('#timer', this.displayElement).html(formattedTime);
};

/**
 * Recreates the duration slider whenever the duration changes.
 * 
 * @param newDuration The new duration.
 */
Display.prototype.updateDuration = function(newDuration) 
{
	if (newDuration == this.duration) return;
	this.duration = newDuration;
	var _this = this;
	
	if (this.duration <= 0)
	{
		$('#seekControl', this.displayElement).slider({
			value : 0,
			range: "min",
			disabled : true
		});
	}
	else
	{
		$('#seekControl', this.displayElement).slider({
			disabled : false,
			range: "min",
			min: 0,
			max: this.duration * 1000,
			step: 1,
			change: function(event, ui) {
				if (event.originalEvent != undefined)
				{
					$.ajax('controller/playback/seek?position=' + parseInt(ui.value / 1000));
				}
			},
			start: function(event, ui) {
				if (event.originalEvent != undefined) _this.isSeeking = true; 
			}, 
			stop: function(event, ui) {
				if (event.originalEvent != undefined) _this.isSeeking = false;
			} 
		});		
	}
};
