

var controllerUrl;

function PlaybackControl()
{
	this.controllerUrl = "controller/playback/";
}

PlaybackControl.prototype.play = function()
{
	this.send("play");
};


PlaybackControl.prototype.stop = function()
{
	this.send("stop");
};


PlaybackControl.prototype.next = function()
{
	this.send("next");
};


PlaybackControl.prototype.previous = function()
{
	this.send("previous");
};


PlaybackControl.prototype.send = function(command)
{
	$.ajax({
		  url: this.controllerUrl + command,
		  success: function()
		  {
		    
		  },
		  error: function(jqXHR, textStatus, errorThrown)
		  {
			  
		  }
		});	
};