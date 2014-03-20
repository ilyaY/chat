package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import delightex.client.model.Message;
import delightex.client.presenter.ChatPresenter;
import delightex.client.ui.widgets.IconButton;
import delightex.client.ui.widgets.ToggleIconButton;
import delightex.client.util.Console;

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
    @UiField(provided = true)
    ToggleIconButton toggleButton;

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

    private ChatPresenter presenter;

    private long myLastStamp = 0;
    private final String myRoom;

    private boolean boxShadowVisible = true;

    private boolean scrollPanelAdded = false;
    private ScrollPanel sp;

    private ChatBubble lastAddedChatBubble = null;
    private boolean nextIsLeft = true;

    public ChatPanel(String userName, String roomName, ChatPresenter presenter) {
        Console.log(userName);
        Console.log(roomName);

        this.presenter = presenter;
        myRoom = roomName;
        IconButton userChatButton = new IconButton("fa-comments", "User Chat");
//        userChatButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                headingWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px 2px 30px 0 #545454");
//                inputWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px -2px 30px 0 #545454");
//            }
//        });
        IconButton figureChatButton = new IconButton("fa-group", "Figure Chat");
//        figureChatButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                headingWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "none");
//                inputWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "none");
//            }
//        });
        toggleButton = new ToggleIconButton(userChatButton, figureChatButton);
        this.initWidget(ourUiBinder.createAndBindUi(this));

        presenter.connect(roomName, new Command() {
            @Override
            public void execute() {
                //Socket opened
            }
        });

        final Timer afterSendCleanUp = new Timer() {
            @Override
            public void run() {
                messageBox.setValue("");
                triggerAutoResize(messageBox.getElement());
            }
        };
        final Timer afterInputResize = new Timer() {
            @Override
            public void run() {
                wrapper.setWidgetSize(inputWrapper, messageBox.getOffsetHeight() + 26);
                if (scrollPanelAdded) {
                    sp.scrollToBottom();
                }
            }
        };
        messageBox.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER && !"".equals(messageBox.getValue())) {
                    Console.log("SEND");
                    send();
                    event.stopPropagation();
                    afterSendCleanUp.schedule(10);
                }
                afterInputResize.schedule(50);
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
                headingWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px 1px 30px 0 #545454");
                headingWrapper.getElement().getParentElement().getStyle().setProperty("zIndex", "2");
//                headingWrapper.getElement().getParentElement().getStyle().setProperty("borderBottom", "1px solid #636363");
                inputWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px -1px 30px 0 #545454");
                inputWrapper.getElement().getParentElement().getStyle().setProperty("zIndex", "2");
//                inputWrapper.getElement().getParentElement().getStyle().setProperty("borderTop", "1px solid #636363");
            }
        });

//        toggleShadow.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                if(boxShadowVisible){
//                    headingWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "none");
//                    inputWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "none");
//                } else {
//                    headingWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px 2px 30px 0 #545454");
//                    inputWrapper.getElement().getParentElement().getStyle().setProperty("boxShadow", "0px -2px 30px 0 #545454");
//                }
//                boxShadowVisible = !boxShadowVisible;
//            }
//        });

        final Timer afterResizeTimer = new Timer() {
            @Override
            public void run() {
                fixScrolling();
            }
        };
        //scroll down after resize
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                afterResizeTimer.schedule(50);
            }
        });
    }

    private void send() {
        String text = messageBox.getValue();

        presenter.send(text);
        messageBox.setValue(null);

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                messageBox.setFocus(true);
            }
        });
    }

    private void fixScrolling() {
        //Change to ScrollPanel if messageList gets too long
        if (!scrollPanelAdded && messagePanel.getOffsetHeight() > mainPanel.getOffsetHeight()) {
            sp = new ScrollPanel();
            sp.addStyleName(style.mainPanel());
            messagePanel.removeStyleName(style.unscrollable());
            sp.add(messagePanel);
            wrapper.remove(mainPanel);
            wrapper.add(sp);
            scrollPanelAdded = true;
        }
        if (scrollPanelAdded) {
            sp.scrollToBottom();
        }
    }

    public void addMessage(Message message) {
        if (lastAddedChatBubble == null || !lastAddedChatBubble.getMessage().getUser().getName().equals(message.getUser().getName())) {
            ChatBubble cb = (nextIsLeft) ? new ChatBubbleLeft(message) : new ChatBubbleRight(message);
            nextIsLeft = !nextIsLeft;
            messagePanel.insert(cb, messagePanel.getWidgetCount());
            lastAddedChatBubble = cb;
        } else {
            lastAddedChatBubble.addMessage(message);
        }
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                fixScrolling();
            }
        });
    }


    //Wrap jQuery.autosize();
    private native void addAutoResize(Element ele)/*-{
      $wnd.$(ele).autosize();
    }-*/;

    private native void triggerAutoResize(Element ele)/*-{
      $wnd.$(ele).trigger('autosize.resize');
    }-*/;
}
