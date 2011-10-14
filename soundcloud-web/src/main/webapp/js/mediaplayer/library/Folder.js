
$.include('js/mediaplayer/library/Item.js');

/**
 * The name of this folder
 */
var name;

/**
 * The relative path of this folder
 */
var path;

/**
 * The level of this folder
 */
var level;

/**
 * The items of this folder
 */
var items;

/**
 * The subfolders of this folder
 */
var subfolders;


/**
 * Creates a new folder
 * 
 * @param name The name of this folder.
 * @param level The level of this folder.
 * @param path The absolute path of this folder.
 * @param items The items of this folder
 * @param subfolders The subfolders of this folder
 */
function Folder(name, level, path, items, subfolders)
{
	this.name = name;
	this.level = level;
	this.path = path;
	this.items = items;
	this.subfolders = subfolders;
}

/**
 * Appends this folder to the given element.
 * 
 * @param element The element where to append the folder to.
 * @param onSelectSubfolder Function that is called when a subfolder is selected
 */
Folder.prototype.appendAsElement = function(element, onSelectSubfolder, onPlayFolder, onEnqueueFolder) 
{
	var _this = this;
	
	// Append play and enqueue buttons
	if (this.level > 0)
	{
		element.append(
				'<div class="folder-title">' + 
					'<button id="play-folder-' + this.level + '">Play</button>' +
					'<button id="enqueue-folder-' + this.level + '">Enqueue</button>' +
					'<span class="title">' + this.name + '</span>' +
				'</div>');
		
		$('#play-folder-' + this.level, element).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() { onPlayFolder(_this.path); });
		$('#enqueue-folder-' + this.level, element).button({icons: {primary: 'ui-icon-plus'}, text: false}).click(function() { onEnqueueFolder(_this.path); });
	}
	
	// Append the subfolders
	element.append('<div class="subfolders"></div>');
	subfolders = $('.subfolders', element);
	for (var i = 0; i < this.subfolders.length; i++)
	{
		subfolders.append(
				'<div class="item">' + 
					'<input type="radio" name="folder-' + this.level + '" id="folder-' + this.level + '-' + i + '" value="' + this.path + "/" + this.subfolders[i] + '"/>' +
					'<label for="folder-' + this.level + '-' + i + '">' + this.subfolders[i] + '</label>' +
				'</div>' +
				'<div style="clear:both;"></div>');
		
	}
	subfolders.buttonset();
	$('input', subfolders).change(function() {
		onSelectSubfolder(_this.level, $(this).val());
	});
	
	// Append the files
	element.append('<div class="files"></div>');
	files = $('.files', element);
	for (var i = 0; i < this.items.length; i++)
		this.items[i].appendAsElement(files);
};

