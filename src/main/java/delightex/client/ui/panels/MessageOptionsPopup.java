package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class MessageOptionsPopup extends PopupPanel {

    private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

    interface ChatListUiBinder extends UiBinder<HTMLPanel, MessageOptionsPopup> {
    }

    public MessageOptionsPopup() {
        super();
        setWidget(ourUiBinder.createAndBindUi(this));
    }
}
