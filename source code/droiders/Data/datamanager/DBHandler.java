package data.datamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import data.Utility;
import data.Beacon;
import data.Building;
import data.Path;
import data.PathInfo;
import data.PathResult;
import data.Proof;
import data.ProofResult;
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
              " title TEXT NOT NULL , description TEXT NOT NULL , target TEXT NOT NULL , estimatedDuration TEXT NOT NULL, position INTEGER NOT NULL" +
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

   private void createPathResultTable(SQLiteDatabase db){
      String CREATE_PATHRESULT_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " PathResult (idPath INTEGER UNIQUE NOT NULL , startTime DATETIME NOT NULL ," +
              " endTime DATETIME NOT NULL, FOREIGN KEY(pathID) REFERENCES Path(id))";

      db.execSQL(CREATE_PATHRESULT_TABLE);
   }

   private void createProofResultTable(SQLiteDatabase db){
      String CREATE_PROOFRESULT_TABLE = "CREATE TABLE" +
              " PathResult (proofID INTEGER NOT NULL , pathResultID INTEGER NOT NULL , startTime DATETIME NOT NULL ," +
              " endTime DATETIME NOT NULL, FOREIGN KEY(proofID) REFERENCES Proof(id)," +
              " FOREIGN KEY(pathResultID) REFERENCES PathResult(pathID),UNIQUE(proofID, pathResultID) )";

      db.execSQL(CREATE_PROOFRESULT_TABLE);
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
      createPathResultTable(db);
      createProofResultTable(db);
   }

   @Override
   //TODO db onUpgrade
   public void onUpgrade(SQLiteDatabase db, int i, int i1) {
   }

   public void addBeacon(Beacon b){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", b.id);
      values.put("UUID", b.UUID);
      values.put("major", b.major);
      values.put("minor", b.minor);

      db.insert("Beacon", null, values);
      db.close();
   }

   public void addBuilding(Building b){
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
      Iterator<PathInfo> it = b.pathsInfos.iterator();
      while(it.hasNext()){
         addPathInfo(b.id, it.next());
      }

   }

   public void addPath(Path p){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", p.id);
      values.put("startingMessage", p.startingMessage);
      values.put("rewardMessage", p.rewardMessage);

      db.insert("Path", null, values);

      db.close();

      //aggiunge al db gli step del path
      Iterator<Step> it = p.steps.iterator();
      while(it.hasNext()){
         addStep(p.id,it.next());
      }
   }

   public void addPathInfo(int buildingID, PathInfo pi){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", pi.id);
      values.put("buildingID", buildingID);
      values.put("title", pi.title);
      values.put("description", pi.description);
      values.put("target", pi.target);
      values.put("estimatedDuration", pi.estimatedDuration);
      values.put("position", pi.position);

      db.insert("PathInfo", null, values);
      db.close();
   }

   public void addStep(int pathID, Step s){
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

   public void addProximity(int stepID, Proximity p){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", p.id);
      values.put("beaconID", p.beacon.id);
      values.put("textToDisplay", p.textToDisplay);
      values.put("percentage", p.percentage);

      db.insert("Proximity", null, values);
      db.close();
   }

   public void addProof(int stepID, Proof p){
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

   public void addPathResult(PathResult pr){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("pathID", pr.pathID);
      values.put("startTime", pr.startTime.toString());
      values.put("endTime", pr.endTime.toString());

      db.insert("PathResult", null, values);
      db.close();

      //aggiunta ProofResult relativi
      Iterator<ProofResult> it = pr.proofResults.iterator();
      while(it.hasNext()){
         addProofResult(pr.pathID, it.next());
      }
   }

   public void addProofResult(int pathID, ProofResult pr){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("proofID", pr.id);
      values.put("pathID", pathID);
      values.put("startTime", pr.startTime.toString());
      values.put("endTime", pr.endTime.toString());

      db.insert("ProofResult", null, values);
      db.close();
   }

   public Building readBuilding(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Building", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Building ret = null;

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
         List<PathInfo> pathsInfos = readPathInfos(id);
         String websiteURL = cursor.getString(14);

         ret = new Building(id,name,description,otherInfos,openingTime,address,latitude,longitude,telephone,email,whatsapp,telegram,twitter,facebook,websiteURL,pathsInfos);
      }

      return ret;
   }

   private List<PathInfo> readPathInfos(int buildingID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("PathInfo", null, "buildingID =?", new String[]{String.valueOf(buildingID)}, null, null, null, null);

      List<PathInfo> ret = null;

      while(cursor.moveToNext()){
         int id = Integer.parseInt(cursor.getString(0));
         String title = cursor.getString(2);
         String description = cursor.getString(3);
         String target = cursor.getString(4);
         String estimatedDuration = cursor.getString(5);
         int position = Integer.parseInt(cursor.getString(6));

         ret.add(new PathInfo(id,title,description,target,estimatedDuration, position));
      }
      return ret;
   }

   public PathInfo readPathInfo(int pathID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("PathInfo", null, "idPath =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      PathInfo ret = null;

      while(cursor.moveToNext()){
         String title = cursor.getString(2);
         String description = cursor.getString(3);
         String target = cursor.getString(4);
         String estimatedDuration = cursor.getString(5);
         int position = Integer.parseInt(cursor.getString(6));

         ret = new PathInfo(pathID,title,description,target,estimatedDuration,position);
      }
      return ret;
   }

   public Path readPath(int pathID) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Path", null, "id =?", new String[]{String.valueOf(pathID)}, null, null, null, null);


      Path ret = null;

      if (cursor != null) {
         cursor.moveToFirst();

         int id = Integer.parseInt(cursor.getString(0));
         String startingMessage = cursor.getString(1);
         String rewardMessage = cursor.getString(2);
         List<Step> steps = readSteps(pathID);

         ret = new Path(id, startingMessage, rewardMessage, steps);
      }

      return ret;
   }

   private List<Step> readSteps(int pathID) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Step", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      List<Step> ret = null;

      while(cursor.moveToNext())
      {
         int id = Integer.parseInt(cursor.getString(0));
         Beacon stopBeacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         List<Proximity> proximities = readProximities(id);
         Proof proof = readProof(Integer.parseInt(cursor.getString(2)));

         ret.add(new Step(stopBeacon, proximities, proof, id));
      }
      return ret;
   }

   public Beacon readBeacon(int beaconID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Beacon", null, "id =?", new String[]{String.valueOf(beaconID)}, null, null, null, null);

      Beacon ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         String UUID = cursor.getString(1);
         int major = Integer.parseInt(cursor.getString(2));
         int minor = Integer.parseInt(cursor.getString(3));

         ret = new Beacon(beaconID, UUID, major, minor);
      }
      return ret;
   }

   private List<Proximity> readProximities(int stepID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proximity", null, "stepID =?", new String[]{String.valueOf(stepID)}, null, null, null, null);

      List<Proximity> ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         int id = Integer.parseInt(cursor.getString(0));
         Beacon beacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         float percentage = Float.parseFloat(cursor.getString(3));
         String textToDisplay = cursor.getString(4);


         ret.add(new Proximity(beacon,percentage,textToDisplay,id));
      }

      return ret;
   }

   public Proof readProof(int proofID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proof", null, "id =?", new String[]{String.valueOf(proofID)}, null, null, null, null);

      Proof ret = null;

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

         ret = new Proof(title,instructions,algorithmData,id);
      }

      return ret;
   }

   public Proximity readProximity(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proximity", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Proximity ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         Beacon beacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         float percentage = Float.parseFloat(cursor.getString(3));
         String textToDisplay = cursor.getString(4);


         ret = new Proximity(beacon,percentage,textToDisplay,id);
      }

      return ret;
   }

   public Step readStep(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Step", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Step ret = null;

      while(cursor.moveToNext()) {
         Beacon stopBeacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         List<Proximity> proximities = readProximities(id);
         Proof proof = readProof(Integer.parseInt(cursor.getString(2)));

         ret = new Step(stopBeacon, proximities, proof, id);
      }
      return ret;
   }

   public PathResult readPathResult(int pathID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("PathResult", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      PathResult ret = null;

      if(cursor != null){
         cursor.moveToFirst();

         String pathName = readPathInfo(pathID).title;
         String buildingName = getBuildingName(pathID);
         GregorianCalendar startTime = Utility.stringToGregorianCalendar(cursor.getString(2), "startTime");
         GregorianCalendar endTime = Utility.stringToGregorianCalendar(cursor.getString(3), "endTime");
         List<ProofResult> proofsResults = readProofResults(pathID);
         int totalScore = Utility.calculateTotalScore(proofsResults);

         ret = new PathResult(pathID, pathName, buildingName, startTime, endTime, totalScore, proofsResults);
      }

      return ret;
   }

   private List<ProofResult> readProofResults(int pathID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("ProofResult", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      List<ProofResult> ret = null;

      while(cursor.moveToNext()){
         int id = Integer.parseInt(cursor.getString(0));
         GregorianCalendar startTime = Utility.stringToGregorianCalendar(cursor.getString(2),"startTime");
         GregorianCalendar endTime = Utility.stringToGregorianCalendar(cursor.getString(3),"endTime");
         int score = Integer.parseInt(cursor.getString(4));

         ret.add(new ProofResult(id, startTime, endTime, score));
      }

      return ret;
   }

   private String getBuildingName(int pathID){
      SQLiteDatabase db = this.getReadableDatabase();
      String query = "SELECT B.name FROM PathInfo PI JOIN Building B ON PI.buildingID = B.id WHERE PI.idPath =?";
      Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(pathID)});

      String ret = null;

      while(cursor.moveToNext())
      {
         ret = cursor.getString(0);
      }

      return ret;
   }

   public void deletePath(int id){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Path", "id =?", new String[] { String.valueOf(id) });
      db.close();

      deletePathInfo(id);
      deleteSteps(id);
      deletePathResult(id);
   }

   public void deletePathInfo(int pathID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("PathInfo", "idPath =?", new String[] { String.valueOf(pathID) });
      db.close();
   }

   public void deleteSteps(int pathID){
      List<Step> steps = readSteps(pathID);

      Iterator<Step> it = steps.iterator();
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
      List<PathInfo> pathInfos = readPathInfos(buildingID);

      Iterator<PathInfo> it = pathInfos.iterator();
      while(it.hasNext())
      {
         deletePathInfo(it.next().id);
      }
   }

   public void deletePathResult(int pathID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("PathResult", "pathID =?", new String[] { String.valueOf(pathID) });
      db.close();

      deleteProofsResults(pathID);
   }

   private void deleteProofsResults(int pathID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("ProofResult", "pathID =?", new String[] { String.valueOf(pathID) });
      db.close();
   }

   public void deleteProofResult(int proofID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("ProofResult", "proofID =?", new String[] { String.valueOf(proofID) });
      db.close();
   }

   public void updatePath(Path p){
      deletePath(p.id);
      addPath(p);
   }

   public void updatePathInfo(int buildingID, PathInfo pi)
   {
      deletePathInfo(pi.id);
      addPathInfo(buildingID, pi);
   }

   public void updateStep(int pathID, Step s){
      deleteStep(s.id);
      addStep(pathID,s);
   }

   public void updateProximity(int stepID, Proximity p){
      deleteProximity(p.id);
      addProximity(stepID, p);
   }

   public void updateBuilding(Building b){
      deleteBuilding(b.id);
      addBuilding(b);
   }

   public void updatePathResult(PathResult pr){
      deletePathResult(pr.pathID);
      addPathResult(pr);
   }

   public void updateProofResult(int pathID, ProofResult pr){
      deleteProofResult(pr.id);
      addProofResult(pathID, pr);
   }


}
