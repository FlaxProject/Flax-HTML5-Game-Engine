package ie.flax.flaxengine.client.Graphic;

import com.google.gwt.canvas.client.Canvas;

import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;


/**
 * This class handles the sprite for objects, like NPC , Characters etc.
 * It handles both non-animated and animated sprites.
 * @author Ciarán McCann
 *
 */
public class Sprite {
	
	//Different Y values for the animations
	public enum AnimationState { 		
		UP(0),
		DOWN(1),
		LEFT(2),
		RIGHT(3),
		IDE(4);
		
		private final int index;
		
		AnimationState(int index)
		{
			this.index = index;
		}
		
		public int index(){
			return index;
		}
	}
	
	private FImage image;
	private int currentFrame; //The current frame, x pos across the image
	private int frameCount; //Number of frames across
	
	private int frameWidth; // size of each frame eg 32 * 32
	private int fameHeight;
	
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
		this.fameHeight = frameHeight;
		this.frameWidth = frameWidth;
		image =  Graphic.getSingleton().getFImage(Path); 
		
		//If the frame width and height are not specified then its not an animated sprite
		if(frameWidth+frameHeight != 0) 
		{
			frameCount = image.getImage().getWidth()/frameWidth;
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
			if(fameHeight != 0)
			{
				if(animationState == AnimationState.IDE )
				{
					nextFrame(); //IDLE status update 
					//This may fuck up as its tied to the refresh rate of the canvas
				}
				
				drawingSpace.getContext2d().drawImage(image.getImage(),currentFrame*frameWidth,(animationState.index*fameHeight));
			}
			
			//Non-animated image
			drawingSpace.getContext2d().drawImage(image.getImage(), position.x-FlaxEngine.camera.getX(), position.y-FlaxEngine.camera.getY(),image.getImage().getWidth(), image.getImage().getHeight());

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
		if(currentFrame < frameCount)
			currentFrame++;	
		else
			currentFrame = 0;
	}
}
