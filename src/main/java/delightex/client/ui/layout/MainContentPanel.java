package delightex.client.ui.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;

public class MainContentPanel extends Composite {
    private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

    interface ChatListUiBinder extends UiBinder<DockLayoutPanel, MainContentPanel> {
    }

    @UiField
    DockLayoutPanel wrapper;
    @UiField
    HTML sidebar;

    public MainContentPanel() {
        this.initWidget(ourUiBinder.createAndBindUi(this));
        wrapper.setWidgetHidden(sidebar, true);
    }

    private boolean toggle = false;
    public void toggleSidebar() {
        wrapper.setWidgetHidden(sidebar, toggle);
        wrapper.animate(100);
        toggle = !toggle;
    }
}