package ie.flax.flaxengine.client;

import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

/**
 * FTile extends FObject and is the main compoenet of the map class.
 * 
 * @author Ciaran McCann
 *
 */
public class FTile extends FObject implements JsonSerializable{

	private boolean solid;
	private int texture;
		
	/**
	 * Constructs the tile object
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param texture
	 */
	public FTile(float x, float y, float width, float height, int texture) {
		super(x, y, width, height);		
		this.texture = texture;		
	}
	
	/**
	 * DO NOT USE THIS CONSTRUCTOR -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public FTile() {
		super();
	}


	/**
	 * Ture or false is the tile collideable or not
	 * @return
	 */
	public boolean getSolid() {
		return solid;
	}

	/**
	 * Ture or false is the tile collideable or not
	 * @param solid
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/**
	 * Sets the texture of the tile which is the location on the tilesheet to used when drawing the tile
	 * @return int texture number reference
	 */
	public int getTexture() {
		return texture;
	}

	/**
	 * Sets the texture of the tile which is the image used when drawing the tile
	 * @param texture
	 */
	public void setTexture(int texture) {
		this.texture = texture;
	}

}
