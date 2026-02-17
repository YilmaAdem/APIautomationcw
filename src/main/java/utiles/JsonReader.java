package utiles;

import freemarker.core.ParseException;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    public static String getTestData(String input) throws IOException, ParseException, org.json.simple.parser.ParseException {
        // Placeholder for JSON reading logic
        String testDataValue;
        return testDataValue = (String) getJsonData().get(input); //input is the key to fetch value from JSON

    }
    public static JSONObject getJsonData() throws IOException, ParseException, org.json.simple.parser.ParseException {
        // Placeholder for JSON reading logic and helps to read the JSON file and return the JSON object from test data.
        File filename = new File("resources//testData//TestData.json");
        String json = FileUtils.readFileToString(filename, "UTF-8"); // Read the file content into this string
      // Parse the string content into a JSON object
        Object obj = new JSONParser().parse(json);
       // Cast the object to JSONObject
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject;
    }
    // Method to read JSON array from the file and return it as a JSONArray based on the key provided
    // folder is testData - language is array name
    public static JSONArray getJsonArray(String key) throws IOException, org.json.simple.parser.ParseException {
        // Placeholder for array JSON reading logic
        // Read the file content into this string from getJsonData method and data is fetched based on key
        JSONObject jsonObject= getJsonData(); // method - jsonObject is assigned with the value returned from getJsonData method
        //cast the object to JSONArray based on the key provided and return it
        JSONArray jsonArray = (JSONArray) jsonObject.get(key); // key is the name of the array in JSON file which is passed as an argument to this method
        return jsonArray;
    }

    public static <T> T getJsonArrayData(String key, int index) //safe to use with any data type as it is generic method and
        // return type is determined at runtime based on the type of data in JSON array
            throws IOException, org.json.simple.parser.ParseException {

        JSONArray array = getJsonArray(key);

        return (T) array.get(index);
    }

}
