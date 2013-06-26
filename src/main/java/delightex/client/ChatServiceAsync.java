package delightex.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Set;

public interface ChatServiceAsync {
  void getRooms(AsyncCallback<Set<String>> callback);
  void login(String name, AsyncCallback<Void> callback);
  void addRoom(String name, AsyncCallback<Void> callback);
}
