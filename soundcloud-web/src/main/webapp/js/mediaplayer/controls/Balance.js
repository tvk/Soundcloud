
/**
 * The balance element
 */
var balanceElement;

/**
 * Constructor
 * 
 * @param balanceElement The balance element
 */
function Balance(balanceElement)
{
	var _this = this;
	this.balanceElement = balanceElement;
	
	$.getJSON('controller/balance/getData', function(data) {
		_this.initBalance(data);
	});
}

/**
 * Initalizes the slider and the reset button
 * 
 * @param value The initial value
 */
Balance.prototype.initBalance = function(value)
{
	var _this = this;
	
	this.balanceElement.append('<div class="balanceSlider"></div>');
	$('.balanceSlider', this.balanceElement).slider({
		value: value,
		min: -1, max: 1, step: 0.05,
		range: "min",
		animate: true,
		change: function(event, ui)
		{
			$.ajax("controller/balance/setData?value=" + ui.value);
		}
	});	
	
	this.balanceElement.append('<button class="balanceReset">Reset</button>');
	$('.balanceReset', this.balanceElement).button({
		icons: { primary: "ui-icon-cancel" }}).click(function()
		{
        	$('.balanceSlider', _this.balanceElement).slider("value", 0);
		});	
};