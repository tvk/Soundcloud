
var radioLibrary;

var localLibrary;

function MediaLibrary(localLibraryElement, radioLibraryElement)
{
	radioLibrary = new RadioLibrary(radioLibraryElement);
	localLibrary = new LocalLibrary(localLibraryElement);
}