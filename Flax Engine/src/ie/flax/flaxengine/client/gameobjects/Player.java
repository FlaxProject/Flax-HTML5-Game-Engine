package ie.flax.flaxengine.client.gameobjects;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.Sprite;
import ie.flax.flaxengine.client.Graphic.Sprite.AnimationState;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * This is a basic entity player, wich has W D A S controls for moving in a direction
 *
 */
public class Player extends FEntity {

	private transient int speed;
	
	/**
	 * Constructs all the data for you atm, just for testing. So it easy to create an entity
	 */
	public Player(FVector pos)
	{
		super((float)pos.x, (float)pos.y, 32, 64, new Sprite("http://flax.ie/test/s.png", 32,64), "audio");
		speed = 12;		
		bind();
		
		FLog.trace(this + " was created ");		
	}
	
	/**
	 * Default construct for serailizer
	 */
	@Deprecated	
	public Player(){
		bind();
		speed = 12;
	}
	

	/**
	 * Binds the key and touch events to the player entity. 
	 */
	private void bind()
	{
		RootPanel.get().addDomHandler( new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				

				y += speed;
				checkCurrentAnimationState(AnimationState.DOWN);
				sprite.nextFrame();
				
				if(cam != null)
				{
					if( cam.getY() >= ( cam.getWidth()/2 )   )
					{
						cam.incrementY(speed);						
					}
				}
				
			}
		}, TouchStartEvent.getType());
		
		
		
		RootPanel.get().addHandler( new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {

				
				if(event.getCharCode() == 'w' || event.getCharCode() == 'W' ){
						
						
						y -= speed;							
						checkCurrentAnimationState(AnimationState.UP);
						sprite.nextFrame();
						

						if(cam != null)
						{
							//FLog.error(" - " + ( y+height - cam.getY() )  + "  <  " + (cam.getHeight()/2));
							if( y+height - cam.getY()  < (cam.getHeight()/2) )
							{
								cam.incrementY(speed*-1);
							}
							
						}
						
				}
				else if(event.getCharCode() == 'd' || event.getCharCode() == 'D'){
				    
						x += speed;								
					    checkCurrentAnimationState(AnimationState.RIGHT);				
						sprite.nextFrame();
						
						if(cam != null)
						{
							cam.incrementX(speed);
						}
						
				}
				else if(event.getCharCode() == 'a' || event.getCharCode() == 'A'){
					
						x -= speed;					
						checkCurrentAnimationState(AnimationState.LEFT);
						sprite.nextFrame();
						
						if(cam != null)
						{
							cam.incrementX(speed*-1);
						}
					
				}
				else if(event.getCharCode() == 's' || event.getCharCode() == 'S'){
					
						y += speed;
						checkCurrentAnimationState(AnimationState.DOWN);
						sprite.nextFrame();

						//FLog.error(" - " + ( y+height - cam.getY() )  + "  >  " + cam.getHeight()/2);
						if( y+height - cam.getY()  > (cam.getHeight()/2) )
						{
							cam.incrementY(speed);
						}
				}
				
			}
		}, KeyPressEvent.getType());
	}
	
	
	/**
	 * Checks if the current animation state is the same as the one been passed in. If not it sets the 
	 * the one passed in to the current animation state and sets the currentFrame back to 0. To start from the start
	 * @param state
	 */
	private void checkCurrentAnimationState(AnimationState state)
	{
		 if(sprite.getAnimationState() != state)
		 {
		    	sprite.setAnimationState(state);
		    	sprite.setCurrentFrame(0);
		  }
	}

}
