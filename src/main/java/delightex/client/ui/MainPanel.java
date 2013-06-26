package delightex.client.ui;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import delightex.client.rpc.ChatService;
import delightex.client.rpc.ChatServiceAsync;

import java.util.Set;

public class MainPanel extends SimplePanel {
  private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

  @UiField
  VerticalPanel chatList;
  @UiField
  Button newButton;
  @UiField
  Button refreshButton;


  public MainPanel() {
    setWidget(ourUiBinder.createAndBindUi(this));
    final ChatServiceAsync service = ChatService.App.getInstance();
    refreshButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        refreshChats();
      }
    });
    newButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        new InputDialog("New Chat") {
          @Override
          public void onOk(String value) {
            service.addRoom(value, new AsyncCallback<Void>() {
              @Override
              public void onFailure(Throwable caught) {
                throw new RuntimeException(caught);
              }

              @Override
              public void onSuccess(Void result) {
                refreshChats();
              }
            });
          }
        };
      }
    });
  }

  private void refreshChats() {
    ChatService.App.getInstance().getRooms(new AsyncCallback<Set<String>>() {
      @Override
      public void onFailure(Throwable caught) {
        throw new RuntimeException(caught);
      }

      @Override
      public void onSuccess(Set<String> result) {
        chatList.clear();
        for (String chat : result) {
          chatList.add(new NavLink(chat));
        }
      }
    });

  }

  interface ChatListUiBinder extends UiBinder<HTMLPanel, MainPanel> {}
}