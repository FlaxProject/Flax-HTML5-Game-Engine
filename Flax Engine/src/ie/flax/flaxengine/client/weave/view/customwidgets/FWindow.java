package ie.flax.flaxengine.client.weave.view.customwidgets;

import ie.flax.flaxengine.client.FLog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Draggable window, with a header and a default settings setup
 * 
 * @param title
 * 
 * @author Ciarán McCann
 * 
 */
public class FWindow {

	private final VerticalPanel mainPanel;
	private final DialogBox window;
	private final Button close;

	public FWindow(String title) {

		window = new DialogBox();
		mainPanel = new VerticalPanel();

		close = new Button("Close");
		close.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				close();

			}
		});

		mainPanel.add(close);
		mainPanel.setCellHeight(close, "0px");
		mainPanel.setCellWidth(close, "0px");

		window.add(mainPanel);
		window.setPopupPosition(Window.getClientWidth() / 2,
				Window.getClientHeight() / 2);
		window.setText(title);
		window.setGlassEnabled(true);
		window.setAnimationEnabled(true);
		window.show();
		window.hide();

		FLog.debug("FWindow with title [" + title + "] was created");
		RootPanel.get().add(window, 200, 200);
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
	 * Return reference to the Vertical panel in the Fwindow
	 * 
	 * @return
	 */
	public HasWidgets asWdidget() {
		return mainPanel;
	}

	public void clear() {

		mainPanel.clear();
		mainPanel.add(close);
	}

	/**
	 * Removes the FWindow from the RootPanel
	 */
	public void close() {

		window.hide();
	}

	public void setTitle(String title) {
		window.setText(title);
	}

	public void show() {
		window.show();
	}

}
