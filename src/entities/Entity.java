package entities;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;

public abstract class Entity {
	
	protected String direction;
	
	protected boolean breakable;
	
	//Coordinate
	protected float x;
	protected float y;
	
	//Image
	protected Animation anim;
	protected Image sprite;
	
	//Collision detection 
	protected Shape hitBox;
	protected Shape footing;
	
	//State
	protected boolean collide;
	protected boolean falling;
	protected boolean damageStatus;

	protected SpriteSheet sh;
	
	public Entity(float x, float y){
		this.x = x;
		this.y = y;
		collide = true;
		falling = true;
		damageStatus = false;
	}
	
	public Entity(float x, float y, boolean collide){
		this.x = x;
		this.y = y;
		this.collide = collide;
		this.falling = true;
		damageStatus = false;
	}
	
	public Entity(float x, float y, Image sprite, boolean collide){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.collide = collide;
		damageStatus = false;
	}
	
	public Entity(float x, float y, Image sprite, boolean collide, boolean breakable){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.collide = collide;
		damageStatus = false;
		this.breakable = breakable;
	}
	public abstract void update(float x, float y);
	public abstract void render(float x, float y);
	protected abstract void setHitBox();
	public abstract void render(Graphics g);
	
	public Shape getHitBox(){
		return hitBox;
	}
	public boolean collision(Entity ob){
		if(this.collide && ob.collide)
			return hitBox.intersects(ob.getHitBox());
		else return false;
	}
	public void getSpriteSheet(SpriteSheet sh){
		this.sh = sh;
	}
	public void getSprite(Image sprite){
		this.sprite = sprite;
	}
	public boolean gravity(Entity ob){
		if(ob.collide)
			return footing.intersects(ob.getHitBox());
		else return false;
	}
	public boolean getFalling(){
		return falling;
	}
	public void setFalling(boolean falling){
		this.falling = falling;
	}
	public boolean getBreakable() {
		return breakable;
	}
	public boolean isAlive(){ return true; }
	public void updateHitBox(){	}
	public void action(LinkedList<Entity> e, int delta){}
	public void action(LinkedList<Entity> e, LinkedList<Entity> f, int delta){}
	public void action(float x , float y, int delta){}
	public void gravityFall(LinkedList<Entity> e, float x, float y){}
	public float getX(){ return x; }
	public float getY(){ return y; }
	public void damage(int damageLimit){}
	public boolean damageStatus(){return false;}
}