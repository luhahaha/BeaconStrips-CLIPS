package Data;

public class PathInfo {
   public final int id;
   public final String title;
   public final String description;
   public final String target;
   public final String estimatedDuration;
   public final int position;

   public PathInfo(int id, String title, String description, String target, String estimatedDuration, int position) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.target = target;
      this.estimatedDuration = estimatedDuration;
      this.position = position;
   }
}
