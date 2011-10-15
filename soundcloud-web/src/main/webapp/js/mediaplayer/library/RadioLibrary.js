
RadioLibrary.prototype = new Library();
RadioLibrary.prototype.constructor = Library;

/**
 * The parent element where to append the library to.
 */
var parent;

/**
 * Creates a new radio library.
 * 
 * @param parent The element where to append the library to.
 */
function RadioLibrary(parent)
{
	var _this = this;
	this.parent = parent;
	
	this.parent.append(
			'<table><tr>' + 
				'<td class="radio-selection"></td>' + 
				'<td class="radio-content-container">' + 
					'<div class="radio-content scollable-content">' + 
						'<div class="stations remote-stations" style="display:none;"></div>' +
						'<div class="stations user-stations"></div>' +
					'</div>' + 
				'</td>' +
			'</tr></table>');
	
	this.initStations(null);
	this.initSelector($('table td.radio-selection', this.parent));
	
	this.appendSearchElement(parent, function(keyword) {
		_this.initStations(keyword);
	});
	
}

/**
 * (Re-) Inititalizes all stations. Removes all previous stations.
 * 
 * @param keyword An optional keyword.
 */
RadioLibrary.prototype.initStations = function(keyword) 
{
	var _this = this;	
	$.getJSON('controller/library/radio/getRemoteStations' + (keyword != null ? ('?keyword=' + keyword) : ''), function(data) {
		_this.initRemoteStations($('table div.radio-content .remote-stations', _this.parent), data);
	});
	$.getJSON('controller/library/radio/getUserStations' + (keyword != null ? ('?keyword=' + keyword) : ''), function(data) {
		_this.initUserStations($('table div.radio-content .user-stations', _this.parent), data);
	});
};

/**
 * Initializes the selector.
 * 
 * @param parent The parent element where to append the selector to.
 */
RadioLibrary.prototype.initSelector = function(parent)
{
	parent.append('<input id="radio-library-selector-user" type="radio" name="radio-library-selector" value="user" checked="checked"/>');
	parent.append('<label class="radio-library-selector" for="radio-library-selector-user">My stations</label>');
	parent.append('<div style="clear:both;"></div>');
	parent.append('<input id="radio-library-selector-remote" type="radio" name="radio-library-selector" value="remote"/>');
	parent.append('<label class="radio-library-selector" for="radio-library-selector-remote">Icecast</label>');
	
	parent.buttonset();
	$('input', parent).change(function() {
		$('.radio-content div.stations').hide();
		$('.radio-content div.' + $(this).val() + '-stations').show();
	});
	
};

/**
 * Initializes the remote stations library.
 * 
 * @param parent The element where to appent the stations to.
 * @param data The initial data.
 */
RadioLibrary.prototype.initRemoteStations = function(parent, data)
{
	var _this = this;
	
	parent.html(data.length == 0 ? '<div class="no-stations-available">No stations found</div>' : '');
	
	for (var i = 0; i < data.length; i++)
	{
		var playFunction = function(id) {
			$.ajax('controller/library/radio/play?type=remote&id=' + id);
		};
		var storeFunction = function(id) {
			$.getJSON('controller/library/radio/store?id=' + id, function(data) {
				_this.appendUserStation(parent, data);
			});
		};
		
		var item = new Item(data[i].id, data[i].shortTitle, data[i].genres, data[i].genres, playFunction, null, null, storeFunction);
		item.appendAsElement(parent);
	}
};

/**
 * Initializes the user stations library.
 * 
 * @param parent The element where to appent the stations to.
 * @param data The initial data.
 */
RadioLibrary.prototype.initUserStations = function(parent, data)
{
	parent.html(data.length == 0 ? '<div class="no-stations-available">No stations found</div>' : '');
	
	for (var i = 0; i < data.length; i++)
		this.appendUserStation(parent, data[i]);
};


/**
 * Appends a single user station.
 * 
 * @param parent The element where to appent the stations to.
 * @param data The item data.
 */
RadioLibrary.prototype.appendUserStation = function(parent, data)
{
	var playFunction = function(id) {
		$.ajax('controller/library/radio/play?type=user&id=' + id);
	};
	var deleteFunction = function(id) {
		$.ajax('controller/library/radio/delete?id=' + id);
		$('.item-' + id, this.parent).remove();
	};
	
	var item = new Item(data.id, data.shortTitle, data.genres, data.keywords, playFunction, null, deleteFunction, null);
	item.appendAsElement(parent);
};

