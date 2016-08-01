package Data;

import org.json.JSONObject;

/**
 * Created by andrea on 20/07/16.
 */
public interface TestFactory {
   Test createTest(JSONObject testData);

   TestCollection createTestCollection(JSONObject testData);
}
