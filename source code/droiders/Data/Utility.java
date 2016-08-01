package data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Enrico on 01/08/2016.
 */
public class Utility {

   public static GregorianCalendar stringToGregorianCalendar(String data, String field){
      JSONObject tmp = new JSONObject();
      try{
         tmp = new JSONObject(data);
      }
      catch(JSONException e){

      }

      GregorianCalendar ret = new GregorianCalendar();
      try {
         ret.setTime(new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", Locale.ITALIAN).parse(tmp.getString(field)));
      }
      catch(Exception e){

      }

      return ret;
   }

   public static int calculateTotalScore(List<ProofResult> proofsResults){
      Iterator<ProofResult> it = proofsResults.iterator();
      int ret = 0;

      while(it.hasNext()){
         ret += it.next().score;
      }

      return ret;
   }
}
