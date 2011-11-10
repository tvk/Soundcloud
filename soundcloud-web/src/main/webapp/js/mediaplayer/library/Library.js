
$.include('js/mediaplayer/library/Item.js');
$.include('js/mediaplayer/library/Folder.js');
$.include('css/medialibrary.css');

/**
 * A timer where the window.setTimeout call is stored.
 */
var timer;

/**
 * Abstract constructor for a library
 */
function Library() 
{
	this.timer = null;
}

/**
 * Appends the search panel
 * 
 * @param parent The element where to append the search panel to
 * @param onSearch Callback function when something was entered in the search panel.
 */
Library.prototype.appendSearchElement = function(parent, onSearch)
{
	var _this = this;
	
	parent.append(
			'<div class="searchPanel">' + 
				'<span class="title">Filter:</span>' +
				'<input type="text" class="search-field"></input>' +
				'<button class="search-field-clear">Clear</button>' +
			'</div>');
	
	$('.searchPanel .search-field-clear', parent).button({icons: {primary: 'ui-icon-cancel'}, text: false}).click(function() 
	{ 
		$('.searchPanel .search-field', parent).val('');
		onSearch('');
	});
			
	$('.searchPanel .search-field', parent).keyup(function()
	{
		if (_this.timer != null) window.clearTimeout(_this.timer);
		_this.timer = window.setTimeout(function() {
			_this.timer = null;
			onSearch($('.searchPanel .search-field', parent).val().trim());
		}, 300);
	});			
};