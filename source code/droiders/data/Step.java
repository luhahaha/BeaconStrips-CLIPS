package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Step {
   public final Beacon stopBeacon;
   public final List<Proximity> proximities;
   public final Proof proof;

   public Step(Beacon stopBeacon, List<Proximity> proximities, JSONObject proofData) {
      this.stopBeacon = stopBeacon;
      this.proximities = proximities;

      Proof temp; //questa variabile serve solo perché altrimenti Android Studio rileva un errore se cerco di inizializzare la variabile statica proof sia nel blocco try che in quello dell'eccezione
      try {
         temp = new Proof(proofData.getString("title"), proofData.getString("instructions"), proofData.getJSONObject("algorithmData"), proofData.getJSONArray("testData"));
      } catch(JSONException e) {
         temp = new Proof("", "", new JSONObject(), new JSONArray()); //non ho listener o qualche altro modo per poter comunicare alle activity l'errore
      }
      this.proof = temp;
   }
}
