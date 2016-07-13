package States;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Entities.Player;
import GUI.ActionBar;
import GUI.Debug;
import Game.Survivr;
import Network.NetworkClient;
import Network.NetworkDetails;

import java.util.ArrayList;
import java.util.Random;

public class Play extends BasicGameState {

	private int state;
    private Rectangle back;

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
    private ArrayList<Shape> shapeList = new ArrayList<>();
    private ArrayList<Point> pointList = new ArrayList<>();


	public Play(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		JFrame frame = new JFrame("Enter Name: ");
		String name = JOptionPane.showInputDialog(this);

        back = new Rectangle(0, 0, Survivr.V_WIDTH, Survivr.V_HEIGHT);

        // create random shapes
        Random rand = new Random();
        for(int i = 0; i < 5; i++){
            shapeList.add(new Rectangle(rand.nextInt(Survivr.V_WIDTH), rand.nextInt(Survivr.V_HEIGHT), 50, 50));
        }

        shapeList.add(new Circle(100, 200, 50));

        // populate points list with points of all shapes
        for(int i = 0; i < shapeList.size(); i++) {
            for (int j = 0; j < shapeList.get(i).getPointCount(); j++) {
                pointList.add(new Point(shapeList.get(i).getPoint(j)[0], shapeList.get(i).getPoint(j)[1]));
            }
        }

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

    public void drawLighting(Graphics g){
        int sourceX = (int)player.getX() + (int)player.getRadius();
        int sourceY = (int)player.getY() + (int)player.getRadius();

        for(int i = 0; i < pointList.size(); i++){
            g.drawLine(sourceX, sourceY, pointList.get(i).getX(), pointList.get(i).getY());
        }
    }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.black);
        g.fill(back);
		debug.render();
		player.render(g);
        for(int i = 0; i < shapeList.size(); i++){
            g.setColor(Color.green);
            g.setAntiAlias(true);
            g.fill(shapeList.get(i));
        }
        drawLighting(g);
		actionBar.render(g, container);
	}

	@Override
	public int getID() {
		return state;
	}

}
