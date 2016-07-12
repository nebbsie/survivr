package States;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Game.Survivr;
import Menu.MenuButtons;

public class Menu extends BasicGameState {

	private int state;
	
	//Menu GUI
	private MenuButtons menuGUI;
	
	//Modules
	private Input input;
	
	//Images
	private Image logo;
	private Image background;

	public Menu(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		input = container.getInput();
		menuGUI = new MenuButtons(container);
		logo = new Image("res\\menu\\logo.png");
		background = new Image("res\\menu\\menuBackground.png");
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			Survivr.app.exit();
		}
		
		
		menuGUI.update(game);
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		background.draw();
		menuGUI.render(container, g);
		logo.draw();
		
	}

	@Override
	public int getID() {
		return state;
	}

}
