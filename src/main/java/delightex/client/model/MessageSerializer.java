package delightex.client.model;

public class MessageSerializer {
  public static String toJson(Message message) {
    org.json.JSONArray array = new org.json.JSONArray();
    array.put(message.getUser().getName());
    array.put(message.getStamp());
    array.put(message.getText());
    return array.toString();
  }
}
