package GUI;

import java.awt.Font;
import java.net.InetSocketAddress;

import Entities.Player;
import org.newdawn.slick.TrueTypeFont;

import Game.Survivr;
import Network.NetworkDetails;

public class Debug {

	private Font Rawfont = new Font("asd Regular", Font.BOLD, 15);
	private TrueTypeFont font = new TrueTypeFont(Rawfont, true);
	
	private int players;
	private int ping;
	private InetSocketAddress address;
	private Player p;

	private boolean isDebug;

	public Debug() {
		this.isDebug = true;
	}

	public void toggle() {
		if (isDebug) {
			isDebug = false;
		} else {
			isDebug = true;
		}
	}

	public void update(Player p) {
		players = Survivr.details.clients;
		ping = Survivr.details.ping;
		address = Survivr.details.address;
		this.p = p;
	}

	public void render() {

		if (isDebug) {
			font.drawString(0, 0, "FPS: " + Survivr.app.getFPS());
			font.drawString(0, 15, "Width: " + Survivr.app.getWidth());
			font.drawString(0, 30, "Height: " + Survivr.app.getHeight());
			font.drawString(0, 45, "Players Connected: " + players);
			font.drawString(0, 60, "Ping: " + ping);
			font.drawString(0, 75, "Server Host: " + address);
			font.drawString(0, 90, "Server Port: " + "N/A");
			font.drawString(Survivr.V_WIDTH - 120, 0, "!DEBUG MODE!");
			font.drawString(0, 105, "X: " + p.getX() + "Y: " + p.getY());
			font.drawString(0, 120, "Username: " + p.getUsername());
		}

	}

}
