package beaconstrips.clips.client.pathprogress;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.configuration.scan.ScanMode;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.device.BeaconRegion;
import com.kontakt.sdk.android.ble.filter.ibeacon.IBeaconFilters;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathProgress;
import beaconstrips.clips.client.data.ProofResult;

/**
 * Created by Luca on 08/09/2016.
 */
public class ProximityManagerPath {
    public PathProgressControllerDelegate delegate;
    ProximityManager proximityManagerPath;
    private PathProgress pathProgress;
    private int index;

    public ProximityManagerPath(Context context, Path path){
        index=0;
        pathProgress=new PathProgress(path,new GregorianCalendar());
        KontaktSDK.initialize(context);
        proximityManagerPath = new ProximityManager(context);
        //getProof(stepIndex);
        configureProximityManager();
        configureListeners();
        configureSpaces();
        //configureFilters();
        //TODO add all algorithms for proximity
    }

    private void configureProximityManager() {
        proximityManagerPath.configuration()
                .scanMode(ScanMode.LOW_LATENCY)
                .scanPeriod(ScanPeriod.create(TimeUnit.SECONDS.toMillis(3), TimeUnit.SECONDS.toMillis(10)))
                .activityCheckConfiguration(ActivityCheckConfiguration.MINIMAL)
                .monitoringEnabled(false);
    }
    private void configureListeners() {
        proximityManagerPath.setIBeaconListener(createIBeaconListener());
    }

    private void configureSpaces() {
        IBeaconRegion region = new BeaconRegion.Builder()
                .setIdentifier("All my iBeacons")
                .setProximity(UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"))
                .build();

        proximityManagerPath.spaces().iBeaconRegion(region);
    }

    private void configureFilters() {
        proximityManagerPath.filters().iBeaconFilter(IBeaconFilters.newDeviceNameFilter(""));
    }

    protected void onDestroy() {
        proximityManagerPath.disconnect();
        proximityManagerPath = null;
    }

    public void startScanning() {
        proximityManagerPath.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManagerPath.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                //Step step = steps.get(stepIndex);
                if (ibeacon.getProximityUUID().equals(java.util.UUID.fromString(pathProgress.getPath().steps.get(index).stopBeacon.UUID))
                        && ibeacon.getMajor() == pathProgress.getPath().steps.get(index).stopBeacon.major
                        && ibeacon.getMinor() == pathProgress.getPath().steps.get(index).stopBeacon.minor) { //qui va modificato il beacon da trovare!
                    Log.i("Searching beacon", String.valueOf(pathProgress.getPath().steps.get(index).stopBeacon.major));
                    Log.i("Distance", "" + ibeacon.getProximity());
                    index++;
                    delegate.didReachProof(pathProgress.getPath().steps.get(index-1).proof);
                }
            }
        };
    }
    public void saveResult(ProofResult proofResult){
        pathProgress.addProofResult(proofResult);
    }
}

