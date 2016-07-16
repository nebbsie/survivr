package States;

import Game.Survivr;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Options extends BasicGameState {

    private Image background;

    private int state;

    private Sound mouseOver;
    private Sound mouseClick;

    private MouseOverArea vsync;

    private Image vsyncON;
    private Image vsyncOFF;
    private Image vsyncOver;




    public Options(int state) throws SlickException {
        this.state = state;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        vsyncON = new Image("res\\menu\\options\\vsyncON.png");
        vsyncOFF = new Image("res\\menu\\options\\vsync.png");
        vsyncOver = new Image("res\\menu\\options\\vsyncOver.png");

        vsync = new MouseOverArea(container,vsyncOFF, Survivr.V_WIDTH / 2 - 82, Survivr.V_HEIGHT / 2 - 100, 165, 37);
        vsync.setMouseOverImage(vsyncOver);
        vsync.setMouseDownImage(vsyncOver);

        if (Survivr.SCALE == 6) {
            background = new Image("res\\menu\\menuBackground.png");
        } else {
            background = new Image("res\\menu\\menuBackground720.png");
        }

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(Survivr.input.isKeyPressed(Input.KEY_ESCAPE)){
            game.enterState(Survivr.menu);
        }


        if(vsync.isMouseOver() && Survivr.input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            if(!Survivr.VSYNC){
                Survivr.VSYNC = true;
                vsync.setNormalImage(vsyncON);
                vsync.setMouseOverImage(vsyncON);
                container.setVSync(Survivr.VSYNC);
            }else{
                Survivr.VSYNC = false;
                vsync.setNormalImage(vsyncOFF);
                vsync.setMouseOverImage(vsyncOver);
                container.setVSync(Survivr.VSYNC);
            }
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0);
        vsync.render(container,g);
    }



    @Override
    public int getID() {
        return state;
    }
}
