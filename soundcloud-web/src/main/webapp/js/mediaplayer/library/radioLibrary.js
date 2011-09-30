

function RadioLibrary(radioLibraryElement)
{
	$(radioLibraryElement).append('<table><tr><td id="radio-library-selection"></td><td id="radio-library-content"></td></tr>');
	
	// Init the left panel selection (My stations and icecast)
	topLevel = $('#radio-library-selection', radioLibraryElement); 
	
	topLevel.append('<input id="radio-library-selection-my-stations" type="radio" name="radio-library-selection" value="my-stations" checked="checked"/>');
	topLevel.append('<label for="radio-library-selection-my-stations">My stations</label>');

	topLevel.append('<input id="radio-library-selection-icecast" type="radio" name="radio-library-selection" value="icecast"/>');
	topLevel.append('<label for="radio-library-selection-icecast">Icecast</label>');
	
	topLevel.buttonset();
	$('input', topLevel).change(function() {
		$('.content').hide();
		$('.content#content-' + $(this).val()).show();
	});
	
	// Init the content panels (The actual stations)
	content = $('#radio-library-content', radioLibraryElement);
	
	content.append('<div class="content" id="content-my-stations"><table></table></div>');
	content.append('<div class="content" id="content-icecast" style="display:none;"><table></table></div>');
	
	$.getJSON('controller/library/radio/getUserStations', function(data) {
		initStations('my-stations', data);
	});
	
	// Init the "Delete" modal box 
	radioLibraryElement.append('<div style="display:none" id="delete-dialog">Do you really want to delete this station?</div>');
	
	// Init the "Add" modal box
	content.append('<div style="display:none" id="add-dialog"><form></form></div>');
	$('#add-dialog form', content).append('<input type="text" id="add-station-name" name="name" /><label for="name">Name</label><div style="clear:both;"></div>');
	$('#add-dialog form', content).append('<input type="text" id="add-station-url" name="url" /><label for="url">URL</label><div style="clear:both;"></div>');
	$('#add-dialog form', content).append('<input type="text" id="add-station-genres" name="genres" /><label for="genres">Genres (seperated by ,)</label>');
	
}

function appendStationElement(data)
{
	var station = data.id;
	
	$('#add-station-row').before('<tr id="station-' + station + '" class="station"></tr>');
	$('#station-' + station).append('<td><button id="play-station-' + station + '" value="' + station + '">Play</button></td>');
	$('#station-' + station).append('<td class="title">' + data.name + '</td>');
	$('#station-' + station).append('<td class="url">' + data.url + '</td>');
	$('#station-' + station).append('<td class="genres">' + data.genres + '</td>');
	$('#station-' + station).append('<td><button id="delete-station-' + station + '" value="' + station + '">Delete</button></td>');
	
	$('#play-station-' + station).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() {
		$.ajax('controller/library/radio/play?id=' + $(this).val());
	});
	
	$('#delete-station-' + station).button({icons: {primary: 'ui-icon-cancel'}, text: false}).click(function() {
		station = $(this).val();
		$('#delete-dialog').dialog({resizable: false, height:140, modal: true,
			buttons: {
				"Delete station": function() {
					$.ajax('controller/library/radio/delete?id=' + station);
					$('#station-' + station).remove(); 
					$(this).dialog("close");
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}});
	});
}

function initStations(name, data)
{
	contentElement = $('#content-' + name + ' table', content);
	
	// Append header
	contentElement.append(
			'<thead><tr>' +
			'<td></td>' + 
			'<td>Name</td>' + 
			'<td>URL</td>' + 
			'<td>Genres</td>' + 
			'<td></td>' + 
			'</tr></thead>');
	
	// Append the add-button
	contentElement.append('<tr id="add-station-row"><td><button id="add-station">Add</button></td><td colspan="5"></td></tr>');
	$('#add-station').button({icons: {primary: 'ui-icon-plus'}, text: false}).click(function() {
		$('#add-dialog').dialog({title: 'Add station', modal: true, width:320, height:200, 
			buttons: {
				"Create station": function() {
					$.ajax({type: "POST", url: "controller/library/radio/create",
						   data: 
							   "name=" + $('#add-station-name').val() +
							   "&genres=" + $('#add-station-genres').val() +
							   "&url=" + $('#add-station-url').val(),
						   success: function(data) {
							   appendStationElement(data);
						   }
						 });
					$(this).dialog("close");
				},
				Cancel: function() {
					$(this).dialog("close");
				}
		}});
	});

	// Append the stations 
	for (var i = 0; i < data.length; i++) this.appendStationElement(data[i]);
	
}