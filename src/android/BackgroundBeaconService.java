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

public class BackgroundBeaconService extends Service implements BootstrapNotifier {

    public BackgroundBeaconService() {
        super();
    }

    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager iBeaconManager;
    private RegionBootstrap regionBootstrap;
    @Override
    public void onCreate() {
        Log.d("customer-tlantic", "BACKGROUND: Creating BackgroundBeaconService.");

        super.onCreate();
        iBeaconManager = BeaconManager.getInstanceForApplication(this);
        iBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        iBeaconManager.setBackgroundBetweenScanPeriod(1000l);
        iBeaconManager.setBackgroundScanPeriod(5000l);
        // Simply constructing this class and holding a reference to it
        // enables auto battery saving of about 60%
        //backgroundPowerSaver = new BackgroundPowerSaver(this);
        iBeaconManager.setDebug(true);


        Region region = new Region("backgroundRegion",
                Identifier.parse("b9407f30-f5f8-466e-aff9-25556b57fe6d"), Identifier.parse("32928"), Identifier.parse("441"));
        regionBootstrap = new RegionBootstrap(this, region);
        Log.d("customer-tlantic", "BACKGROUND: Created RegionBootstrap in BackgroundBeaconService.");
    }
    @Override
    public void onDestroy(){
        Log.d("customer-tlantic", "Destroying BackgroundBeaconService");
    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d("customer-tlantic", "BackgroundBeaconService.didEnterRegion called!");
        Intent intent = new Intent();
        intent.setAction("com.example.andrepinto.NOTIFY_FOR_BEACON");
        getApplicationContext().sendOrderedBroadcast(intent, null);
    }

    @Override
    public void didExitRegion(Region region) {
        Log.d("customer-tlantic", "BackgroundBeaconService.didExitRegion called!");
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
        Log.d("customer-tlantic", "BackgroundBeaconService.didDetermineStateForRegion called!");
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
