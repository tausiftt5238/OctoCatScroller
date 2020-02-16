package entities.projectile;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class PlayerProjectile extends Entity{
	
	protected Image moving;
	protected Image stationary;
	protected int alive;
	protected int speed;
	
	public PlayerProjectile(float x, float y, String direction) {
		super(x, y);
		this.direction = direction;
		try{
			sh = new SpriteSheet("projectiles.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		speed = 5;
		
		alive = (speed << 3);
		loadSprite();
		setHitBox();
	}

	@Override
	public void update(float x, float y) {
		hitBox.setLocation(this.x + x + 5, this.y + y + 5);
	}

	@Override
	public void render(float x, float y) {
		moving.draw(this.x + x , this.y + y);	
	}

	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,50,32);
		hitBox.setLocation(x,y);		
	}

	@Override
	public void render(Graphics g) {
		g.draw(hitBox);		
	}
	public boolean isAlive(){
		alive--;
		if(alive < 0) return false;
		else return true;
	}
	protected void loadSprite(){
		if(direction.equals("right")) moving = sh.getSprite(0, 0);
		else moving = sh.getSprite(0, 0).getFlippedCopy(true, false);
		stationary = sh.getSprite(1, 0);
	}
	
	public void action(LinkedList<Entity> e, int delta){
		for(int i = 0 ; i < delta ; i++){
			for(Entity z : e){
				if(z.collision(this) && !z.damageStatus()){
					if(!z.toString().equals("dementor"))
						z.damage(48);
					else
						z.damage(10);
					this.alive = 0;
				}
			}
			if(direction.equals("left")) this.x -= 1;
			else this.x += 1;
			updateHitBox();
		}
		
	}
	
	@Override
	public void damage(int damageLimit){
		alive = 0;
	}
	
	@Override
	public String toString(){
		return "projectile:" + x + " " + y;
	}
}
