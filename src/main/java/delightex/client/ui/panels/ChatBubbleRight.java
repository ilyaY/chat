package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import delightex.client.model.Message;

public class ChatBubbleRight extends ChatBubble {
    interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatBubbleRight> {
    }

    public interface Style extends CssResource {
    }

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    @UiField
    HTML userName;
    @UiField
    HTML message;
    @UiField
    FlowPanel subBubbleList;

    private Message msg;

    public ChatBubbleRight(Message msg) {
        this.msg = msg;
        this.initWidget(ourUiBinder.createAndBindUi(this));
        this.userName.setText(msg.getUser().getName() + ":");
        this.message.setText(msg.getText());
    }

    public void addMessage(Message message) {
        subBubbleList.add(new HTML(message.getText()));
    }

    public Message getMessage() {
        return msg;
    }
}
