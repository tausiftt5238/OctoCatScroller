package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import mainGame.Main;

public class Player extends Entity{
	public static Player player = new Player(Main.width/2,Main.height/2);
	
	private int health = 5;
	private boolean shooting;
	
	private Animation movingLeft, movingRight;
	private Animation shootingLeft, shootingRight;
	private Animation standingRight, standingLeft;
	private Animation standShootRight, standShootLeft;
	private Animation jumpingLeft, jumpingRight;
	private Animation jumpShootLeft, jumpShootRight;
	private Animation damageLeft, damageRight;
		
	public Player(float x, float y){		
		super(x,y);
		x = Main.width/2;
		y = Main.height/2;
		
		
		shooting = false;
		
		
		movingLeft = new Animation(); movingLeft.setAutoUpdate(true);
		movingRight = new Animation(); movingRight.setAutoUpdate(true);
		
		shootingLeft = new Animation(); shootingLeft.setAutoUpdate(true);
		shootingRight = new Animation(); shootingRight.setAutoUpdate(true);
		
		standingLeft = new Animation(); standingLeft.setSpeed(0.5f);
		standingRight = new Animation(); standingRight.setSpeed(0.5f);
		
		standShootLeft = new Animation();
		standShootRight = new Animation();
		
		jumpingLeft = new Animation();
		jumpingRight = new Animation();
		
		jumpShootLeft = new Animation();
		jumpShootRight = new Animation();
		
		damageLeft = new Animation(); damageLeft.setAutoUpdate(true);
		damageRight = new Animation(); damageRight.setAutoUpdate(true);
		
		try{
			sh = new SpriteSheet("octocat.png",64, 64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		loadAnimation();
		
		setHitBox();
	}
	
	public void loadAnimation(){
		int row = 1;
//		for(int col = 0 ; col < 10 ; col++){
//			
//			movingRight.addFrame(sh.getSprite(col, row), 100);
//			movingLeft.addFrame(sh.getSprite(col, row).getFlippedCopy(true, false), 100);
//			
//			shootingRight.addFrame(sh.getSprite(col, row+1), 100);
//			shootingLeft.addFrame(sh.getSprite(col, row+1).getFlippedCopy(true, false), 100);
//			
//		}
		
		movingRight.addFrame(sh.getSprite(1, 0), 100);
		movingRight.addFrame(sh.getSprite(2, 0), 100);
		movingLeft.addFrame(sh.getSprite(1, 0).getFlippedCopy(true, false), 100);
		movingLeft.addFrame(sh.getSprite(2, 0).getFlippedCopy(true, false), 100);
		
		standingRight.addFrame(sh.getSprite(0, 0), 100);
		standingRight.addFrame(sh.getSprite(0, 1), 100);
		standingLeft.addFrame(sh.getSprite(0, 0).getFlippedCopy(true, false),100);
		standingLeft.addFrame(sh.getSprite(0, 1).getFlippedCopy(true, false),100);
		
//		standShootRight.addFrame(sh.getSprite(4, 0), 100);
//		standShootLeft.addFrame(sh.getSprite(4, 0).getFlippedCopy(true, false),100);
		
		jumpingRight.addFrame(sh.getSprite(4, 0), 100);
		jumpingLeft.addFrame(sh.getSprite(4, 0).getFlippedCopy(true, false),100);
		
//		jumpShootRight.addFrame(sh.getSprite(5, 0), 100);
//		jumpShootLeft.addFrame(sh.getSprite(5, 0).getFlippedCopy(true, false),100);
		
		damageRight.addFrame(sh.getSprite(1, 1), 100);
//		damageRight.addFrame(sh.getSprite(3, 0), 100);
		damageLeft.addFrame(sh.getSprite(1, 1).getFlippedCopy(true, false), 100);
//		damageLeft.addFrame(sh.getSprite(3, 0).getFlippedCopy(true, false), 100);
		
		anim = jumpingRight;
		direction = "right";
	}
	
	public void update(float x, float y){ }
	
	public void update(String direction) {
		if(direction.equals("left")){
			anim = movingLeft;
			this.direction = "left";
		}
		else if(direction.equals("right")){
			anim = movingRight;
			this.direction = "right";
		}
		else if(direction.equals("standing")){
			if(this.direction.equals("left")) anim = standingLeft;
			else anim = standingRight;
		}
		if(falling){
			if(this.direction.equals("left")) anim = jumpingLeft;
			else anim = jumpingRight;
		}
		
		if(shooting && falling){
			if(this.direction.equals("left")) anim = jumpShootLeft;
			else anim = jumpShootRight;
		}
		else if(shooting && direction.equals("standing")){
			if(this.direction.equals("left")) anim = standShootLeft;
			else anim = standShootRight;
		}
		else if(shooting){
			if(this.direction.equals("left")) anim = shootingLeft;
			else anim = shootingRight;
		}
		if(damageStatus){
			if(this.direction.equals("left")) anim = damageLeft;
			else anim = damageRight;
		}
		
	}

	@Override
	public void render(float x, float y) {
		anim.draw(this.x, this.y);
	}
	public void render(Graphics g){
		g.draw(hitBox);
		g.draw(footing);
	}
	@Override
	protected void setHitBox() {
		hitBox = new Rectangle(0,0,32,32);
		hitBox.setLocation(x+20, y+17);
		
		footing = new Rectangle(0,0,32,1);
		footing.setLocation(x+20, y+17+32);
	}
	public String getDirection(){
		return direction;
	}
	public void damage(boolean damageStatus){
		this.damageStatus = damageStatus;
	}
	public void healthDeplete(){
		health--;
	}
	public int animationNumber(){
		return anim.getFrame();
	}
	public boolean getDamageStatus(){
		return damageStatus;
	}
	public void setShooting(boolean shooting){
		this.shooting = shooting;
	}
	public boolean getShooting(){
		return shooting;
	}
	public int getHealth(){
		return health;
	}
	public void restoreHealth(int health){
		damageStatus = false;
		anim = jumpingRight;
		direction = "right";
		this.health = health;
	}
}
