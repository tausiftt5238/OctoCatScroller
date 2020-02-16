package states;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import entities.Entity;
import entities.Player;
import entities.hostile.Enemy;
import entities.hostile.EnemyFactory;
import entities.inanimate.InanimateFactory;
import entities.sfx.SFXFactory;
import mainGame.Main;

public class LevelDesign extends BasicGameState{
	
	BufferedImage terrainMap;
	
	private int state;
	
	protected float x;
	protected float y;
	
	private String map;
	private String backgroundPath;
	
	private int jump;
	private int damage;
	private int shooting;
	
	private final int jumpLimit = 40;
	private final int damageLengthLimit = 24;
//	private final int shootingLimit = 24;
	
	private Image background;
	
	Music msk;
	
	float speed = 0.3f;
	
	LinkedList <Entity> entityList;
	LinkedList <Entity> enemyList;
	LinkedList <Entity> playerProjectileList;
//	LinkedList <Entity> enemyProjectileList;
	LinkedList <Entity> sfxList;
	
	InanimateFactory infac;
	SFXFactory sfxfac;
	EnemyFactory enemyfac;
	
	Image heart;
	Image green;
	Image red;
	
	Shape nextStage;
	
	public LevelDesign(int state, String map, String backgroundPath){
		super();
		
		this.state = state;
		this.map = map;
		this.backgroundPath = backgroundPath;
		
		//nextStage = new Rectangle(0,0,64,64);
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Player.player.restoreHealth(5);
		damage = 0;
		
		x = -(10 << 6) + Main.width/2;		//10
		y = -(18 << 6) + Main.height/2;		//18
		
		infac = new InanimateFactory();
		sfxfac = new SFXFactory();
		enemyfac = new EnemyFactory();
		
		entityList = new LinkedList <Entity>();
		enemyList = new LinkedList <Entity> ();
		playerProjectileList = new LinkedList <Entity> ();
//		enemyProjectileList = new LinkedList <Entity> ();
		sfxList = new LinkedList <Entity> ();
		
		//msk = new Music("megaman2title.ogg");
		//msk.play();
		
		loadEntity();
		
		SpriteSheet sh = null;
		try{
			background = new Image(backgroundPath);
			sh = new SpriteSheet("projectiles.png",64,64);
		}catch(SlickException e){
			System.out.println("didn't work out");
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e);
		}
		heart = sh.getSprite(4, 0);
		green = sh.getSprite(0, 0);
		red = sh.getSprite(1, 0);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(background != null) background.draw(0,0);
		drawEntity(g);
		drawProjectile(g);
		drawEnemy(g);
		
		Player.player.render(x, y);
		
		for(int i = 0 ; i < Player.player.getHealth(); i++){
			//heart.draw((i<<5),0,0.5f);
			green.draw((i<<5), 0, 0.5f);
		}
		for(int i = Player.player.getHealth(); i<5; i++) {
			red.draw((i<<5), 0, 0.5f);
		}
		
		
		//Player.player.render(g);
		
		/*g.drawString(Player.player.animationNumber() + " ", 500, 400);
		g.drawString(x + " " + y, 500, 0);
		g.drawString(jump + "", 500, 20);
		g.drawString(Player.player.getFalling() + " ", 500, 40);
		g.drawString(shooting + " ", 500, 60);*/
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//if(!msk.playing()) msk.loop();
		if(Player.player.getHealth() <= 0) {
			//msk.stop();
			sbg.enterState(Main.gameOver ,  new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		//nextStage.setLocation(x + (120 << 6), y + (23 << 6));
//		if(nextStage.intersects(Player.player.getHitBox())){
//			//msk.stop();
//			sbg.enterState(Main.congrats ,  new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
//		}
		
		Input input = gc.getInput();
		
		gravity();
		//Enemy on the move
		
		
		
		float change = delta * speed;

		float tempX = x ;

		//Input start
		if(input.isKeyDown(Input.KEY_LEFT) && !Player.player.getDamageStatus()){
			Player.player.update("left");
			x += (int)change;
		}
		
		else if(input.isKeyDown(Input.KEY_RIGHT) && !Player.player.getDamageStatus()){
			Player.player.update("right");
			x -= (int)change;
		}
		else {
			Player.player.update("standing");
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE) 
				&& !Player.player.getFalling() && !Player.player.getDamageStatus()){
			jump = jumpLimit;
			
		}
//		if(input.isKeyPressed(Input.KEY_Z)
//				&& !Player.player.getDamageStatus() && shooting < 10){
//			shooting = shootingLimit;
//			if(Player.player.getDirection().equals("right"))
//				playerProjectileList.add(new PlayerProjectile(-x + Main.width/2 + 45,-y + Main.height/2 + 8,Player.player.getDirection()));
//			else
//				playerProjectileList.add(new PlayerProjectile(-x + Main.width/2 - 45,-y + Main.height/2 + 8,Player.player.getDirection()));
//			Player.player.setShooting(true);
//		}
		
		//Input ends
		
		enemyCheck(delta);
		
		//Checking jump
		if(jump > 0){
			jump--;
			y += 12;
		}
		
		//Checking shooting
		if(shooting > 0){
			shooting--;
		}else{
			Player.player.setShooting(false);
		}
		
		
		//Checking damage
		if(damage > 0){
			damage--;
			if(Player.player.getDirection().equals("left")){
				x -= (int) change/2;
			}
			else if(Player.player.getDirection().equals("right")){
				x += (int) change/2;
			}
			Player.player.damage(true);
		} else Player.player.damage(false);
		
		//Collision checking start
		updateEntities();
		
		//Collision with Non-hostile
		for(Entity z: entityList){
			if(z.collision(Player.player)){
				x = tempX;
				break;
			}
		}
		//Collision with hostile
		if(!Player.player.getDamageStatus())
		for(Entity z: enemyList){
			if(z.collision(Player.player) && !z.damageStatus()){
				damage = damageLengthLimit;
				Player.player.healthDeplete();
				break;
			}
		}
		
//		for(Entity z: enemyProjectileList){
//			if(z.collision(Player.player) && !z.damageStatus()){
//				damage = damageLengthLimit;
//				Player.player.healthDeplete();
//				z.damage(0);
//				break;
//			}
//		}
//		
		updateEntities();
		
		//Collision checking ends
		
		//Check if projectile is done
		projectileCheck(delta);
				
		
	}
	
	private void enemyCheck(int delta){
		Iterator<Entity> it = enemyList.iterator();
		while(it.hasNext()){
			Entity p = it.next();
			
			if(!p.isAlive()){
				sfxList.add(sfxfac.getSFX((int) p.getX(), (int) p.getY(), "apparate", "green"));
				it.remove();
			}
			p.action(entityList, null, (int)(delta * speed));
		}
		
		
	}
	
	private void projectileCheck(int delta){
		Iterator<Entity> it = playerProjectileList.iterator();
		while(it.hasNext()){
			Entity p = it.next();
			
			if(!p.isAlive()){
				if(!p.toString().equals("patronus"))
					sfxList.add(sfxfac.getSFX((int)p.getX(), (int)p.getY() - 10, "particle", "blue"));
				it.remove();
			}
			p.action(enemyList, (int)(2*delta*speed));
		}
		
//		it = enemyProjectileList.iterator();
//		while(it.hasNext()){
//			Entity p = it.next();
//			if(!p.isAlive()){
//				sfxList.add(sfxfac.getSFX((int)p.getX(), (int)p.getY()- 10, "particle", "green"));
//				it.remove();
//			}
//			p.action(playerProjectileList,(int)(2 * delta * speed));
//		}
		
		it = sfxList.iterator();
		while(it.hasNext()){
			Entity p = it.next();
			if(!p.isAlive()){
				it.remove();
			}
			p.action(playerProjectileList,(int)(2 * delta * speed));
		}
	}
	
	private void gravity(){
		//Drop the enemies
//		for(Entity z: enemyList){
//			z.gravityFall(entityList, x , y);
//		}
		
		//drop the player
		boolean tempFall = false;
		for(Entity z: entityList){
			tempFall = tempFall | Player.player.gravity(z);
		}
		Player.player.setFalling(!tempFall);

		if(Player.player.getFalling()){
			for(int i = 0 ; i < 7 ; i++){
				boolean done = false;
				for(Entity z: entityList){
					if(Player.player.gravity(z)){
						done = true;
						break;
					}
				}
				if(done) break;
				y--;
				updateEntities();
			}
		}
		else if(!Player.player.getFalling()){
			jump = 0;
		}
		
	}

	private void loadEntity(){
		try {
			terrainMap = ImageIO.read(getClass().getResourceAsStream(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i < terrainMap.getWidth(); i++){
			for(int j = 0 ; j < terrainMap.getHeight(); j++){
				Entity inanim = infac.getInanimateObject(i, j, terrainMap.getRGB(i, j));
				if(inanim != null)
					entityList.add(inanim);
				Enemy enemy = enemyfac.getEnemy(i, j, terrainMap.getRGB(i, j));
				if(enemy != null)
					enemyList.add(enemy);
			}
		}
	}
	private void drawEntity(Graphics g){
		for(Entity z : entityList){
			z.render(x, y);
			//z.render(g);
		}
	}
	private void drawEnemy(Graphics g){
		for(Entity z: enemyList){
			z.render(x,y);
			//z.render(g);
		}
	}
	private void drawProjectile(Graphics g){
		for(Entity z: playerProjectileList){
			z.render(x,y);
			//z.render(g);
		}
		
//		for(Entity z: enemyProjectileList){
//			z.render(x,y);
//			//z.render(g);
//		}
		
		for(Entity z: sfxList){
			z.render(x,y);
		}
	}
	private void updateEntities(){
		for(Entity z : entityList) z.update(x, y);
		for(Entity z : enemyList) z.update(x, y);
		for(Entity z : playerProjectileList) z.update(x, y);
//		for(Entity z : enemyProjectileList) z.update(x, y);
		for(Entity z : sfxList) z.update(x, y);
	}
	
	@Override
	public int getID() {
		
		return state;
	}
	
}
