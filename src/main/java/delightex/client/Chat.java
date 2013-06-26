package delightex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import delightex.client.rpc.ChatService;
import delightex.client.rpc.ChatServiceAsync;

import java.util.Set;

public class Chat implements EntryPoint {
  public static final String ROOM_KEY = "room";
  public static final String STAMP_KEY = "stamp";

  @Override
  public void onModuleLoad() {
    ChatServiceAsync service = ChatService.App.getInstance();
    service.getRooms(new AsyncCallback<Set<String>>() {
      @Override
      public void onFailure(Throwable caught) {

      }

      @Override
      public void onSuccess(Set<String> result) {
        Window.alert("Hello!" + result.toString());
      }
    });
  }
}
