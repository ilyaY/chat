package delightex.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import delightex.client.ChatService;

import java.util.Set;

public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {

  @Override
  public Set<String> getRooms() {
    return null;
  }
}