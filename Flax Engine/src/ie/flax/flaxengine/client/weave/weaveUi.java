package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.Graphic;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Lists the ID's of elements in the UI of weave which allows the HTML to be moddifed, without braking code
 * @author Ciarán McCann
 *
 */
public class weaveUi {
	

	public HTMLPanel weave;
	private UiClientFileLoader tileSheetUploader;
	private String insertId;
	private HTMLPanel consoleTab;
	private HTMLPanel tileSheetTab;
	private UiSaveLoadPanel loadSaveTab;
	private UiSaveLoadPanel saveLoadPanel;
	

	
	public weaveUi(String insertID)
	{
		
		/**
		 * Setups the image uploader and the canvas for the tilehsheet that was loaded
		 */
		tileSheetUploader = new UiClientFileLoader("Load Image");	
		Graphic.createCanvas("Weave");	
		
		
		
		weave = new HTMLPanel("<div id=menu></div>");
		weave.getElement().setId(WEAVE);
		
		/**
		 * Construct menu lable styles
		 */
		final Label consoleTabLabel = new Label("Console");
		final Label closetabLabel = new Label("Close All");	
		final Label tileSheetLabel = new Label("Tiles");
		final Label loadSaveLabel = new Label("Map");
		
		/**
		 * Set menu lable
		 */
		consoleTabLabel.setStyleName("label");
		closetabLabel.setStyleName("label");
		tileSheetLabel.setStyleName("label");
		loadSaveLabel.setStyleName("label");
		
		/**
		 * Add labesl to menu Bars
		 */
		weave.add(closetabLabel, "menu");
		weave.add(consoleTabLabel, "menu");
		weave.add(tileSheetLabel, "menu");
		weave.add(loadSaveLabel, "menu");
		
		/**
		 * Add onClick functionality to labels
		 */
		consoleTabLabel.addClickHandler( new ClickHandler() {		
			@Override
			public void onClick(ClickEvent event) {
				
				show("consoleTab");				
			}
		});
		
		
		loadSaveLabel.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				show("loadSaveTab");
				
			}
		});
		
		tileSheetLabel.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				show("tileSheetTab");	
				
			}
		});
		
		closetabLabel.addClickHandler( new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				closeAll();				
			}
		});
			
		
		/**
		 * Create Tab content, style and ID it and then add it to the weave panel
		 * Console Panel which has the GWT logger in it
		 */
		consoleTab = new HTMLPanel("");
		consoleTab.getElement().setId(CONSOLE_TAB);
		consoleTab.add(FLog.getWidget(), CONSOLE_TAB);
		consoleTab.setStyleName(HIDE_TAB);
		weave.add(consoleTab, WEAVE);
	
		/**
		 * FPS label
		 */
		final Label fps = new Label("FPS");
		fps.setStyleName("label");
		fps.getElement().setId("fps");
		weave.add(fps, "menu");
		
		/**
		 * Sets the tile sheet uploader tab
		 */
		tileSheetTab = (tileSheetUploader.getElement());
		tileSheetTab.getElement().setId(TILES_TAB);
		tileSheetTab.add(Graphic.getCanvas("Weave"), TILES_TAB);
		tileSheetTab.setStyleName(HIDE_TAB);
		weave.add(tileSheetTab,WEAVE);
		
		/**
		 * Export to JSON
		 */
		loadSaveTab = new UiSaveLoadPanel();
		weave.add(loadSaveTab.getElement(), WEAVE);
		
		
		
		this.insertId = insertID;	
		weave.setVisible(false);
		RootPanel.get(insertId).add(weave);
		
		
	}
	
	
	 
     public native void show(String tab)
     /*-{
             
             var arrayOfelemnets = $doc.getElementsByTagName("div")
     
     for( var i = 0; i < arrayOfelemnets.length; i++)
     {
                     if(arrayOfelemnets[i].className == 'tabShow')
                     arrayOfelemnets[i].className = 'tabHide';
     
     }
     //TODO: Rewrite in GWT code - be faster
     
     if($doc.getElementById(tab).className == 'tabHide')
     $doc.getElementById(tab).className = 'tabShow';
     else
     $doc.getElementById(tab).className = 'tabHide';
     }-*/;
	
	
	/**
	 * Close all tabs
	 */
	public void closeAll()
	{
		consoleTab.setStyleName(HIDE_TAB);
		tileSheetTab.setStyleName(HIDE_TAB);
		loadSaveTab.getElement().setStyleName(HIDE_TAB);
				
	};

	/**
	 * Switchs the weave UI from visible to hidden
	 */
	public void toggleDisplay()
	{
		if(weave.isVisible())
			weave.setVisible(false);
		else
			weave.setVisible(true);
	}
	
	
	
	
	/**
	 * Given an ID and content it inserts the content into element with provided ID
	 * To get a list of UI elements to update use UiElement.yourElementName. You should NOT raw string ID's. 
	 * @param id
	 * @param content
	 */
   public void updateElement(final String id,String content)
	{
	   //checks first if the element with ID=id exists
		if (weave.getElementById(id) != null) {
			weave.getElementById(id).setInnerHTML(content);

		} else {

			FLog.warn("Update of UI element [" + id + "] failed due to it been null.");
		}
	}
	
   
   /**
	 * Given an ID and content it appends the content into element with provided ID
	 * To get a list of UI elements to update use UiElement.yourElementName. You should NOT raw string ID's. 
	 * @param id
	 * @param content
	 */
  public void appendElement(final String id,String content)
	{
	   //checks first if the element with ID=id exists
		if (weave.getElementById(id) != null) {
			weave.getElementById(id).setInnerHTML(content);//Inserts the content
		} else {

			FLog.warn("Update of UI element [" + id + "] failed due to it been null.");
		}
	}
  
  /**
   * Given an ID it inserts a widget 
   * To get a list of UI elements to update use UiElement.yourElementName. You should NOT raw string ID's. 
   * @param UiElement
   * @param widget
   */
   public void insertWidget(final String UiElement, Widget widget)
   {
	   //checks first if the element with ID=id exists
		if (weave.getElementById(UiElement) != null) {
			weave.add(widget, UiElement);//Inserts the content
		} else {
			FLog.warn("Insert widget of UI element [" + UiElement + "] failed due to it been null.");
		}
   }
		
	
	
	/**
	 * Where the numbers for the FPS counter are displayed, in the bottom weave panel
	 */
	static public final String FPS_COUNTER_BOTTOM_PANEL = "fps"; 
	
	/**
	 * Where the output for the logger is piped, in the bottom weave panel
	 */
	static public final String LOGGER = "consoleTab"; 
	
	/**
	 * The bottom panel in the weave UI
	 */
	static public final String WEAVE = "weave";	
	static public final String SHOW_TAB  = "tabShow";
	static public final String HIDE_TAB  = "tabHide";
	
	/**
	 * The ID reference for the consoleTab
	 */
	static public final String CONSOLE_TAB = "consoleTab";
	
	/**
	 * The ID reference for the tile tab
	 */
	static public final String TILES_TAB = "tileSheetTab";
	
	/**
	 * The ID reference for the import/export tabs
	 */
	static public final String MAP_TAB = "loadSaveTab";



	public void onKeyDown(KeyDownEvent event) {
	
		  if(event.getNativeEvent().getKeyCode() == 49)
		   {

			   closeAll();
		   }
		   
		   if(event.getNativeEvent().getKeyCode() == 50)
		   {

			   show(weaveUi.CONSOLE_TAB);
		   }
		   
		   if(event.getNativeEvent().getKeyCode() == 51)
		   {

			   show(weaveUi.TILES_TAB);
		   }
		
	}
}
