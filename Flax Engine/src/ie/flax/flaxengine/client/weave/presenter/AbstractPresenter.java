package ie.flax.flaxengine.client.weave.presenter;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is base Presenter which all presenter must implementent
 * @author Ciaran McCann
 *
 */
public abstract class AbstractPresenter {
	
	/**
	 * This method will get the view which the presenter manages and give it to you to inject
	 * @return
	 */
	public abstract Widget getView();
}
