package delightex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import delightex.client.ui.panels.LoginPanel;
import delightex.client.ui.layout.MainContentPanel;
import delightex.client.ui.layout.MainLayoutPanel;

public class ChatAppController implements EntryPoint {
    public static final String ROOM_KEY = "room";
    public static final String STAMP_KEY = "stamp";

    private final MainContentPanel mainContentPanel = new MainContentPanel();

    @Override
    public void onModuleLoad() {
        //new LoginPanel().center();
        RootLayoutPanel rlp = RootLayoutPanel.get();
        MainLayoutPanel mainLayout = new MainLayoutPanel();

        mainLayout.setMainContent(mainContentPanel);
        mainLayout.setFooter(new HTML("&#169; Coachingspaces 2014"));

        HorizontalPanel headingPanel = new HorizontalPanel();
        headingPanel.add(new HTML("<h1>Coachingspaces</h1>"));
        headingPanel.getElement().getStyle().setMarginLeft(16, Style.Unit.PX);

        com.github.gwtbootstrap.client.ui.Button showChat = new com.github.gwtbootstrap.client.ui.Button("Toggle Chat");
        showChat.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mainContentPanel.toggleSidebar();
            }
        });
        showChat.getElement().getStyle().setMarginTop(18, Style.Unit.PX);
        showChat.getElement().getStyle().setMarginLeft(16, Style.Unit.PX);
        headingPanel.add(showChat);
        //mainLayout.setHeader(new MainNavPanel());
        mainLayout.setHeader(headingPanel);

        rlp.add(mainLayout);
        // RootPanel.get().add(new MainNavPanel());
        RootPanel.get().add(rlp);

        setSidebarContent(new LoginPanel(this));
    }

    public void setSidebarContent(Widget sidebarContent){
        mainContentPanel.setSidebarContent(sidebarContent);
    }

    public void setMainContentPanel(Widget mainContent){
        mainContentPanel.setMainContent(mainContent);
    }
}
