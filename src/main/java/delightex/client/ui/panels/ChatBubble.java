package delightex.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.*;
import delightex.client.model.Message;
import delightex.client.presenter.ChatPresenter;

public class ChatBubble extends Composite {

    interface ChatPanelUiBinder extends UiBinder<HTMLPanel, ChatBubble> {
    }

    public interface Style extends CssResource {
        String portraitWrapperRight();

        String portraitRight();

        String messageWrapperRight();

        String optionsAnchor();

        String optionsAnchorHover();

        String angleUp();
    }

    @UiField
    Style style;

    @UiField
    HTMLPanel wrapper;
    @UiField
    DivElement portraitWrapper;
    @UiField
    Anchor optionsAnchor;
    @UiField
    HTML userName;
    @UiField
    HTML message;
    @UiField
    FlowPanel subBubbleList;

    private Message msg;
    private ChatPresenter presenter;

    private static ChatPanelUiBinder ourUiBinder = GWT.create(ChatPanelUiBinder.class);

    public ChatBubble(final ChatPresenter presenter, Message msg, boolean rightPortrait) {
        this.initWidget(ourUiBinder.createAndBindUi(this));
        this.msg = msg;
        this.presenter = presenter;
        init();
        if (rightPortrait) {
            portraitWrapper.addClassName(style.portraitWrapperRight());
            portraitWrapper.addClassName(style.portraitRight());
            subBubbleList.addStyleName(style.messageWrapperRight());
        }
        optionsAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                optionsAnchor.addStyleName(style.optionsAnchorHover());
                presenter.openMessageOptionsMenu(optionsAnchor.getAbsoluteLeft(), optionsAnchor.getAbsoluteTop(), new Command() {
                    @Override
                    public void execute() {
                        optionsAnchor.removeStyleName(style.optionsAnchorHover());
                    }
                });
            }
        });
    }

    protected void init() {
        this.userName.setText(msg.getUser().getName() + ":");
        this.message.setText(msg.getText());
    }

    public void addMessage(Message message) {
        HTMLPanel wrapper = new HTMLPanel("");
        final Anchor optionsAnchor = new Anchor();
        optionsAnchor.setHTML("<i class=\"fa fa-cog\"></i>&nbsp;<i class=\"fa fa-angle-up " + style.angleUp() + "\"></i>");
        optionsAnchor.addStyleName(style.optionsAnchor());
        optionsAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                optionsAnchor.addStyleName(style.optionsAnchorHover());
                presenter.openMessageOptionsMenu(optionsAnchor.getAbsoluteLeft(), optionsAnchor.getAbsoluteTop(), new Command() {
                    @Override
                    public void execute() {
                        optionsAnchor.removeStyleName(style.optionsAnchorHover());
                    }
                });
            }
        });
        wrapper.add(optionsAnchor);
        HTML messageWrapper = new HTML();
        messageWrapper.setText(message.getText());
        wrapper.add(messageWrapper);
        subBubbleList.add(wrapper);
    }

    public Message getMessage() {
        return msg;
    }
}
