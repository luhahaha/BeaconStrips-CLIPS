package beaconstrips.clips.client.data;

import java.util.ArrayList;

/**
 * @file Building.java
 * @date 19/07/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che rappresenta i dati di un edificio
 */
public class Building {
   public final String name;
   public final String description;
   public final String otherInfos;
   public final String openingTime;
   public final String address;
   public final double latitude;
   public final double longitude;
   public final String telephone;
   public final String email;
   public final String whatsapp;
   public final String telegram;
   public final String twitter;
   public final String facebook;
   public final ArrayList<PathInfo> pathsInfos;
   public final String websiteURL;

   public Building(String name, String description, String otherInfos, String openingTime, String address, double latitude, double longitude, String telephone, String email, String whatsapp, String telegram, String twitter, String facebook, String websiteURL, ArrayList<PathInfo> pathsInfos) {
      this.name = name;
      this.description = description;
      this.otherInfos = otherInfos;
      this.openingTime = openingTime;
      this.address = address;
      this.latitude = latitude;
      this.longitude = longitude;
      this.telephone = telephone;
      this.email = email;
      this.whatsapp = whatsapp;
      this.telegram = telegram;
      this.twitter = twitter;
      this.facebook = facebook;
      this.pathsInfos = pathsInfos;
      this.websiteURL = websiteURL;
   }
}
