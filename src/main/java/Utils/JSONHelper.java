package Utils;

import com.google.gson.JsonParser;
//import io.cucumber.messages.internal.com.google.gson.JsonParser;
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JSONHelper {
    public static Object[][] getJSONData(String JSON_path, String JSON_Data, int JSON_attributes) throws IOException, ParseException {
        Object object = new JSONParser().parse(new FileReader(JSON_path));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray js = (JSONArray) jsonObject.get(JSON_Data);
        Object[][] arr = new String[js.size()][JSON_attributes];
        for (int i = 0; i <js.size(); i++)
        {
            JSONObject objec1 = (JSONObject) js.get(i);
            arr[i][0] = String.valueOf(objec1.get(""));
            arr[i][1] = String.valueOf(objec1.get(""));
        }
        return arr;
    }

    public static JSONObject parse(String JSON_path) {
        InputStream is = JsonParser.class.getClassLoader().getResourceAsStream(JSON_path);
        assert is != null;
        return new JSONObject((Map) new JSONTokener(is));
    }
}
