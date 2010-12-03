package ie.flax.flaxengine.client.weave;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;


public class Weave {
	
	private HTMLPanel bottomPanel;
	private String insertId;
	
	/**
	 * This construct takes in the width and height of the canvas. It then inserts the panel of into the element 
	 * which you have provided the ID to.
	 * @param insertID
	 * @param width
	 * @param height
	 */
	public Weave(String insertID, int width, int height)
	{
		bottomPanel = new HTMLPanel("<div id=\"weavebottomPanel\" class=\"weaveHide\" style=\"width:"+ width +"px;height:" + 90 + "px;\">" +
				"<div id=\"fps\">" +
				"<p class=\"header\">FPS</p><h2 id=\"fpscount\"></h2></div>" +
				"<div id=\"log\"><p class=\"header\">Logger</p></div>  " +
				"</div>");
		
		this.insertId = insertID;	
		RootPanel.get(insertId).add(bottomPanel);
	}
	
	/**
	 * Displays the UI
	 */
	public void show()
	{
		RootPanel.get(UiElement.WEAVE_UI).setStylePrimaryName("weaveShow");
	}
	
	/**
	 * Displays the UI
	 */
	public void hide()
	{
		RootPanel.get(UiElement.WEAVE_UI).setStylePrimaryName("weaveHide");
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
		if (RootPanel.get(id) != null) {
			RootPanel.get(id).clear();
			RootPanel.get(id).add(new HTMLPanel(content));//Inserts the content
		} else {

			Log.warn("Update of UI element [" + id + "] failed due to it been null.");
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
		if (RootPanel.get(id) != null) {
			RootPanel.get(id).add(new HTMLPanel(content));//Inserts the content
		} else {

			Log.warn("Update of UI element [" + id + "] failed due to it been null.");
		}
	}
   
   

}
