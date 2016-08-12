package beaconstrips.clips.client.data.datamanager;

import android.content.Context;

import beaconstrips.clips.client.data.AppInfo;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.Score;

/**
 * @file DataRequestMaker.java
 * @date 16/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che funziona da interfaccia tra il datamanager e l'applicazione, escludendo LoginManager che viene usata senza passare per DataRequestMaker
 */
public class DataRequestMaker {
   public static void getBuildings(Context cx, double latitude, double longitude, int maxBuildings, AbstractDataManagerListener<Building[]> listener) {
      BuildingsDataRequest request = new BuildingsDataRequest(cx, latitude, longitude, maxBuildings, listener);
   }

   public static void getPath(Context cx, int pathID, AbstractDataManagerListener<Path> listener) {
      PathDataRequest request = new PathDataRequest(cx, pathID, listener);
   }

   public static void getRanking(Context cx, int pathID, AbstractDataManagerListener<Score[]> listener) {
      GetRankingDataRequest request = new GetRankingDataRequest(cx, pathID, listener);
   }

   public static void getResults(Context cx, AbstractDataManagerListener<PathResult[]> listener) {
      GetResultsDataRequest request = new GetResultsDataRequest(cx, listener);
   }

   public static void saveResult(Context cx, PathResult pathResult, AbstractDataManagerListener<Boolean> listener) {
      SaveResultDataRequest request = new SaveResultDataRequest(cx, pathResult, listener);
   }

   public static void getAppInfo(Context cx, AbstractDataManagerListener<AppInfo> listener) {
      AppInfoDataRequest request = new AppInfoDataRequest(cx, listener);
   }
}
