package delightex.server.model;

import delightex.client.model.Message;
import delightex.client.model.Room;
import delightex.server.RoomPort;

import java.util.ArrayList;
import java.util.List;

public class RoomContainer {
  private MessageContainer myMessageContainer;
  private Room myRoom;
  private List<RoomPort> myPorts = new ArrayList<RoomPort>();

  public MessageContainer getMessageContainer() {
    return myMessageContainer;
  }

  public void addPort(RoomPort p) {

  }

  public void removePort(RoomPort p) {}

  public void onMessage(Message m) {
    myMessageContainer.addMessage(m);
    for (RoomPort port : myPorts) {
      if (!port.getUser().equals(m.getUser())) {
        port.send(m);
      }
    }
  }
}
