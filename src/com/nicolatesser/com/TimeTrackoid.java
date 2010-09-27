package com.nicolatesser.com;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
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
import android.widget.TextView;

public class TimeTrackoid extends Activity {
	
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

        
        
        // define storage
        this.storageHelper = new StorageHelper(getApplicationContext());
        // define service
        this.timeTrackerService = new TimeTrackerService(storageHelper, this);
        
        
        setupWiFiListener();
        
        updateCheckInsOutsView();
        
        
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
	
	
	
	// view
	

	public void updateCheckInsOutsView() {
		Cursor cursor = this.storageHelper.getCheckInsOuts();
        if (cursor == null) {
            // There are no results
            mTextView.setText("You haven't got any Check-ins nor check-outs so far.");
        } else {
            // Display the number of results
            int count = cursor.getCount();
   
            mTextView.setText("Displaying "+count+" Check-ins/outs.");
            
            // Specify the columns we want to display in the result
            String[] from = new String[] { StorageHelper.LOCATION_NAME,StorageHelper.TIME,StorageHelper.CHECK_TYPE,StorageHelper.MATCH_TYPE };

            // Specify the corresponding layout elements where we want the columns to go
            int[] to = new int[] {R.id.name,R.id.time,R.id.check_type,R.id.match_type};

            // Create a simple cursor adapter for the definitions and apply them to the ListView

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.check_ins_outs_results, cursor, from, to);
            
            mListView.setAdapter(adapter);

        }

	}
	
	
	
}