package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.user.client.ui.Widget;


public interface MapImportExportView 
{
	void setData(String data);
	String getData();
	Widget asWidget();
	
	public interface presenter
	{	
		void exportJSON();
		void importJSON();
		void loadFromLocalStorage();
		void saveToLocalStorage();
		void toggleCompression();
	}

}
