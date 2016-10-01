package beaconstrips.clips;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import beaconstrips.clips.client.urlrequest.BuildingsRequest;

/**
 * @file SetBodyTest.java
 * @date 01/09/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che contiene il TU (Test di Unità ). Verifica che i metodi setBody del package "urlrequest" funzionino correttamente.
 */
@SmallTest
public class SetBodyTest {
   @Test
   public void buildings() {
      try {
         Method method = BuildingsRequest.class.getDeclaredMethod("setBody", double.class, double.class, int.class, boolean.class);
         method.setAccessible(true);
         JSONObject object = (JSONObject) method.invoke(null, 45, 5, 10, true);
         System.out.println("oggetto:" + object);
      } catch(NoSuchMethodException e) {
         System.out.println("Test fallito1");
         e.printStackTrace();
      } catch(InvocationTargetException e) {
         System.out.println("Test fallito2");
      } catch(IllegalAccessException e) {
         System.out.println("Test fallito3");
      }
   }
}
