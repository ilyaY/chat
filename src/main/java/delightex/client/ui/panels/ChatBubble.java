package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import delightex.client.model.Message;

public class ChatBubble extends Composite {
    interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatBubble> {
    }

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    @UiField
    HTML userName;
    @UiField
    HTML message;

    public ChatBubble(Message message) {
        this.initWidget(ourUiBinder.createAndBindUi(this));
        this.userName.setText(message.getUser().getName());
        this.message.setText(message.getText());
    }
}
