package delightex.client.ui;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import delightex.client.rpc.ChatService;
import delightex.client.rpc.ChatServiceAsync;

import java.util.Set;

public class MainPanel extends SimplePanel {
  private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

  private Timer myTimer;

  @UiField
  VerticalPanel chatList;
  @UiField
  Button newButton;
  @UiField
  TextBox chatName;
  @UiField
  Label helloText;

  private final String myUserName;

  public MainPanel(String name) {
    myUserName = name;
    setWidget(ourUiBinder.createAndBindUi(this));
    helloText.setText("Hello, " + name + "! Choose some chat you want to enter!");
    final ChatServiceAsync service = ChatService.App.getInstance();

    newButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        final String name = chatName.getValue();
        if (name == null || name.isEmpty()) {
          Window.alert("Chat name cannot be empty");
        } else {
          service.addRoom(name, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
              Window.alert("Cannot create new chat");
            }

            @Override
            public void onSuccess(String result) {
              if (result != null) {
                Window.alert(result);
              } else {
                enterChat(name);
              }
            }
          });
        }
      }
    });

    refreshChats();
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
        for (final String chat : result) {
          NavLink link = new NavLink(chat);
          link.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              enterChat(chat);
            }
          });
          chatList.add(link);
        }
        if (result.isEmpty()) {
          chatList.add(new Label("There is no chat yet, create new one!"));
        }
      }
    });
  }

  @Override
  protected void onAttach() {
    super.onAttach();

    myTimer = new Timer() {
      @Override
      public void run() {
        refreshChats();
      }
    };

    myTimer.scheduleRepeating(2000);
  }

  @Override
  protected void onDetach() {
    super.onDetach();

    myTimer.cancel();
  }

  private void enterChat(String name) {
    removeFromParent();
    RootPanel.get().add(new ChatPanel(myUserName, name));
  }

  interface ChatListUiBinder extends UiBinder<HTMLPanel, MainPanel> {}
}