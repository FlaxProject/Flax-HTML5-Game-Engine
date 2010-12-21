package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.Graphic;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Lists the ID's of elements in the UI of weave which allows the HTML to be moddifed, without braking code
 * @author ciaran
 *
 */
public class weaveUi {
	
	public HTMLPanel bottomPanel;
	public HTMLPanel verticalPanel;
	public UiClientFileLoader tileSheetUploader;
	private String insertId;
	
	public weaveUi(String insertID, int width, int height)
	{
		tileSheetUploader = new UiClientFileLoader("Load Image");	
		
		
		bottomPanel = new HTMLPanel(
				"<div id="+ WEAVE_UI_BOTTOM_PANEL + " class=weave style=width:"+ width +"px;height:" + height + "px;>" +
				"<div id=fps>" +
				"<p class=header>FPS</p><h2 id=fpscount></h2></div>" +
				"<div id=log><p class=header>Logger</p></div>  " +
				"</div>" +
				"<div id=" + WEAVE_UI_VERTICAL_PANEL + " class=weave style=width:"+ width +"px;>" +
				

				"<div>"
		);
		
		
		Graphic.createCanvas("Weave", width-300, height);		
		
		bottomPanel.add(tileSheetUploader.getElement(), WEAVE_UI_VERTICAL_PANEL);
		bottomPanel.add(Graphic.getCanvas("Weave"),WEAVE_UI_VERTICAL_PANEL);
		
		
		//verticalPanel = new HTMLPanel(); 		
		//verticalPanel.setStyleName(UiElement.WEAVE_UI_VERTICAL_PANEL);	
		
		this.insertId = insertID;	
		bottomPanel.setVisible(false);
		RootPanel.get(insertId).add(bottomPanel);
	}
	

	/**
	 * Switchs the weave UI from visible to hidden
	 */
	public void toggleDisplay()
	{
		if(bottomPanel.isVisible())
			bottomPanel.setVisible(false);
		else
			bottomPanel.setVisible(true);
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
		if (bottomPanel.getElementById(id) != null) {
			bottomPanel.getElementById(id).setInnerHTML(content);

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
		if (bottomPanel.getElementById(id) != null) {
			bottomPanel.getElementById(id).setInnerHTML(content);//Inserts the content
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
		if (bottomPanel.getElementById(UiElement) != null) {
			bottomPanel.add(widget, UiElement);//Inserts the content
		} else {

			FLog.warn("Insert widget of UI element [" + UiElement + "] failed due to it been null.");
		}
   }
		
	
	
	/**
	 * Where the numbers for the FPS counter are displayed, in the bottom weave panel
	 */
	static public final String FPS_COUNTER_BOTTOM_PANEL = "fpscount"; 
	
	/**
	 * Where the output for the logger is piped, in the bottom weave panel
	 */
	static public final String LOGGER_BOTTOM_PANEL = "log"; 
	
	/**
	 * The bottom panel in the weave UI
	 */
	static public final String WEAVE_UI_BOTTOM_PANEL = "weavebottomPanel";
	
	/**
	 * The vertical panel in the weave UI
	 */
	static public final String WEAVE_UI_VERTICAL_PANEL = "weaveVerticalPanel";

}
