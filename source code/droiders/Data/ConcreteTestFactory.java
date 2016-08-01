package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrea on 01/08/16.
 */
public class ConcreteTestFactory implements AbstractTestFactory {
   public Test createTest(JSONObject testData) {
      try {
         String testType = testData.getString("testType");
         switch(testType) {
            case "MultipleChoiceText": {
               JSONArray answers = testData.getJSONArray("answers");
               int correctIndex = testData.getInt("correctIndex");
               String[] wrongAnswers = new String[3]; //i quesiti a risposta multipla hanno sempre 4 risposte possibili
               for (int i = 0; i < correctIndex; i++)
                  wrongAnswers[i] = answers.getString(i);
               for (int i = correctIndex + 1; i < 4; i++)
                  wrongAnswers[i - 1] = answers.getString(i);
               return new MultipleChoiceText(testData.getString("helpText"), testData.getString("question"), answers.getString(correctIndex), wrongAnswers);
            }
            case "MultipleChoiceImage": {
               JSONArray answers = testData.getJSONArray("answers");
               int correctIndex = testData.getInt("correctIndex");
               String[] wrongAnswers = new String[3]; //i quesiti a risposta multipla hanno sempre 4 risposte possibili
               for (int i = 0; i < correctIndex; i++)
                  wrongAnswers[i] = answers.getString(i);
               for (int i = correctIndex + 1; i < 4; i++)
                  wrongAnswers[i - 1] = answers.getString(i);
               return new MultipleChoiceImage(testData.getString("helpText"), testData.getString("instructions"), testData.getString("pathImage"), answers.getString(correctIndex), wrongAnswers);
            }
            case "TrueFalseText": {
               return new TrueFalseText(testData.getString("helpText"), testData.getString("instructions"), testData.getBoolean("correctAnswer"));
            }
            case "TrueFalseImage": {
               return new TrueFalseImage(testData.getString("helpText"), testData.getString("instructions"), testData.getString("pathImage"), testData.getBoolean("correctAnswer"));
            }
            default: {
               return null; //non c'è modo di segnalare l'errore all'activity
            }
         }
      } catch(JSONException e) {
         return null; //non c'è modo di segnalare l'errore all'activity
      }
   }

   public TestCollection createTestCollection(JSONObject testData) {
      try {
         String testType = testData.getString("testType");
         switch(testType) {
            case "GameCollection": {
               ArrayList<TestCollection> games = new ArrayList<>();
               JSONArray array = testData.getJSONObject("data").getJSONArray("games");
               for(int i=0; i<array.length(); i++) {
                  games.add(new ConcreteTestFactory().createTestCollection(array.getJSONObject(i)));
               }
               return new GameCollection(testData.getJSONObject("data").getBoolean("shuffleGames"), games);
            }
            case "MultipleChoiceTextCollection": {
               ArrayList<MultipleChoiceText> questions = new ArrayList<>();
               JSONArray array = testData.getJSONObject("data").getJSONArray("questions");
               for(int i=0; i<array.length(); i++) {
                  JSONObject testObject = array.getJSONObject(i);
                  testObject.put("testType", "MultipleChoiceText");
                  Test test = new ConcreteTestFactory().createTest(testObject);
                  if(test instanceof MultipleChoiceText) {
                     questions.add(((MultipleChoiceText)test));
                  }
               }
               return new MultipleChoiceTextCollection(testData.getJSONObject("data").getBoolean("shuffleQuestions"), testData.getJSONObject("data").getBoolean("shuffleAnswers"), questions);
            }
            case "MultipleChoiceImageCollection": {
               ArrayList<MultipleChoiceImage> questions = new ArrayList<>();
               JSONArray array = testData.getJSONObject("data").getJSONArray("questions");
               for(int i=0; i<array.length(); i++) {
                  JSONObject testObject = array.getJSONObject(i);
                  testObject.put("testType", "MultipleChoiceImage");
                  Test test = new ConcreteTestFactory().createTest(testObject);
                  if(test instanceof MultipleChoiceImage) {
                     questions.add(((MultipleChoiceImage)test));
                  }
               }
               return new MultipleChoiceImageCollection(testData.getJSONObject("data").getBoolean("shuffleQuestions"), testData.getJSONObject("data").getBoolean("shuffleAnswers"), questions);
            }
            case "TrueFalseTextCollection": {
               ArrayList<TrueFalseText> questions = new ArrayList<>();
               JSONArray array = testData.getJSONObject("data").getJSONArray("questions");
               for(int i=0; i<array.length(); i++) {
                  JSONObject testObject = array.getJSONObject(i);
                  testObject.put("testType", "TrueFalseText");
                  Test test = new ConcreteTestFactory().createTest(testObject);
                  if(test instanceof TrueFalseText) {
                     questions.add(((TrueFalseText)test));
                  }
               }
               return new TrueFalseTextCollection(testData.getJSONObject("data").getBoolean("shuffleQuestions"), questions);
            }
            case "TrueFalseImageCollection": {
               ArrayList<TrueFalseImage> questions = new ArrayList<>();
               JSONArray array = testData.getJSONObject("data").getJSONArray("questions");
               for(int i=0; i<array.length(); i++) {
                  JSONObject testObject = array.getJSONObject(i);
                  testObject.put("testType", "TrueFalseImage");
                  Test test = new ConcreteTestFactory().createTest(testObject);
                  if(test instanceof TrueFalseImage) {
                     questions.add(((TrueFalseImage)test));
                  }
               }
               return new TrueFalseImageCollection(testData.getJSONObject("data").getBoolean("shuffleQuestions"), questions);
            }
            default: {
               return null;
            }
         }
      } catch(JSONException e) {
         return null;
      }
   }
}
