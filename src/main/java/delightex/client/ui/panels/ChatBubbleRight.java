package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import delightex.client.model.Message;

public class ChatBubbleRight extends ChatBubble {
    interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatBubbleRight> {
    }

    public interface Style extends CssResource {
    }

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    public ChatBubbleRight(Message msg) {
        super(msg);
        initWidget(ourUiBinder.createAndBindUi(this));
        init();
    }
}
