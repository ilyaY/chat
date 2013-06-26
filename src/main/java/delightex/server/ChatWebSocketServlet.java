package delightex.server;

import delightex.server.model.Model;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ChatWebSocketServlet extends WebSocketServlet {
  public static final String MODEL_KEY = "model";
  @Override
  public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
    return null;
  }

  @Override
  public void init() throws ServletException {
    Model model = new Model();
    getServletContext().setAttribute(MODEL_KEY, model);
  }
}
