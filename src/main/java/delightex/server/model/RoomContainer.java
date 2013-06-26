package delightex.server.model;

import delightex.client.model.Message;
import delightex.client.model.Room;
import delightex.server.RoomPort;

import java.util.ArrayList;
import java.util.List;

public class RoomContainer {
  private Room myRoom;
  private MessageContainer myMessageContainer = new MessageContainer();
  private List<RoomPort> myPorts = new ArrayList<RoomPort>();

  public RoomContainer(Room room) {
    myRoom = room;
  }

  public MessageContainer getMessageContainer() {
    return myMessageContainer;
  }

  public void addPort(RoomPort p) {
    myPorts.add(p);
  }

  public void removePort(RoomPort p) {
    myPorts.remove(p);
  }

  public void onMessage(Message m) {
    myRoom.activate();
    myMessageContainer.addMessage(m);
    for (RoomPort port : myPorts) {
      if (!port.getUser().equals(m.getUser())) {
        port.send(m);
      }
    }
  }
}
