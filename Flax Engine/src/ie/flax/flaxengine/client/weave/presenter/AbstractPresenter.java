package ie.flax.flaxengine.client.weave.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * This is base Presenter which all presenter must implementent
 * @author Ciarán McCann
 *
 */
public abstract class AbstractPresenter {
	
	/**
	 * This bind method will be phased out as the MVP model been used is now bi-directional
	 */
	public abstract void bind();
	
	/**
	 * The widget passed in will be the widget which the view is inserted into
	 * @param containerElement
	 */
	public abstract void go(final HasWidgets containerElement);

}
