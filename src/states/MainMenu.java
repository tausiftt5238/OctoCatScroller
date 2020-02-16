package states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import mainGame.Main;

public class MainMenu extends BasicGameState{

	int state;
	Image bg;
	SpriteSheet sh;
	Animation pressEnter;
	
	public MainMenu(int state){
		this.state = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		try{
			bg = new Image("mainMenu.png");
			sh = new SpriteSheet("texts.png", 128,64);
		}catch(SlickException e){
			e.printStackTrace();
		}
		pressEnter = new Animation(); pressEnter.setAutoUpdate(true);
		pressEnter.addFrame(sh.getSprite(0, 0),1000);
		pressEnter.addFrame(sh.getSprite(0, 1), 1000);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw();
		
		pressEnter.draw(410,395);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_ENTER)){
			sbg.getState(Main.levelDesign).init(gc, sbg);
			sbg.enterState(Main.levelDesign, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

	@Override
	public int getID() {
		
		return state;
	}

}
