package delightex.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import delightex.client.Chat;
import delightex.client.WebSocket;
import delightex.client.model.Message;

import static delightex.client.model.MessageDeserializer.fromJson;

public class ChatPanel extends SimplePanel {
  private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

  @UiField
  Label helloText;
  @UiField
  VerticalPanel messagePanel;
  @UiField
  Button sendButton;
  @UiField
  TextBox messageBox;

  private long myLastStamp = 0;
  private final String myRoom;
  private WebSocket mySocket;


  public ChatPanel(String userName, String chatName) {
    myRoom = chatName;
    setWidget(ourUiBinder.createAndBindUi(this));

    helloText.setText(userName + ", you are in \"" + chatName + "\" chat room");

    connect();

    sendButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        send();
      }
    });

    messageBox.addKeyPressHandler(new KeyPressHandler() {
      @Override
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          send();
        }
      }
    });
  }

  private void send() {
    String text = messageBox.getValue();
    if (mySocket != null) {
      mySocket.send(text);
      messageBox.setValue(null);
    }
  }

  private void connect() {
    String baseUrl = GWT.getHostPageBaseURL();

    String webSocketUrl = baseUrl.replace("http", "ws") + "wschat";

    webSocketUrl += "?" + Chat.ROOM_KEY + "=" + URL.encodeQueryString(myRoom);
    if (myLastStamp != 0) {
      webSocketUrl += "&" + Chat.STAMP_KEY + "=" + myLastStamp;
    }

    mySocket = new WebSocket(webSocketUrl) {
      @Override
      protected void callOnClose(String reason) {
        sendButton.setEnabled(false);
        mySocket = null;
        connect();
      }

      @Override
      protected void callOnError() {
        //error
      }

      @Override
      protected void callOnMessage(String s) {
        Message message = fromJson(s);
        myLastStamp = message.getStamp();
        messagePanel.insert(new Label("[" + message.getUser().getName() + "]: " + message.getText()), 0);
      }

      @Override
      protected void callOnOpen() {
        sendButton.setEnabled(true);
      }
    };
  }



  interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatPanel> {}
}
