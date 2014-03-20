package delightex.client.ui.panels;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import delightex.client.model.Message;

/**
 * Created by Sebastisn on 15.03.14.
 */
public abstract class ChatBubble extends Composite {

    @UiField
    HTML userName;
    @UiField
    HTML message;
    @UiField
    FlowPanel subBubbleList;

    private Message msg;

    public ChatBubble(Message msg) {
        this.msg = msg;
    }

    protected void init(){
        this.userName.setText(msg.getUser().getName() + ":");
        this.message.setText(msg.getText());
    }

    public void addMessage(Message message) {
        HTML wrapper = new HTML();
        wrapper.setText(message.getText());
        subBubbleList.add(wrapper);
    }

    public Message getMessage() {
        return msg;
    }
}
