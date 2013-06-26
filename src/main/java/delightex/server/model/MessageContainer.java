package delightex.server.model;

import delightex.client.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageContainer {
  private final static int MAX_SIZE = 100;

  private final List<Message> myContainer = new ArrayList<Message>();

  synchronized void addMessage(Message message) {
    while (myContainer.size() >= MAX_SIZE) {
      myContainer.remove(0);
    }

    myContainer.add(message);
  }

  public synchronized List<Message> getMessages(long timestamp) {
    List<Message> result = new ArrayList<Message>();
    for (Message message: myContainer) {
      if (message.getStamp() > timestamp) {
        result.add(message);
      }
    }
    return result;
  }
}
