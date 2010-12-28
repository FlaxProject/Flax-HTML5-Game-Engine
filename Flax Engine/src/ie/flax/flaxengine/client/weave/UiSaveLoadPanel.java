package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FObject;
import ie.flax.flaxengine.client.FTile;
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

	private Button newMap;
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
		tabDiv = new HTMLPanel("<fieldset id=new><legend>New Map:</legend>" +
				"<label>Name</label><input type=text value=mymap.json id=name ></input>" +
				"<label>Tilesize</label><input type=text id=tileSize value=32></input>" +
				"<lable>Unit Width</label><input type=text id=width value=10></input>" +		
				"<lable>Unit Height</label><input type=text id=height value=10></input> " +	
				"</fieldset><br>" +
				"<fieldset id=load><legend>Export/Import Maps</legend>"
				
		);
		tabDiv.getElement().setId(ELEMENT_ID);
		tabDiv.setStyleName(weaveUi.HIDE_TAB);
		
		newMap = new Button("Create new map");
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
				
				if(jsonData.getValue() != "")
				{		
					//Gets the current map and replaces it with the object form from the JSON in the textarea in the import tab
					Weave.getFMapReference().replaceMap(Weave.getFMapReference().JsonToFMap(jsonData.getValue()));
										
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
			//jsonData.selectAll();
				
			}
		});
		
		
		newMap.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				FMap tmp = Weave.getFMapReference();
				
				int tileSize = Integer.parseInt(tabDiv.getElementById("tileSize").getAttribute("value"));
				int width = Integer.parseInt(tabDiv.getElementById("width").getAttribute("value"));
				int height = Integer.parseInt(tabDiv.getElementById("height").getAttribute("value"));
				String name = tabDiv.getElementById("name").getAttribute("value");
				
				/*
				tmp.setEntities(null);
				tmp.setTiles(null);
				tmp.setObjects(null);
				
				tmp.addEntity(new FEntity(0,0,10,10,null,null));
				tmp.addObjects( new FObject(0,0,10,10,null,null));
				tmp.addTile(new FTile(0,0,false, 0));
				*/
				
				tmp.setTileSize(tileSize);
				tmp.setHeight(height);
				tmp.setWidth(width);
				tmp.setName(name);
			}
		});
		
		tabDiv.add(newMap, "new");
		tabDiv.add(load,"load");
		tabDiv.add(save, "load");
		//tabDiv.add(saveToServer, ELEMENT_ID);
		tabDiv.add(jsonData, "load");
		
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


