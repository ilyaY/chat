package delightex.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import delightex.client.ChatAppController;
import delightex.client.WebSocket;
import delightex.client.model.Message;
import delightex.client.rpc.ChatService;
import delightex.client.rpc.ChatServiceAsync;
import delightex.client.ui.panels.ChatPanel;
import delightex.client.ui.panels.MessageOptionsPopup;
import delightex.client.ui.panels.RoomsPanel;
import delightex.client.util.Console;

import java.util.Date;
import java.util.Set;

import static delightex.client.model.MessageDeserializer.fromJson;

public class ChatPresenter {
    private ChatAppController myAppController;
    private ChatServiceAsync myChatService = ChatService.App.getInstance();
    private WebSocket mySocket;
    private String myUserName;

    private MessageOptionsPopup popup;
    private boolean popupVisible = false;

    public ChatPresenter(ChatAppController appController) {
        this.myAppController = appController;
//        Window.addWindowScrollHandler(new Window.ScrollHandler() {
//            @Override
//            public void onWindowScroll(Window.ScrollEvent event) {
//                if(popup != null){
//                    popup.hide();
//                }
//            }
//        });

        // Global click event handler to close popups
        RootPanel.get().sinkEvents(Event.ONCLICK);
        RootPanel.get().addHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
               if(popupVisible){
                   // If click was outside of popup, hide popup
                   if(popup != null && !popup.getElement().isOrHasChild(Element.as(event.getNativeEvent().getEventTarget()))){
                       hideMessageOptionsMenu();
                   }
               }
            }

        }, ClickEvent.getType());
    }

    public void doLogin(final String name) {
        myUserName = name;
        if (name == null || name.isEmpty()) {
        } else {
            ChatService.App.getInstance().login(name, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Failed to log in");
                }

                @Override
                public void onSuccess(Void result) {
                    myAppController.setSidebarContent(new RoomsPanel(name, getThis()));
                }
            });
        }
    }

    private ChatPresenter getThis() {
        return this;
    }

    private ChatPanel chatPanel;

    public void createRoom(final String roomName) {
        Console.log("Create Room");
        if (roomName == null || roomName.isEmpty()) {
            Window.alert("ChatAppController name cannot be empty");
        } else {
            myChatService.addRoom(roomName, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Cannot create new chat");
                }

                @Override
                public void onSuccess(String result) {
                    Console.log(result);
                    chatPanel = new ChatPanel(myUserName, roomName, getThis());
                    myAppController.setSidebarContent(chatPanel);
                }
            });
        }
    }

    public void getRooms(AsyncCallback<Set<String>> asyncCallback) {
        myChatService.getRooms(asyncCallback);
    }

    private long myLastStamp;
    public void connect(final String myRoom, final Command onConnected) {
        String baseUrl = GWT.getHostPageBaseURL();

        String webSocketUrl = baseUrl.replace("http", "ws") + "wschat";

        webSocketUrl += "?" + ChatAppController.ROOM_KEY + "=" + URL.encodeQueryString(myRoom);
        if (myLastStamp != 0) {
            webSocketUrl += "&" + ChatAppController.STAMP_KEY + "=" + myLastStamp;
        }

        Console.log("OPEN WEBSOCKET " + webSocketUrl);

        mySocket = new WebSocket(webSocketUrl) {
            @Override
            protected void callOnClose(String reason) {
                mySocket = null;
                myLastStamp = new Date().getTime();
                connect(myRoom, onConnected);
            }

            @Override
            protected void callOnError() {
                //error
            }

            @Override
            protected void callOnMessage(String s) {
                Message message = fromJson(s);
                chatPanel.addMessage(message);
            }

            @Override
            protected void callOnOpen() {
                onConnected.execute();
            }
        };
    }

    public void send(String msg) {
        mySocket.send(msg);
    }

    private Command cleanUpSourceAnchorStyling;

    public void openMessageOptionsMenu(int left, int top, Command cleanUpSourceAnchorStyling){
        if(popup != null){
            hideMessageOptionsMenu();
        }
        this.cleanUpSourceAnchorStyling = cleanUpSourceAnchorStyling;
        popup = new MessageOptionsPopup();
        popup.setWidget(new HTML("Edit Statement<hr/>Delete Statement"));
        popup.show();
        popup.setPopupPosition(left, top - popup.getOffsetHeight());
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                popupVisible = true;
            }
        });
    }

    public void hideMessageOptionsMenu(){
        if(popup != null){
            popup.hide();
            if(cleanUpSourceAnchorStyling != null){
                cleanUpSourceAnchorStyling.execute();
            }
        }
        popupVisible = false;
    }
}
