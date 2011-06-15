package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.weave.presenter.*;
import ie.flax.flaxengine.client.weave.view.animation.AnimationSlide;

public class WeaveView implements WeavePresenter.Display{
	
	private static final int AnimationTime = 300;
	private static final String MAIN_PANEL_STYLE = "weavePanel";
	private ScrollPanel northPanel;
	private SimplePanel southPanel;
	private SimplePanel eastPanel;	
	private AnimationSlide northAnimate, southAnimate, eastAnimate;		
	private enum State {SHOW,HIDDEN}	
	
	private State currentViewState;
	
	
	
	public WeaveView()
	{
		currentViewState = State.HIDDEN;
				
		northPanel = new ScrollPanel();		

		northPanel.setStylePrimaryName(MAIN_PANEL_STYLE);
		northPanel.setWidth(Window.getClientWidth()+"px");
		FLog.debug(northPanel.getOffsetHeight()+"H");
		RootPanel.get().add(northPanel, 0, -44 );
		
		eastPanel = new SimplePanel();
		eastPanel.setStylePrimaryName(MAIN_PANEL_STYLE);
		eastPanel.setWidth("200px");
		eastPanel.setHeight(Window.getClientHeight()+"px");
		//eastPanel.add(new HTML("east Panel"));
		RootPanel.get().add(eastPanel, Window.getClientWidth(), 0);
				
		southPanel = new SimplePanel();
		//DOM.setIntStyleAttribute(southPanel.getElement(), "zIndex", 500);
		southPanel.setStylePrimaryName(MAIN_PANEL_STYLE);
		southPanel.setWidth(Window.getClientWidth()+"px");
		//southPanel.add(new HTML("south Panel"));
		southPanel.setHeight("100px");
		RootPanel.get().add(southPanel, 0, Window.getClientHeight()+southPanel.getOffsetHeight());
		
		
		northAnimate = new AnimationSlide(northPanel.getElement());
		southAnimate = new AnimationSlide(southPanel.getElement());
		eastAnimate = new AnimationSlide(eastPanel.getElement());

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
	public HasWidgets getNorth() {
		return northPanel;
	}


	@Override
	public HasWidgets getSouth() {
		return southPanel;
	}


	@Override
	public HasWidgets getEast() {
		return eastPanel;
	}



}
