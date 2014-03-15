package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import delightex.client.ChatAppController;
import delightex.client.WebSocket;
import delightex.client.model.Message;
import delightex.client.util.Console;

import static delightex.client.model.MessageDeserializer.fromJson;

public class ChatPanel extends Composite {
    interface ChatPanelUiBinder extends UiBinder<DockLayoutPanel, ChatPanel> {
    }

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    public interface Style extends CssResource {
        String unscrollable();
        String mainPanel();
    }

    @UiField
    Style style;

    @UiField
    HTMLPanel headingWrapper;
    @UiField
    DockLayoutPanel wrapper;
    //    @UiField
//    ScrollPanel messagePanelWrapper;
    @UiField
    FlowPanel messagePanel;

    @UiField
    HTMLPanel inputWrapper;
    @UiField
    TextArea messageBox;

    @UiField
    HTMLPanel mainPanel;

    private long myLastStamp = 0;
    private final String myRoom;
    private WebSocket mySocket;

    public ChatPanel(String userName, String chatName) {
        myRoom = chatName;
        this.initWidget(ourUiBinder.createAndBindUi(this));

        connect();

        final Timer afterSendCleanUp = new Timer() {
            @Override
            public void run() {
                messageBox.setValue("");
                triggerAutoResize(messageBox.getElement());
            }
        };
        messageBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    send();
                    event.stopPropagation();
                    afterSendCleanUp.schedule(10);
                }
            }
        });

        messageBox.getElement().setAttribute("placeholder", "Send message");
        // Window.alert("SUPER DEV MODE WORKS");

        addAutoResize(messageBox.getElement());

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                messageBox.setFocus(true);
            }
        });

//        Window.addResizeHandler(new ResizeHandler() {
//            @Override
//            public void onResize(ResizeEvent event) {
//                if(messagePanel.getOffsetHeight() > wrapper.get.getOffsetHeight()){
//                    messagePanel.addStyleName(style.scrollable());
//                }
//            }
//        });

        //Hack to access DockLayoutPanel wrappers style
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                headingWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px 0px 30px 0 #636363");
                headingWrapper.getElement().getParentElement().getStyle().setProperty("zIndex", "2");
                headingWrapper.getElement().getParentElement().getStyle().setProperty("borderBottom", "1px solid #636363");
                inputWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px 0px 30px 0 #636363");
                inputWrapper.getElement().getParentElement().getStyle().setProperty("zIndex", "2");
                inputWrapper.getElement().getParentElement().getStyle().setProperty("borderTop", "1px solid #636363");
            }
        });

        final Timer t = new Timer() {
            @Override
            public void run() {
                wrapper.setWidgetSize(inputWrapper, messageBox.getOffsetHeight() + 26);
            }
        };
        messageBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                t.schedule(50);
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

    private ChatBubble lastAddedChatBubble = null;

    private boolean scrollPanelAdded = false;
    private ScrollPanel sp;

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
                if (lastAddedChatBubble == null || !lastAddedChatBubble.getMessage().getUser().getName().equals(message.getUser().getName())) {
                    ChatBubble cb = new ChatBubble(message);
                    messagePanel.insert(cb, messagePanel.getWidgetCount());
                    lastAddedChatBubble = cb;
                } else {
                    lastAddedChatBubble.addMessage(message);
                }

                // ye olde switcheru
//                Console.log("MainPanel: " + mainPanel.getOffsetHeight());
//                Console.log("MainPanel Parent: " + mainPanel.getElement().getParentElement().getParentElement().getOffsetHeight());

                //Change to ScrollPanel if messageList gets too long
                if(messagePanel.getOffsetHeight() > mainPanel.getOffsetHeight()){
                    Console.log("Switcheru");
                    if(!scrollPanelAdded){
                        sp = new ScrollPanel();
                        sp.addStyleName(style.mainPanel());
                        messagePanel.removeStyleName(style.unscrollable());
                        sp.add(messagePanel);
                        wrapper.remove(mainPanel);
                        wrapper.add(sp);
                        scrollPanelAdded = true;
                    }
                }
                if(scrollPanelAdded){
                    sp.scrollToBottom();
                }
            }

            @Override
            protected void callOnOpen() {
            }
        };
    }

    //Wrap jQuery.autosize();
    private native void addAutoResize(Element ele)/*-{
      $wnd.$(ele).autosize();
    }-*/;

    private native void triggerAutoResize(Element ele)/*-{
      $wnd.$(ele).trigger('autosize.resize');
    }-*/;
}
