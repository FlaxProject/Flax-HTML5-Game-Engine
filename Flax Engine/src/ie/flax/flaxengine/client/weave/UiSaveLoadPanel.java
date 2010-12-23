package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FileHandle;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * Cotians the load and save tab panel of weave and all the elements that make up that system.
 * Handles all the html and user input for the tab
 * @author Ciarán McCann
 *
 */
public class UiSaveLoadPanel {

	private Button load;
	private Button save;
	private Button saveToServer;
	private TextArea jsonData;
	private HTMLPanel tabDiv;
	
	/**
	 * Does most of the work constructing html and adding event handlers to buttons
	 */
	public UiSaveLoadPanel()
	{
		tabDiv = new HTMLPanel("");
		tabDiv.getElement().setId(ELEMENT_ID);
		tabDiv.setStyleName(weaveUi.HIDE_TAB);
		
		load = new Button("Import Map");
		save = new Button("Export Map");
		saveToServer = new Button("Save to server");
		saveToServer.setTitle("This overrides the current map file - only temp functionality");
		
		jsonData = new TextArea();
		jsonData.setStyleName(STYLE);
		
		/**
		 * Defines fucnitonality when load is clicked
		 */
		load.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(jsonData.getValue() != null)
				{
					
					FMap map = Weave.getFMapReference();
					map = Weave.getFMapReference().JsonToFMap(jsonData.getValue());
				}
				else
				{
					jsonData.setValue("You need to put a JSON map string into this textarea before you can load it into the engine!");
				}
				
			}
		});
		
		
		/**
		 * Defines the functionality when save if clicked
		 */
		save.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				jsonData.setValue(Weave.getFMapReference().FMapToJson());
			}
		});
		
		
	
		/**
		 * When a user clicks on the save to server button
		 */
		saveToServer.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					
				if(jsonData.getValue() != null)
				{
					FileHandle.writeStringToFile("map.json", jsonData.getValue(),"UiSaveLoadObj");	
				}
				else
				{
					jsonData.setValue("You need to put a JSON map string into this textarea before you can load it into the engine!");
				}
				
			}
		});
		
		jsonData.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				jsonData.selectAll();
				
			}
		});
		
		
		tabDiv.add(load,ELEMENT_ID);
		tabDiv.add(save, ELEMENT_ID);
		tabDiv.add(saveToServer, ELEMENT_ID);
		tabDiv.add(jsonData, ELEMENT_ID);
		
	}
	
	/**
	 * Gets the element so it can be added to a widget panel
	 * @return
	 */
	public HTMLPanel getElement()
	{
		return tabDiv;
	}
	
	private final String STYLE = "mapJsonTextarea";
	private final String ELEMENT_ID = "loadSaveTab";

	
}


