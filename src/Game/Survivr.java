package Game;

import Network.NetworkClient;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Network.NetworkDetails;
import org.newdawn.slick.state.transition.Transition;

public class Survivr extends StateBasedGame {

	public static AppGameContainer app;
	public static NetworkDetails details;
	public static Screen screen;
	public static Input input;
	public static NetworkClient server;

	// Game parameters
	private final static String gamename = "Survivr";
	public static int WIDTH = 320;
	public static int HEIGHT = 180;
	public static int SCALE = 4; // 6 == 1080 4 == 720
	public static int V_WIDTH = WIDTH * SCALE;
	public static int V_HEIGHT = HEIGHT * SCALE;

	// Game settings
	private static boolean FULLSCREEN = false;
	private static int FPS = 60;
	private static boolean VSYNC = true;

	// Game states
	public static int menu = 0;
	public static int play = 1;
	public static int splash = 2;


	public Survivr(String name) {
		super(name);
		this.addState(new States.Menu(menu));
		this.addState(new States.Play(play));
		this.addState(new States.Splash(splash));
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		input = container.getInput();
		//this.getState(menu).init(container, this);
		this.getState(splash).init(container, this);
		//this.getState(play).init(container, this);

		this.enterState(splash);
	}

	public static void main(String[] args) {
		try {
			app = new AppGameContainer(new Survivr(gamename));
			details = new NetworkDetails();

			if (SCALE == 6) {
				FULLSCREEN = true;
			}
			app.setDisplayMode(WIDTH * SCALE, HEIGHT * SCALE, FULLSCREEN);
			//app.setTargetFrameRate(FPS);
			app.setAlwaysRender(true);
			app.setShowFPS(false);
			//app.setVSync(VSYNC);
			app.start();
		} catch (SlickException e) {

			e.printStackTrace();
		}
	}

}
