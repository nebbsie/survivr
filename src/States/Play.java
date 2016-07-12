package States;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Entities.Player;
import GUI.ActionBar;
import GUI.Debug;
import Game.Survivr;
import Network.NetworkClient;
import Network.NetworkDetails;

public class Play extends BasicGameState {

	private int state;

	// Colours
	private Color backgroundColour = new Color(126, 178, 255);

	// Modules
	private Input input;
	private NetworkClient server;

	// GUI
	private Debug debug;
	private ActionBar actionBar;
	
	//Entities
	private Player player;

	public Play(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		JFrame frame = new JFrame("Enter Name: ");
		String name = JOptionPane.showInputDialog(this);
		
		debug = new Debug();
		input = container.getInput();
		actionBar = new ActionBar(container);
		player = new Player(container);
		
		try {
			server = new NetworkClient(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (input.isKeyPressed(Input.KEY_F1)) {
			debug.toggle();
		}
	
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(Survivr.menu);
		}
		

		debug.update();
		actionBar.update();
		player.update(delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(backgroundColour);
		debug.render();
		
		player.render(g);
		
		actionBar.render(g, container);

	}

	@Override
	public int getID() {
		return state;
	}

}
