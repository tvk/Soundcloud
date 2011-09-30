
var listeners;

function MessageMediator(_listeners)
{
	
	listeners = _listeners;
	getNextMessage();
}

function getNextMessage()
{
	$.ajax('controller/messageListener/getNextEvent',
			{success: function(data) {
				getNextMessage();
				processMessage(data);
			}});
}

function processMessage(data)
{

	for (var i = 0; i < listeners.length; i++) 
	{
		listeners[i].processMessage(data);
	}
}