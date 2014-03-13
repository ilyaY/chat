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
import delightex.client.ChatAppController;
import delightex.client.rpc.ChatService;

public class LoginPanel extends Composite {
    private static LoginDialogUiBinder ourUiBinder = GWT.create(LoginDialogUiBinder.class);

    @UiField
    Button enter;
    @UiField
    TextBox nameField;

    private ChatAppController chatAppController;
    public LoginPanel(final ChatAppController chatAppController) {
        this.chatAppController = chatAppController;
        this.initWidget(ourUiBinder.createAndBindUi(this));

        enter.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doLogin();
            }
        });
        nameField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
                    doLogin();
                }
            }
        });
    }

    private void doLogin(){
        final String name = nameField.getValue();
        if (name == null || name.isEmpty()) {
        } else {
            ChatService.App.getInstance().login(name, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Failed to log in");
                }
                @Override
                public void onSuccess(Void result) {
                    chatAppController.setSidebarContent(new RoomsPanel(name, chatAppController));
                }
            });
        }
    }

    interface LoginDialogUiBinder extends UiBinder<HTMLPanel, LoginPanel> {
    }
}
