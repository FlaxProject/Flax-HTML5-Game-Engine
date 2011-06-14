package ie.flax.flaxengine.client.weave.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TileMenuPresenter extends AbstractPresenter{

	private Display display;
		
	public interface Display {
		HasClickHandlers getTileCanvas();	
		Widget asWidget();
	}
	
	
	public TileMenuPresenter(Display display)
	{
		this.display = display;
	}
	
	
	@Override
	public void bind() {
		
		display.getTileCanvas().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Click on canvas");
				
			}
		});
		
	}

	@Override
	public void go() {
		// TODO Auto-generated method stub
		
	}

}
