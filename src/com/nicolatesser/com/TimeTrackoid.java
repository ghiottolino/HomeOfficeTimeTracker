package com.nicolatesser.com;

import java.util.List;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;

public class TimeTrackoid extends TabActivity {
	
    private static final String TAG = "TimeTrackoid";
	
	private  WifiManager wifi;
	
	private BroadcastReceiver receiver;
	
	private TimeTrackerService timeTrackerService;
	
	private StorageHelper storageHelper;
	
	
    private TextView mTextView;
    private ListView mListView;

	
	
	
	
    public WifiManager getWifi() {
		return wifi;
	}

	public void setWifi(WifiManager wifi) {
		this.wifi = wifi;
	}
	
	

	public TimeTrackerService getTimeTrackerService() {
		return timeTrackerService;
	}

	public void setTimeTrackerService(TimeTrackerService timeTrackerService) {
		this.timeTrackerService = timeTrackerService;
	}
	
	

	public StorageHelper getStorageHelper() {
		return storageHelper;
	}

	public void setStorageHelper(StorageHelper storageHelper) {
		this.storageHelper = storageHelper;
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // gui elements
        
        mTextView = (TextView) findViewById(R.id.text);
        mListView = (ListView) findViewById(R.id.list);

        
        // define tabs:
        
        Resources res = getResources();
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // tab1
        
        intent = new Intent().setClass(this, CheckInOutActivity.class);        
        spec = tabHost.newTabSpec("Check Ins Outs").setIndicator("Check Ins Outs",
                res.getDrawable(R.drawable.ic_tab_check_ins_outs))
            .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, CheckInOutActivity.class);        
        spec = tabHost.newTabSpec("Check Ins Outs2").setIndicator("Check Ins Outs2",
                res.getDrawable(R.drawable.ic_tab_check_ins_outs))
            .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(1);

        
        
        
        // define storage
        this.storageHelper = new StorageHelper(getApplicationContext());
        
        
        setupWiFiListener();
        
        
        
        // location listener
        
         // Acquire a reference to the system Location Manager
        //LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
//        LocationListener locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//              // Called when a new location is found by the network location provider.
//              makeUseOfNewLocation(location);
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            public void onProviderEnabled(String provider) {
//            	
//            	makeUseOfNewProvider(provider);
//            	
//            }
//
//            public void onProviderDisabled(String provider) {
//            	
//            	makeUseOfNewProvider(provider);
//            	
//            	
//            }
//          };
//
//        // Register the listener with the Location Manager to receive location updates
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        
        
        
        
    }


	protected void setupWiFiListener() {

        // http://marakana.com/forums/android/examples/40.html
        // wifi listener
        
        // Setup WiFi
        this.wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		// Register Broadcast Receiver
		if (receiver == null)
			receiver = new WiFiScanReceiver(this);

		registerReceiver(receiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		
		registerReceiver(receiver, new IntentFilter(
				WifiManager.NETWORK_IDS_CHANGED_ACTION));
			
		Log.d("TimeTrackoid", "onCreate()");
		
	}

	protected void makeUseOfNewLocation(Location location) {
		// TODO Auto-generated method stub
		Log.i("TimeTrackoid", "TimeTrackoid.makeUseOfNewLocation() — location " + location);
	}
	
	
	protected void makeUseOfNewProvider(String provider) {
		// TODO Auto-generated method stub
		Log.i("TimeTrackoid", "makeUseOfNewProvider() — location " + provider);	
	}
	
	
	
}