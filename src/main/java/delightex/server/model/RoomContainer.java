package delightex.server.model;

import delightex.client.model.Message;
import delightex.client.model.Room;
import delightex.client.model.User;
import delightex.server.RoomPort;

import java.util.concurrent.CopyOnWriteArrayList;

public class RoomContainer {
  private final Room myRoom;
  private final MessageContainer myMessageContainer = new MessageContainer();
  private final CopyOnWriteArrayList<RoomPort> myPorts = new CopyOnWriteArrayList<RoomPort>();

  public RoomContainer(Room room) {
    myRoom = room;
    myMessageContainer.addMessage(new Message(new User("admin"), "Hello! You are in a chat!"));
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
      port.send(m);
    }
  }

  public Room getRoom() {
    return myRoom;
  }
}
