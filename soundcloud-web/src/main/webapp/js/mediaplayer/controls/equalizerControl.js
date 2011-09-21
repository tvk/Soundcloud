
/**
 * Init the equalizer control
 * 
 * @param equalizerControlElement The equalizer control element
 */
function EqualizerControl(equalizerControlElement)
{
	
	$.getJSON('controller/equalizer/getData', function(data) {
		initEqualizer(equalizerControlElement, data);
	});
	
}

function initEqualizer(equalizerControlElement, data)
{
	for (var i = 0; i < 10; i++)
	{
		$(equalizerControlElement).append('<span class="band band' + i + '"></span>');
		$('.band' + i, equalizerControlElement).empty().slider({
			value: 10,
			min: -12,
			max: 24,
			range: "min",
			animate: true,
			orientation: "vertical"
		});
	}
}