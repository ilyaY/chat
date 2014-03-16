package delightex.client.ui.widgets;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Anchor;

import javax.xml.soap.Text;

public class IconButton extends Anchor {
    public IconButton(String faIconName, String title){
        this.setHTML("<i class='fa " + faIconName +"' style='line-height: 38px'></i>");
        this.setTitle(title);
        Style style = this.getElement().getStyle();
        style.setDisplay(Style.Display.BLOCK);
        style.setFontSize(22, Style.Unit.PX);
        style.setWidth(38, Style.Unit.PX);
        style.setHeight(38, Style.Unit.PX);
        style.setTextAlign(Style.TextAlign.CENTER);
        style.setColor("#ffffff");
        style.setBackgroundColor("#2c75ba");
        style.setBorderWidth(1, Style.Unit.PX);
        style.setBorderColor("#366594");
        style.setBorderStyle(Style.BorderStyle.SOLID);
        style.setProperty("borderRadius", "3px");
        style.setCursor(Style.Cursor.POINTER);
        setDefaultStyle();
        this.addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                setHoverStyle();
            }
        });
        this.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                if(inactive){
                    setInactiveStyle();
                } else {
                    setDefaultStyle();
                }
            }
        });
        this.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                setPressedStyle();
            }
        });
        this.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                setHoverStyle();
            }
        });
    }

    public void setDefaultStyle(){
        inactive = false;
        Style style = this.getElement().getStyle();
        style.setColor("#ffffff");
        style.setBackgroundColor("#2c75ba");
        style.setBorderColor("#366594");
    }

    public void setPressedStyle(){
        Style style = this.getElement().getStyle();
        style.setColor("#ffffff");
        style.setBackgroundColor("#3d83d6");
        style.setBorderColor("#366594");
    }

    public void setHoverStyle(){
        Style style = this.getElement().getStyle();
        style.setColor("#ffffff");
        style.setBackgroundColor("#5b95e8");
        style.setBorderColor("#366594");
    }

    private boolean inactive = false;

    public boolean isInactive(){
        return inactive;
    }

    public void setInactiveStyle(){
        inactive = true;
        Style style = this.getElement().getStyle();
        style.setColor("#f3f3f3");
        style.setBackgroundColor("#d0d0d0");
        style.setBorderColor("#d5d5d5");
    }
}
