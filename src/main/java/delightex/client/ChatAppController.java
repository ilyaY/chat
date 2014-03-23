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
        /*  Create Basic Layout */
        RootLayoutPanel rlp = RootLayoutPanel.get();
        MainLayoutPanel mainLayout = new MainLayoutPanel();

        mainLayout.setMainContent(mainContentPanel);

        HorizontalPanel headingPanel = new HorizontalPanel();
        headingPanel.getElement().getStyle().setHeight(100, Style.Unit.PCT);
        HTML brandIcon = new HTML("<img style='margin-top: 3px; margin-right: 16px' width='256px' src='/img/cs_logo.png' />");
        headingPanel.add(brandIcon);
        headingPanel.getElement().getStyle().setMarginLeft(16, Style.Unit.PX);
        headingPanel.getElement().getStyle().setMarginRight(16, Style.Unit.PX);

        final IconButton iconButton = new IconButton("fa-comment", "Click to toggle Chat sidebar");
        iconButton.setInactiveStyle();
        iconButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mainContentPanel.toggleSidebar();
                if (iconButton.isInactive()) {
                    iconButton.setDefaultStyle();
                } else {
                    iconButton.setInactiveStyle();
                }
            }
        });
        iconButton.getElement().getStyle().setMarginTop(1, Style.Unit.PX);

        headingPanel.add(iconButton);
        headingPanel.setCellVerticalAlignment(brandIcon, HasVerticalAlignment.ALIGN_MIDDLE);
        headingPanel.setCellVerticalAlignment(iconButton, HasVerticalAlignment.ALIGN_MIDDLE);

        //mainLayout.setHeader(new MainNavPanel());
        mainLayout.setHeader(headingPanel);

        rlp.add(mainLayout);
        RootPanel.get().add(rlp);

         /* Create Content */
        ChatPresenter chatPresenter = new ChatPresenter(this);
        setSidebarContent(new LoginPanel(chatPresenter));
    }

    public void setSidebarContent(Widget sidebarContent) {
        mainContentPanel.setSidebarContent(sidebarContent);
    }

    public void setMainContentPanel(Widget mainContent) {
        mainContentPanel.setMainContent(mainContent);
    }
}
