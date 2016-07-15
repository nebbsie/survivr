package States;

import Game.Survivr;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Splash extends BasicGameState {

    private int state;
    private Image img;
    private float DELAY = 20;
    private float count = 0;

    public Splash(int state){
        this.state = state;
    }

    @Override
    public int getID() {
        return state;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        if(Survivr.SCALE == 6){
            img = new Image("res/splash/splash.png");
        }else{
            img = new Image("res/splash/splash720.png");
        }


    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        img.draw();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(count < DELAY){
            count+= 1*delta;
        }else{
            game.enterState(Survivr.menu);
        }
    }
}
