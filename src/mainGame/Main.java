package mainGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.Congrats;
import states.GameOver;
import states.LevelDesign;
import states.MainMenu;


public class Main extends StateBasedGame {

	private static String name = "Github Adventure!";
	
	public static int previousRoom = -1;
	
	public static final int mainMenu = 0;
	public static final int levelDesign = 1;
	public static final int gameOver = 2;
	public static final int congrats = 3;
	
	public static boolean transition = false;
	
	public static int width = (int)(640*1.5);
	public static int height = 480;
	
	public static void main(String args[]){
		AppGameContainer apc;
		try{
			apc = new AppGameContainer(new Main(name));
			apc.setDisplayMode(width, height, false);
			apc.setTargetFrameRate(60);
			apc.setShowFPS(false);
			apc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
	
	public Main(String name) throws SlickException{
		super(name);
		
		this.addState(new MainMenu(mainMenu));
		this.addState(new GameOver(gameOver));
		this.addState(new Congrats(congrats));
		this.addState(new LevelDesign(levelDesign, "/lv1.png", "stage_1.png"));

	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(levelDesign).init(gc, this);
		this.getState(mainMenu).init(gc, this);
		this.getState(gameOver).init(gc,this);
		this.getState(congrats).init(gc,this);
		this.enterState(mainMenu);
	}

}
