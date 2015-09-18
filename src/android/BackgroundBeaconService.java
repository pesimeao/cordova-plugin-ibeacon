package com.unarin.cordova.beacon;

/**
 * Created by andre.pinto on 08/09/2015.
 */

import android.app.Application;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.*;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.List;

public class BackgroundBeaconService extends Service implements BootstrapNotifier {

    public BackgroundBeaconService() {
        super();
    }

    private BeaconManager iBeaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("CustomerTlantic", "BACKGROUND: Creating BackgroundBeaconService.");

        // Start beacons service
        iBeaconManager = BeaconManager.getInstanceForApplication(this);

        // Settings
        iBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        iBeaconManager.setBackgroundBetweenScanPeriod(1000L);
        iBeaconManager.setBackgroundScanPeriod(5000L);
        iBeaconManager.setDebug(true);

        // Simply constructing this class and holding a reference to it
        // enables auto battery saving of about 60%
        // backgroundPowerSaver = new BackgroundPowerSaver(this);

        // Create regions
        List<Region> regions = new ArrayList<Region>();

        regions.add(new Region("backgroundRegion", Identifier.parse("b9407f30-f5f8-466e-aff9-25556b57fe6d"), Identifier.parse("43549"), Identifier.parse("64185")));

        // Register regions to watch
        RegionBootstrap regionBootstrap = new RegionBootstrap(this, regions);

        Log.d("CustomerTlantic", "BACKGROUND: Created RegionBootstrap in BackgroundBeaconService.");
    }

    @Override
    public void onDestroy() {
        Log.d("CustomerTlantic", "Destroying BackgroundBeaconService");
    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d("CustomerTlantic", "BackgroundBeaconService.didEnterRegion called!");

        Intent intent = new Intent();
        intent.setAction("com.tlantic.customer.demo.NOTIFY_FOR_BEACON");
        getApplicationContext().sendOrderedBroadcast(intent, null);
    }

    @Override
    public void didExitRegion(Region region) {
        Log.d("CustomerTlantic", "BackgroundBeaconService.didExitRegion called!");
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
        Log.d("CustomerTlantic", "BackgroundBeaconService.didDetermineStateForRegion called!");
    }

    @Override
    public Context getApplicationContext() {
        return this.getApplication().getApplicationContext();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
