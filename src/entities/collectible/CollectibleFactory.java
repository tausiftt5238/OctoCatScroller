package entities.collectible;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class CollectibleFactory {
	SpriteSheet sh;
	//SpriteSheet text;
	
	public CollectibleFactory(){
		try {
			sh = new SpriteSheet("Projectible.png",64,64);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Collectible getCollectible(int co_x, int co_y, int color){
		if(color == 0xFFe6ff00)
			return new Collectible((co_x<<6),(co_y<<6),sh.getSprite(3, 0), true);
		
		return null;
	}
}
