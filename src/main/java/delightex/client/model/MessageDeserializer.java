package delightex.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

public class MessageDeserializer {
    public static Message fromJson(String s) {
        JSONArray array = (JSONArray) JSONParser.parseStrict(s);
        String name = ((JSONString) array.get(0)).stringValue();
        long stamp = (long) ((JSONNumber) array.get(1)).doubleValue();
        String text = ((JSONString) array.get(2)).stringValue();
        return new Message(new User(name), text, stamp);
    }
}
