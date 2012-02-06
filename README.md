OpenLobby
=========
This is a modular, cross-platform lobby for Spring (www.springrts.com) intended as a successor to CheeseLobby.

Bundles are found inside /modules/.

## Structure
- OpenLobby:			Aggregator/parent project inherited by children.
	- Core: 			Handles OSGi dirty work.
	- Launcher: 		Contains main class (loads OSGi framework and scripting environment).
	- Listener: 		Listens for server messages, see https://github.com/spring/LobbyProtocol
	- Login:			Provides functionality for login consumers (login, registration).
	- Communication:	Provides functionality for chat consumer (chat, channels, listing users).
	- Room:				Provides functionality for battle consumers (join, host, leave, list).
	- Download:			Provides functionality for download consumers (rapid, http, torrent).
	- JUnitsync:		See https://github.com/spring/JUnitSync	
	- Logging:			File and remote logging wrapper for the OSGi logging service.
	- Distribution:		Collects artifacts from all modules and assembles them.

This is the backend to OpenLobby. OpenLobby frontend(GUI) will be composed of JSR223 scripts.

Usage
-----	
###Unit tests
TBA.	
	
###Build
Run 'mvn package'

###Running
Run 'mvn exec:exec' on the Launcher module.

###Deployment
After building find the zip file in modules/Distribution/target.

