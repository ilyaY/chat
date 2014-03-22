package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import delightex.client.model.Message;

public class ChatBubbleLeft extends ChatBubble {
    interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatBubbleLeft> {
    }

    public interface Style extends CssResource {
    }

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    public ChatBubbleLeft(Message msg) {
        super(msg, false);
        initWidget(ourUiBinder.createAndBindUi(this));
        init();
    }
}
