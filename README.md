OpenLobby
=========
This is a modular cross-platform lobby for Spring (www.springrts.com) intended as a successor to CheeseLobby.

Essentials
-----	
###Unit tests
Run the 'surefire:test' goal.
	
###Build
Run the 'package' goal.

###Running
Run the 'exec:exec' goal on the Launcher module.

###Deployment
After building, zip file will be found in modules/Distribution/target/.

Architecture
------------
Bundles are found inside /modules/.

## Overview
- OpenLobby:			Aggregator/parent project inherited by children.
	- Core-manager: 	Handles OSGi dirty work.
	- Launcher: 		Contains main class (loads OSGi framework and scripting environment).
	- Listener: 		Listens for server messages, see https://github.com/spring/LobbyProtocol
	- Login:			Provides functionality for login consumers (login, registration).
	- Communication:	Provides functionality for chat consumer (chat, channels, listing users).
	- Room:				Provides functionality for battle consumers (join, host, leave, list).
	- Download:			Provides functionality for download consumers (rapid, http, torrent).
	- JUnitsync:		See https://github.com/spring/JUnitSync	
	- Logging:			File and remote logging wrapper for the OSGi logging service.
	- Distribution:		Collects artifacts from all modules and assembles them.
	
## Frontend(GUI)
The OpenLobby frontend will be composed of JSR223 scripts for flexibility.
	
### Core-manager
### Launcher
### Listener
### Login
### Communication
### Room
### Download
### JUnitsync
### Logging
### Distribution