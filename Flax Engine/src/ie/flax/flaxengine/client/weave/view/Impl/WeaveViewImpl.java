package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.weave.view.WeaveView;
import ie.flax.flaxengine.client.weave.view.animation.AnimationSlide;
import ie.flax.flaxengine.client.weave.view.customwidgets.FWindow;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WeaveViewImpl implements WeaveView{
	
	private static final int AnimationTime = 300;
	private SimplePanel northPanel;
	private TabPanel southPanel;
	private VerticalPanel eastPanel;	
	private StackLayoutPanel eastStackPanel;	
	private AnimationSlide northAnimate, southAnimate, eastAnimate;		
	private enum State {SHOW,HIDDEN}
	
	private State currentViewState;
	
	private final int EAST_PERCENT_WIDTH = 20;
	private final int EAST_PERCENT_HEIGHT = 100;
	private final int SOUTH_PERCENT_WIDTH = 100 - EAST_PERCENT_WIDTH;
	private final int SOUTH_PERCENT_HEIGHT = 15;
	
	
	//FIXME there is a problem with the canvas been overlapping the panels made here
	// I fixed it using a CSS position absolute. Though might want to make the game canvas resize
	// when the editor is involved.
	public WeaveViewImpl()
	{
		
		currentViewState = State.HIDDEN;
				
		northPanel = new SimplePanel();		
		northPanel.setWidth(Window.getClientWidth()+"px");
		RootPanel.get().add(northPanel, 0, -44 );
		
		eastStackPanel = new StackLayoutPanel(Unit.PX);
		eastStackPanel.setAnimationDuration(AnimationTime);		
		eastStackPanel.setWidth("100%");
		eastStackPanel.setHeight("100%");
	
		
		eastPanel = new VerticalPanel(); 
		eastPanel.setWidth(EAST_PERCENT_WIDTH + "%");
		eastPanel.setHeight(EAST_PERCENT_HEIGHT+ "%");
		eastPanel.add(eastStackPanel);
		eastPanel.setCellHeight(eastStackPanel, "80%");
		RootPanel.get().add(eastPanel, Window.getClientWidth(), 0);
				
		
				
		southPanel = new TabPanel();
		//southPanel.setWidth( (Window.getClientWidth()- eastPanel.getOffsetWidth()) +"px");
		southPanel.setWidth(SOUTH_PERCENT_WIDTH+"%");
		southPanel.setHeight(SOUTH_PERCENT_HEIGHT+"%");
		southPanel.setAnimationEnabled(true);
	
		RootPanel.get().add(southPanel, 0, Window.getClientHeight()+southPanel.getOffsetHeight());
		
		
		northAnimate = new AnimationSlide(northPanel.getElement());
		southAnimate = new AnimationSlide(southPanel.getElement());
		eastAnimate = new AnimationSlide(eastPanel.getElement());
		
		
		FLog.debug("WeaveView was constructed successfully");

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
		eastStackPanel.add(widgetToInsert, headerText, 30);
	}
	
	@Override
	public void addToSouthEastCornor(Widget widgetToInsert) {
		
			eastPanel.add(widgetToInsert);
			eastPanel.setCellHeight(widgetToInsert, "20%");
	}


	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return null;
	}
}
