package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.weave.view.ImageLibView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * This view consists of a list of items and a button.
 * @author Ciarán McCann
 *
 */
public class ImageLibViewImpl extends Composite implements ImageLibView{

	private static ImageLibViewImplUiBinder uiBinder = GWT.create(ImageLibViewImplUiBinder.class);
	private ImageLibView.presenter presenter;
	@UiField Button apply;
	@UiField Label title;
	@UiField ListBox collection;

	interface ImageLibViewImplUiBinder extends UiBinder<Widget, ImageLibViewImpl> {}

	public ImageLibViewImpl(ImageLibView.presenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));	
		this.presenter = presenter;
		
		apply.setVisible(false);
		title.setVisible(false);
	}

	@UiHandler("apply")
	void onApplyClick(ClickEvent event) {
		presenter.setImageToBeUsed(collection.getItemText(collection.getSelectedIndex()));
	}

	@Override
	public void addItem(String imageName) {		
		collection.addItem(imageName, imageName);
		
		apply.setVisible(true);
		title.setVisible(true);
	}

	@Override
	public void clear() {
		collection.clear();
	}
}
