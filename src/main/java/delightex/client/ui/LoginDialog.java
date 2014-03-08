package delightex.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import delightex.client.rpc.ChatService;

public class LoginDialog extends DialogBox {
  private static LoginDialogUiBinder ourUiBinder = GWT.create(LoginDialogUiBinder.class);

  @UiField
  Button enter;
  @UiField
  TextBox nameField;

  public LoginDialog() {
    setText("Login");
    add(ourUiBinder.createAndBindUi(this));
    enter.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
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
              hide();
              RootPanel.get().add(new MainPanel(name));
            }
          });
        }
      }
    });
  }

  interface LoginDialogUiBinder extends UiBinder<HTMLPanel, LoginDialog> {}
}
