
var controllerUrl;

function PlaylistControl()
{
	this.controllerUrl = "controller/playlist/";
}

PlaylistControl.prototype.addUrl = function(url)
{
	$.ajax({
		  url: this.controllerUrl + "addUrl",
		  data: "url=" + url,
		  success: function()
		  {
		    
		  },
		  error: function(jqXHR, textStatus, errorThrown)
		  {
			  
		  }
		});	
};


PlaylistControl.prototype.send = function(command)
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