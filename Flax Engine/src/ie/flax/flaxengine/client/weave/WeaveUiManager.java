package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class manages all the tabs in the UI menu bar. Each tab once created is automatically added 
 * to the menu div of weave and is registered for some generic events such as label click to show/hide the tab. 
 * 
 * This class should probly be refractored to have a collection, like a hashtable of tabs and have add functions.
 * Though this editor is only to be extended by me and not a thrid party so no real need.
 * 
 * @author Ciarán McCann
 *
 */
public class WeaveUiManager {

	/**
	 * Main html div which weave UI is contained in
	 */
	public HTMLPanel weaveUIdiv;

	/**
	 * Tabs in the weave UI
	 */
	private Tab mapMenu;
	private Tab tileSheetMenu;
	private Tab consoleMenu;

	/**
	 * 
	 * @param insertId - the HTML id of where to insert the weave UI
	 * @param referenceToTheEditor - reference to Weave, so controllers can acess things like current tile, enetity etc
	 */
	public WeaveUiManager(String insertId, Weave referenceToTheEditor) {

		/**
		 * Constructs the panel which all panels enter into
		 */
		weaveUIdiv = new HTMLPanel("<div id=" + MENU + "></div>");
		weaveUIdiv.getElement().setId(WEAVE);

		/**
		 * Here each tab is contructed and added to the menu. Each tab is give a controller
		 * object which has a specific task, be it maniplauating the map object, loading tiles etc
		 */
		mapMenu = new Tab("Map", weaveUIdiv,new MapController());
		tileSheetMenu = new Tab("Tiles", weaveUIdiv,new TileSheetController(referenceToTheEditor));
		consoleMenu = new Tab("Console", weaveUIdiv,FLog.getWidget());

		
		/**
		 * Closs all open tabs button 
		 */
		final Label closeAll = new Label("X");
		closeAll.setStyleName("label");
		closeAll.getElement().setId("closeAll");
		closeAll.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				 closeAll();
			}
		});
		weaveUIdiv.add(closeAll, MENU);
		
		
		/**
		 * FPS label
		 */
		final Label fps = new Label("FPS");
		fps.setStyleName("label");
		fps.getElement().setId(FPS_COUNTER);
		weaveUIdiv.add(fps, MENU);
		
		/**
		 * Sets the default state of the weave UI to hidden and adds it to the HTML panel with id = insertId
		 */
		weaveUIdiv.setVisible(false);
		RootPanel.get(insertId).add(weaveUIdiv);

	}

	/**
	 * Close all tabs
	 */
	private void closeAll() {
		mapMenu.hide();
		tileSheetMenu.hide();
		consoleMenu.hide();
	};

	/**
	 * Switchs the weave UI from visible to hidden
	 */
	public void toggleDisplay() {
		if (weaveUIdiv.isVisible())
			weaveUIdiv.setVisible(false);
		else
			weaveUIdiv.setVisible(true);
	}

	/**
	 * Given an ID and content it inserts the content into element with provided
	 * ID To get a list of UI elements to update use UiElement.yourElementName.
	 * You should NOT raw string ID's.
	 * 
	 * @param id
	 * @param content
	 */
	public void updateElement(final String id, String content) {
		// checks first if the element with ID=id exists
		if (weaveUIdiv.getElementById(id) != null) {
			weaveUIdiv.getElementById(id).setInnerHTML(content);

		} else {

			FLog.warn("Update of UI element [" + id
					+ "] failed due to it been null.");
		}
	}


	/**
	 * On key press weave UI is displayed
	 * 
	 * @param event
	 */
	public void onKeyDown(KeyDownEvent event) {

		if (event.getNativeEvent().getKeyCode() == 49) {

			closeAll();
		}

	}

	/**
	 * HTML id attributes used to reference the DOM elements
	 */
	static public final String FPS_COUNTER = "fps";
	static public final String WEAVE = "weave";
	static public final String MENU = "menu";

}
