package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.FileHandle;
import ie.flax.flaxengine.client.weave.view.PreferencesView;
import ie.flax.flaxengine.client.weave.view.customwidgets.FWindow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class PreferencesViewImpl extends Composite implements PreferencesView {

	interface PreferencesViewImplUiBinder extends UiBinder<Widget, PreferencesViewImpl> {}
	private static PreferencesViewImplUiBinder uiBinder = GWT.create(PreferencesViewImplUiBinder.class);
	private PreferencesView.presenter presenter;
	@UiField CheckBox autosave;
	@UiField Button clearMaps;
	@UiField Button clearAll;

	public PreferencesViewImpl(PreferencesView.presenter p) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = p;
	}
	
	@UiHandler("autosave")
	void onAutosaveValueChange(ValueChangeEvent<Boolean> event) {
		presenter.setAutosave(event.getValue());
	}
	
	@UiHandler("clearMaps")
	void onClearMapsClick(ClickEvent event) {
		//TODO Carl: this needs to actually clear _all_ maps from local storage - that means
		//that local storage needs to contain more than one map too ;) -Carl
		final FWindow w = new FWindow("Confirm");
		w.add(new HTML("<p>Are you sure you want to clear all your maps from local storage?</p>"));
		HorizontalPanel hp = new HorizontalPanel();
		
		Button cnf = new Button("Confirm");
		cnf.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				presenter.removeMaps();
				w.close();
			}
		});
		Button ccl = new Button("Cancel");
		ccl.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				w.close();
			}
		});
		

		hp.setSpacing(5);
		hp.add(ccl);
		hp.add(cnf);
		
		w.add(hp);
		w.show();
	}
	
	@UiHandler("clearAll")
	void onClearAllClick(ClickEvent event) {
		final FWindow w = new FWindow("Confirm");
		w.add(new HTML("<p>Are you sure you want to clear everything related to the Flax Engine from local storage?</p>"));
	//	w.add(new HTML("<p>Be aware that this might delete save games, for example. Be very sure before hitting the button.</p>"));
		HorizontalPanel hp = new HorizontalPanel();
		
		Button cnf = new Button("Confirm");
		cnf.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				FileHandle.clearLocalStorage();
				presenter.clearLocalStorage();
				w.close();
			}
		});
		Button ccl = new Button("Cancel");
		ccl.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				w.close();
			}
		});
		
		hp.setSpacing(5);
		hp.add(ccl);
		hp.add(cnf);
		
		w.add(hp);
		w.show();
	}
}
