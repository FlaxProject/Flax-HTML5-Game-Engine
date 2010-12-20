package ie.flax.flaxengine.client.weave;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.Graphic;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FileUpload;

/**
 * Weave is the editor that allows the user to create maps.
 * @author Ciarán McCann
 *
 */
public class Weave {
	
	private FMap map;
	private HTMLPanel bottomPanel;
	private HTMLPanel verticalPanel;
	private UiClientFileLoader fileUpload;

	
	private String insertId;

	
	/**
	 * This construct takes in the width and height of the canvas. It then inserts the panel of into the element 
	 * which you have provided the ID to.
	 * @param insertID
	 * @param width
	 * @param height
	 */
	public Weave(FMap mapRef, String insertID, int width, int height)
	{
		map = mapRef; // stores references 
		fileUpload = new UiClientFileLoader("Load Image",map);		
		
		bottomPanel = new HTMLPanel(
				"<div id=weavebottomPanel class=weaveHide style=width:"+ width +"px;height:" + height + "px;>" +
				"<div id=fps>" +
				"<p class=header>FPS</p><h2 id=fpscount></h2></div>" +
				"<div id=log><p class=header>Logger</p></div>  " +
				"</div>" +
				"<div id=weaveVerticalPanel class=weaveHide style=width:"+ width +"px;height:" + height + "px;>" +
				"<canvas width="+ (width-300) +" height=" + height + " id=weaveTileSheet></canvas>" +
						"<div id=controler>"  + 					
						"</div>" +		
				"<div>"
		);
		
		
		bottomPanel.add(fileUpload.getElement(), "controler");
		
		//verticalPanel = new HTMLPanel(); 		
		//verticalPanel.setStyleName(UiElement.WEAVE_UI_VERTICAL_PANEL);	
		
		this.insertId = insertID;	
		RootPanel.get(insertId).add(bottomPanel);
		
		
		//RootPanel.get(insertId).add(verticalPanel);
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
   public void updateUIelement(final String id,String content)
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
  public void appendUIelement(final String id,String content)
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
   
   

}
