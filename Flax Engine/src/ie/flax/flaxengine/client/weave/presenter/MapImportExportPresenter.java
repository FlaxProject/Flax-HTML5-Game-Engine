package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FileHandle;
import ie.flax.flaxengine.client.LzwCompression;
import ie.flax.flaxengine.client.exception.MapDataCorrupt;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MapImportExportView;
import ie.flax.flaxengine.client.weave.view.Impl.MapImportExportViewImpl;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class handles the Export and Import view in the editor. 
 * 
 * @author Ciaran McCann
 *
 */
public class MapImportExportPresenter extends AbstractPresenter implements MapImportExportView.presenter {

	private MapImportExportView display;
	private Weave model;
	private boolean compression;
	
	public MapImportExportPresenter(Weave model){
		this.display = new MapImportExportViewImpl(this);
		this.model = model;
		this.compression = true;
		
		               //Vpanel   //FWindow-Dialog
	 	//this.getView().getParent().getParent();	
	}
	
	
	/**
	 * On close of the export/import window this method should be called to clear the content
	 */
	public void clearData(){
		display.setData("");
	}
	

	@Override
	public void exportJSON() {		
			
		display.setData("This may take some time, please wait. Compressing.....");
		if(compression)
		{		
			String data = LzwCompression.compress(FMap.toJson(model.getFMapReference())) ;
			display.setData( data );	
		}else{
			display.setData( FMap.toJson(model.getFMapReference()) );	
		}
	}


	@Override
	public void importJSON() {
					
		try {
			
			if(compression)
			{
				model.getFMapReference().replaceMap(FMap.fromJson( LzwCompression.decompress( display.getData()  )));
			}else{
				model.getFMapReference().replaceMap(FMap.fromJson( display.getData()));
			}		  
			
		} catch (MapDataCorrupt e) {
			Window.alert(e.getError());
		}
	}
	
	

	@Override
	public Widget getView() {
		clearData();
		return display.asWidget();
	}


	/**
	 * Turns the compression of the map format on or off
	 */
	@Override
	public void toggleCompression() {		
		compression = !compression;		
	}


	@Override
	public void loadFromLocalStorage() {

		FMap map = null;
		String jsonData = null;
		
		if(compression)
			jsonData =   LzwCompression.decompress( FileHandle.readStringFromLocalStorage("map") );
		else
			jsonData = FileHandle.readStringFromLocalStorage("map");
		
		
			try {
				map = (FMap.fromJson(jsonData));				
			} catch (MapDataCorrupt e) {				
				Window.alert(e.getError());
			}	
			
			model.getFMapReference().replaceMap(map);
			display.setData("Loaded from local storage sucessfully");
	}


	@Override
	public void saveToLocalStorage() {
							
		if(compression)
		{		
			FileHandle.writeStringToLocalStorage("map", LzwCompression.compress(FMap.toJson(model.getFMapReference())) );	
			display.setData("Saved to local storage sucessfully");
			
		}else{
			FileHandle.writeStringToLocalStorage("map",FMap.toJson(model.getFMapReference()));
		}
			
	}
	
}
