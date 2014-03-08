package delightex.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
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

        // Window.alert("SUPER DEV MODE WORKS");

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                messageBox.setFocus(true);
            }
        });
    }

    private void send() {
        String text = messageBox.getValue();
        if (mySocket != null) {
            mySocket.send(text);
            messageBox.setValue(null);
        }
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                messageBox.setFocus(true);
            }
        });
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
                FlowPanel p1 = new FlowPanel();
                p1.getElement().setClassName("chat-message");
                FlowPanel headerPanel = new FlowPanel();
                p1.add(headerPanel);
                FlowPanel pic = new FlowPanel();
                pic.getElement().setClassName("chat-userpic");
                headerPanel.add(pic);
                pic.add(new Image("img/userpic.gif"));
                FlowPanel sender = new FlowPanel();
                sender.getElement().setClassName("chat-message-sender");
                headerPanel.add(sender);
                sender.add(new Label(message.getUser().getName()));
                FlowPanel text = new FlowPanel();
                text.getElement().setClassName("chat-message-text");
                p1.add(text);
                Label label = new Label(message.getText());
                label.getElement().addClassName("chat-message-body");
                text.add(label);

                // messagePanel.insert(p1, 0);
                messagePanel.insert(p1, messagePanel.getWidgetCount());
            }

            @Override
            protected void callOnOpen() {
                sendButton.setEnabled(true);
            }
        };
    }


    interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatPanel> {
    }
}
