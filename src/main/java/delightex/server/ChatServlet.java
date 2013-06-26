package delightex.server;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;

public class ChatServlet extends WebSocketServlet {
  @Override
  public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
    return null;
  }
}
