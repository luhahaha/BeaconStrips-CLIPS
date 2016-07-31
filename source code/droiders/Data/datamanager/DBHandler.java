package Data.datamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import data.Beacon;
import data.Building;
import data.Path;
import data.PathInfo;
import data.Proof;
import data.Proximity;
import data.Step;

/**
 * Created by Enrico on 25/07/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

   private static final String DB_NAME = "clipsDB";
   private static final int DB_VERSION = 1;

   public DBHandler(Context cx){
      super(cx,DB_NAME,null,DB_VERSION);
   }

   private void createBeaconTable(SQLiteDatabase db){
      String CREATE_BEACON_TABLE = "CREATE  TABLE  IF NOT EXISTS " +
              "Beacon (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , UUID VARCHAR NOT NULL  UNIQUE , major INTEGER NOT NULL , minor INTEGER NOT NULL )";

      db.execSQL(CREATE_BEACON_TABLE);
   }

   private void createBuildingTable(SQLiteDatabase db){
      String CREATE_BUILDING_TABLE = "CREATE  TABLE  IF NOT EXISTS " +
              "Building (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , name TEXT NOT NULL , description TEXT, otherInfos TEXT," +
              " openingTime TEXT, address TEXT, latitude DOUBLE NOT NULL , longitude DOUBLE NOT NULL , telephone TEXT, email TEXT, whatsapp TEXT," +
              " telegram TEXT, twitter TEXT, facebook TEXT, websiteURL TEXT UNIQUE )";

      db.execSQL(CREATE_BUILDING_TABLE);
   }

   private void createPathTable(SQLiteDatabase db){
      String CREATE_PATH_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " Path (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , startingMessage TEXT, rewardMessage TEXT)";

      db.execSQL(CREATE_PATH_TABLE);
   }

   private void createPathInfoTable(SQLiteDatabase db){
      String CREATE_PATHINFO_TABLE = "CREATE TABLE" +
              " PathInfo (idPath INTEGER PRIMARY KEY  NOT NULL  UNIQUE , buildingID INTEGER NOT NULL," +
              " title TEXT NOT NULL , description TEXT NOT NULL , target TEXT NOT NULL , estimatedDuration TEXT NOT NULL," +
              " FOREIGN KEY(idPath) REFERENCES Path(id), FOREIGN KEY(buildingID) REFERENCES Building(id))";

      db.execSQL(CREATE_PATHINFO_TABLE);
   }

   private void createStepTable(SQLiteDatabase db){
      String CREATE_STEP_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " Step (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , stopBeaconID INTEGER NOT NULL  UNIQUE , proofID INTEGER NOT NULL ," +
              " pathID INTEGER NOT NULL  UNIQUE , position INTEGER NOT NULL, " +
              " FOREIGN KEY(stopBeaconID) REFERENCES Beacon(id), FOREIGN KEY(pathID) REFERENCES Path(id), FOREIGN KEY(proofID) REFERENCES Proof(id)";

      db.execSQL(CREATE_STEP_TABLE);
   }

   private void createProofTable(SQLiteDatabase db){
      String CREATE_PROOF_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " Proof (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
              " title VARCHAR NOT NULL , instructions TEXT NOT NULL , scoringAlgorithmData TEXT NOT NULL ," +
              " testType INTEGER NOT NULL , testData TEXT NOT NULL , testTitle VARCHAR NOT NULL ," +
              " testInstructions TEXT NOT NULL, FOREIGN KEY(stepID) REFERENCES Step(id) )";

      db.execSQL(CREATE_PROOF_TABLE);
   }

   private void createProximityTable(SQLiteDatabase db){
      String CREATE_PROXIMITY_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " Proximity (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , beaconID INTEGER NOT NULL ," +
              " stepID INTEGER NOT NULL , percentage FLOAT NOT NULL , testToDisplay VARCHAR NOT NULL," +
              " FOREIGN KEY(beaconID) REFERENCES Beacon(id), FOREIGN KEY(stepID) REFERENCES Step(id) )";

      db.execSQL(CREATE_PROXIMITY_TABLE);
   }




   @Override
   public void onCreate(SQLiteDatabase db) {
      createBeaconTable(db);
      createBuildingTable(db);
      createProofTable(db);
      createProximityTable(db);
      createPathTable(db);
      createPathInfoTable(db);
      createStepTable(db);
   }

   @Override
   //TODO db onUpgrade
   public void onUpgrade(SQLiteDatabase db, int i, int i1) {
   }

   public void addBeacon(Data.Beacon b){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", b.id);
      values.put("UUID", b.UUID);
      values.put("major", b.major);
      values.put("minor", b.minor);

      db.insert("Beacon", null, values);
      db.close();
   }

   public void addBuilding(Data.Building b){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", b.id);
      values.put("name", b.name);
      values.put("description", b.description);
      values.put("otherInfos", b.otherInfos);
      values.put("openingTime", b.openingTime);
      values.put("address", b.address;
      values.put("latitude", b.latitude);
      values.put("longitude", b.longitude);
      values.put("telephone", b.telephone);
      values.put("email", b.email);
      values.put("whatsapp", b.whatsapp);
      values.put("telegram", b.telegram);
      values.put("twitter", b.twitter);
      values.put("facebook", b.facebook);
      values.put("websiteURL", b.websiteURL);

      db.insert("Building", null, values);
      db.close();

      //add PathInfos dell'edificio
      Iterator<Data.PathInfo> it = b.pathsInfos.iterator();
      while(it.hasNext()){
         addPathInfo(b.id, it.next());
      }

   }

   public void addPath(Data.Path p){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", p.id);
      values.put("startingMessage", p.startingMessage);
      values.put("rewardMessage", p.rewardMessage);

      db.insert("Path", null, values);

      db.close();

      //aggiunge al db gli step del path
      Iterator<Data.Step> it = p.steps.iterator();
      while(it.hasNext()){
         addStep(p.id,it.next());
      }
   }

   public void addPathInfo(int buildingID, Data.PathInfo pi){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", pi.id);
      values.put("buildingID", buildingID);
      values.put("title", pi.title);
      values.put("description", pi.description);
      values.put("target", pi.target);
      values.put("estimatedDuration", pi.estimatedDuration);

      db.insert("PathInfo", null, values);
      db.close();
   }

   public void addStep(int pathID, Data.Step s){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", s.id);
      values.put("stopBeacon", s.id);
      values.put("pathID", pathID);
      values.put("proof", s.proof.id);

      db.insert("Step", null, values);
      db.close();

      //add Proximities
      Iterator<Proximity> it = s.proximities.iterator();
      while(it.hasNext()){
         addProximity(s.id, it.next());
      }

      //add Proof
      addProof(s.id,s.proof);
   }

   public void addProximity(int stepID, Data.Proximity p){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", p.id);
      values.put("beaconID", p.beacon.id);
      values.put("textToDisplay", p.textToDisplay);
      values.put("percentage", p.percentage);

      db.insert("Proximity", null, values);
      db.close();
   }

   public void addProof(int stepID, Data.Proof p){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", p.id);
      values.put("stepID", stepID);
      values.put("title", p. title);
      values.put("instructions", p.instructions);
      values.put("algorithmData", p.scoringAlgorithm.toString());

      db.insert("Proof", null, values);
      db.close();
   }

   public Data.Building readBuilding(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Building", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Data.Building ret = null;

      if (cursor != null) {
         cursor.moveToFirst();

         String name = cursor.getString(1);
         String description = cursor.getString(2);
         String otherInfos = cursor.getString(3);
         String openingTime = cursor.getString(4);
         String address = cursor.getString(5);
         double latitude = Double.parseDouble(cursor.getString(6));
         double longitude = Double.parseDouble(cursor.getString(7));
         String telephone = cursor.getString(8);
         String email = cursor.getString(9);
         String whatsapp = cursor.getString(10);
         String telegram = cursor.getString(11);
         String twitter = cursor.getString(12);
         String facebook = cursor.getString(13);
         List<Data.PathInfo> pathsInfos = readPathInfos(id);
         String websiteURL = cursor.getString(14);

         ret = new Data.Building(id,name,description,otherInfos,openingTime,address,latitude,longitude,telephone,email,whatsapp,telegram,twitter,facebook,websiteURL,pathsInfos);
      }

      return ret;
   }

   private List<Data.PathInfo> readPathInfos(int buildingID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("PathInfo", null, "buildingID =?", new String[]{String.valueOf(buildingID)}, null, null, null, null);

      List<Data.PathInfo> ret = null;

      while(cursor.moveToNext()){
         int id = Integer.parseInt(cursor.getString(0));
         String title = cursor.getString(2);
         String description = cursor.getString(3);
         String target = cursor.getString(4);
         String estimatedDuration = cursor.getString(5);

         ret.add(new Data.PathInfo(id,title,description,target,estimatedDuration));
      }
      return ret;
   }

   public Data.Path readPath(int pathID) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Path", null, "id =?", new String[]{String.valueOf(pathID)}, null, null, null, null);


      Data.Path ret = null;

      if (cursor != null) {
         cursor.moveToFirst();

         int id = Integer.parseInt(cursor.getString(0));
         String startingMessage = cursor.getString(1);
         String rewardMessage = cursor.getString(2);
         List<Data.Step> steps = readSteps(pathID);

         ret = new Data.Path(id, startingMessage, rewardMessage, steps);
      }

      return ret;
   }

   private List<Data.Step> readSteps(int pathID) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Step", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      List<Data.Step> ret = null;

      while(cursor.moveToNext())
      {
         int id = Integer.parseInt(cursor.getString(0));
         Data.Beacon stopBeacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         List<Data.Proximity> proximities = readProximities(id);
         Data.Proof proof = readProof(Integer.parseInt(cursor.getString(2)));

         ret.add(new Data.Step(stopBeacon, proximities, proof, id));
      }
      return ret;
   }

   public Data.Beacon readBeacon(int beaconID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Beacon", null, "id =?", new String[]{String.valueOf(beaconID)}, null, null, null, null);

      Data.Beacon ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         String UUID = cursor.getString(1);
         int major = Integer.parseInt(cursor.getString(2));
         int minor = Integer.parseInt(cursor.getString(3));

         ret = new Data.Beacon(beaconID, UUID, major, minor);
      }
      return ret;
   }

   private List<Data.Proximity> readProximities(int stepID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proximity", null, "stepID =?", new String[]{String.valueOf(stepID)}, null, null, null, null);

      List<Data.Proximity> ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         int id = Integer.parseInt(cursor.getString(0));
         Data.Beacon beacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         float percentage = Float.parseFloat(cursor.getString(3));
         String textToDisplay = cursor.getString(4);


         ret.add(new Data.Proximity(beacon,percentage,textToDisplay,id));
      }

      return ret;
   }

   public Data.Proof readProof(int proofID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proof", null, "id =?", new String[]{String.valueOf(proofID)}, null, null, null, null);

      Data.Proof ret = null;

      if (cursor != null) {
         cursor.moveToFirst();

         int id = Integer.parseInt(cursor.getString(0));
         String title = cursor.getString(1);
         String instructions = cursor.getString(2);
         JSONObject algorithmData = null;
         try {
            algorithmData = new JSONObject(cursor.getString(3));
         }
         catch(Throwable t){
            //TODO catch conversione string to JSONObject
         }

         ret = new Data.Proof(title,instructions,algorithmData,id);
      }

      return ret;
   }

   public Data.Proximity readProximity(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proximity", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Data.Proximity ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         Data.Beacon beacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         float percentage = Float.parseFloat(cursor.getString(3));
         String textToDisplay = cursor.getString(4);


         ret = new Data.Proximity(beacon,percentage,textToDisplay,id);
      }

      return ret;
   }

   public Data.Step readStep(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Step", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Data.Step ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         while(cursor != null) {

            Data.Beacon stopBeacon = readBeacon(Integer.parseInt(cursor.getString(1)));
            List<Data.Proximity> proximities = readProximities(id);
            Data.Proof proof = readProof(Integer.parseInt(cursor.getString(2)));

            ret = new Data.Step(stopBeacon, proximities, proof, id);
         }
      }
      return ret;
   }

   public void deletePath(int id){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Path", "id =?", new String[] { String.valueOf(id) });
      db.close();

      deletePathInfo(id);
      deleteSteps(id);
   }

   public void deletePathInfo(int pathID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("PathInfo", "idPath =?", new String[] { String.valueOf(pathID) });
      db.close();
   }

   public void deleteSteps(int pathID){
      List<Data.Step> steps = readSteps(pathID);

      Iterator<Data.Step> it = steps.iterator();
      while(it.hasNext())
      {
         deleteStep(it.next().id);
      }
   }

   public void deleteStep(int id){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Step", "id =?", new String[] { String.valueOf(id) });
      db.close();

      deleteProximities(id);
   }

   public void deleteProximities(int stepID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Proximity", "stepID =?", new String[] { String.valueOf(stepID) });
      db.close();
   }

   public void deleteProximity(int id){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Proximity", "id =?", new String[] { String.valueOf(id) });
      db.close();
   }

   public void deleteProof(int id){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Proof", "id =?", new String[] { String.valueOf(id) });
      db.close();
   }

   public void deleteBuilding(int id){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Building", "id =?", new String[] { String.valueOf(id) });
      db.close();

      deletePathInfos(id);
   }

   private void deletePathInfos(int buildingID){
      List<Data.PathInfo> pathInfos = readPathInfos(buildingID);

      Iterator<Data.PathInfo> it = pathInfos.iterator();
      while(it.hasNext())
      {
         deletePathInfo(it.next().id);
      }
   }

   public void updatePath(Data.Path p){
      deletePath(p.id);
      addPath(p);
   }

   public void updatePathInfo(int buildingID, Data.PathInfo pi)
   {
      deletePathInfo(pi.id);
      addPathInfo(buildingID, pi);
   }

   public void updateStep(int pathID, Data.Step s){
      deleteStep(s.id);
      addStep(pathID,s);
   }

   public void updateProximity(int stepID, Data.Proximity p){
      deleteProximity(p.id);
      addProximity(stepID, p);
   }

   public void updateBuilding(Data.Building b){
      deleteBuilding(b.id);
      addBuilding(b);
   }


}
