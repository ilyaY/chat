package delightex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Set;

public class Chat implements EntryPoint {
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
