package delightex.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import java.util.Set;

@RemoteServiceRelativePath("ChatService")
public interface ChatService extends RemoteService {

  public static class App {
    private static final ChatServiceAsync ourInstance = (ChatServiceAsync) GWT.create(ChatService.class);

    public static ChatServiceAsync getInstance() {
      return ourInstance;
    }
  }

  Set<String> getRooms();
  void login(String name);
  void addRoom(String name);
}
