package delightex.client.model;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

public class MessageSerializer {
  public static String toJson(Message message) {
    JSONArray array = new JSONArray();
    array.set(0, new JSONString(message.getUser().getName()));
    array.set(1, new JSONNumber(message.getStamp()));
    array.set(2, new JSONString(message.getText()));
    return array.toString();
  }

  public static Message fromJson(String s) {
    JSONArray array = (JSONArray)JSONParser.parseStrict(s);
    String name = ((JSONString)array.get(0)).stringValue();
    long stamp = (long)((JSONNumber)array.get(1)).doubleValue();
    String text = ((JSONString)array.get(2)).stringValue();
    return new Message(new User(name), text, stamp);
  }
}
