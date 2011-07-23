package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.user.client.ui.Widget;

public interface PreferencesView {
	Widget asWidget();
	
	public interface presenter
	{
		void setAutosave(boolean on);
	}
}
