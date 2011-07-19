package ie.flax.flaxengine.client.gameobjects;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.Graphic.Sprite;
import ie.flax.flaxengine.client.Graphic.Sprite.AnimationState;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is a basic entity player, wich has W D A S controls for moving in a direction
 *
 */
public class Player extends FEntity {

	private int speed;
	
	/**
	 * Constructs all the data for you atm, just for testing. So it easy to create an entity
	 */
	public Player(FVector pos)
	{
		super((float)pos.x, (float)pos.y, 32, 64, new Sprite("http://www.allacrost.org/media/art/sprites_map_claudius.png", 32,64), "audio");
		speed = 10;		
		bind();
		
		FLog.trace(this + " was created ");		
	}

	private void bind()
	{
		RootPanel.get().addHandler( new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
			
				//TODO capitial, incase capts lock is on
				if(event.getCharCode() == 'w'){
						
						
						y -= speed;	
						
//						if(cam != null)
//							cam.incrementY(speed*-1);
						
						checkCurrentAnimationState(AnimationState.UP);
						sprite.nextFrame();
				}
				else if(event.getCharCode() == 'd'){
				    
						x += speed;		
						
//						if(cam != null)
//							cam.incrementX(speed);
						
					    checkCurrentAnimationState(AnimationState.RIGHT);				
						sprite.nextFrame();
				}
				else if(event.getCharCode() == 'a'){
					
						x -= speed;					
						checkCurrentAnimationState(AnimationState.LEFT);
						sprite.nextFrame();
					
				}
				else if(event.getCharCode() == 's'){
					
						y += speed;
						checkCurrentAnimationState(AnimationState.DOWN);
						sprite.nextFrame();
				}
				
			}
		}, KeyPressEvent.getType());
	}
	
	
	
	private void checkCurrentAnimationState(AnimationState state)
	{
		 if(sprite.getAnimationState() != state)
		 {
		    	sprite.setAnimationState(state);
		    	sprite.setCurrentFrame(0);
		  }
	}
	
	
	/**
	 * DO NOT USE THIS Constructor -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public Player(){
		
	}
	

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
