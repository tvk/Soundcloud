
/**
 * The equalizerControlElement
 */
var equalizerControlElement;

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

/**
 * Initialize the equalizer element and attach listeners to it
 * 
 * @param equalizerControlElement The equalizerControlElement 
 * @param data The initial data
 */
function initEqualizer(equalizerControlElement, data)
{
	for (var i = 0; i < 10; i++)
	{
		$(equalizerControlElement).append('<span class="band band' + i + '"></span>');
		$('.band' + i, equalizerControlElement).slider({
			value: data['BAND' + i],
			min: -12,
			max: 24,
			step: 0.1,
			range: "min",
			animate: true,
			orientation: "vertical",
			slide: function(event, ui)
			{
				var request = "controller/equalizer/setData?";
				for (var j = 0; j < 10; j++)
				{
					var value = $(".band" + j).slider("option", "value");
					request += "BAND" + j + "=" + value + (j < 9 ? "&" : "");
				}
				$.ajax(request);
			}
		});
	}
}
