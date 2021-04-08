# RetroArch Randomizer

## Why?
I built this tool in an attempt to overcome choice paralysis when deciding which game to play. Too often I was finding myself playing the same games over and over again, so writing a tool like this to randomly select a game and play it for a set length of time was a way to discover some of the more obscure games out there.

## How?
RetroArch Randomizer scans your RetroArch playlists, selects a random game and plays it for a set period of time. 
After the timeout has elapsed, it closes the game and selects another random game. While a game is playing, a timer and information about the current game, system, and time left is shown at the top of the screen.

## Usage
Usage is fairly straightforward.
- Ensure that you have RetroArch installed and playlists created for each of your game systems.
- Clone the repo and run mvn package to create the executable jar file
- If you installed RetroArch in the default location, no command line parameters need to be supplied. The randomizer should work out of the box.

## Requirements
- RetroArch + configured playlists
- Apache Maven (to build)
- Java 1.8+

## Command line arguments
```
usage: java -jar retroarch-randomizer.jar [-d <dir>] [-e <path>] [-h] [-p <dir>] [-t <interval>] [-u <unit>]
RetroArch Randomizer scans your RetroArch library configuration files, selects a random game and plays it for a set period of time. After the timeout
has elapsed, it closes the game and selects another.
 -d,--install-directory <dir>     RetroArch directory (default: C:\Users\<user>\AppData\Roaming\RetroArch)
 -e,--executable-path <path>      RetroArch executable path (default: C:\Users\<user>\AppData\Roaming\RetroArch\retroarch.exe)
 -h,--help                        Display this help message.
 -p,--playlist-directory <dir>    RetroArch playlist directory (default: C:\Users\<user>\AppData\Roaming\RetroArch\playlists)
 -t,--time-interval <interval>    Interval between games (default: 10 MINUTES)
 -u,--time-interval-unit <unit>   Interval time unit (default: MINUTES)
 ```
