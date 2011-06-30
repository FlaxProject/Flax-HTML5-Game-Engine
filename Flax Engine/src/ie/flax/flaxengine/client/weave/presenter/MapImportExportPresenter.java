package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.weave.Weave;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class handles the Export and Import view in the editor. 
 * 
 * @author Ciarán McCann
 *
 */
public class MapImportExportPresenter extends AbstractPresenter{

	private Display display;
	private Weave model;
	
	public MapImportExportPresenter(Display view, Weave model)
	{
		this.display = view;
		this.model = model;
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
					
				display.setMapDataString(FMap.toJson(model.getFMapReference()));
			}
		});
		
		display.getImportButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			
				if (display.getMapDataString() != null) {
										
					//TODO Create copy constructor for the FMap class and remove the replaceMap function
					   model.getFMapReference().replaceMap( FMap.fromJson(display.getMapDataString()));
					  			  
					
					//FileHandle.writeStringToFile("map.json",display.getMapDataString(), "UiSaveLoadObj");
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
