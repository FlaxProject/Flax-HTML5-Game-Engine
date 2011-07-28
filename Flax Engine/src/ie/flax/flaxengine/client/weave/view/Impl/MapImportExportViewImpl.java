package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.weave.view.MapImportExportView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.ui.CheckBox;

public class MapImportExportViewImpl extends Composite implements MapImportExportView {
	
	private MapImportExportView.presenter presenter;
	@UiField Button buttonExport;
	@UiField Button buttonImport;
	@UiField TextArea data;
	@UiField Button saveToLocalStorage;
	@UiField Button loadFromLoadStorage;
	@UiField CheckBox compressed;

	private static MapImportExportViewImplUiBinder uiBinder = GWT.create(MapImportExportViewImplUiBinder.class);

	interface MapImportExportViewImplUiBinder extends
			UiBinder<Widget, MapImportExportViewImpl> {
	}

	public MapImportExportViewImpl(MapImportExportView.presenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}


	@UiHandler("buttonExport")
	void onButtonExportClick(ClickEvent event) {
		presenter.exportJSON();
		
	}
	@UiHandler("buttonImport")
	void onButtonImportClick(ClickEvent event) {
		presenter.importJSON();
	}


	@Override
	public void setData(String data) {
			this.data.setText(data);
	}


	@Override
	public String getData() {
		return this.data.getText();
	}
	
	
	@UiHandler("saveToLocalStorage")
	void onSaveToLocalStorageClick(ClickEvent event) {		
		presenter.saveToLocalStorage();
	}
	
	@UiHandler("loadFromLoadStorage")
	void onLoadFromLoadStorageClick(ClickEvent event) {
		presenter.loadFromLocalStorage();
	}
	
	
	@UiHandler("compressed")
	void onCompressedClick(ClickEvent event) {
		presenter.toggleCompression();
	}
}
