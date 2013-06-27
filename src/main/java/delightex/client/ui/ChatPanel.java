package delightex.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class ChatPanel extends SimplePanel {
  private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

  @UiField
  Label helloText;


  public ChatPanel(String userName, String chatName) {
    setWidget(ourUiBinder.createAndBindUi(this));

    helloText.setText(userName + ", you are in \"" + chatName + "\" chat room");
  }



  interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatPanel> {}
}
