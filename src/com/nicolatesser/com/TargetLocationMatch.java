package com.nicolatesser.com;

import android.location.Location;

/**
 * A target location which has been detected via wifi or android location.
 * @author tex
 *
 */
public class TargetLocationMatch {

	public static String WIFI_MATCH = "WIFI_MATCH";
	
	public static String LOCATION_MATCH = "LOCATION_MATCH";
	
	private TargetLocation targetLocation;
	
	private String match;
	
	public TargetLocationMatch(TargetLocation targetLocation, String match)
	{
		this.targetLocation=targetLocation;
		this.match=match;
	}

	public TargetLocation getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(TargetLocation targetLocation) {
		this.targetLocation = targetLocation;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}
	
	
	
}
