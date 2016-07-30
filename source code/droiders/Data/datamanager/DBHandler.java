package data.datamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Iterator;

import data.Beacon;
import data.Building;
import data.Path;
import data.PathInfo;
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
      String CREATE_PATH_TABLE = "CREATE  TABLE  IF NOT EXISTS Path (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , startingMessage TEXT, rewardMessage TEXT)";

      db.execSQL(CREATE_PATH_TABLE);
   }

   private void createPathInfoTable(SQLiteDatabase db){
      String CREATE_PATHINFO_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " PathInfo (idPath INTEGER PRIMARY KEY  NOT NULL  UNIQUE , title TEXT NOT NULL , description TEXT NOT NULL ," +
              " target TEXT NOT NULL , estimatedDuration TEXT NOT NULL, FOREIGN KEY(idPath) REFERENCES Path(id))";

      db.execSQL(CREATE_PATHINFO_TABLE);
   }

   private void createStepTable(SQLiteDatabase db){
      String CREATE_STEP_TABLE = "CREATE  TABLE  IF NOT EXISTS" +
              " Step (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , stopBeaconID INTEGER NOT NULL  UNIQUE ," +
              " pathID INTEGER NOT NULL  UNIQUE , position INTEGER NOT NULL, FOREIGN KEY(stopBeaconID) REFERENCES Beacon(id))";

      db.execSQL(CREATE_STEP_TABLE);
   }





   @Override
   public void onCreate(SQLiteDatabase db) {
      createBeaconTable(db);
      createBuildingTable(db);
      createPathTable(db);
      createPathInfoTable(db);
      createStepTable(db);
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
      values.put("websiteURL", b.webSiteURL);

      db.insert("Building", null, values);
      db.close();
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

   public void addPathInfo(PathInfo pi){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", pi.id);
      values.put("title", pi.title);
      values.put("description", pi.description);
      values.put("target", pi.target);
      values.put("estimatedDuration", pi.estimatedDuration);

      db.insert("PathInfo", null, values);
      db.close();
   }

   public void addStep(int pathID, Step s){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();

      values.put("id", s.id);
      values.put("stopBeaconID", s.id);
      values.put("pathID", pathID);

      //TODO manca il valore position
      //values.put("position", s.position);

      db.insert("Step", null, values);
      db.close();
   }



}
