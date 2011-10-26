<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Soundcloud</title>

		<!-- jquery -->
		<script type="text/javascript" src="js/libraries/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="js/libraries/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="js/libraries/jquery.scrollTo-1.4.2-min.js"></script>
		<script type="text/javascript" src="js/libraries/jquery.includeMany-1.2.0.js"></script>
		<link rel="stylesheet" type="text/css" href="css/dark-hive/jquery-ui-1.8.16.custom.css">
		
		<!-- CSS -->
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/mediaplayer.css">
		
		<!-- Media Library -->
		<script type="text/javascript" src="js/mediaplayer/library/Library.js"></script>
		<script type="text/javascript" src="js/mediaplayer/library/RadioLibrary.js"></script>
		<script type="text/javascript" src="js/mediaplayer/library/LocalLibrary.js"></script>
		
		<!-- Media Player -->
		<script type="text/javascript" src="js/mediaplayer/controls/Display.js"></script>
		<script type="text/javascript" src="js/mediaplayer/controls/Equalizer.js"></script>
		<script type="text/javascript" src="js/mediaplayer/controls/PlaybackControl.js"></script>
		<script type="text/javascript" src="js/mediaplayer/controls/Playlist.js"></script>
		<script type="text/javascript" src="js/mediaplayer/controls/VolumeControl.js"></script>
		<script type="text/javascript" src="js/mediaplayer/MessageMediator.js"></script>
		<script type="text/javascript" src="js/mediaplayer/MediaPlayer.js"></script>
				
		<!-- Init -->
		<script>
		
			$(document).ready(function() {
				
				// Init the media player
				var mediaPlayer = new MediaPlayer($('#display'), $('#playlist'), $('#playback'), $('#volume'));
				
				// Init the media library
				var radioLibrary = new RadioLibrary($('#tabpanel #tab-radio'));
				var localLibrary = new LocalLibrary($('#tabpanel #tab-medialibrary'));
				$("#tabpanel").tabs();
				

				// Init the settings panels
				var equalizer = new Equalizer($('#equalizer'));
				$("#tabpanel-settings").tabs();
			});
		
		</script>
		
	</head>
	<body>
	
		<table id="mainPanel">
			<tr>
				
				<td id="leftPanel" class="panel">
					<div class="mainTitle">SOUNDCLOUD</div>
					<div style="clear:both;"></div><hr/>
					<div id="display"></div>
					<div style="clear:both;"></div><hr/>
					<div id="playback"></div><div id="volume"></div>
					<div style="clear:both;"></div><hr/>
					<div id="playlist"></div>
					<div style="clear:both;"></div><hr/>
				</td>
		
				<td id="rightPanel" class="panel">
					<div id="tabpanel">
						<ul>
							<li><a href="#tab-medialibrary">Media Library</a></li>
							<li><a href="#tab-radio">Radio</a></li>
							<li><a href="#tab-settings">Settings</a></li>
						</ul>
						<div id="tab-medialibrary"></div>
						<div id="tab-radio"></div>
						<div id="tab-settings">
							<div id="tabpanel-settings">
								<ul>
									<li><a href="#tab-settings-audio">Audio</a></li>
									<li><a href="#tab-settings-system">System</a></li>
								</ul>							
								<div id="tab-settings-audio">
									<span>Equalizer</span>
									<div id="equalizer"></div>
								</div>
								<div id="tab-settings-system">
								</div>
							</div>
						</div>
					</div>
				</td>
				
			</tr>
		</table>
		
	</body>
</html>
	