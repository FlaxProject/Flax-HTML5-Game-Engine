package ie.flax.flaxengine.client.Graphic;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;


/**
 * This class handles the sprite for objects, like NPC , Characters etc.
 * It handles both non-animated and animated sprites.
 * @author Ciar�n McCann
 *
 */
public class Sprite implements JsonSerializable {
		
	private transient FImage image; 
	private transient int currentFrame; //The current frame, x pos across the image	
	private transient int frameCount; //Number of frames across
	private transient AnimationState animationState;	//This holds the animated row
	private transient double timeSinceLastFrame;
	
	private String imagePath;	
	private int frameWidth; // size of each frame eg 32 * 32
	private int frameHeight;

	
	
	/**
	 * The path is the string URL to the image
	 * 
	 * Non-Animated Sprites:
	 * 		If frame width and height equal 0 then its an non animated sprite
	 * 
	 * Animated Sprites
	 * 		Once the frame with and height is greater then 0, the sprite is 
	 * 		treated as an animated sprite
	 * 
	 * @param Path
	 * @param frameWidth 
	 * @param frameHeight
	 */
	public Sprite(final String path, final int frameWidth, final int frameHeight)
	{
		this.frameHeight = frameHeight;
		this.frameWidth = frameWidth;
		this.imagePath = path;
		
		init();
	}
	
	
	
	/**
	 * FIXME CIARAN	- Get the serializer to call a method of name init() if the class type implements a certain interface
	 * or go crazy and try and get rid of the end for public get and sets and have the system load from just the construct paramters. 
	 * 
	 * 
	 */
	
	
	/**
	 * This method most be called in constructor and also after deserailzation
	 */
	public void init()
	{
								
		Graphic.getSingleton().loadImage(imagePath).addLoadHanderl(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				
				image = Graphic.getSingleton().getFImage(imagePath);
				//If the frame width and height are not specified then its not an animated sprite
				if(frameWidth+frameHeight != 0) 
				{
					frameCount = (image.getImage().getWidth()/frameWidth)-1;
					currentFrame = 0;	
					animationState = AnimationState.DOWN; //default to idle state
				}
			}
		});
	}
	
	

	@Deprecated	
	public Sprite()
	{
		animationState = AnimationState.IDE;
	}
	
	
	/**
	 * This draws the sprite, if its an animated sprite or just a simple image
	 */
	public void draw(Canvas drawingSpace, FVector position)
	{
		
		
		if(image.isLoaded())
		{
			//If its frameheight is not specified then its not an animated sprite
			if(frameHeight != 0)
			{
				if(animationState == AnimationState.IDE )
				{
					nextFrame(); //IDLE status update 
					//This may fuck up as its tied to the refresh rate of the canvas
				}
				
				
				// TODO CIARAN - optimize this drawing as more entites are added 	
				
				drawingSpace.getContext2d().drawImage(image.getImage(),currentFrame*frameWidth,(animationState.index*frameHeight), frameWidth, frameHeight, position.x-FlaxEngine.camera.getX(), position.y-FlaxEngine.camera.getY(),frameWidth, frameHeight);
				//FLog.error("sprite xs " + currentFrame*frameWidth + " sy " + animationState.index*frameHeight);
			
			}
			else{						
				//Non-animated image
				drawingSpace.getContext2d().drawImage(image.getImage(), position.x-FlaxEngine.camera.getX(), position.y-FlaxEngine.camera.getY(),image.getImage().getWidth(), image.getImage().getHeight());
			}

		}
	}
	
	/**
	 * Sets the row of the sprite image, also if the current state isnt the same as the one beening passed in
	 * it will reset the currentframe count
	 * @param state
	 */
	public void setAnimationState(AnimationState state)
	{
		if(this.animationState != state)
		{
			this.animationState = state;
			this.currentFrame = 0;
		}
	}

	/**
	 * Moves the animation across the image row to the next frame
	 * and loops back once it reaches the end.
	 */
	public void nextFrame()
	{
		if(timeSinceLastFrame >= 3000)
		{
		
			if(currentFrame < frameCount)
			{
				currentFrame++;	
			}
			else
			{
				currentFrame = 3;	//FIXME set back to zero, only for spefic sprite	
			}
			
		}

	}
		
	
	
	
	//Different Y values for the animations
		public enum AnimationState { 		
			IDE(0),
			UP(2),
			DOWN(0),
			LEFT(1),
			RIGHT(3);
			
			
			private final int index;
			
			AnimationState(int index)
			{
				this.index = index;
			}
			
			public int index(){
				return index;
			}
		}
		
			
		public String objectState()
		{
			return this + " currentFrame " + currentFrame + " frameWidth " + frameWidth + " frameCount " + frameCount + " anaimtion state " + animationState;
		}
				
	

		public void setCurrentFrame(int currentFrame) {
			this.currentFrame = currentFrame;
		}
	
		
		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public void setFrameWidth(int frameWidth) {
			this.frameWidth = frameWidth;
			init();
		}
		

		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public int getFrameWidth() {
			return frameWidth;
		}
		
		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public int getFrameHeight() {
			return frameHeight;
		}
		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public void setFrameHeight(int frameHeight) {
			this.frameHeight = frameHeight;
		}
		

		public AnimationState getAnimationState() {
			return animationState;
		}


		public String getImagePath() {
			return imagePath;
		}


		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}



		public void update(double deltaTime) {
			
			timeSinceLastFrame += deltaTime;
		}
}
