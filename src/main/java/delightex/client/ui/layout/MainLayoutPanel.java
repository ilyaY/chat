package delightex.client.ui.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import delightex.client.rpc.ChatService;
import delightex.client.rpc.ChatServiceAsync;

public class MainLayoutPanel extends Composite {
    private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

    interface ChatListUiBinder extends UiBinder<DockLayoutPanel, MainLayoutPanel> {
    }

    @UiField
    SimplePanel header;
    @UiField
    DockLayoutPanel mainContent;
    @UiField
    SimplePanel footer;

    final ChatServiceAsync service = ChatService.App.getInstance();

    public MainLayoutPanel() {
        this.initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void setHeader(Widget header) {
        this.header.add(header);
    }

    public void setMainContent(Widget mainContent) {
        this.mainContent.add(mainContent);
        //this.mainContent.setWidgetLeftWidth(mainContent, 0d, Style.Unit.PX, 100d, Style.Unit.PCT);
    }

    public void setFooter(Widget footer) {
        this.footer.add(footer);
    }
}