package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.weave.view.PreferencesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PreferencesViewImpl extends Composite implements PreferencesView {

	interface PreferencesViewImplUiBinder extends UiBinder<Widget, PreferencesViewImpl> {}
	private static PreferencesViewImplUiBinder uiBinder = GWT.create(PreferencesViewImplUiBinder.class);
	private PreferencesView.presenter presenter;
	@UiField Button autosave;


	public PreferencesViewImpl(PreferencesView.presenter p) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = p;
	}
	
	@UiHandler("autosave")
	public void onAutosavePress(ClickEvent event) {
		if (autosave.getText() == "Autosave is off") {
			//switch on autosave
			presenter.setAutosave(true);
			autosave.setText("Autosave is ON");
		} else if (autosave.getText() == "Autosave is ON") {
			//switch off autosave
			presenter.setAutosave(false);
			autosave.setText("Autosave is off");
		}
	}
}
