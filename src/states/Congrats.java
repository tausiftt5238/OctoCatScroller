package states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Congrats extends BasicGameState{
	int state;
	Image bg;
	SpriteSheet sh;
	Animation pressEnter;
	Music msk;
	Animation birthdaymsg;
	int index;
	
	public Congrats(int state){
		this.state = state;
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		index = 0;
		try{
			sh = new SpriteSheet("texts.png", 128, 64);
			bg = sh.getSprite(0, 3);
		}catch(SlickException e){
			e.printStackTrace();
		}
		birthdaymsg = new Animation(); birthdaymsg.setAutoUpdate(false);
		for(int i = 0 ; i <3 ; i++){
			birthdaymsg.addFrame(sh.getSprite(i, 2), 100);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw(350, 300, 2f);
		Image temp = birthdaymsg.getCurrentFrame();
		temp.draw(350, 300, 2f);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(index == 3) gc.exit();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			birthdaymsg.setCurrentFrame(++index);
		}
	}

	@Override
	public int getID() {
		
		return state;
	}
}
