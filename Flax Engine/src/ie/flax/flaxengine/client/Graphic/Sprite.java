package ie.flax.flaxengine.client.Graphic;

import com.google.gwt.canvas.client.Canvas;
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
	
	private FImage image; //FIXME serialiable problems
	private int currentFrame; //The current frame, x pos across the image
	private int frameCount; //Number of frames across
	
	private int frameWidth; // size of each frame eg 32 * 32
	private int frameHeight;
	
	private AnimationState animationState;	//This holds the animated row
	

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
	public Sprite(String Path, int frameWidth, int frameHeight)
	{
		this.frameHeight = frameHeight;
		this.frameWidth = frameWidth;
		image =  Graphic.getSingleton().getFImage(Path); 
		
		//If the frame width and height are not specified then its not an animated sprite
		if(frameWidth+frameHeight != 0) 
		{
			frameCount = (image.getImage().getWidth()/frameWidth)-1;
			currentFrame = 0;	
			animationState = AnimationState.IDE; //default to idle state
		}
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
	 * Sets the current row of the image in which the animation frames are
	 * @param state
	 */
	public void setAnimationState(AnimationState state)
	{
		this.animationState = state;
	}

	/**
	 * Moves the animation across the image row to the next frame
	 * and loops back once it reaches the end.
	 */
	public void nextFrame()
	{
		FLog.debug(" current  " + currentFrame + "  framecount " + frameCount);
				
		if(currentFrame < frameCount)
			currentFrame++;	
		else
			currentFrame = 0;
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
		
		
		/**
		 * DO NOT USE THIS Constructor -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated	
		public Sprite()
		{
			
		}
		
		
		
		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public FImage getImage() {
			return image;
		}
		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public void setImage(FImage image) {
			this.image = image;
		}
		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public int getCurrentFrame() {
			return currentFrame;
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
		public int getFrameCount() {
			return frameCount;
		}
		/**
		 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
		 * can work Using this method is at your own risk and will most likely break
		 * your code in RUNTIME!!
		 * 
		 */
		@Deprecated
		public void setFrameCount(int frameCount) {
			this.frameCount = frameCount;
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
		public void setFrameWidth(int frameWidth) {
			this.frameWidth = frameWidth;
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
}
