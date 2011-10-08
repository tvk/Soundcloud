
/**
 * Initializes the local library control.
 * 
 * @param localLibraryElement
 */
function LocalLibrary(localLibraryElement)
{
	localLibraryElement.append('<div class="local-library-container"><table><tr></tr></table></div>');
	
	$.getJSON('controller/library/local/getFolder', function(data) {
		initLevel(localLibraryElement, 0, data);
	});
}

function initLevel(localLibraryElement, level, data)
{
	// Remove everything of this level and above levels
	$('table tr td.level-' + level, localLibraryElement).remove();
	
	var classLevelIdentifier = '';
	for (var i = 0; i <= level; i++) classLevelIdentifier += 'level-' + i + ' '; 
	$('table tr', localLibraryElement).append(
			'<td class="' + classLevelIdentifier + 'library-column" id="library-column-level-' + level + '">' + 
				'<div class="level-container">' +
					'<div class="folder-buttons">' + 
						'<span class="title">All: </span>' +
						'<button id="play-folder-' + level + '">Play</button>' + 
						'<button id="enqueue-folder-' + level + '">Enqueue</button>' + 
					'</div>' +
					'<div class="level-container-subfolders"></div>' + 
					'<div class="level-container-files"></div>' + 
				'</div>' + 
			'</td>');
	
	// Init play and enqueue button
	$('#play-folder-' + level).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() {
		$.ajax('controller/library/local/playFolder?folder=' + encodeURIComponent(data.path));
	});
	
	$('#enqueue-folder-' + level).button({icons: {primary: 'ui-icon-plus'}, text: false}).click(function() {
		$.ajax('controller/library/local/enqueueFolder?folder=' + encodeURIComponent(data.path));
	});
	
	// Append subfolders
	var subfolders = $('table tr td#library-column-level-' + level + ' div.level-container div.level-container-subfolders', localLibraryElement); 

	for (var i = 0; i < data.subfolders.length; i++)
	{
		subfolders.append('<input id="library-folder-level-' + level + '-' + i + '" type="radio" name="library-folder-level-' + level + '" value="' + data.path + "/" + data.subfolders[i] + '"/>');
		subfolders.append('<label for="library-folder-level-' + level + '-' + i + '">' + data.subfolders[i] + '</label>');
	}
	subfolders.buttonset();
	$('input', subfolders).change(function() {
		$.getJSON('controller/library/local/getFolder?folder=' + encodeURIComponent($(this).val()), function(data) {
					initLevel(localLibraryElement, level+1, data);
			  });
	});
	
	// Append files
	var files = $('table tr td#library-column-level-' + level + ' div.level-container div.level-container-files', localLibraryElement); 

	for (var i = 0; i < data.files.length; i++)
	{
		files.append(
				'<label class="ui-button ui-widget ui-state-default ui-button-text-only">' + 
					'<span class="ui-button-text">' +
						'<button value="' + data.files[i].id + '" id="play-file-' + data.files[i].id + '"></button>' + 
						'<button value="' + data.files[i].id + '" id="enqueue-file-' + data.files[i].id + '"></button>' + 
						data.files[i].name + 
					'</span>' + 
				'</label>');
		
		$('#enqueue-file-' + data.files[i].id).button({icons: {primary: 'ui-icon-plus'}, text: false}).click(function() {
			$.ajax('controller/library/local/enqueueFile?file=' + $(this).val());			
		});
		$('#play-file-' + data.files[i].id).button({icons: {primary: 'ui-icon-play'}, text: false}).click(function() {
			$.ajax('controller/library/local/playFile?file=' + $(this).val());			
		});
		
	}
	
}