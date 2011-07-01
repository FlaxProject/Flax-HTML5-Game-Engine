package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.user.client.ui.Widget;

public interface CreateEntityView {
	
	void setImage(String Path);
	Widget asWidget();
	
	public interface presenter
	{
		void setEntityType(String entity);
	}

}
