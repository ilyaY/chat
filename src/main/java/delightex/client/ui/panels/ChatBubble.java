package delightex.client.ui.panels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import delightex.client.model.Message;

/**
 * Created by Sebastisn on 15.03.14.
 */
public abstract class ChatBubble extends Composite {
    public abstract void addMessage(Message message);

    public abstract Message getMessage();
}
