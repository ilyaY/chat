package delightex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import delightex.client.ui.LoginDialog;

public class Chat implements EntryPoint {
    public static final String ROOM_KEY = "room";
    public static final String STAMP_KEY = "stamp";

    @Override
    public void onModuleLoad() {
        new LoginDialog().center();
        Window.alert("ON LOAD");
    }
}
