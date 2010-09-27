package com.nicolatesser.com;

import java.util.List;
import java.util.Vector;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.widget.Toast;

public class TimeTrackerService {

	private List<TargetLocation> targetLocations = getTargetLocations();

	private TargetLocationMatch currentMatch = null;

	private StorageHelper storageHelper;
	
	private TimeTrackoid timeTrackoid;
	
	public static String CHECK_IN ="CHECK-IN";

	public static String CHECK_OUT ="CHECK-OUT";

	public TimeTrackerService(StorageHelper storageHelper, TimeTrackoid timeTrackoid) {
		this.storageHelper = storageHelper;
		this.timeTrackoid = timeTrackoid;
		
		this.currentMatch = this.storageHelper.getLastCheckInOut();
	}

	protected void checkIn(TargetLocationMatch match) {
		String name = match.getTargetLocation().getName();
		this.storageHelper.addCheckInOut(name, CHECK_IN, match.getMatch());
		this.currentMatch = match;
		this.timeTrackoid.updateCheckInsOutsView();
	}

	protected void checkOut(TargetLocationMatch match) {
		String name = match.getTargetLocation().getName();
		this.storageHelper.addCheckInOut(name, CHECK_OUT, match.getMatch());
		this.currentMatch = null;
		this.timeTrackoid.updateCheckInsOutsView();

	}

	public void listenNetworkScanResult(List<ScanResult> results) {

		boolean currentLocationFound = false;
		boolean targetLocationFound = false;

		for (ScanResult result : results) {

			String ssid = result.SSID;

			TargetLocation targetLocation = getTargetLocationBySsid(ssid);
			if (targetLocation != null) {
				targetLocationFound = true;

				// check current match
				if (matchesCurrentMatch(targetLocation)) {
					// do nothing, the wifi confirms that we are in the
					// checked-in location
					currentLocationFound = true;
				} else {
					// check-out from current position
					if (this.currentMatch != null) {
						checkOut(currentMatch);
					}
					// check-in
					checkIn(new TargetLocationMatch(targetLocation,
							TargetLocationMatch.WIFI_MATCH));
				}
				// listen just to first match
				break;
			}

		}

		// if I did not find any target location,then checkout from current

		if (!targetLocationFound) {
			if (this.currentMatch != null) {
				checkOut(currentMatch);
			}
		}

	}

	public void listenLocationChanged(Location location) {
		// TODO : complete
	}

	protected boolean matchesCurrentMatch(TargetLocation targetLocation) {
		if (currentMatch == null) {
			return false;
		}

//		if (matchType.equals(TargetLocationMatch.WIFI_MATCH)) {
//			if (currentMatch.getMatch().equals(TargetLocationMatch.WIFI_MATCH)) {
//				if (currentMatch.getTargetLocation().getSsid()
//						.equals(targetLocation.getSsid())) {
//					return true;
//				}
//			}
//		}

		if (targetLocation.getName().equals(currentMatch.getTargetLocation().getName()))
		{
			return true;
		}
		
		return false;

	}

	protected TargetLocation getTargetLocationBySsid(String ssid) {
		for (TargetLocation targetLocation : targetLocations) {
			if (targetLocation.getSsid().equals(ssid)) {
				return targetLocation;
			}
		}
		return null;
	}

	protected List<TargetLocation> getTargetLocations() {

		List<TargetLocation> targetLocations = new Vector<TargetLocation>();

		String name = "HOME";
		String ssid = "FRITZ!Box Fon WLAN 7113";
		Location location = null;
		TargetLocation targetLocation = new TargetLocation(name, ssid, location);
		targetLocations.add(targetLocation);

		name = "SEITENBAU";
		ssid = "SB-Guest";
		location = null;
		targetLocation = new TargetLocation(name, ssid, location);
		targetLocations.add(targetLocation);

		return targetLocations;
	}

}
