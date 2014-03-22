package delightex.client.util;

public class Console {
    public static native void log(String text)/*-{
      $wnd.console.log(text);
    }-*/;
}
