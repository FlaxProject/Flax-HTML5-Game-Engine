package ie.flax.flaxengine.client.gameobjects;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.Graphic.Sprite;

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
	public Player()
	{
		super(0, 0, 32, 32, new Sprite("http://www.allacrost.org/media/art/sprites_map_claudius.png", 32,64), "audio");
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
				}
				else if(event.getCharCode() == 'd'){
				    x += speed;
				}
				else if(event.getCharCode() == 'a'){
					x -= speed;
				}
				else if(event.getCharCode() == 's'){
					y += speed;
				}
				
			}
		}, KeyPressEvent.getType());
	}
	
	

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
