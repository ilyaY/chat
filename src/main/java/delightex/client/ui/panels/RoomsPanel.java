package delightex.client.ui.panels;

import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import delightex.client.ChatAppController;
import delightex.client.rpc.ChatService;
import delightex.client.rpc.ChatServiceAsync;

import java.util.Set;

public class RoomsPanel extends SimplePanel {
    private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

    interface ChatListUiBinder extends UiBinder<HTMLPanel, RoomsPanel> {
    }

    private Timer myTimer;

    @UiField
    VerticalPanel chatList;
    @UiField
    com.github.gwtbootstrap.client.ui.Button newButton;
    @UiField
    TextBox chatName;
    @UiField
    Label helloText;

    private final String myUserName;
    final ChatServiceAsync service = ChatService.App.getInstance();
    private ChatAppController chatAppController;

    public RoomsPanel(String name, final ChatAppController chatAppController) {
        this.chatAppController = chatAppController;
        myUserName = name;
        setWidget(ourUiBinder.createAndBindUi(this));
        helloText.setText("Hello, " + name + "! Choose some chat you want to enter!");

        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                sendNewRequest();
            }
        });

        chatName.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    sendNewRequest();
                }
            }
        });

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                chatName.setFocus(true);
            }
        });

        refreshChats();
    }

    private void sendNewRequest(){
        final String name = chatName.getValue();
        if (name == null || name.isEmpty()) {
            Window.alert("ChatAppController name cannot be empty");
        } else {
            service.addRoom(name, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Cannot create new chat");
                }

                @Override
                public void onSuccess(String result) {
                    if (result != null) {
                        //Window.alert(result);
                        enterChat(name);
                    } else {
                        enterChat(name);
                    }
                }
            });
        }
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
                    Anchor link = new Anchor(chat);
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
        chatAppController.setSidebarContent(new ChatPanel(myUserName, name));
    }
}