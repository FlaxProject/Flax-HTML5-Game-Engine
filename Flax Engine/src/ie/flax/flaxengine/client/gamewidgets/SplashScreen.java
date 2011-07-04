package ie.flax.flaxengine.client.gamewidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class SplashScreen extends Composite{

	private static SplashScreenUiBinder uiBinder = GWT
			.create(SplashScreenUiBinder.class);

	interface SplashScreenUiBinder extends UiBinder<Widget, SplashScreen> {
	}

	public SplashScreen() {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
