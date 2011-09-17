

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

PlaybackControl.prototype.send = function(command)
{
	$.ajax({
		  url: this.controllerUrl + controller,
		  success: function()
		  {
		    
		  },
		  error: function(jqXHR, textStatus, errorThrown)
		  {
			  
		  }
		});	
};