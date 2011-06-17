package ie.flax.flaxengine.client.weave.view;

import ie.flax.flaxengine.client.weave.presenter.ImageLibaryPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class ImageLibaryView extends Composite implements ImageLibaryPresenter.Display {

	private static ImageLibaryViewUiBinder uiBinder = GWT
			.create(ImageLibaryViewUiBinder.class);
	@UiField Tree listOfLoadedImages;

	interface ImageLibaryViewUiBinder extends UiBinder<Widget, ImageLibaryView> {
	}

	public ImageLibaryView() {
		initWidget(uiBinder.createAndBindUi(this));	
		listOfLoadedImages.setAnimationEnabled(true);		
	}

	@Override
	public void addTreeItem(TreeItem item) {
		listOfLoadedImages.addItem(item);
		
	}

	@Override
	public Widget asWidget()
	{
		return this;
	}


}
