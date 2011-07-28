package ie.flax.flaxengine.client.weave.view.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;

public class AnimationFade extends Animation{

	private final Element el;
	private boolean inOut; //and the award for least descriptive name goes to...
	
	public AnimationFade(Element element) {
        this.el = element;
    }
	
	public void fadeIn(int milliseconds) {
		inOut = true;
		run(milliseconds);
	}
	
	public void fadeOut(int milliseconds) {
		inOut = false;
		run(milliseconds);
	}
	
	@Override
	protected void onUpdate(double progress) {
		if (inOut) {
			el.getStyle().setOpacity(progress);
		} else {
			el.getStyle().setOpacity(1.0-progress);
		}
	}
	
	@Override
	protected void onStart() {
		if(inOut){
			el.getStyle().setZIndex(0); 
		}
		super.onStart();
	}

	 @Override
	protected void onComplete(){
		 if (!inOut) {
			 el.getStyle().setZIndex(-1);
		 }
		 
		 
		 super.onComplete();
	}
}
