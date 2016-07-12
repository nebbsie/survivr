package GUI;

import org.lwjgl.opengl.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.MouseOverArea;

import Game.Survivr;

public class ActionBar {

	// Menu bar
	private Rectangle bar;
	private MouseOverArea itemsArea;

	// Images
	private Image itemsButton;
	private Image itemsButtonOver;

	// Colours
	private Color barColour;
	private Color itemColour;

	
	//Modules
	private Input input;

	public ActionBar(GameContainer container) {

		input = container.getInput();

		loadImages();

		bar = new Rectangle(0, Survivr.V_HEIGHT - 100, Survivr.V_WIDTH, 200);
		barColour = new Color(212, 212, 212, 160);

		itemsArea = new MouseOverArea(container, itemsButton, 5, Survivr.V_HEIGHT - 100);
		itemsArea.setMouseOverImage(itemsButtonOver);
	}

	public void update() {

	}

	public void render(Graphics g, GameContainer container) {
		g.setColor(barColour);
		g.fill(bar);

		itemsArea.render(container, g);

	}

	public void loadImages() {
		try {
			itemsButton = new Image("res\\game\\actionbar\\itemsButton.png");
			itemsButtonOver = new Image("res\\game\\actionbar\\itemsButtonOver.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
