package States;
//
import Entities.Player;
import GUI.ActionBar;
import GUI.Debug;
import Game.Survivr;
import Entities.Tile;
import Network.NetworkPlayer;
import Renderer.LightSource;
import Renderer.shader.Shader;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

public class Play extends BasicGameState {

    private int state;
    private Rectangle back;
    private Rectangle rect;


    // Colours
    private Color backgroundColour = new Color(100, 150, 180);
    private Color boxColour = new Color(10, 90, 130);
    private Color lightAlpha = new Color(0f,0f,0f,0.7f);

    // GUI
    private Debug debug;
    private ActionBar actionBar;

    //Entities
    private Player player;
    private ArrayList<Shape> shapeList = new ArrayList<>();

    //World
    private ArrayList<Tile> scene;
    private Image tile;
    private ArrayList<LightSource> worldLights;
    private Shape alpha;

    private Shader wave;
    private Image img;

    private boolean isGridShowing;
    private float shift = 1.0f;

    public Play(int state) {
        this.state = state;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        back = new Rectangle(0, 0, Survivr.V_WIDTH, Survivr.V_HEIGHT);
        debug = new Debug();
        actionBar = new ActionBar(container);
        player = new Player();
        scene = new ArrayList<>();
        worldLights = new ArrayList<>();
        worldLights.add(new LightSource(0, 0, 400));
        alpha = new Rectangle(0, 0, Survivr.V_WIDTH / 2, Survivr.V_HEIGHT);

        wave = Shader.makeShader("data/basic.vrt", "data/basic.frg");
        rect = new Rectangle(400, 400, 400, 400);

        tile = new Image("res\\game\\tile.png");

        populateShapes(5);
        generateScene();

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        checkInput(game);
        debug.update(player);
        actionBar.update(delta);
        player.update(delta);
        // temporary! move the world light with the player
        worldLights.get(0).update((int)player.getX() + (player.getWidth() / 2), (int)player.getY() + (player.getHeight() / 2));
        shift += delta/150.0f;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(backgroundColour);
        g.fill(back);

        // Lighting rendering
       //

        renderGrid(g);
        drawLighting(g);
        drawShapes(g);


        // Player rendering
        player.render(g);
        drawNetworkPlayers(g);

        //GUI rendering
        debug.render();
        actionBar.render(g, container);

        wave.startShader();
        g.fill(rect);

        Shader.forceFixedShader();

    }

    private void drawLighting(Graphics g) {
        for(int i = 0; i < worldLights.size(); i++){
            alpha = worldLights.get(i).render(shapeList, g, alpha);
        }

        g.setColor(lightAlpha);
        g.fill(alpha);
        alpha = new Rectangle(0, 0, Survivr.V_WIDTH, Survivr.V_HEIGHT);
    }

    private void renderGrid(Graphics g){
        if(isGridShowing){
            for(Tile t : scene){
                t.render(g);
            }
        }
    }

    private void checkInput(StateBasedGame game){
        if (Survivr.input.isKeyPressed(Input.KEY_F1))
            debug.toggle();
        else if (Survivr.input.isKeyPressed(Input.KEY_ESCAPE))
            game.enterState(Survivr.menu);
        else if(Survivr.input.isKeyPressed(Input.KEY_F5)){
            if(isGridShowing){
                isGridShowing = false;
            }else{
                isGridShowing = true;
            }
        }
    }

    private void populateShapes(int count) {
        // create random shapes
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            shapeList.add(new Rectangle(rand.nextInt(Survivr.V_WIDTH), rand.nextInt(Survivr.V_HEIGHT), 50, 50));
        }
    }

    private void generateScene(){
        for (int x = 0; x < Survivr.V_WIDTH; x+=64){
            for(int y = 0; y < Survivr.V_HEIGHT; y+=64){
                scene.add(new Tile(x ,y, tile));
            }

        }
    }

    private void drawNetworkPlayers(Graphics g) {
        if (Survivr.details.players.size() > 0) {
            for (NetworkPlayer p : Survivr.details.players) {
                // Draw Player
                g.setColor(Color.red);
                Circle c = new Circle(0, 0, 20);
                c.setLocation(p.x, p.y);
                g.fill(c);

                // Draw username
                g.setColor(Color.yellow);
                g.drawString(p.username, p.x - 10, p.y - 40);
            }
        }
    }

    private void drawShapes(Graphics g) {
        for (int i = 0; i < shapeList.size(); i++) {
            g.setColor(boxColour);
            g.setAntiAlias(true);
            shapeList.get(i).setLocation(shapeList.get(i).getX() + Survivr.screen.offsetX, shapeList.get(i).getY()+Survivr.screen.offsetY);
            g.fill(shapeList.get(i));
        }
    }

    @Override
    public int getID() {
        return state;
    }

}
