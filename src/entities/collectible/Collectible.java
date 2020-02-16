package entities.collectible;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class Collectible extends Entity{

	
	public Collectible(float x, float y, Image sprite, boolean collide) {
		super(x, y, sprite,collide);
		setHitBox();
	}

	@Override
	public void update(float x, float y) {
		hitBox.setLocation(this.x + x, this.y + y);
	}

	@Override
	public void render(float x, float y) {
		sprite.draw(this.x + x , this.y + y);		
	}
	public void render(Graphics g){
		g.draw(hitBox);
	}
	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,64,64);
		hitBox.setLocation(x,y);
	}
	
}
