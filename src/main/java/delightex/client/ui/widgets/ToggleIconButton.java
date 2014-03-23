package delightex.client.ui.widgets;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class ToggleIconButton extends FlowPanel {
    public ToggleIconButton(final IconButton left, final IconButton right) {
        left.getElement().getStyle().setFloat(Style.Float.LEFT);
        right.getElement().getStyle().setFloat(Style.Float.LEFT);

        left.getElement().getStyle().setProperty("borderTopRightRadius", "0px");
        left.getElement().getStyle().setProperty("borderBottomRightRadius", "0px");
        left.getElement().getStyle().setProperty("borderRight", "none");

        left.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                left.setDefaultStyle();
                right.setInactiveStyle();
            }
        });
        right.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                right.setDefaultStyle();
                left.setInactiveStyle();
            }
        });

        right.getElement().getStyle().setProperty("borderTopLeftRadius", "0px");
        right.getElement().getStyle().setProperty("borderBottomLeftRadius", "0px");
        right.getElement().getStyle().setProperty("borderLeft", "none");

        right.setInactiveStyle();

        this.add(left);
        this.add(right);

        HTML clearFix = new HTML("&nbsp;");
        clearFix.getElement().getStyle().setClear(Style.Clear.BOTH);
        this.add(clearFix);
    }
}
