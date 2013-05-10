# OpenLobby
This is a modular cross-platform lobby for Spring (www.springrts.com) intended as a successor to CheeseLobby.

##Getting started
For Eclipse, you will need the following plug-ins:
*	[m2eclipse-scala](https://github.com/sonatype/m2eclipse-scala)
*	[Scala IDE for Eclipse](http://scala-ide.org/)
*	[jgit](http://eclipse.org/jgit/)

Scala IDE may prompt you to add build paths, always choose No. This is handled by Maven.

For Netbeans, you will need the following plug-ins:
*	[nbscala](https://github.com/dcaoyuan/nbscala)

##Building
###Unit tests
Run the 'surefire:test' goal.
	
###Build
Run the 'package' goal.

###Running
Run the 'exec:exec' goal on the Launcher module.

###Deployment
After building, zip file will be found in modules/Distribution/target/.

##Project overview
From 10,000 ft.

OpenLobby is the parent project, an aggregator project in Maven terminology. The modules are:
*	Battle
*	Chat
*	Commons
*	Communication
*	IO
*	Launcher
*	Logging
*	Login
*	Primer

These are found in /modules/.

The /protocol/ directory contains a description of the implemented spring lobby protocol.

The (future) UI may support JSR223 scripting. The idea is to allow spring engine games to customize the lobby to fit their needs.