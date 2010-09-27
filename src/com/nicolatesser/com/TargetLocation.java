package com.nicolatesser.com;

import android.location.Location;

/**
 * A location which one wants to monitor defined in terms of Android.location and WiFi SSID.
 * @author tex
 *
 */
public class TargetLocation {

	private String name;
	private String ssid;	
	private Location location;
	
	
	public TargetLocation(String name, String ssid, Location location) {
		super();
		this.name = name;
		this.ssid = ssid;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
