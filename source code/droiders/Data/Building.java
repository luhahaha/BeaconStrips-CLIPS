public class Building {
   public final int id;
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
   public final list<PathInfo> pathsInfos;
   public final String webSiteURL;

   Building(int id, String name, String description, String otherInfos, String openingTime, String address, double latitude, double longitude, String telephone, String email, String whatsapp, String telegram, String twitter, String facebook, list<PathInfo> pathsInfos, String webSiteURL) {
      this.id=id;
      this.name=name;
      this.description=description;
      this.otherInfos=otherInfos;
      this.openingTime=opneningTime;
      this.address=address;
      this.latitude=latitude;
      this.longitude=longitude;
      this.telephone=telephone;
      this.email=email;
      this.whatsapp=whatsapp;
      this.telegram=telegram;
      this.twitter=twitter;
      this.facebook=facebook;
      this.pathsInfos=pathsInfos;
      this.webSiteURL=webSiteURL;
   }
}