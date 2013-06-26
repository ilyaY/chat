package delightex.server.model;

import delightex.client.model.Room;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model {
  private Map<String, Room> myRooms;

  public void addRoom(Room room) {
    if (myRooms == null) {
      myRooms = new HashMap<String, Room>();
    }
    myRooms.put(room.getName(), room);
  }

  public void removeRoom(String name) {
    myRooms.remove(name);
    if (myRooms.size() == 0) {
      myRooms = null;
    }
  }

  public Room getRoom(String name) {
    if (myRooms == null) return null;
    return myRooms.get(name);
  }

  public Set<String> getRoomNames() {
    if (myRooms == null) return Collections.emptySet();
    return myRooms.keySet();
  }
}

