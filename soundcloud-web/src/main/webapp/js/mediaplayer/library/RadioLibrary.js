
RadioLibrary.prototype = new Library();
RadioLibrary.prototype.constructor = Library;

/**
 * The parent element where to append the library to.
 */
var parent;

/**
 * The parent element where to append the remote stations to
 */
var remoteStationsParent;


/**
 * The parent element where to append the user stations to
 */
var userStationsParent;

/**
 * Creates a new radio library.
 * 
 * @param parent The element where to append the library to.
 */
function RadioLibrary(parent)
{
	var _this = this;
	this.parent = parent;
	
	// Create the root dom elements
	this.parent.append(
			'<table><tr>' + 
				'<td class="radio-selection"></td>' + 
				'<td class="radio-content-container">' + 
					'<div class="radio-content scollable-content">' + 
						
						'<div class="remote-stations-container stations-container" style="display:none;">' +
							'<div class="stations remote-stations"></div>' +
						'</div>' +
						
						'<div class="user-stations-container stations-container">' +
							'<div class="stations user-stations"></div>' +
							'<div class="create-station-container">' +
								'<button id="create-station">Create new station</button>' +
							'</div>' +
						'</div>' +
						
					'</div>' + 
				'</td>' +
			'</tr></table>');
	
	this.remoteStationsParent = $('.stations.remote-stations', this.parent);
	this.userStationsParent = $('.stations.user-stations', this.parent);
	
	// Initialize the stations and the user/remote selector
	this.initStations(null);
	this.initSelector($('table td.radio-selection', this.parent));
	
	// Init the search element
	this.appendSearchElement(parent, function(keyword) {
		_this.initStations(keyword);
	});
	
	// Init the "Create new Station" button
	$('#create-station', this.parent).button({icons: {primary: 'ui-icon-plus'}, text: true}).click(function() {
		$('#add-dialog').dialog({title: 'Add station', modal: true, width:320, height:200, 
			buttons: {
				"Create station": function() {
					$.ajax({type: "POST", url: "controller/library/radio/create",
						   data: 
							   "name=" + $('#add-station-name').val() +
							   "&genres=" + $('#add-station-genres').val() +
							   "&url=" + $('#add-station-url').val(),
						   success: function(data) {
							   _this.appendUserStation(data);
						   }
						 });
					$(this).dialog("close");
				},
				Cancel: function() {
					$(this).dialog("close");
				}
		}});
	});

	// Init the "Create new station" modal box
	this.parent.append(
			'<div style="display:none" id="add-dialog"><form>' +
				'<input type="text" id="add-station-name" name="name" /><label for="name">Name</label><div style="clear:both;"></div>' +
				'<input type="text" id="add-station-url" name="url" /><label for="url">URL</label><div style="clear:both;"></div>' +
				'<input type="text" id="add-station-genres" name="genres" /><label for="genres">Genres (seperated by ,)</label>' +
			'</form></div>');
	
	// Init the "Delete" modal box 
	this.parent.append('<div style="display:none" id="delete-dialog">Do you really want to delete this station?</div>');
	
	// Init the "New station created" modal box 
	this.parent.append('<div style="display:none" id="station-stored-dialog">The station is now available under "My stations"</div>');

	this.userStationsParent.append('<div class="createUserStation">');	
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
		_this.initRemoteStations(data);
	});
	$.getJSON('controller/library/radio/getUserStations' + (keyword != null ? ('?keyword=' + keyword) : ''), function(data) {
		_this.initUserStations(data);
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
		$('.radio-content div.stations-container').hide();
		$('.radio-content div.' + $(this).val() + '-stations-container').show();
	});
	
};

/**
 * Initializes the remote stations library.
 * 
 * @param parent The element where to appent the stations to.
 * @param data The initial data.
 */
RadioLibrary.prototype.initRemoteStations = function(data)
{
	var _this = this;
	
	this.remoteStationsParent.html(data.length == 0 ? '<div class="no-stations-available">No stations found</div>' : '');
	
	for (var i = 0; i < data.length; i++)
	{
		var playFunction = function(id) {
			$.ajax('controller/library/radio/play?type=remote&id=' + id);
		};
		var storeFunction = function(id) {
			$.getJSON('controller/library/radio/store?id=' + id, function(data) {
				_this.appendUserStation(data);
				$('#station-stored-dialog', _this.parent).dialog({modal: true, buttons: {
					Ok: function() {
						$(this).dialog("close");
					}}
				});
			});
		};
		
		var item = new Item(data[i].id, data[i].shortTitle, data[i].genres, data[i].genres, playFunction, null, null, storeFunction);
		item.appendAsElement(this.remoteStationsParent);
	}
};

/**
 * Initializes the user stations library.
 * 
 * @param parent The element where to appent the stations to.
 * @param data The initial data.
 */
RadioLibrary.prototype.initUserStations = function(data)
{
	var _this = this;
	this.userStationsParent.html(data.length == 0 ? '<div class="no-stations-available">No stations found</div>' : '');
	
	for (var i = 0; i < data.length; i++)
		this.appendUserStation(data[i]);
};


/**
 * Appends a single user station.
 * 
 * @param parent The element where to appent the stations to.
 * @param data The item data.
 */
RadioLibrary.prototype.appendUserStation = function(data)
{
	var _this = this;
	var playFunction = function(id) {
		$.ajax('controller/library/radio/play?type=user&id=' + id);
	};
	var deleteFunction = function(id) {
		$('#delete-dialog', _this.parent).dialog({resizable: false, height:140, modal: true,
			buttons: {
				"Delete station": function() {
					$.ajax('controller/library/radio/delete?id=' + id);
					$('.item-' + id, this.userStationsParent).remove();
					$(this).dialog("close");
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}});
	};
	
	var item = new Item(data.id, data.shortTitle, data.genres, data.keywords, playFunction, null, deleteFunction, null);
	item.appendAsElement(this.userStationsParent);
};

