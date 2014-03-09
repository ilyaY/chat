package delightex.client.ui.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import delightex.client.rpc.ChatService;
import delightex.client.rpc.ChatServiceAsync;

public class MainNavPanel extends Composite {
    private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

    interface ChatListUiBinder extends UiBinder<FlowPanel, MainNavPanel> {
    }

    public MainNavPanel() {
        this.initWidget(ourUiBinder.createAndBindUi(this));
    }
}