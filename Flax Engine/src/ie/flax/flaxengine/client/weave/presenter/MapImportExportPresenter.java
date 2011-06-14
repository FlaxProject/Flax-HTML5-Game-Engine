package ie.flax.flaxengine.client.weave.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;

public class MapImportExportPresenter extends AbstractPresenter{

	private Display display;
	
	public MapImportExportPresenter(Display view)
	{
		this.display = view;
	}
	
	
	public interface Display 
	{
		HasClickHandlers getExportButton();
		HasClickHandlers getImportButton();
		String getMapDataString();
	}
		
	
	@Override
	public void bind() {
		
		display.getExportButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Export Button");
				
			}
		});
		
		display.getImportButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Import");
				
			}
		});
		
	}

	@Override
	public void go() {
		// TODO Auto-generated method stub
		
	}

	
}
