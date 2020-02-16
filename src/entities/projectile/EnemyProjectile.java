package entities.projectile;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import entities.Entity;
import entities.hostile.Enemy;

public class EnemyProjectile extends Enemy{

	int health = 1;
	protected int alive ;
	
	public EnemyProjectile(float x, float y, String direction) {
		super(x, y);
		
		this.direction = direction;
		
		movingLeft = new Animation(); movingLeft.setAutoUpdate(true);
		movingRight = new Animation(); movingRight.setAutoUpdate(true);
		
		try{
			sh = new SpriteSheet("projectiles.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		health = (5 << 3);
		
		loadAnimation();
		
		setHitBox();
		
	}
	
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,50,32);
		hitBox.setLocation(x,y);
	}
	
	public void update(float x, float y) {
		hitBox.setLocation(this.x + x, this.y + y);
	}
	
	public void render(Graphics g) {
		g.draw(hitBox);
		g.drawString(falling + " ", 500, 80);
	}

	
	@Override
	public void loadAnimation(){

		movingRight.addFrame(sh.getSprite(1, 0), 100);
		movingLeft.addFrame(sh.getSprite(1, 0).getFlippedCopy(true, false), 100);
		
		if(direction.equals("left"))
			anim = movingLeft;
		else anim = movingRight;
		
	}
	@Override
	public void action(LinkedList <Entity> e, int delta){
		for(int i = 0 ; i < delta ; i++){
			for(Entity z : e){
				if(z.collision(this) && !z.damageStatus()){
					z.damage(0);
					damage(0);
					this.alive = 0;
				}
			}
			if(direction.equals("left")) this.x -= 1;
			else this.x += 1;
			updateHitBox();
		}
	}
	@Override
	public void updateHitBox(){
		hitBox.setLocation(x + 5, y + 5);
	}
	
	@Override
	public void damage(int damageLimit){
		health = 0;
	}
	@Override 
	public boolean isAlive(){
		health--;
		if(health <= 0) return false;
		else return true;
	}

}
