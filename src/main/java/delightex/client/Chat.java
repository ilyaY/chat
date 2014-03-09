package delightex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import delightex.client.ui.layout.MainContentPanel;
import delightex.client.ui.layout.MainLayoutPanel;

public class Chat implements EntryPoint {
    public static final String ROOM_KEY = "room";
    public static final String STAMP_KEY = "stamp";

    @Override
    public void onModuleLoad() {
        //new LoginDialog().center();
        RootLayoutPanel rlp = RootLayoutPanel.get();
        MainLayoutPanel mainLayout = new MainLayoutPanel("");

        final MainContentPanel mcp = new MainContentPanel();
        mainLayout.setMainContent(mcp);
        mainLayout.setFooter(new HTML("Footer"));

        HorizontalPanel headingPanel = new HorizontalPanel();
        headingPanel.add(new HTML("<h1>Coachingspaces</h1>"));
        Anchor showChat = new Anchor("Toggle Chat");
        showChat.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mcp.toggleSidebar();
            }
        });
        headingPanel.add(showChat);
        mainLayout.setHeader(headingPanel);

        rlp.add(mainLayout);
        RootPanel.get().add(rlp);
    }
}
