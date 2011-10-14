
$.include('js/mediaplayer/library/Item.js');
$.include('js/mediaplayer/library/Folder.js');
$.include('css/medialibrary.css');

/**
 * Abstract constructor for a library
 */
function Library() { }

/**
 * Appends the search panel
 * 
 * @param parent The element where to append the search panel to
 */
Library.prototype.appendSearchElement = function(parent)
{
	parent.append(
			'<div class="searchPanel">' + 
				'<span class="title">Filter:</span>' +
				'<input type="text" class="search-field"></input>' +
			'</div>');
};