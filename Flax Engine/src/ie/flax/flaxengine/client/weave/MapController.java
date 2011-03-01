package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FileHandle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Cotians the load and save tab panel of weave and all the elements that make up that system.
 * Handles all the html and user input for the tab
 * @author Ciarán McCann
 *
 */
public class MapController extends Controller {

	private Button newMap;
	private Button load;
	private Button save;
	private Button saveToServer;
	private TextArea jsonData;
	private HTMLPanel element;
	
	/**
	 * Does most of the work constructing html and adding event handlers to buttons
	 */
	public MapController()
	{
		element = new HTMLPanel("<fieldset id=new><legend>New Map:</legend>" +
				"<label>Name</label><input type=text value=mymap.json id=name ></input>" +
				"<label>Tilesize</label><input type=text id=tileSize value=32></input>" +
				"<lable>Unit Width</label><input type=text id=width value=10></input>" +		
				"<lable>Unit Height</label><input type=text id=height value=10></input> " +	
				"</fieldset><br>" +
				"<fieldset id=load><legend>Export/Import Maps</legend>"
				
		);
		element.getElement().setId(ELEMENT_ID);
		
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
					Weave.getFMapReference().replaceMap(Weave.getFMapReference().fromJson(jsonData.getValue()));
										
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
				jsonData.setValue(Weave.getFMapReference().toJson());
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
				
				int tileSize = Integer.parseInt(element.getElementById("tileSize").getAttribute("value"));
				int width = Integer.parseInt(element.getElementById("width").getAttribute("value"));
				int height = Integer.parseInt(element.getElementById("height").getAttribute("value"));
				String name = element.getElementById("name").getAttribute("value");
				
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
		
		element.add(newMap, "new");
		element.add(load,"load");
		element.add(save, "load");
		//tabDiv.add(saveToServer, ELEMENT_ID);
		element.add(jsonData, "load");
		
	}
	
	/**
	 * Gets the element so it can be added to a widget panel
	 * @return
	 */
	public HTMLPanel getHTML()
	{
		return element;
	}
	
	private final String STYLE = "mapJsonTextarea";
	private final String ELEMENT_ID = "loadSaveTab";

	
}


