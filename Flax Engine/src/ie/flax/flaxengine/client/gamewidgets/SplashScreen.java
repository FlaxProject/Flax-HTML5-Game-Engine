package ie.flax.flaxengine.client.gamewidgets;

import ie.flax.flaxengine.client.weave.UIStrings;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SplashScreen extends Composite{

	private static SplashScreenUiBinder uiBinder = GWT
			.create(SplashScreenUiBinder.class);

	interface SplashScreenUiBinder extends UiBinder<Widget, SplashScreen> {
	}
	
	@UiField Label load;

	public SplashScreen() {
		initWidget(uiBinder.createAndBindUi(this));
		load.setText(UIStrings.version + "  loading....");
	}
	



}
