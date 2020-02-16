package entities.sfx;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entities.Entity;

public class SFXFactory {
	SpriteSheet sh_projectiles;
	SpriteSheet sh_enemy1;
	
	public SFXFactory(){

		try{
			sh_projectiles = new SpriteSheet("projectiles.png",64, 64);
			sh_enemy1 = new SpriteSheet("enemy1.png",64,64);
		}catch(SlickException e){
			e.printStackTrace();
		}

	}
	
	public Entity getSFX(int x, int y,String type, String color){
		if(type.equals("particle")){
			return new SFX(x,y,color,sh_projectiles);
		}
		else if(type.equals("apparate")){
			return new ApparateSFX(x,y,color,sh_enemy1);
		}
		
		
		return null;
	}
}
