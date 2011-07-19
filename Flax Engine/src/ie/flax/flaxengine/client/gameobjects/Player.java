package ie.flax.flaxengine.client.gameobjects;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.Graphic.Sprite;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.RootPanel;

@SuppressWarnings("deprecation")
public class Player extends FEntity {

	private int speed;
	
	public Player()
	{
		super(2, 2, 32, 32, new Sprite("http://flax.ie/test/g.png", 0, 0), "audio");
		speed = 10;		
		bind();
		
		FLog.trace(this + " was created ");
		
	}

	private void bind()
	{
		RootPanel.get().addHandler( new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
			
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
