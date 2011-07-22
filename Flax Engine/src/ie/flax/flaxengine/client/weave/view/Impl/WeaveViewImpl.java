package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.weave.view.WeaveView;
import ie.flax.flaxengine.client.weave.view.animation.AnimationFade;
import ie.flax.flaxengine.client.weave.view.animation.AnimationSlide;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WeaveViewImpl implements WeaveView{
	private enum AnimationStyle {FADE, SLIDE};
	private AnimationStyle animationStyle = AnimationStyle.FADE;
	
	private static final int AnimationTime = 300;
	private SimplePanel northPanel;
	private TabPanel southPanel;
	private VerticalPanel eastPanel;	
	private StackLayoutPanel eastStackPanel;
	
	private Animation northAnimate, southAnimate, eastAnimate;		
	
	private enum State {SHOW,HIDDEN}
	
	private State currentViewState;
	
	private final int EAST_PERCENT_WIDTH = 20;
	private final int EAST_PERCENT_HEIGHT = 100;
	private final int EAST_PERCENT_TOP_CELL_HEIGHT = 80;
	private final int SOUTHEAST_CORNER_PERCENT_CELL_HEIGHT = 100-EAST_PERCENT_TOP_CELL_HEIGHT;
	private final int SOUTH_PERCENT_WIDTH = 100 - EAST_PERCENT_WIDTH;
	private final int SOUTH_PERCENT_HEIGHT = 15;
	
	
	//FIXME there is a problem with the canvas been overlapping the panels made here
	// I fixed it using a CSS position absolute. Though might want to make the game canvas resize
	// when the editor is involved.
	public WeaveViewImpl() {
		
		currentViewState = State.HIDDEN;
				
		northPanel = new SimplePanel();		
		northPanel.setWidth("100%"/*FlaxEngine.settings.getWidth()+"px"*/);
		
		/*
		 * This adds the northpanel to the RootPanel.
		 * The positioning (0,-44) is in order to get the panel to slide from the top on toggle.
		 */
		//FlaxEngine.settings.getContainer().add(northPanel, 0, -44 );
		
		eastStackPanel = new StackLayoutPanel(Unit.PX);
		eastStackPanel.setAnimationDuration(AnimationTime);		
		eastStackPanel.setWidth("100%");
		eastStackPanel.setHeight("100%");
	
		
		eastPanel = new VerticalPanel(); 
		eastPanel.setWidth(EAST_PERCENT_WIDTH + "%");
		eastPanel.setHeight(EAST_PERCENT_HEIGHT+ "%");
		eastPanel.add(eastStackPanel);
		eastPanel.setCellHeight(eastStackPanel, EAST_PERCENT_TOP_CELL_HEIGHT+"%");
		//FlaxEngine.settings.getContainer().add(eastPanel, FlaxEngine.settings.getWidth(), 0);
		
		
		southPanel = new TabPanel();
		
		southPanel.setWidth(SOUTH_PERCENT_WIDTH+"%");
		southPanel.setHeight(SOUTH_PERCENT_HEIGHT+"%");
		southPanel.setAnimationEnabled(true);
		
		//FlaxEngine.settings.getContainer().add(southPanel, 0, FlaxEngine.settings.getHeight()+southPanel.getOffsetHeight());
		
		if (animationStyle == AnimationStyle.SLIDE) {
			FlaxEngine.settings.getContainer().add(northPanel, 0, -44 );
			FlaxEngine.settings.getContainer().add(southPanel, 0, FlaxEngine.settings.getHeight()+southPanel.getOffsetHeight());
			FlaxEngine.settings.getContainer().add(eastPanel, FlaxEngine.settings.getWidth(), 0);
			
			northAnimate = new AnimationSlide(northPanel.getElement());
			southAnimate = new AnimationSlide(southPanel.getElement());
			eastAnimate = new AnimationSlide(eastPanel.getElement());
		} else if (animationStyle == AnimationStyle.FADE) {
			/*
			 * TODO don't add these twice
			 */
			FlaxEngine.settings.getContainer().add(northPanel, 0,0);
			FlaxEngine.settings.getContainer().add(southPanel, 0, (FlaxEngine.settings.getHeight()-southPanel.getOffsetHeight()));
			FlaxEngine.settings.getContainer().add(southPanel, 0, (FlaxEngine.settings.getHeight()-southPanel.getOffsetHeight()));
			FlaxEngine.settings.getContainer().add(eastPanel, FlaxEngine.settings.getWidth()-eastPanel.getOffsetWidth(),0);
			FlaxEngine.settings.getContainer().add(eastPanel, FlaxEngine.settings.getWidth()-eastPanel.getOffsetWidth(),0);
			
			northAnimate = new AnimationFade(northPanel.getElement());
			southAnimate = new AnimationFade(southPanel.getElement());
			eastAnimate = new AnimationFade(eastPanel.getElement());
		}
		FLog.debug("WeaveView was constructed successfully");

	}


	@Override
	public void toggle() {
		
		if(currentViewState == State.SHOW)
		{
			//hiding code
			if (animationStyle == AnimationStyle.SLIDE) {
				((AnimationSlide) northAnimate).slideTo(0, northPanel.getOffsetHeight()*-1, AnimationTime);
				((AnimationSlide) southAnimate).slideTo(0, FlaxEngine.settings.getHeight(), AnimationTime);
				((AnimationSlide) eastAnimate).slideTo(FlaxEngine.settings.getWidth()+eastPanel.getOffsetWidth(), 0, AnimationTime);
			} else if (animationStyle == AnimationStyle.FADE) {
				((AnimationFade) northAnimate).fadeOut(AnimationTime);
				((AnimationFade) southAnimate).fadeOut(AnimationTime);
				((AnimationFade) eastAnimate).fadeOut(AnimationTime);
			}
			currentViewState = State.HIDDEN;
			
		}else{
			
			//showing code
			if (animationStyle == AnimationStyle.SLIDE) {
				((AnimationSlide) northAnimate).slideTo(0, 0, AnimationTime);
				((AnimationSlide) southAnimate).slideTo(0, FlaxEngine.settings.getHeight()-southPanel.getOffsetHeight(), AnimationTime);
				((AnimationSlide) eastAnimate).slideTo(FlaxEngine.settings.getWidth()-eastPanel.getOffsetWidth(), 0, AnimationTime);
			} else if (animationStyle == AnimationStyle.FADE) {
				((AnimationFade) northAnimate).fadeIn(AnimationTime);
				((AnimationFade) southAnimate).fadeIn(AnimationTime);
				((AnimationFade) eastAnimate).fadeIn(AnimationTime);
			}
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
		if(southPanel.getWidgetCount() == 1) {
			southPanel.selectTab(0);
		}
	}

	@Override
	public void addToSouth(IsWidget widgetToInsert, Widget tabWidget) {

		southPanel.add(widgetToInsert, tabWidget);	
	}

	@Override
	public void addToEast(Widget widgetToInsert, String headerText) {
		eastStackPanel.add(widgetToInsert, headerText, 30); //make each stack title 30px tall
	}
	
	@Override
	public void addToSouthEastCorner(Widget widgetToInsert) {
			eastPanel.add(widgetToInsert);
			eastPanel.setCellHeight(widgetToInsert, SOUTHEAST_CORNER_PERCENT_CELL_HEIGHT+"%");
	}


	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return null;
	}
}
