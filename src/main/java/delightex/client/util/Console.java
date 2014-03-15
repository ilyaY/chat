package delightex.client.util;

/**
 * Created by Sebastian on 15.03.14.
 */
public class Console {
    public static native void log(String text)/*-{
      $wnd.console.log(text);
    }-*/;
}
