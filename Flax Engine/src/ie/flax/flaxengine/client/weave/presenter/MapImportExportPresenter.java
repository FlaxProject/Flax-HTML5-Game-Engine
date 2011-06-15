package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FileHandle;
import ie.flax.flaxengine.client.Graphic;
import ie.flax.flaxengine.client.weave.Weave;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

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
		void setMapDataString(String msg);
		
		Widget asWidget();
	}
		
	
	@Override
	public void bind() {
		
		display.getExportButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					
				//Window.alert("Export Button");
				//display.setMapDataString(Weave.getFMapReference().toJson());
			}
		});
		
		display.getImportButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Import");

				if (display.getMapDataString() != null) {
					FileHandle.writeStringToFile("map.json",display.getMapDataString(), "UiSaveLoadObj");
				} else {
					display.setMapDataString("You need to put a JSON map string into this textarea before you can load it into the engine!");
				}
				
			}
		});
		
	}


	@Override
	public void go(HasWidgets containerElement) {
		bind();
		containerElement.add(display.asWidget());
		
	}

	
}
