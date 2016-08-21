package beaconstrips.clips.client.pathprogress;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import beaconstrips.clips.client.urlrequest.ServerError;

/**
 * @file GPSManager.java
 * @date 20/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe in cui viene effettuata la richiesta di ottenere i dati della prima posizione sufficientemente precisa tramite il GPS.
 */
class GPSManager {
   GoogleApiClient.ConnectionCallbacks callback;
   GoogleApiClient.OnConnectionFailedListener failedListener;
   GoogleApiClient googleApiClient;
   LocationRequest locationRequest= LocationRequest.create();
   LocationListener locationListener;

   public GPSManager(final Context cx, final GPSListener listener) {
      callback = new GoogleApiClient.ConnectionCallbacks() {
         @Override
         public void onConnected(Bundle bundle) {
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            int status = cx.getPackageManager().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, cx.getPackageName());
            if(status == PackageManager.PERMISSION_GRANTED){
               LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
            }
         }

         @Override
         public void onConnectionSuspended(int i) {
            listener.onError(new ServerError(1006, "GPSManager: GoogleApiClient connection has been suspend", ""));
         }

         protected void startLocationUpdates() {
            int status = cx.getPackageManager().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, cx.getPackageName());
            if(status == PackageManager.PERMISSION_GRANTED) {
               LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
            }
         }
      };
      failedListener = new GoogleApiClient.OnConnectionFailedListener() {
         @Override
         public void onConnectionFailed(ConnectionResult result) {
            listener.onError(new ServerError(1006, "GPSManager: GoogleApiClient connection has failed", ""));
         }
      };
      locationListener = new LocationListener() {
         @Override
         public void onLocationChanged(Location location) {
            listener.onResponse(location.getLatitude(), location.getLongitude());
            googleApiClient.disconnect();
         }
      };
      googleApiClient = new GoogleApiClient.Builder(cx).addConnectionCallbacks(callback).addOnConnectionFailedListener(failedListener).addApi(LocationServices.API).build();
      googleApiClient.connect();
   }
}
