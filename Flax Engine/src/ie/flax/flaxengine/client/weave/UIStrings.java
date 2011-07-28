package ie.flax.flaxengine.client.weave;

/**
 * Use this for any outward-facing writing. Log messages, help text, etc.
 * TODO Carl these should be HTML, not String.
 * @author elangca
 *
 */
public final class UIStrings {
	
	public final static String version = " Version 0.2 ";
	public final static String logo = "<a href='http://flax.ie' target='_blank'><img style='padding-bottom:5px' src='http://flax.ie/images/flaxLogo.png' width='100%'/></a>";
	public final static String newline = "<br/>";
	public final static String twitterPlug = "<br/><p style='text-align:center'>You should follow us on Twitter " +
			"<a href='http://twitter.com/flaxproject' target='_blank'>here</a>.";
	public final static String sitePlug = "Read more about this project at <a href='http://flax.ie' target='_blank'>flax.ie</a>!</p>";
	
	
	public final static String about(){
		String aboutFlax = "The Flax Project is a blog, centring primarily on the development of the Flax HTML5 game engine" +
				". Flax has expanded to blog posts from other games development students, " +
				"ranging from articles on gameplay mechanics to Windows Phone 7 game development.";
		String aboutUs = "The Flax project began with Ciar&aacute;n McCann and Carl Lange in Summer 2010." +
				" McCann and Lange are third year Computer Games Development students at IT Carlow, in the southeast of Ireland. " +
				"Flax begun when IT Carlow gave the two students office space for the summer of 2010. They needed a good " +
				"project to work on, and, having realised that social networks were done already, " +
				"they settled on a HTML5 game engine. This is the demo of its map editor.";
		
		String about = logo + newline + version + newline + newline + aboutFlax + newline + aboutUs + newline + twitterPlug + newline + sitePlug;
		return about;
	}
	
	public final static String help(){
		String tiling = "<strong>Tiling: </strong> To tile, select a tile from the tilesheet below " +
				"and click on the map on the left. You can use any image as a tilesheet, just press " +
				"<em>Select Tilesheet</em> at the bottom left.";
		
		String move = "<strong>Tiling on move:</strong> To tile where your mouse is moving to, hold down shift and " +
				"simply move your mouse. Don't forget to select a tile!";
		
		String regions = "<strong>Tiling regions:</strong> To tile a region, hold down shift and click on the map. "
				+ "Then drag out from that point. You'll see a red box form. "
				+ "When you let go, the tiles in that box will be textured with the current texture.";
		
		String minimap = "<strong>Using the minimap:</strong> To use the minimap, press the \"Toggle minimap\" button underneath it. " +
				"Be aware that this will hurt performance (in some cases, quite badly), so it's off by default. When on, the minimap will refresh " +
				"more or less when you edit tiles or move the camera.";
		
		String cam = "<strong>Moving the camera:</strong> To move the camera, use the arrow keys or click on the minimap below.";
		
		String player = "<strong>Moving the player:</strong> To move the player, use WASD (W is up, A is left, S is down, D is right).";
		
		String editor = "<strong>Toggling Weave:</strong> Simply press backslash (\\) to toggle the editor.";
		
		String twitterPlug = "<br/><p style='text-align:center'>You should follow us on Twitter " +
				"<a href='http://twitter.com/flaxproject' target='_blank'>here</a>.";
		
		String sitePlug = "Read more about this project at <a href='http://flax.ie' target='_blank'>flax.ie</a>!</p>";
		
		
		return 
		logo + newline +  
		version + newline + newline +
		editor + newline +newline+
		tiling + newline + newline+ 
		move + newline + newline + 
		regions + newline + newline + 
		minimap + newline +newline +
		cam + newline + newline +
		player + newline +
		twitterPlug + newline + 
		sitePlug;
	}
}
