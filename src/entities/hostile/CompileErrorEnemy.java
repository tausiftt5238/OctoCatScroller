package entities.hostile;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import entities.Entity;
import entities.projectile.EnemyProjectile;

public class CompileErrorEnemy extends Enemy{
	
	int health = 3;	
	int shooting = 0;
	boolean standing ;
	
	public CompileErrorEnemy(float x, float y, boolean standing, SpriteSheet sh) {
		super(x, y);
		
		this.standing = standing;
		this.sh = sh;
		
		movingLeft = new Animation(); 
		
		offsetX = 0;
		offsetY = 0;
		
		loadAnimation();
		
		setHitBox();
	}
	@Override
	public void loadAnimation(){
		
		movingLeft.addFrame(sh.getSprite(2, 0), 400);
		anim = movingLeft;
		direction = "left";
		
		anim = movingLeft;
	}
	
	public void update(float x, float y) {
		
		hitBox.setLocation(this.x + x + offsetX, this.y + y + offsetY);
		footing.setLocation(this.x + x + offsetX, this.y + y + offsetY + 32);
	}
	
	public void action(LinkedList<Entity> e, LinkedList<Entity> f, int delta){
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
