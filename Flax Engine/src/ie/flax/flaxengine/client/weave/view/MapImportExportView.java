package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;

import ie.flax.flaxengine.client.weave.presenter.*;

public class MapImportExportView extends Composite implements MapImportExportPresenter.Display{

	private static MapImportExportViewUiBinder uiBinder = GWT
			.create(MapImportExportViewUiBinder.class);
	@UiField Button buttonExport;
	@UiField Button buttonImport;
	@UiField TextArea data;

	interface MapImportExportViewUiBinder extends
			UiBinder<Widget, MapImportExportView> {
	}

	public MapImportExportView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getExportButton() {
		return buttonExport;
	}

	@Override
	public HasClickHandlers getImportButton() {
		return buttonImport;
	}

	@Override
	public String getMapDataString() {
		return data.getText();
	}

	@Override
	public void setMapDataString(String msg) {
		data.setText(msg);
		
	}

}
