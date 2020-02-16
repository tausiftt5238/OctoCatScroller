package entities.hostile;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import entities.Entity;
import entities.projectile.EnemyProjectile;

public class DummyEnemy extends Enemy{
	
	int health = 3;
	int shooting = 0;
	boolean standing ;
	
	public DummyEnemy(float x, float y, boolean standing, SpriteSheet sh) {
		super(x, y);
		
		this.standing = standing;
		this.sh = sh;
		
		movingLeft = new Animation(); movingLeft.setAutoUpdate(true);
		movingRight = new Animation(); movingRight.setAutoUpdate(true);
		
		damageLeft = new Animation(); damageLeft.setAutoUpdate(true);
		damageRight = new Animation(); damageRight.setAutoUpdate(true);
		
		offsetX = 20;
		offsetY = 17;
		
		loadAnimation();
		
		setHitBox();
	}
	@Override
	public void loadAnimation(){
		if(standing){
			movingRight.addFrame(sh.getSprite(4, 0), 400);
			movingRight.addFrame(sh.getSprite(0, 0), 400);
			movingLeft.addFrame(sh.getSprite(4, 0).getFlippedCopy(true, false), 400);
			movingLeft.addFrame(sh.getSprite(0, 0).getFlippedCopy(true, false), 400);
		}
		else{	
			int row = 1;
			for(int col = 0 ; col < 10 ; col++){
				
				movingRight.addFrame(sh.getSprite(col, row), 100);
				movingLeft.addFrame(sh.getSprite(col, row).getFlippedCopy(true, false), 100);
			}
		}
		
		damageRight.addFrame(sh.getSprite(2, 0), 100);
		damageRight.addFrame(sh.getSprite(3, 0), 100);
		damageLeft.addFrame(sh.getSprite(2, 0).getFlippedCopy(true, false), 100);
		damageLeft.addFrame(sh.getSprite(3, 0).getFlippedCopy(true, false), 100);
		
		anim = movingLeft;
		direction = "left";
	}
	
	public void update(float x, float y) {
		
		hitBox.setLocation(this.x + x + offsetX, this.y + y + offsetY);
		footing.setLocation(this.x + x + offsetX, this.y + y + offsetY + 32);
		
		if(damageStatus){
			if(this.direction.equals("left")) anim = damageLeft;
			else anim = damageRight;
		}
	}
	
	public void action(LinkedList<Entity> e, LinkedList<Entity> f, int delta){
		if(shooting > 0 && !damageStatus)
			shooting--;
		if(damaging > 0){
			damaging--;
			damageStatus = true;
		} else damageStatus = false;
		
		if(shooting == 0 && !damageStatus){
			shooting = 48;
			if(direction.equals("left"))
				f.add(new EnemyProjectile(x - 32,y + offsetY - 10,direction));
			else f.add(new EnemyProjectile(x + 32, y + offsetY - 10, direction));
		}
			
		boolean collision = false;
		for(Entity x : e){
			if(x.collision(this)){
				collision = true;
				if(direction.equals("left")){
					this.x += (delta + 5);
					direction = "right";
				}
				else{
					this.x -= (delta + 5);
					direction = "left";
				}
				break;
			}
		}
		updateHitBox();
				
		if(!collision && !falling && !damageStatus && !standing){
			if(direction.equals("left"))
				x -= delta;
			else x += delta;
		}
		updateHitBox();
		if(direction.equals("left")) anim = movingLeft;
		else anim = movingRight;
	}
	@Override
	public void damage(int damageLimit){
		health--;
		damageStatus = true;
		damaging = damageLimit;
	}
	@Override
	public boolean damageStatus(){
		return damageStatus;
	}
	@Override 
	public boolean isAlive(){
		if(health <= 0) return false;
		else return true;
	}
	@Override
	public String toString(){
		return "dummyEnemy: " + x + " " + y;
	}
}
