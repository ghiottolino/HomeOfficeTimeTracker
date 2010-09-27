Design

DataModel

TargetLocation(name, ssid, geo coordinates)
TargetLocationMatch(targetLocation, matchType)
Check-in/out (TargetLocationMatch, time, type (in-out))



Architecture:

1) Activity
2) WifiManager, LocationManager
3) TimeTrackerService
4) Database

Service:
 - currentTargetLocationMatch(could be null)
 
 - listenNetworkScanResult
   - if targetlocationMatch is in & different from current location, write log
   - if currentLocationmatch was a wifi match, and not anymore in, then write a exit log. 
 
 
 - listenPositionChange





Views:
- Time Tracker views (show last time tracker)
- Target Location (possibility to add more locations).




