package ie.flax.flaxengine.client.weave.view.customwidgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FWindow extends DialogBox{

	private HorizontalPanel headerBar;
	private VerticalPanel mainPanel;
	
	
	public FWindow(String title)
	{
				
		headerBar = new HorizontalPanel();
		mainPanel = new VerticalPanel();		
		
		
		mainPanel.add(headerBar);
		mainPanel.setCellHeight(headerBar, "0px");
		mainPanel.setCellWidth(headerBar, "300px");
		
		mainPanel.add(new Button());
		
		this.add(mainPanel);
		this.setPopupPosition(200, 200);
		this.show();
		this.setText(title);
		
		
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		
		
	}
	
	@Override
	public void add(Widget widget)
	{
		mainPanel.add(widget);
	}
	
}
