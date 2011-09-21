
/**
 * Initalizes and controls volume control panel
 * 
 * @param volumeControlElement The volume control element.
 */
function VolumeControl(volumeControlElement)
{

	$.getJSON('controller/volume/getData', function(data) {
				initVolumeSlider(volumeControlElement, data.volume);
				initMuteButton(volumeControlElement, data.mute);
			});
	
}

/**
 * Init the volume slider
 * 
 * @param volumeControlElement The volume control element.
 * @param volume The current volume
 */
function initVolumeSlider(volumeControlElement, volume)
{
	$(volumeControlElement).append('<div class="volumeControlSlider"></div>');
	$('.volumeControlSlider', volumeControlElement).slider({
		value: volume * 100,
		orientation: "horizontal",
		range: "min",
		change: function(event, ui) { 
			$(this).blur();
			$.ajax({
				  url: "controller/volume/setVolume?volume=" + (ui.value / 100.0),
				  error: function(jqXHR, textStatus, errorThrown) {
					  
				  }
				});	
		}
	});
};

/**
 * Init the mute button
 * 
 * @param volumeControlElement The volume control element.
 * @param mute The current mute value
 */
function initMuteButton(volumeControlElement, mute)
{
	
	$(volumeControlElement).append('<input type="checkbox" id="volumeMuteButton" ' + (mute ? 'checked="true"' : '') + '/>');
	$(volumeControlElement).append('<label for="volumeMuteButton" class="volumeMuteButtonLabel"></label>');
	
	$('#volumeMuteButton', volumeControlElement).button({
		icons: {primary: "ui-icon-volume-on"}, text: false}).click(function() {
			$(this).blur();
			$.ajax({
				  url: "controller/volume/setMute?mute=" + $(this).is(':checked'),
				  error: function(jqXHR, textStatus, errorThrown) {
					  
				  }
				});
	});
	
};