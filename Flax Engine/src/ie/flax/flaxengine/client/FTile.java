package ie.flax.flaxengine.client;

/**
 * FTile extends FObject and is the main compoenet of the map class
 * 
 * @author Ciarán McCann
 *
 */
public class FTile extends FObject{

	private boolean solid;
	private int texture;
	private byte layer;
	
	public FTile(float x, float y, float width, float height) {
		super(x, y, width, height);
		
	}
	
	/**
	 * Ture or false is the tile collideable or not
	 * @return
	 */
	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public int getTexture() {
		return texture;
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}

	public byte getLayer() {
		return layer;
	}

	public void setLayer(byte layer) {
		this.layer = layer;
	}


}
