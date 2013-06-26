package delightex.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InputDialog extends DialogBox {
  public InputDialog(String title) {
    super();
    setText(title);
    Button ok = new Button("Ok");
    final TextBox input = new TextBox();
    ok.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        hide();
        onOk(input.getText());
      }
    });
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.add(input);
    vPanel.add(ok);
    setWidget(vPanel);
    center();
    show();
  }

  public void onOk(String value) {
  }
}
