
/**
 * The id
 */
var id;

/**
 * The name
 */
var name;

/**
 * The genres
 */
var genres;

/**
 * The keywords
 */
var keywords;

/**
 * The function that is used to play this item. 
 */
var playFunction;

/**
 * The function that is used to enqueue this item. If undefined, enqueuing is not supported.
 */
var enqueueFunction;

/**
 * The function that is used to delete this item. If undefined, deleting is not supported.
 */
var deleteFunction;

/**
 * The function that is used to store this item. If undefined, storing is not supported.
 */
var storeFunction;

/**
 * Creates a new library item
 * 
 * @param id The id 
 * @param name The name
 * @param genres The genres
 * @param keywords The keywords
 * @param playFunction The function that is used to play this item.
 * @param enqueueFunction The function that is used to enqueue this item. If undefined, enqueuing is not supported.
 * @param deleteFunction The function that is used to delete this item. If undefined, deleting is not supported.
 * @param storeFunction The function that is used to store this item. If undefined, storing is not supported.
 */
function Item(id, name, genres, keywords, playFunction, enqueueFunction, deleteFunction, storeFunction)
{
	this.id = id;
	this.name = name;
	this.genres = genres;
	this.keywords = keywords;
	this.playFunction = playFunction;
	this.enqueueFunction = enqueueFunction;
	this.deleteFunction = deleteFunction;
	this.storeFunction = storeFunction;
}

/**
 * Appends this item to a parent element.
 * 
 * @param parent The parent element.
 */
Item.prototype.appendAsElement = function(parent)
{
	var _this = this;
	
	var enqueabale = this.enqueueFunction != null && this.enqueueFunction != undefined;
	var deletable = this.deleteFunction != null && this.deleteFunction != undefined;
	var storable = this.storeFunction != null && this.storeFunction != undefined;
	
	parent.append(
			'<div class="ui-button ui-widget ui-state-default ui-button-text-only item item-' + this.id + '">' + 
				'<span class="ui-button-text"><table><tr>' +
					'<td class="button"><button value="play-' + this.id + '" id="play-file-' + this.id + '"></button></td>' + 
					(enqueabale ? '<td class="button"><button id="enqueue-file-' + this.id + '"></button></td>' : '') + 
					'<td class="title">' + this.name + '</td>' +  
					'<td class="genre">' + this.genres + '</td>' +  
					(deletable ? '<td class="button"><button value="' + this.id + '" id="delete-file-' + this.id + '"></button></td>' : '') + 
					(storable ? '<td class="button"><button value="store-' + this.id + '" id="store-file-' + this.id + '"></button></td>' : '') + 
				'</tr></table></span>' + 
			'</div><div style="clear:both;"></div>');
	
	$('#play-file-' + this.id).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() {_this.playFunction(_this.id);});
	if (enqueabale) $('#enqueue-file-' + this.id).button({icons: {primary: 'ui-icon-plus'}, text: false}).click(function() {_this.enqueueFunction(_this.id);});
	if (deletable) $('#delete-file-' + this.id).button({icons: {primary: 'ui-icon-cancel'}, text: false}).click(function() {_this.deleteFunction(_this.id);});
	if (storable) $('#store-file-' + this.id).button({icons: {primary: 'ui-icon-plus'}, text: false}).click(function() {_this.storeFunction(_this.id);});
	
};

/**
 * Filters this item.
 * 
 * @param filter The filter string
 * 
 * @return true if this item has been filtered away, false otherwise.
 */
Item.prototype.filter = function(filter)
{
	var isValid = false;
	for (var keyword in this.keywords)
	{
		if (this.keywords[keyword].toLowerCase().indexOf(filter.toLowerCase()) != -1) 
		{
			isValid = true;
			break;
		}
	}
	
	var element = $('.item-' + this.id);
	if (isValid) element.show();
	else element.hide();
	
	return !isValid;
};
