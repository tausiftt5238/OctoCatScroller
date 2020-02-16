package entities.inanimate;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entities.Entity;

public class InanimateFactory {
	
	SpriteSheet terrain;
	SpriteSheet text;
	
	public InanimateFactory(){
		try {
			terrain = new SpriteSheet("Terrain.png",64,64);
			text = new SpriteSheet("texts.png",128,64);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Entity getInanimateObject(int co_x, int co_y, int color){
		if(color == 0xFF3a4646)
			return new Inanimate((co_x<<6),(co_y<<6),terrain.getSprite(1, 2), true);
		else if(color == 0xFF00FF01){
			return new Inanimate((co_x<<6),(co_y<<6),text.getSprite(1, 0), false);
		}
		else if(color == 0xFF00FF02){
			return new Inanimate((co_x<<6),(co_y<<6),text.getSprite(2, 0), false);
		}
		else if(color == 0xFF00FF03){
			return new Inanimate((co_x<<6),(co_y<<6),text.getSprite(3, 0), false);
		}
		else if(color == 0xFFe6ff00){
			return new Inanimate((co_x<<6),(co_y<<6),terrain.getSprite(1, 3), false);
		}
		return null;
	}
}
