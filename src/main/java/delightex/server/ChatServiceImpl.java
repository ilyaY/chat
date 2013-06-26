package delightex.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import delightex.client.ChatService;
import delightex.server.model.Model;

import java.util.Set;

public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {

  @Override
  public Set<String> getRooms() {
    Model model = (Model) getServletContext().getAttribute(ChatWebSocketServlet.MODEL_KEY);
    return model.getRoomNames();
  }
}