package delightex.client.ui.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;

public class IconButton extends Composite implements HasClickHandlers {

    @UiField
    Anchor anchor;

    public IconButton(String faIconName, String title) {
        initWidget(ourUiBinder.createAndBindUi(this));

        anchor.setHTML("<i class='fa " + faIconName + "' style='line-height: 38px'></i>");
        anchor.setTitle(title);

        setDefaultStyle();

        anchor.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                if (inactive) {
                    setInactiveStyle();
                } else {
                    setDefaultStyle();
                }
            }
        });
        anchor.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                setPressedStyle();
            }
        });

    }

    public void setDefaultStyle() {
        inactive = false;
    }

    public void setPressedStyle() {
//        Style style = this.getElement().getStyle();
//        style.setColor("#ffffff");
//        style.setBackgroundColor("#3d83d6");
//        style.setBorderColor("#366594");
    }

    private boolean inactive = false;
    public boolean isInactive() {
        return inactive;
    }

    public void setInactiveStyle() {
        inactive = true;
//        Style style = this.getElement().getStyle();
//        style.setColor("#f3f3f3");
//        style.setBackgroundColor("#d0d0d0");
//        style.setBorderColor("#d5d5d5");
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return anchor.addClickHandler(handler);
    }

    interface IconButtonBinder extends UiBinder<Anchor, IconButton> {
    }

    private static IconButtonBinder ourUiBinder = GWT.create(IconButtonBinder.class);
}
