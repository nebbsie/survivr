package Entities;

import Network.Packets.Packet04ClientUpdate;
import com.esotericsoftware.kryonet.Connection;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import Game.Survivr;

public class Player {

	private Input input;
	private String username;
	private Circle player;
	private Image img;

	private float x;
	private float y;
	private float dx = 0;
	private float dy = 0;

	private float speed = 0.2f;

	private int width = 40;
	private int height = 40;

	private float targetAng;
	private int tx;
	private int ty;

	private Circle lightCircle;

	public Player(GameContainer container, String username){
		this.username = username;
		x = Survivr.V_WIDTH/2-10;
		y = Survivr.V_HEIGHT/2-10;

		lightCircle = new Circle(x + (width/2), y + (height/2), 400);

		try {
			img  = new Image("res\\game\\player.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		player = new Circle(x, y, width, height);

		input = container.getInput();
	}

	public void update(int delta){
		//checkInput();

		targetAng = (float) getTargetAngle(x, y, input.getMouseX(), input.getMouseY());
		tx = (img.getWidth() / 2);
		ty = (img.getHeight() / 2);
		img.setRotation(targetAng);

		Vector2f direction = new Vector2f(input.getMouseX()  - x, input.getMouseY()  - y);
		direction.getTheta();

		speed = 0.1f * delta;

		if(input.isKeyDown(Input.KEY_W)){
			x += speed * Math.cos(Math.toRadians(direction.getTheta()));
			y += speed * Math.sin(Math.toRadians(direction.getTheta()));
		}

		player.setLocation(x, y);

		updateServer();
		reset();
	}
	
	public void render(Graphics g){
		img.draw(x, y);
		lightCircle.setCenterX(x + (width/2));
		lightCircle.setCenterY(y + (height/2));
		g.draw(lightCircle);
	}

	public void checkInput(){
		if(input.isKeyDown(Input.KEY_W))
			dy -= speed;
		else if(input.isKeyDown(Input.KEY_S))
			dy += speed;

		if(input.isKeyDown(Input.KEY_A))
			dx -= speed;
		else if(input.isKeyDown(Input.KEY_D))
			dx += speed;
	}

	private void updateServer(){
		Packet04ClientUpdate p = new Packet04ClientUpdate();
		p.x = x;
		p.y = y;

		if(Survivr.details.connection != null)
			Survivr.details.connection.sendTCP(p);
	}

	private void reset(){
		dx = 0;
		dy = 0;
	}

	public double getDegAngleTo(int xE, int yE){
		int xS = (int)x + (width / 2);
		int yS = (int)y + (height / 2);
		return Math.toDegrees(Math.atan2(xE - xS, yE - yS));
	}

	public double getDistanceBetween(float startX, float startY, float endX, float endY) {
		return Math.sqrt((Math.pow((endX - startX), 2)) + (Math.pow((endY - startY), 2)));
	}

	public double getTargetAngle(float startX, float startY, float targetX, float targetY) {
		double dist = getDistanceBetween(startX, startY, targetX, targetY);
		double sinNewAng = (startY - targetY) / dist;
		double cosNewAng = (targetX - startX) / dist;
		double angle = 0;

		if (sinNewAng > 0) {
			if (cosNewAng > 0) {
				angle = 90 - Math.toDegrees(Math.asin(sinNewAng));
			} else {
				angle = Math.toDegrees(Math.asin(sinNewAng)) + 270;
			}
		} else {
			angle = Math.toDegrees(Math.acos(cosNewAng)) + 90;
		}
		return angle;
	}

	public float getX(){return x;}
	public float getY(){return y;}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getUsername() {
		return username;
	}
}
