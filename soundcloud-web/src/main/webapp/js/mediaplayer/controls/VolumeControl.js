
/**
 * The volumeControlElement
 */
var volumeControlElement;

/**
 * Initalizes and controls volume control panel
 * 
 * @param volumeControlElement The volume control element.
 */
function VolumeControl(volumeControlElement)
{
	this.volumeControlElement = volumeControlElement;
	var _this = this;
	
	$.getJSON('controller/volume/getData', function(data) {
		_this.initVolumeSlider(data.volume);
		_this.initMuteButton(data.mute);
	});
}

/**
 * Init the volume slider
 * 
 * @param volume The current volume
 */
VolumeControl.prototype.initVolumeSlider = function(volume)
{
	this.volumeControlElement.append('<div class="volumeControlSlider"></div>');
	$('.volumeControlSlider', this.volumeControlElement).slider({
		value: volume * 100,
		orientation: "horizontal",
		range: "min",
		slide: function(event, ui) { 
			$(this).blur();
			$.ajax({ url: "controller/volume/setVolume?volume=" + (ui.value / 100.0) });	
		}
	});
};

/**
 * Init the mute button
 * 
 * @param mute The current mute value
 */
VolumeControl.prototype.initMuteButton = function(mute)
{
	
	this.volumeControlElement.append('<input type="checkbox" id="volumeMuteButton" ' + (mute ? 'checked="true"' : '') + '/>');
	this.volumeControlElement.append('<label for="volumeMuteButton" class="volumeMuteButtonLabel"></label>');
	
	$('#volumeMuteButton', this.volumeControlElement).button({
		icons: {primary: "ui-icon-volume-on"}, text: false}).click(function() {
			$(this).blur();
			$.ajax({ url: "controller/volume/setMute?mute=" + $(this).is(':checked') });
	});
};

