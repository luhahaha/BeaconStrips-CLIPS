package beaconstrips.clips.client.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @file TestBuilder.java
 * @date 02/08/16
 * @version 1.0.0
 * @author Andrea Grendene
 *
 * classe che crea i test previsti estrendoli dal JSON inviato dal server
 */
public class TestBuilder {
   public static Test createTest(JSONObject testData) {
      try {
         String testType = testData.getString("testType");
         switch (testType) {
            case "GameCollection": { //risolvo il caso inline, visto anche che risulta in una chiamata ricorsiva del metodo
               ArrayList<Test> games = new ArrayList<>();
               JSONArray array = testData.getJSONObject("data").getJSONArray("games");
               for (int i = 0; i < array.length(); i++) {
                  createTest(array.getJSONObject(i));
               }
               return new GameCollection(testData.getJSONObject("data").getBoolean("shuffleGames"), games);
            }
            case "MultipleChoice": {
               return getMultipleChoiceTest(testData);
            }
            case "TrueFalse": {
               return getTrueFalseTest(testData);
            }
            case "BiggerShape": {
               return getBiggerShapeTest(testData);
            }
            default: {
               return null;
            }
         }
      } catch(JSONException e) {
         System.out.println("Test: errore JSON");
         return null;
      }
   }

   public static MultipleChoiceTest getMultipleChoiceTest(JSONObject testData) {
      try {
         ArrayList<MultipleChoiceTextQuiz> questions = new ArrayList<>();
         JSONArray array = testData.getJSONObject("data").getJSONArray("questions");
         for (int i = 0; i < array.length(); i++) {
            JSONObject testObject = array.getJSONObject(i);
            JSONArray answers = testObject.getJSONArray("answers");
            int correctIndex = testObject.getInt("correctIndex");
            String[] wrongAnswers = new String[3]; //i quesiti a risposta multipla hanno sempre 4 risposte possibili, di cui 3 errate
            for (int j = 0; j < correctIndex; j++)
               wrongAnswers[j] = answers.getString(j);
            for (int j = correctIndex + 1; j < 4; j++)
               wrongAnswers[j - 1] = answers.getString(j);
            String image = testObject.optString("image");
            if(image==null) {
               questions.add(new MultipleChoiceTextQuiz(/*testObject.getString("helpText")*/"HELP", testObject.getString("question"), answers.getString(correctIndex), wrongAnswers));
            } else {
               questions.add(new MultipleChoiceImageQuiz(/*testObject.getString("helpText")*/"HELP", testObject.getString("question"), answers.getString(correctIndex), wrongAnswers, image));
            }
         }
         return new MultipleChoiceTest(testData.getJSONObject("data").getBoolean("shuffleQuestions"), testData.getJSONObject("data").getBoolean("shuffleAnswers"), questions);
      } catch(JSONException e) {
         System.out.println("Test: errore JSON");
         return null;
      }
   }

   public static TrueFalseTest getTrueFalseTest(JSONObject testData) {
      try {
         ArrayList<TrueFalseTextQuiz> questions = new ArrayList<>();
         JSONArray array = testData.getJSONObject("data").getJSONArray("questions");
         for(int i=0; i<array.length(); i++) {
            JSONObject testObject = array.getJSONObject(i);
            String image = testObject.optString("image");
            if(image==null) {
               questions.add(new TrueFalseTextQuiz(/*testObject.getString("helpText")*/"HELP", testObject.getString("question"), testObject.getBoolean("correctAnswer")));
            } else {
               questions.add(new TrueFalseImageQuiz(/*testObject.getString("helpText")*/"HELP", testObject.getString("question"), testObject.getBoolean("correctAnswer"), image));
            }
         }
         return new TrueFalseTest(testData.getJSONObject("data").getBoolean("shuffleQuestions"), questions);
      } catch(JSONException e) {
         return null;
      }
   }

   public static BiggerShapeTest getBiggerShapeTest(JSONObject testData) {
      try {
         ArrayList<BiggerShape> sets = new ArrayList<>();
         JSONArray array = testData.getJSONObject("data").getJSONArray("sets");
         for(int i=0; i<array.length(); i++) {
            JSONObject testObject = array.getJSONObject(i);
            sets.add(new BiggerShape(/*testObject.getString("helptext")*/"HELP", testObject.getString("shape"), testObject.getInt("left"), testObject.getInt("right")));
         }
         return new BiggerShapeTest(testData.getJSONObject("data").getString("instructions"), testData.getJSONObject("data").getBoolean("shuffleSets"), sets);
      } catch(JSONException e) {
         return null;
      }
   }
}
