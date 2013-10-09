package com.nicolatesser.com;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class WiFiScanReceiver extends BroadcastReceiver {
	
  private static final String TAG = "WiFiScanReceiver";
  
  private TimeTrackoid wifiDemo;
  
  
  private static String TARGET_SSID = "FRITZ!Box Fon WLAN 7113";

  public WiFiScanReceiver(TimeTrackoid wifiDemo) {
    super();
    this.wifiDemo = wifiDemo;
  }

  @Override
  public void onReceive(Context c, Intent intent) {
    List<ScanResult> results = wifiDemo.getWifi().getScanResults();
    TimeTrackerService.getTimeTrackerService().listenNetworkScanResult(results);
  }
  
  
  
  
  
  
  
  
  
  
  

}