package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import delightex.client.model.Message;

public class ChatBubble extends Composite {

    interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatBubble> {
    }

    public interface Style extends CssResource {
        String portraitWrapperRight();

        String portraitRight();

        String messageWrapperRight();

        String optionsAnchor();

        String angleUp();
    }

    @UiField
    Style style;

    @UiField
    HTMLPanel wrapper;
    @UiField
    DivElement portraitWrapper;
    @UiField
    HTML userName;
    @UiField
    HTML message;
    @UiField
    FlowPanel subBubbleList;

    private Message msg;

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    public ChatBubble(Message msg, boolean rightPortrait) {
        this.initWidget(ourUiBinder.createAndBindUi(this));
        this.msg = msg;
        init();
        if (rightPortrait) {
            portraitWrapper.addClassName(style.portraitWrapperRight());
            portraitWrapper.addClassName(style.portraitRight());
            subBubbleList.addStyleName(style.messageWrapperRight());
        }
    }

    protected void init() {
        this.userName.setText(msg.getUser().getName() + ":");
        this.message.setText(msg.getText());
    }

    public void addMessage(Message message) {
        HTMLPanel wrapper = new HTMLPanel("");
        Anchor optionAnchor = new Anchor();
        optionAnchor.setHTML("<i class=\"fa fa-cog\"></i>&nbsp;<i class=\"fa fa-angle-up " + style.angleUp() +"\"></i>");
        optionAnchor.addStyleName(style.optionsAnchor());
        wrapper.add(optionAnchor);
        HTML messageWrapper = new HTML();
        messageWrapper.setText(message.getText());
        wrapper.add(messageWrapper);
        subBubbleList.add(wrapper);
    }

    public Message getMessage() {
        return msg;
    }
}
