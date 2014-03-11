package delightex.client.ui.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class MainContentPanel extends Composite {
    private static ChatListUiBinder ourUiBinder = GWT.create(ChatListUiBinder.class);

    interface ChatListUiBinder extends UiBinder<DockLayoutPanel, MainContentPanel> {
    }

    private DockLayoutPanel wrapper;
    private Widget mainContent;
    private Widget sidebar;

    public MainContentPanel() {
        //this.initWidget(ourUiBinder.createAndBindUi(this));
        wrapper = new DockLayoutPanel(Style.Unit.EM);
        sidebar = new HTML("<h2>Sidebar</h2>");
        mainContent = new HTML("<img src='/img/canvas.png' />");
        mainContent.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
        mainContent.getElement().getStyle().setBackgroundColor("#aaa");

        wrapper.addWest(sidebar, 24);
        wrapper.add(mainContent);

        this.initWidget(wrapper);
    }

    private boolean toggle = false;
    public void toggleSidebar() {
        wrapper.setWidgetHidden(sidebar, toggle);
        wrapper.animate(200);
        toggle = !toggle;
    }

    public void setSidebarContent(Widget w){
        wrapper.clear();
        this.sidebar = w;
        wrapper.addWest(sidebar, 24);
        wrapper.add(mainContent);
        sidebar.getElement().getStyle().setBackgroundColor("#868686");
        wrapper.setWidgetHidden(sidebar, !toggle);
    }

    public void setMainContent(Widget w){
        this.mainContent = w;
        wrapper.add(mainContent);
    }
}