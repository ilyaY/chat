package delightex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import delightex.client.ui.MainPanel;

public class Chat implements EntryPoint {
  public static final String ROOM_KEY = "room";
  public static final String STAMP_KEY = "stamp";

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(new MainPanel());
  }
}
