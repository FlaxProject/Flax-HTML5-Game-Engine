package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.Graphic.Sprite;

import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

/**
 * This is the NPC, player etc type class. to be expanded
 * 
 * @author Ciaran McCann
 *
 */
public class FEntity extends FObject implements JsonSerializable {

	public FEntity(float x, float y, float width, float height, Sprite sprite, String audio)
	{
		super(x, y, width, height, sprite, audio);
	}
	

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public FEntity() {
		
		super();
		
	}
}
