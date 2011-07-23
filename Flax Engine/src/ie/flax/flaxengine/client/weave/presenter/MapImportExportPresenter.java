package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MapImportExportView;
import ie.flax.flaxengine.client.weave.view.Impl.MapImportExportViewImpl;

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
	
	public MapImportExportPresenter(Weave model)
	{
		this.display = new MapImportExportViewImpl(this);
		this.model = model;
		
		                      //Vpanel   //FWindow-Dialog
		//TODO 	this.getView().getParent().getParent().setVisible(false);
		
	}
	
	/**
	 * On close of the export/import window this method should be called to clear the content
	 */
	public void clearData()
	{
		display.setData("");
	}
	

	@Override
	public void exportJSON() {		
		display.setData(FMap.toJson(model.getFMapReference()));	
	}


	@Override
	public void importJSON() {
		
		if (display.getData() != null) {

			// TODO Create copy constructor for the FMap class and remove the replaceMap function
			model.getFMapReference().replaceMap(FMap.fromJson(display.getData()));

		} else {
			display.setData("You need to put a JSON map string into this textarea before you can load it into the engine!");
		}
	}
	
	

	@Override
	public Widget getView() {
		return display.asWidget();
	}
	
}
