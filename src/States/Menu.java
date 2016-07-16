package States;

import Network.NetworkClient;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


import Game.Survivr;
import Menu.MenuButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;

public class Menu extends BasicGameState {

    private int state;

    //Network
    private NetworkClient server;
    private String username;

    //Menu GUI
    private MenuButtons menuGUI;

    private MouseOverArea connectButton;
    private MouseOverArea leaderboardButton;
    private MouseOverArea optionsButton;
    private MouseOverArea quitButton;


    //Modules
    private Input input;

    //Images
    private Image background;
    private Image crab;

    //Sounds
    private Sound mouseOver;
    private Sound mouseClick;
    private Music sound;

    //Fonts
    private Font Rawfont;
    private TrueTypeFont font;


    private boolean playCount;
    private boolean leaderboardCount;
    private boolean optionsCount;
    private boolean quitCount;
    private boolean clicked;
    private boolean held;
    private Float vol = 0.2f;

    public Menu(int state) {
        this.state = state;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        input = container.getInput();
        Rawfont = new Font("Anke", Font.PLAIN, 45);
        font = new TrueTypeFont(Rawfont, true);
        mouseOver = new Sound("res\\menu\\sounds\\mouseOver.ogg");
        mouseClick = new Sound("res\\menu\\sounds\\mouseClick.ogg");
        sound = new Music("res\\sounds\\menu.ogg");

        //sound.play();

        connect();


        crab = new Image("res\\menu\\crab.png");

        connectButton = new MouseOverArea(container, new Image("res\\menu\\connect.png"), Survivr.V_WIDTH / 2 - 91, Survivr.V_HEIGHT / 2 - 100, 183, 39);
        connectButton.setMouseOverImage(new Image("res\\menu\\connectOver.png"));
        connectButton.setMouseDownImage(new Image("res\\menu\\connectOver.png"));

        leaderboardButton = new MouseOverArea(container, new Image("res\\menu\\leaderboard.png"), Survivr.V_WIDTH / 2 - 151, Survivr.V_HEIGHT / 2 - 25, 303, 39);
        leaderboardButton.setMouseOverImage(new Image("res\\menu\\leaderboardOver.png"));
        leaderboardButton.setMouseDownImage(new Image("res\\menu\\leaderboardOver.png"));

        optionsButton = new MouseOverArea(container, new Image("res\\menu\\options.png"), Survivr.V_WIDTH / 2 - 85, Survivr.V_HEIGHT / 2 + 50, 170, 50);
        optionsButton.setMouseOverImage(new Image("res\\menu\\optionsOver.png"));
        optionsButton.setMouseDownImage(new Image("res\\menu\\optionsOver.png"));

        quitButton = new MouseOverArea(container, new Image("res\\menu\\quit.png"), Survivr.V_WIDTH / 2 - 46, Survivr.V_HEIGHT / 2 + 125, 92, 41);
        quitButton.setMouseOverImage(new Image("res\\menu\\quitOver.png"));
        quitButton.setMouseDownImage(new Image("res\\menu\\quitOver.png"));


        if (Survivr.SCALE == 6) {
            background = new Image("res\\menu\\menuBackground.png");
        } else {
            background = new Image("res\\menu\\menuBackground720.png");
        }
    }

    public void playMouseOver() {
        mouseOver.play(1f, vol);
    }

    public void playMouseClick() {
        mouseClick.play(1f, vol);
    }


    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            Survivr.app.exit();
        }

        if (!held) {
            connectButton.setAcceptingInput(true);
            quitButton.setAcceptingInput(true);
            leaderboardButton.setAcceptingInput(true);
            optionsButton.setAcceptingInput(true);
        }


        if (connectButton.isMouseOver() && playCount == false) {
            playMouseOver();
            playCount = true;

        } else if (connectButton.isMouseOver() == false)
            playCount = false;


        if (optionsButton.isMouseOver() && optionsCount == false) {
            playMouseOver();
            optionsCount = true;
        } else if (optionsButton.isMouseOver() == false) {
            optionsCount = false;
        }


        if (quitButton.isMouseOver() && quitCount == false) {
            playMouseOver();
            quitCount = true;
        } else if (quitButton.isMouseOver() == false)
            quitCount = false;


        if (leaderboardButton.isMouseOver() && leaderboardCount == false) {
            playMouseOver();
            leaderboardCount = true;
        } else if (leaderboardButton.isMouseOver() == false) {
            leaderboardCount = false;
        }


        if (connectButton.isMouseOver() && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            game.enterState(Survivr.play);
        }

        if (quitButton.isMouseOver() && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            container.exit();
        }


        // Check if mouse is clicked and play a sound
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && clicked == false) {
            playMouseClick();
            clicked = true;
            held = false;
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) == false) {
            clicked = false;
            held = false;
        } else {
            held = true;
            connectButton.setAcceptingInput(false);
            quitButton.setAcceptingInput(false);
            leaderboardButton.setAcceptingInput(false);
            optionsButton.setAcceptingInput(false);
        }


    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw();
        connectButton.render(container, g);
        leaderboardButton.render(container, g);
        optionsButton.render(container, g);
        quitButton.render(container, g);
        crab.draw(5, Survivr.V_HEIGHT - 46);
    }

    public void connect() {

        JFrame frame = new JFrame("Enter Name: ");
        Survivr.NAME = JOptionPane.showInputDialog(this);

        try {
            server = new NetworkClient(Survivr.NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getID() {
        return state;
    }

}
