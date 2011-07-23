package ie.flax.flaxengine.client.gameobjects;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.Graphic.FCamera.Directoin;
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

	
	/**
	 * Constructs all the data for you atm, just for testing. So it easy to create an entity
	 */
	public Player(FVector pos)
	{
		super((float)pos.x, (float)pos.y, 32, 64, new Sprite("http://flax.ie/test/s.png", 32,64), "audio");
		speed = 4;		
		bind();
		
		FLog.trace(this + " was created ");		
	}
	
	/**
	 * Default construct for serailizer
	 */
	@Deprecated	
	public Player(){
		bind();
		speed = 4;
	}
	

	/**
	 * Binds the key and touch events to the player entity. 
	 */
	private void bind()
	{
		
		attachCamera(FlaxEngine.camera); //FIXME Ciaran remove after 0.2 when doing the entity system proper
		
		RootPanel.get().addDomHandler( new TouchStartHandler() {
			
			@Override
			public void onTouchStart(TouchStartEvent event) {
				

				y += speed;
				checkCurrentAnimationState(AnimationState.DOWN);
				sprite.nextFrame();
				
				if(cam != null)
				{
					
						cam.incrementY(speed);						
				}
				
			}
		}, TouchStartEvent.getType());
		
		
		
		RootPanel.get().addHandler( new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {		
				
				keyControls(event);
				
			}
		}, KeyPressEvent.getType());
		
	}
	
	
	
	/**
	 * This sets up the logic behind the keyControls for the player. I.E the
	 * player moving and animating on move. Also the camera panning with the
	 * player
	 * 
	 * TODO - create different camera style options, such as camera centered on player or new area when player walks to the border
	 * 
	 * @param event
	 */
	private void keyControls(KeyPressEvent event) {
		
		int cameraSpeed = this.speed;

		if (event.getCharCode() == 'w' || event.getCharCode() == 'W') {

			y -= speed;
			checkCurrentAnimationState(AnimationState.UP);
			sprite.nextFrame();

			/**
			 * Checks if the player is in the center of the screen and if so
			 * moves the camera. This stops the camera been moved when the
			 * player is at the boundary of the map
			 */
			if (cam != null && y + height - cam.getY() < (cam.getHeight() / 2)) {
				cam.incrementY(cameraSpeed * -1);
			}
			
		} else if (event.getCharCode() == 'd' || event.getCharCode() == 'D') {

			x += speed;
			checkCurrentAnimationState(AnimationState.RIGHT);
			sprite.nextFrame();
			cam.panCentered(this, Directoin.EAST);

			
		} else if (event.getCharCode() == 'a' || event.getCharCode() == 'A') {

			x -= speed;
			checkCurrentAnimationState(AnimationState.LEFT);
			sprite.nextFrame();
			//cam.panCentered(this, Directoin.WEST);
			if (cam != null && x + width - cam.getX() < (cam.getWidth() / 2)) {
				cam.incrementX(cameraSpeed * -1);
			}
			
		} else if (event.getCharCode() == 's' || event.getCharCode() == 'S') {

			y += speed;
			checkCurrentAnimationState(AnimationState.DOWN);
			sprite.nextFrame();
			cam.panCentered(this, Directoin.SOUTH);
		}

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
