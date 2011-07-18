package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.user.client.ui.Widget;

public interface WeaveView {
	
		void addToNorth(Widget widgetToInsert);
		void addToSouth(Widget widgetToInsert, String tabText);
		void addToEast(Widget widgetToInsert, String headerText);
		void addToSouthEastCorner(Widget widgetToInsert);
		void toggle();
		
		Widget getWidget();
}
