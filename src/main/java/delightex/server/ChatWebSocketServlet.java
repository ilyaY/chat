package delightex.server;

import delightex.server.model.Model;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ChatWebSocketServlet extends WebSocketServlet {
  public static final String MODEL_KEY = "model";
  @Override
  public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String protocol) {
    String room = httpServletRequest.getParameter("room");
    String lastMessage = httpServletRequest.getParameter("stamp");
    long lastStamp = lastMessage == null || lastMessage.isEmpty() ? 0 : Long.parseLong(lastMessage);
    return new WebSocket() {
      @Override
      public void onOpen(Connection connection) {
        //send messages
        //add me to room
      }

      @Override
      public void onClose(int closeCode, String message) {
        //remove me from room
      }
    };
  }

  @Override
  public void init() throws ServletException {
    Model model = new Model();
    getServletContext().setAttribute(MODEL_KEY, model);
  }
}
