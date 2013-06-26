package delightex.client;

import com.google.gwt.core.client.JavaScriptObject;

public class WebSocket {
  private JavaScriptObject myWebSocket;
  private String myUrl;


  public WebSocket(String url) {
    myUrl = url;
    myWebSocket = initWebSocket(myUrl);
  }

  private native JavaScriptObject initWebSocket(String url) /*-{
      var ws = new $wnd.WebSocket(url);

      var javaWebSocket = this;
      ws.onclose = function(event) {
          javaWebSocket.@delightex.client.WebSocket::callOnClose(Ljava/lang/String;)(event.reason);
      };

      ws.onerror = function() {
          javaWebSocket.@delightex.client.WebSocket::callOnError()();
      };

      ws.onmessage = function(event) {
          javaWebSocket.@delightex.client.WebSocket::callOnMessage(Ljava/lang/String;)(event.data);
      };

      ws.onopen = function() {
          javaWebSocket.@delightex.client.WebSocket::callOnOpen()();
      };

      return ws;
  }-*/;

  public native void send(String message) /*-{
      this.@delightex.client.WebSocket::myWebSocket.send(message);
  }-*/;

  public native void close() /*-{
      this.@delightex.client.WebSocket::myWebSocket.close();
  }-*/;

  protected void callOnClose(String reason) {
  }

  protected void callOnError() {
  }

  protected void callOnMessage(String message) {
  }

  protected void callOnOpen() {
  }
}

