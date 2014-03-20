package delightex.client.ui.panels;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import delightex.client.presenter.ChatPresenter;
import delightex.client.rpc.ChatService;

public class LoginPanel extends Composite {
    private static LoginDialogUiBinder ourUiBinder = GWT.create(LoginDialogUiBinder.class);

    @UiField
    Button enter;
    @UiField
    TextBox nameField;

    private ChatPresenter chatPresenter;

    public LoginPanel(final ChatPresenter chatPresenter) {
        this.chatPresenter = chatPresenter;
        this.initWidget(ourUiBinder.createAndBindUi(this));

        enter.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                chatPresenter.doLogin(nameField.getValue());
            }
        });
        nameField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    chatPresenter.doLogin(nameField.getValue());
                }
            }
        });
    }



    interface LoginDialogUiBinder extends UiBinder<HTMLPanel, LoginPanel> {
    }
}
