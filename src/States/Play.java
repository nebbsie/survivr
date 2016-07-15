package States;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Network.NetworkPlayer;
import Renderer.FBORenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.pbuffer.FBOGraphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Entities.Player;
import GUI.ActionBar;
import GUI.Debug;
import Game.Survivr;
import Network.NetworkClient;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Play extends BasicGameState {

	private int state;
    private Rectangle back;

	// Colours
	private Color backgroundColour = new Color(126, 178, 255);

	//Rendering
	private FBORenderer fbo;
	private boolean done;

	// Modules
	private Input input;
	private Rectangle light;

	private Image image;

	// GUI
	private Debug debug;
	private ActionBar actionBar;
	
	//Entities
	private Player player;
    private ArrayList<Shape> shapeList = new ArrayList<>();

	private Graphics imageGraphics;

	public Play(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		fbo = new FBORenderer();

        back = new Rectangle(0, 0, Survivr.V_WIDTH, Survivr.V_HEIGHT);

		light = new Rectangle(0, 0, 1280, 720);

		image = new Image(1280, 720);

        // create random shapes
        Random rand = new Random();
        for(int i = 0; i < 5; i++){
            shapeList.add(new Rectangle(rand.nextInt(Survivr.V_WIDTH), rand.nextInt(Survivr.V_HEIGHT), 50, 50));
        }

		debug = new Debug();
		input = container.getInput();
		actionBar = new ActionBar(container);
		player = new Player(container, "jerry");

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (input.isKeyPressed(Input.KEY_F1)) {
			debug.toggle();
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			game.enterState(Survivr.menu);
		}

		debug.update(player);
		actionBar.update(delta);
		player.update(delta);
		fbo.update(delta);
	}



    public void drawLighting(Graphics g){
        int sourceX = (int)player.getX() + player.getWidth()/2;
        int sourceY = (int)player.getY() + player.getHeight()/2;

		g.setColor(new Color(240, 240, 240));
        for(int i = 0; i < shapeList.size(); i++){
            // solve line angles from light source to points
            double [][] angles = new double[4][4];
            for(int j = 0; j < 4; j++){
                int pointX = (int)shapeList.get(i).getPoint(j)[0];
                int pointY = (int)shapeList.get(i).getPoint(j)[1];
                angles[j][0] = player.getDegAngleTo(pointX, pointY);
                angles[j][1] = j;
            }

            double low = angles[0][0];
            double high = angles[0][0];
            int lIndex = 0;
            int hIndex = 0;
            for(int j = 1; j < 4; j++){
                if(angles[j][0] < low){
                    low = angles[j][0];
                    lIndex = j;
                }
                if(angles[j][0] > high){
                    high = angles[j][0];
                    hIndex = j;
                }
            }

            g.drawLine(sourceX, sourceY, shapeList.get(i).getPoint(lIndex)[0], shapeList.get(i).getPoint(lIndex)[1]);
            g.drawLine(sourceX, sourceY, shapeList.get(i).getPoint(hIndex)[0], shapeList.get(i).getPoint(hIndex)[1]);
        }
    }

	public void drawNetworkPlayers(Graphics g){
		if(Survivr.details.players.size() > 0){
			for(NetworkPlayer p : Survivr.details.players){

				Circle c = new Circle(0, 0, 20);

				c.setLocation(p.x, p.y);

				g.fill(c);
				g.setColor(Color.yellow);
				g.drawString(p.username, p.x - 10, p.y-40);
			}

		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

		g.setColor(new Color(20, 112, 162));
		g.fill(back);

		drawLighting(g);
		player.render(g);

        for(int i = 0; i < shapeList.size(); i++){
            g.setColor(new Color(10, 90, 130));
            g.setAntiAlias(true);
            g.fill(shapeList.get(i));
        }

		g.setColor(Color.red);
		drawNetworkPlayers(g);

		g.setDrawMode(Graphics.MODE_NORMAL);

		debug.render();
		actionBar.render(g, container);

		g.drawImage(image, 0, 0);


		// FBO RENDERING
		//fbo.draw(new Rectangle(400, 400, 100, 100), Color.cyan);
		//fbo.render();

	}

	@Override
	public int getID() {
		return state;
	}

}
