package Menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;

import Game.Survivr;

public class MenuButtons {

	private MouseOverArea playButton;
	private MouseOverArea quitButton;
	private Sound mouseOver;
	private Sound mouseClick;
	private boolean playCount = false;
	private boolean quitCount = false;
	private boolean clicked = false;
	private boolean held = false;

	private Input input;

	private String location = "res\\menu\\buttons\\";

	public MenuButtons(GameContainer container) {

		input = container.getInput();

		try {

			mouseOver = new Sound("res\\menu\\sounds\\mouseOver.ogg");
			mouseClick = new Sound("res\\menu\\sounds\\mouseClick.ogg");

			mouseOver = new Sound("res\\sounds\\oawhh.ogg");
			mouseClick = new Sound("res\\sounds\\ouch.ogg");

			if (Survivr.SCALE == 5 || Survivr.SCALE == 6) {

				playButton = new MouseOverArea(container, new Image(location + "1080\\playButton.png"),
						Survivr.V_WIDTH / 20, Survivr.V_HEIGHT - 250);
				playButton.setMouseOverImage(new Image(location + "1080\\playButtonOver.png"));
				playButton.setMouseDownImage(new Image(location + "1080\\playButtonOver.png"));

				quitButton = new MouseOverArea(container, new Image(location + "1080\\quitButton.png"),
						Survivr.V_WIDTH / 20, Survivr.V_HEIGHT - 150);
				quitButton.setMouseOverImage(new Image(location + "1080\\quitButtonOver.png"));
				quitButton.setMouseDownImage(new Image(location + "1080\\quitButtonOver.png"));

			} else if (Survivr.SCALE == 4) {

				playButton = new MouseOverArea(container, new Image(location + "810\\playButton.png"),
						Survivr.V_WIDTH / 20, Survivr.V_HEIGHT - 200);
				playButton.setMouseOverImage(new Image(location + "810\\playButtonOver.png"));
				playButton.setMouseDownImage(new Image(location + "810\\playButtonOver.png"));

				quitButton = new MouseOverArea(container, new Image(location + "810\\quitButton.png"),
						Survivr.V_WIDTH / 20, Survivr.V_HEIGHT - 120);
				quitButton.setMouseOverImage(new Image(location + "810\\quitButtonOver.png"));
				quitButton.setMouseDownImage(new Image(location + "810\\quitButtonOver.png"));

			}

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void update(StateBasedGame game) {

		// Check if mouse is being held
		if (!held) {
			playButton.setAcceptingInput(true);
			quitButton.setAcceptingInput(true);
		}

		// Check if play button is being clicked
		if (playButton.isMouseOver() && playCount == false && !held) {
			mouseOver.play();
			playCount = true;

		} else if (playButton.isMouseOver() == false) 
			playCount = false;
		

		// Check if quit button is being clicked
		if (quitButton.isMouseOver() && quitCount == false && !held) {
			mouseOver.play();
			quitCount = true;
			
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				Survivr.app.exit();

		} else if (quitButton.isMouseOver() == false) 
			quitCount = false;
		
		
		// Check if mouse has been clicked
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && playButton.isMouseOver()) 
			game.enterState(Survivr.play);
		
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && quitButton.isMouseOver()) 
			Survivr.app.exit();
		
		// Check if mouse is clicked and play a sound
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked == false) {
			mouseClick.play();
			clicked = true;
			held = false;
		} else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) == false) {
			clicked = false;
			held = false;
		} else {
			held = true;
			playButton.setAcceptingInput(false);
			quitButton.setAcceptingInput(false);
		}
	}

	public void render(GameContainer container, Graphics g) {
		playButton.render(container, g);
		quitButton.render(container, g);
	}
}
