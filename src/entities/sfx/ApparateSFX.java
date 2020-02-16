package entities.sfx;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import entities.Entity;

public class ApparateSFX extends Entity{

	private String color;
	private int alive;
	
	public ApparateSFX(float x, float y, String color, SpriteSheet sh) {
		super(x, y);
		this.color = color;
		this.sh = sh;
		
		alive = 16;
		
		anim = new Animation(); anim.setAutoUpdate(true);
		
		loadAnimation();
	}
	
	@Override
	public boolean isAlive(){
		if(alive > 0)
			alive--;
		if(alive == 0) return false;
		return true;
	}
	
	public void loadAnimation(){
		
		
		int row = 0;
		if(color.equals("green")) row = 2;
		else if(color.equals("blue")) row = 3;
		
		for(int i = 0; i < 5 ; i++){
			anim.addFrame(sh.getSprite(i, row), 50);
		}
		
	}

	@Override
	public void update(float x, float y) {
		
	}

	@Override
	public void render(float x, float y) {
		
		anim.draw(this.x + x, this.y + y);		
	}

	@Override
	protected void setHitBox() {
			
	}

	@Override
	public void render(Graphics g) {
		
	}
	
}
