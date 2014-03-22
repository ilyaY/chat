package delightex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import delightex.client.presenter.ChatPresenter;
import delightex.client.ui.panels.LoginPanel;
import delightex.client.ui.layout.MainContentPanel;
import delightex.client.ui.layout.MainLayoutPanel;
import delightex.client.ui.widgets.IconButton;

public class ChatAppController implements EntryPoint {
    public static final String ROOM_KEY = "room";
    public static final String STAMP_KEY = "stamp";

    private final MainContentPanel mainContentPanel = new MainContentPanel();

    @Override
    public void onModuleLoad() {
        RootLayoutPanel rlp = RootLayoutPanel.get();
        MainLayoutPanel mainLayout = new MainLayoutPanel();

        mainLayout.setMainContent(mainContentPanel);
        mainLayout.setFooter(new HTML("&#169; Coachingspaces 2014"));

        HorizontalPanel headingPanel = new HorizontalPanel();

        headingPanel.add(new HTML("<h3 style='line-height: 28px; margin-right: 16px;'>Coachingspaces</h3>"));
        headingPanel.getElement().getStyle().setMarginLeft(16, Style.Unit.PX);
        headingPanel.getElement().getStyle().setMarginRight(16, Style.Unit.PX);

        final IconButton iconButton = new IconButton("fa-comment", "Click to toggle Chat sidebar");
        iconButton.setInactiveStyle();
        iconButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mainContentPanel.toggleSidebar();
                if(iconButton.isInactive()){
                    iconButton.setDefaultStyle();
                } else {
                    iconButton.setInactiveStyle();
                }
            }
        });

        headingPanel.add(iconButton);
        headingPanel.setCellVerticalAlignment(iconButton, HasVerticalAlignment.ALIGN_MIDDLE);

        //mainLayout.setHeader(new MainNavPanel());
        mainLayout.setHeader(headingPanel);

        rlp.add(mainLayout);
        // RootPanel.get().add(new MainNavPanel());
        RootPanel.get().add(rlp);

        ChatPresenter chatPresenter = new ChatPresenter(this);
        setSidebarContent(new LoginPanel(chatPresenter));
    }

    public void setSidebarContent(Widget sidebarContent){
        mainContentPanel.setSidebarContent(sidebarContent);
    }

    public void setMainContentPanel(Widget mainContent){
        mainContentPanel.setMainContent(mainContent);
    }
}
