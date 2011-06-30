package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.weave.view.WeaveView;
import ie.flax.flaxengine.client.weave.view.animation.AnimationSlide;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class WeaveViewImpl implements WeaveView{
	
	private static final int AnimationTime = 300;
	private SimplePanel northPanel;
	private TabPanel southPanel;
	private StackLayoutPanel eastPanel;	
	private AnimationSlide northAnimate, southAnimate, eastAnimate;		
	private enum State {SHOW,HIDDEN}	
	
	private State currentViewState;
	
	
	//FIXME there is a problem with the canvas been overlapping the panels made here
	// I fixed it using a CSS position absolute. Though might want to make the game canvas resize
	// when the editor is involved.
	public WeaveViewImpl()
	{
		currentViewState = State.HIDDEN;
				
		northPanel = new SimplePanel();		
		northPanel.setWidth(Window.getClientWidth()+"px");
		RootPanel.get().add(northPanel, 0, -44 );
		
		eastPanel = new StackLayoutPanel(Unit.PC); 
		eastPanel.setWidth("200px");
		eastPanel.setAnimationDuration(AnimationTime);
		eastPanel.setHeight(Window.getClientHeight()+"px");
		eastPanel.setHeight(Window.getClientHeight()+"px");
		
		//TODO Remove code - only for demo look
		eastPanel.add(new Label("Widget - 1"), "Create Entity Type", 2);
		eastPanel.add(new Label("Widget - 2"), "Entity Type List", 2);
		eastPanel.add(new Label("Widget - 3"), "Entity List", 2);
		
		RootPanel.get().add(eastPanel, Window.getClientWidth(), 0);
		
				
		southPanel = new TabPanel();
		southPanel.setWidth( (Window.getClientWidth()- eastPanel.getOffsetWidth()) +"px");
		southPanel.setHeight("140px");
		RootPanel.get().add(southPanel, 0, Window.getClientHeight()+southPanel.getOffsetHeight());
		
		
		northAnimate = new AnimationSlide(northPanel.getElement());
		southAnimate = new AnimationSlide(southPanel.getElement());
		eastAnimate = new AnimationSlide(eastPanel.getElement());
		
		
		FLog.debug("WeaveView was constructed sucessfully");

	}


	@Override
	public void toggle() {
		
		if(currentViewState == State.SHOW)
		{
			//hidding code
			northAnimate.slideTo(0, northPanel.getOffsetHeight()*-1, AnimationTime);
			southAnimate.slideTo(0, Window.getClientHeight(), AnimationTime);
			eastAnimate.slideTo(Window.getClientWidth()+eastPanel.getOffsetWidth(), 0, AnimationTime);
			currentViewState = State.HIDDEN;
			
		}else{
			//showing code
			
			northAnimate.slideTo(0, 0, AnimationTime);
			southAnimate.slideTo(0, Window.getClientHeight()-southPanel.getOffsetHeight(), AnimationTime);
			eastAnimate.slideTo(Window.getClientWidth()-eastPanel.getOffsetWidth(), 0, AnimationTime);
			currentViewState = State.SHOW;			
		}		
	}


	@Override
	public void addToNorth(Widget widgetToInsert) {
		northPanel.clear();
		northPanel.add(widgetToInsert);		
	}


	@Override
	public void addToSouth(Widget widgetToInsert, String tabText) {

		southPanel.add(widgetToInsert, tabText);	
		if(southPanel.getWidgetCount() == 1);
		{
			southPanel.selectTab(0);
		}
		
	}


	@Override
	public void addToEast(Widget widgetToInsert, String headerText) {
		eastPanel.add(widgetToInsert, headerText, 2);
	}


	
	
	

}
