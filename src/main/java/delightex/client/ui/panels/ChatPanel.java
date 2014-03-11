package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import delightex.client.ChatAppController;
import delightex.client.WebSocket;
import delightex.client.model.Message;

import static delightex.client.model.MessageDeserializer.fromJson;

public class ChatPanel extends Composite {
    interface ChatPanelUiBinder extends UiBinder<DockLayoutPanel, ChatPanel> {
    }

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    @UiField
    DockLayoutPanel wrapper;
    @UiField
    ScrollPanel messagePanelWrapper;
    @UiField
    VerticalPanel messagePanel;
    @UiField
    TextBox messageBox;

    private long myLastStamp = 0;
    private final String myRoom;
    private WebSocket mySocket;

    public ChatPanel(String userName, String chatName) {
        myRoom = chatName;
        this.initWidget(ourUiBinder.createAndBindUi(this));

        connect();

        messageBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    send();
                }
            }
        });

        messageBox.getElement().setAttribute("placeholder", "Send message");
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

        webSocketUrl += "?" + ChatAppController.ROOM_KEY + "=" + URL.encodeQueryString(myRoom);
        if (myLastStamp != 0) {
            webSocketUrl += "&" + ChatAppController.STAMP_KEY + "=" + myLastStamp;
        }

        mySocket = new WebSocket(webSocketUrl) {
            @Override
            protected void callOnClose(String reason) {
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
//                myLastStamp = message.getStamp();
//                FlowPanel p1 = new FlowPanel();
//                p1.getElement().setClassName("chat-message");
//                FlowPanel headerPanel = new FlowPanel();
//                p1.add(headerPanel);
//                FlowPanel pic = new FlowPanel();
//                pic.getElement().setClassName("chat-userpic");
//                headerPanel.add(pic);
//                pic.add(new Image("img/userpic.gif"));
//                FlowPanel sender = new FlowPanel();
//                sender.getElement().setClassName("chat-message-sender");
//                headerPanel.add(sender);
//                sender.add(new Label(message.getUser().getName()));
//                FlowPanel text = new FlowPanel();
//                text.getElement().setClassName("chat-message-text");
//                p1.add(text);
//                Label label = new Label(message.getText());
//                label.getElement().addClassName("chat-message-body");
//                text.add(label);

                // messagePanel.insert(p1, 0);
                messagePanel.insert(new ChatBubble(message), messagePanel.getWidgetCount());
                messagePanelWrapper.scrollToBottom();
            }

            @Override
            protected void callOnOpen() {
            }
        };
    }


}
