package beaconstrips.clips.client.data.datamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.ArrayList;

import beaconstrips.clips.client.data.Beacon;
import beaconstrips.clips.client.data.Building;
import beaconstrips.clips.client.data.Path;
import beaconstrips.clips.client.data.PathInfo;
import beaconstrips.clips.client.data.PathResult;
import beaconstrips.clips.client.data.Proof;
import beaconstrips.clips.client.data.ProofResult;
import beaconstrips.clips.client.data.Proximity;
import beaconstrips.clips.client.data.Step;
import beaconstrips.clips.client.data.Utility;

/**
 * @file DBHandler.java
 * @date 25/07/16
 * @version 1.0.0
 * @author Enrico Bellio
 *
 * classe per la gestione del database locale
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
              " Path (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , startingMessage TEXT, rewardMessage TEXT)";

      db.execSQL(CREATE_PATH_TABLE);
   }

   private void createPathInfoTable(SQLiteDatabase db){
      String CREATE_PATHINFO_TABLE = "CREATE TABLE IF NOT EXISTS" +
              " PathInfo (pathID INTEGER PRIMARY KEY  NOT NULL  UNIQUE , buildingID INTEGER NOT NULL," +
              " title TEXT NOT NULL , description TEXT NOT NULL , target TEXT NOT NULL , estimatedDuration TEXT NOT NULL, position INTEGER NOT NULL," +
              " FOREIGN KEY(pathID) REFERENCES Path(id), FOREIGN KEY(buildingID) REFERENCES Building(id))";

      db.execSQL(CREATE_PATHINFO_TABLE);
   }

   private void createStepTable(SQLiteDatabase db){
      String CREATE_STEP_TABLE = "CREATE TABLE  IF NOT EXISTS" +
              " Step (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , stopBeaconID INTEGER NOT NULL ," +
              " proofID INTEGER NOT NULL , pathID INTEGER NOT NULL  UNIQUE , position INTEGER NOT NULL," +
              "  FOREIGN KEY(stopBeaconID) REFERENCES Beacon(id), FOREIGN KEY(pathID) REFERENCES Path(id)," +
              " FOREIGN KEY(proofID) REFERENCES Proof(id))";

      db.execSQL(CREATE_STEP_TABLE);
   }

   private void createProofTable(SQLiteDatabase db){
      //TODO controllo campi tabella
      String CREATE_PROOF_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " Proof (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
              " title VARCHAR NOT NULL , instructions TEXT NOT NULL , scoringAlgorithmData TEXT NOT NULL , " +
              " testType INTEGER NOT NULL , testData TEXT NOT NULL , testTitle VARCHAR NOT NULL ," +
              " testInstructions TEXT NOT NULL)";

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
              " PathResult (pathID INTEGER UNIQUE NOT NULL , startTime DATETIME NOT NULL ," +
              " endTime DATETIME NOT NULL, FOREIGN KEY(pathID) REFERENCES Path(id))";

      db.execSQL(CREATE_PATHRESULT_TABLE);
   }

   private void createProofResultTable(SQLiteDatabase db){
      String CREATE_PROOFRESULT_TABLE = "CREATE TABLE IF NOT EXISTS" +
              " ProofResult (proofID INTEGER NOT NULL , pathResultID INTEGER NOT NULL , startTime DATETIME NOT NULL ," +
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

   public void writeBeacons(Beacon[] beacons){
      for(int i=0; i<beacons.length; ++i){
         writeBeacon(beacons[i]);
      }
   }

   private void writeBeacon(Beacon b){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", b.id);
      values.put("UUID", b.UUID);
      values.put("major", b.major);
      values.put("minor", b.minor);

      db.insert("Beacon", null, values);
      db.close();
   }

   public void writeBuildings(Building[] buildings){
      for(int i=0; i<buildings.length; ++i){
         writeBuilding(i, buildings[i]);
      }
   }

   private void writeBuilding(int id, Building b){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", id);
      values.put("name", b.name);
      values.put("description", b.description);
      values.put("otherInfos", b.otherInfos);
      values.put("openingTime", b.openingTime);
      values.put("address", b.address);
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

      //write PathInfos dell'edificio
      writePathInfos(id, b.pathsInfos);
   }

   public void writePaths(Path[] paths){
      for(int i=0; i<paths.length; ++i){
         writePath(paths[i]);
      }
   }

   private void writePath(Path p){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", p.id);
      values.put("startingMessage", p.startingMessage);
      values.put("rewardMessage", p.rewardMessage);

      db.insert("Path", null, values);

      db.close();

      //aggiunge al db gli step del path
      writeSteps(p.id,p.steps);
   }

   public void writePathInfos(int buildingID, ArrayList<PathInfo> pathInfos){
      for(int i=0; i<pathInfos.size(); ++i){
         writePathInfo(buildingID, pathInfos.get(i));
      }
   }

   private void writePathInfo(int buildingID, PathInfo pi){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("pathID", pi.id);
      values.put("buildingID", buildingID);
      values.put("title", pi.title);
      values.put("description", pi.description);
      values.put("target", pi.target);
      values.put("estimatedDuration", pi.estimatedDuration);
      values.put("position", pi.position);

      db.insert("PathInfo", null, values);
      db.close();
   }

   public void writeSteps(int pathID,ArrayList<Step> steps){
      for(int i=0; i<steps.size(); ++i){
         writeStep(pathID, i, steps.get(i));
      }
   }

   public void writeStep(int pathID, int stepID, Step s){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", stepID);
      values.put("stopBeacon", s.stopBeacon.id);
      values.put("pathID", pathID);
      values.put("proof", s.proof.id);

      db.insert("Step", null, values);
      db.close();

      //write Proximities
      writeProximities(stepID, s.proximities);

      //write Proof
      writeProof(stepID, s.proof);
   }

   public void writeProximities(int stepID, ArrayList<Proximity> proximities){
      for(int i=0; i<proximities.size(); ++i){
         writeProximity(stepID, i, proximities.get(i));
      }
   }

   private void writeProximity(int stepID, int proximityID, Proximity p){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", proximityID);
      values.put("beaconID", p.beacon.id);
      values.put("textToDisplay", p.textToDisplay);
      values.put("percentage", p.percentage);

      db.insert("Proximity", null, values);
      db.close();
   }

   public void writeProof(int stepID, Proof p){
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

   public void writePathResults(PathResult[] pathResults){
      for(int i=0; i<pathResults.length; ++i){
         writePathResult(pathResults[i]);
      }
   }

   private void writePathResult(PathResult pr){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("pathID", pr.pathID);
      values.put("startTime", pr.startTime.toString());
      values.put("endTime", pr.endTime.toString());

      db.insert("PathResult", null, values);
      db.close();

      //aggiunta ProofResult relativi
      writeProofResults(pr.pathID, pr.proofResults);
   }

   public void writeProofResults(int pathID, ArrayList<ProofResult> proofResults){
      for(int i=0; i<proofResults.size(); ++i){
         writeProofResult(pathID, proofResults.get(i));
      }
   }

   private void writeProofResult(int pathID, ProofResult pr){
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

      if (cursor.getCount() > 0) {
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
         ArrayList<PathInfo> pathsInfos = readPathInfos(id);
         String websiteURL = cursor.getString(14);

         ret = new Building(name,description,otherInfos,openingTime,address,latitude,longitude,telephone,email,whatsapp,telegram,twitter,facebook,websiteURL,pathsInfos);
      }

      return ret;
   }

   public ArrayList<Building> readBuildings(){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Building", null, null, null, null, null, null, null); //Ritorna tutti gli edifici salvati nel DB

      ArrayList<Building> ret = null;

      while(cursor.moveToNext()){
         int id = Integer.parseInt(cursor.getString(0));
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
         ArrayList<PathInfo> pathsInfos = readPathInfos(id);
         String websiteURL = cursor.getString(14);

         ret.add(new Building(name,description,otherInfos,openingTime,address,latitude,longitude,telephone,email,whatsapp,telegram,twitter,facebook,websiteURL,pathsInfos));
      }

      return ret;
   }

   private ArrayList<PathInfo> readPathInfos(int buildingID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("PathInfo", null, "buildingID =?", new String[]{String.valueOf(buildingID)}, null, null, null, null);

      ArrayList<PathInfo> ret = null;

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
      Cursor cursor = db.query("PathInfo", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

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

      if (cursor.getCount() > 0) {
         cursor.moveToFirst();

         int id = Integer.parseInt(cursor.getString(0));
         String startingMessage = cursor.getString(1);
         String rewardMessage = cursor.getString(2);
         ArrayList<Step> steps = readSteps(pathID);

         ret = new Path(id, startingMessage, rewardMessage, steps);
      }

      return ret;
   }

   private ArrayList<Step> readSteps(int pathID) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Step", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      ArrayList<Step> ret = null;

      while(cursor.moveToNext())
      {
         int id = Integer.parseInt(cursor.getString(0));
         Beacon stopBeacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         ArrayList<Proximity> proximities = readProximities(id);
         Proof proof = readProof(Integer.parseInt(cursor.getString(2)));

         ret.add(new Step(stopBeacon, proximities, proof));
      }
      return ret;
   }

   public Beacon readBeacon(int beaconID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Beacon", null, "id =?", new String[]{String.valueOf(beaconID)}, null, null, null, null);

      Beacon ret = null;

      if(cursor.getCount() > 0){
         cursor.moveToFirst();

         String UUID = cursor.getString(1);
         int major = Integer.parseInt(cursor.getString(2));
         int minor = Integer.parseInt(cursor.getString(3));

         ret = new Beacon(beaconID, UUID, major, minor);
      }

      return ret;
   }

   private ArrayList<Proximity> readProximities(int stepID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proximity", null, "stepID =?", new String[]{String.valueOf(stepID)}, null, null, null, null);

      ArrayList<Proximity> ret = null;

      while(cursor.moveToNext()){

         Beacon beacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         float percentage = Float.parseFloat(cursor.getString(3));
         String textToDisplay = cursor.getString(4);


         ret.add(new Proximity(beacon,percentage,textToDisplay));
      }

      return ret;
   }

   public Proof readProof(int proofID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proof", null, "id =?", new String[]{String.valueOf(proofID)}, null, null, null, null);

      Proof ret = null;

      if (cursor.getCount() > 0) {
         cursor.moveToFirst();

         int id = Integer.parseInt(cursor.getString(0));
         String title = cursor.getString(1);
         String instructions = cursor.getString(2);
         JSONObject algorithmData = null;
         try {
            algorithmData = new JSONObject(cursor.getString(3));
         }
         catch(Throwable t){
            //TODO catch conversione string to JSONObject - algorithmData
         }
         JSONObject testData = null;
         try {
            testData = new JSONObject(cursor.getString(4));
         }
         catch(Throwable t){
            //TODO catch conversione string to JSONObject - testData
         }

         ret = new Proof(id, title, instructions, algorithmData, testData);
      }

      return ret;
   }

   public Proximity readProximity(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Proximity", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Proximity ret = null;

      if(cursor.getCount() > 0){
         cursor.moveToFirst();

         Beacon beacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         float percentage = Float.parseFloat(cursor.getString(3));
         String textToDisplay = cursor.getString(4);


         ret = new Proximity(beacon,percentage,textToDisplay);
      }

      return ret;
   }

   public Step readStep(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("Step", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);

      Step ret = null;

      while(cursor.moveToNext()) {
         Beacon stopBeacon = readBeacon(Integer.parseInt(cursor.getString(1)));
         ArrayList<Proximity> proximities = readProximities(id);
         Proof proof = readProof(Integer.parseInt(cursor.getString(2)));

         ret = new Step(stopBeacon, proximities, proof);
      }
      return ret;
   }

   public PathResult[] readPathResults(){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("PathResult", null, null, null, null, null, null, null);

      ArrayList<PathResult> pathResults = null;

      while(cursor.moveToNext()){
         int id = Integer.parseInt(cursor.getString(0));
         String pathName = readPathInfo(id).title;
         String buildingName = getBuildingName(id);
         GregorianCalendar startTime = Utility.stringToGregorianCalendar(cursor.getString(2), "startTime");
         GregorianCalendar endTime = Utility.stringToGregorianCalendar(cursor.getString(3), "endTime");
         ArrayList<ProofResult> proofsResults = readProofResults(id);
         int totalScore = Utility.calculateTotalScore(proofsResults);

         pathResults.add(new PathResult(id, pathName, buildingName, startTime, endTime, totalScore, proofsResults));
      }

      PathResult[] ret = new PathResult[pathResults.size()];

      return ret;
   }

   public PathResult readPathResult(int pathID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("PathResult", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      PathResult ret = null;

      if(cursor.getCount() > 0){
         cursor.moveToFirst();

         String pathName = readPathInfo(pathID).title;
         String buildingName = getBuildingName(pathID);
         GregorianCalendar startTime = Utility.stringToGregorianCalendar(cursor.getString(2), "startTime");
         GregorianCalendar endTime = Utility.stringToGregorianCalendar(cursor.getString(3), "endTime");
         ArrayList<ProofResult> proofsResults = readProofResults(pathID);
         int totalScore = Utility.calculateTotalScore(proofsResults);

         ret = new PathResult(pathID, pathName, buildingName, startTime, endTime, totalScore, proofsResults);
      }

      return ret;
   }

   private ArrayList<ProofResult> readProofResults(int pathID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.query("ProofResult", null, "pathID =?", new String[]{String.valueOf(pathID)}, null, null, null, null);

      ArrayList<ProofResult> ret = null;

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
      String query = "SELECT B.name FROM PathInfo PI JOIN Building B ON PI.buildingID = B.id WHERE PI.pathID =?";
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

      deleteSteps(id);
      deletePathResult(id);
   }


   public void deletePathInfo(int pathID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("PathInfo", "pathID =?", new String[] { String.valueOf(pathID) });
      db.close();
   }

   public void deleteAllSteps(){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Step", null, null);
      db.close();
   }


   public void deleteSteps(int pathID){
      ArrayList<Step> steps = readSteps(pathID);

      Iterator<Step> it = steps.iterator();
      while(it.hasNext())
      {
         deleteStep(getStepID(it.next()));
      }
   }

   private int getStepID(Step s){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery("SELECT id FROM Step WHERE stopBeacon=? AND proof=?", new String[]{String.valueOf(s.stopBeacon.id), String.valueOf(s.proof.id)});

      int ret = -1;

      while(cursor.moveToNext()) {
         ret = Integer.parseInt(cursor.getString(0));
      }
      return ret;
   }

   public void deleteStep(int id){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Step", "id =?", new String[] { String.valueOf(id) });
      db.close();

      deleteProximities(id);
   }

   public void deleteAllProximities(){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Proximity", null, null);
      db.close();
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

   public void deleteAllBuildings(){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("Building", null, null);
      db.close();

      deleteAllPathInfos();
   }

   private void deletePathInfos(int buildingID){
      ArrayList<PathInfo> pathInfos = readPathInfos(buildingID);

      Iterator<PathInfo> it = pathInfos.iterator();
      while(it.hasNext())
      {
         deletePathInfo(it.next().id);
      }
   }

   public void deleteAllPathInfos(){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("PathInfo", null, null);
      db.close();
   }

   public void deletePathResult(int pathID){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("PathResult", "pathID =?", new String[] { String.valueOf(pathID) });
      db.close();

      deleteProofsResults(pathID);
   }

   public void deletePathResults(){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("PathResult", null, null);
      db.close();

      deleteAllProofsResults();
   }

   public void deleteAllProofsResults(){
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete("ProofResult", null, null);
      db.close();
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
      writePath(p);
   }

   public void updatePathInfo(int buildingID, PathInfo pi)
   {
      deletePathInfo(pi.id);
      writePathInfo(buildingID, pi);
   }

   public void updateStep(int pathID, Step s){
      int stepID = getStepID(s);
      deleteStep(stepID);
      writeStep(pathID, stepID, s);
   }

   /*
   public void updateProximity(int stepID, Proximity p){
      deleteProximity(p.id);
      writeProximity(stepID, p);
   }


   public void updateBuilding(Building b){
      deleteBuilding(b.id);
      writeBuilding(b);
   }
   */

   public void updatePathResult(PathResult pr){
      deletePathResult(pr.pathID);
      writePathResult(pr);
   }


   public void updateBuildings(Building[] buildings){
      deleteAllBuildings();
      writeBuildings(buildings);
   }

   public void updatePathResults(PathResult[] pr){
      deletePathResults();
      writePathResults(pr);
   }

   public void updateProofResult(int pathID, ProofResult pr){
      deleteProofResult(pr.id);
      writeProofResult(pathID, pr);
   }

   public Building[] getNearestBuildings(int buildingsNumber, boolean searchByDistance, double latitude, double longitude){
      SQLiteDatabase db = this.getReadableDatabase();

      ArrayList<Building> buildings = readBuildings();
      Iterator<Building> it = buildings.iterator();
      Building[] ret =  new Building[buildingsNumber];

      while(it.hasNext()){
         ret = Utility.addNearestBuilding(it.next(), ret, latitude, longitude);
      }

      return ret;
   }

}
