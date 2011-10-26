
/**
 * The equalizerControlElement
 */
var equalizerControlElement;

/**
 * Init the equalizer control
 * 
 * @param equalizerControlElement The equalizer control element
 */
function Equalizer(equalizerControlElement)
{
	this.equalizerControlElement = equalizerControlElement;
	var _this = this;
	
	$.getJSON('controller/equalizer/getData', function(data) {
		_this.initEqualizer(data);
	});
	
}

/**
 * Initialize the equalizer element and attach listeners to it
 * 
 * @param data The initial data
 */
Equalizer.prototype.initEqualizer = function(data)
{
	for (var i = 0; i < 10; i++)
	{
		this.equalizerControlElement.append('<span class="band band' + i + '"></span>');
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
};
