package ie.flax.flaxengine.client.weave;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Weave {
	
	private HTMLPanel bottomPanel;
	private String insertId;
	
	public Weave(String insertID, int width, int height)
	{
		bottomPanel = new HTMLPanel("<div id=\"weavebottomPanel\" style=\"width:"+ width +"px;height:" + 90 + "px;\"><div id=\"fps\"><p class=\"header\">FPS</p></div>" +
				"<div id=\"log\"><p class=\"header\">Logger</p></div>  " +
				"</div>");
		this.insertId = insertID;
	}
	
	public void display()
	{
		RootPanel.get(insertId).add(bottomPanel);
	}
	

}
