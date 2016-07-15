package Entities;

import Network.Packets.Packet04ClientUpdate;
import com.esotericsoftware.kryonet.Connection;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import Game.Survivr;

public class Player {
	
	private Circle player;
	private Input input;
	private String username;
	
	private Vector2f pos;
	private float x;
	private float y;
	private float dx = 0;
	private float dy = 0;
	private float speed = 0.2f;
	
	public Player(GameContainer container, String username){
		x = Survivr.V_WIDTH/2-10;
		y = Survivr.V_HEIGHT/2-10;
		player = new Circle(x,y,20);
		input = container.getInput();
		this.username = username;
	}
	
	public void update(int delta){

		
		if(input.isKeyDown(Input.KEY_W)){

			dy -= speed;
		}else if(input.isKeyDown(Input.KEY_S)){
			dy += speed;
		}
		
		if(input.isKeyDown(Input.KEY_A)){
			dx -= speed;

		}else if(input.isKeyDown(Input.KEY_D)){
			dx += speed;
		}
		
		x += dx * delta;
		y += dy * delta;
		
		player.setLocation(x, y);
		reset();
		updateServer();
	}
	
	public void render(Graphics g){
		g.setColor(new Color(240, 240, 240));
		g.fill(player);
	}

	private void updateServer(){
		Packet04ClientUpdate p = new Packet04ClientUpdate();
		p.x = x;
		p.y = y;

		if(Survivr.details.connection != null){
			Survivr.details.connection.sendTCP(p);
		}
	}
	
	private void reset(){
		dx = 0;
		dy = 0;
	}

	public float getX(){return x;}
	public float getY(){return y;}
	public float getRadius(){return player.getRadius();}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
