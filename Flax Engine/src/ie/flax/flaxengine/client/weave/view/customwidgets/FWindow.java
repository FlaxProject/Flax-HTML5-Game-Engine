package ie.flax.flaxengine.client.weave.view.customwidgets;

import ie.flax.flaxengine.client.FlaxEngine;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Draggable, closeable, modal window.
 * 
 * @param title
 * 
 * @author Ciaran McCann
 * @author Carl Lange
 * 
 */
public class FWindow {
	
    private final VerticalPanel mainPanel;
    private final ClosablePopup window;

    /**
     * Constructs, but does not show, a draggable, closeable, modal window.
     * 
     * @param title
     *            The title of the window to construct. This can be changed
     *            later with {@link setTitle}.
     */
    public FWindow(String title) {

        window = new ClosablePopup(title, true);

        mainPanel = new VerticalPanel();

        window.add(mainPanel);

        window.setGlassEnabled(true);
        window.setAnimationEnabled(true);
        window.show();

        window.hide();

        FlaxEngine.settings.getContainer().add(window);
        window.addCloseHandler(new CloseHandler<PopupPanel>() {

            @Override
            public void onClose(CloseEvent<PopupPanel> event) {
                mainPanel.clear();
            }
        });
    }

    /**
     * Adds widgets to vertical panel in digial window
     * 
     * @param widget
     */
    public void add(Widget widget) {
        mainPanel.add(widget);
    }

    /**
     * Closes the window - unsure if this works. TODO: find out if this works.
     */
    public void close() {
        window.close();
    }

    /**
     * Return reference to the Vertical panel in the Fwindow
     * 
     * @return
     */
    public HasWidgets asWdidget() {
        return mainPanel;
    }

    /**
     * Sets the title of the window - that is, the name in the titlebar, not the
     * tooltip.
     * 
     * @param title
     */
    public void setTitle(String title) {
        window.setText(title);
    }

    /**
     * Shows the window in the centre of the screen.
     */
    public void show() {
        window.show();
        window.setPopupPosition(
                		(FlaxEngine.settings.getContainer().getAbsoluteLeft() + (FlaxEngine.settings.getWidth() ) /2) - (window.getOffsetWidth() / 2),
                		(FlaxEngine.settings.getContainer().getAbsoluteTop() + (FlaxEngine.settings.getHeight() ) /2) - (window.getOffsetHeight() / 2));
    }

}

/**
 * @author Andrey Talnikov
 */
class ClosablePopup extends DialogBox {

    private Anchor closeAnchor;
    FlexTable captionLayoutTable = new FlexTable();

    /**
     * Instantiates new closable popup.
     * 
     * @param title
     *            the title
     * @param defaultClose
     *            it {@code true}, hide popup on 'x' click
     */
    public ClosablePopup(String title, boolean defaultClose) {
        super(true);

        closeAnchor = new Anchor("X");

        captionLayoutTable = new FlexTable();
        captionLayoutTable.setWidth("100%");
        captionLayoutTable.setText(0, 0, title);
        captionLayoutTable.setWidget(0, 1, closeAnchor);
        captionLayoutTable.getCellFormatter().setHorizontalAlignment(
                0,
                1,
                HasHorizontalAlignment.HorizontalAlignmentConstant
                        .endOf(HasDirection.Direction.LTR));

        HTML caption = (HTML) getCaption();
        caption.getElement().appendChild(captionLayoutTable.getElement());

        caption.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                EventTarget target = event.getNativeEvent().getEventTarget();
                Element targetElement = (Element) target.cast();

                if (targetElement == closeAnchor.getElement()) {
                    closeAnchor.fireEvent(event);
                }
            }
        });

        if (defaultClose) {
            addCloseHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    hide();
                }
            });
        }
    }

    public void addCloseHandler(ClickHandler handler) {
        closeAnchor.addClickHandler(handler);
    }

    public void close() {
        hide();
    }

    @Override
    public void setText(String text) {
        captionLayoutTable.setText(0, 0, text);
    }

}