package entities.hostile;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class Enemy extends Entity{
	
	protected int offsetX, offsetY;
	
	protected Animation movingRight, movingLeft;
	protected Animation damageLeft, damageRight;
	
	int damaging;
	
	public Enemy(float x, float y) {
		super(x, y);
	}
	
	@Override
	public void update(float x, float y) {
		hitBox.setLocation(this.x + x + offsetX, this.y + y + offsetY);
		footing.setLocation(this.x + x + offsetX, this.y + y + offsetY + 32);
	}

	@Override
	public void render(float x, float y) {
		if(anim != null) {
			anim.draw(this.x + x, this.y + y);
		}
		
	}

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,32,32);
		hitBox.setLocation(x+offsetX, y+offsetY);
		
		footing = new Rectangle(0,0,32,1);
		footing.setLocation(x+offsetX, y+offsetY+32);
	}

	@Override
	public void render(Graphics g) {
		g.draw(hitBox);
		g.draw(footing);
		g.drawString(falling + " ", 500, 80);
	}
	
	public void updateHitBox(){
		hitBox.setLocation(this.x + offsetX, this.y + offsetY);
		footing.setLocation(this.x + offsetX, this.y + offsetY + 32);
	}
	
	public void gravityFall(LinkedList<Entity> e, float x, float y){
		boolean tempFall = false;
		for(int i = 0 ; i < 8; i++){
			tempFall = false;
			for(Entity z : e){
				tempFall |= gravity(z);
				if(tempFall){
					
					break;
				}
			}
			if(tempFall){
				break;
			}
			this.y++;
			update(x,y);
		}
		this.setFalling(!tempFall);
	}
	
	public void loadAnimation(){}
}
