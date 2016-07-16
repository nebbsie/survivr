package Entities;

import Network.Packets.Packet04ClientUpdate;
import com.esotericsoftware.kryonet.Connection;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import Game.Survivr;

import java.util.ArrayList;

public class Player {

    private ArrayList<Projectile> projectiles;

    private Input input;

    private String username;

    private Circle player;
    public Circle lightCircle;

    private Image img;

    private float x;
    private float y;

    private int width = 40;
    private int height = 40;

    // Movement and angle
    private float targetAng;
    private Vector2f direction;
    private float speed;
    private float maxSpeed;
    private boolean walking;


    public Player() {

        // Load player texture
        try {
            img = new Image("res\\game\\player.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        // Setup player position
        x = Survivr.V_WIDTH / 2 - 10;
        y = Survivr.V_HEIGHT / 2 - 10;

        // Lighting??
        lightCircle = new Circle(x + (width / 2), y + (height / 2), 300);

        // Create player
        this.username = Survivr.NAME;
        this.walking = false;
        this.maxSpeed = 0.2f;
        player = new Circle(x, y, width, height);

        // Load input
        input = Survivr.app.getInput();
        projectiles = new ArrayList<>();
    }

    public void update(int delta) {
        setPointingAngle();
        setWalkingDirection(delta);
        checkForWalking();
        checkAction();
        walk();
        updateProjectiles(delta);
        updateServer();
    }

    public void render(Graphics g) {
        img.draw(x, y);
        lightCircle.setCenterX(x + (width / 2));
        lightCircle.setCenterY(y + (height / 2));
        renderProjectiles(g);
        g.draw(lightCircle);
    }

    private void renderProjectiles(Graphics g){
        for(Projectile p : projectiles){
            p.render(g);
        }
    }

    private void updateProjectiles(int delta){
        for(Projectile p : projectiles){
            p.update(delta);
        }
    }

    private void shoot(){
        System.out.println("new projectile");
        projectiles.add(new Projectile(x + width/2, y + height/2));
    }

    private void walk() {
        if (walking) {
            x += speed * Math.cos(Math.toRadians(direction.getTheta()));
            y += speed * Math.sin(Math.toRadians(direction.getTheta()));
            player.setLocation(x, y);
        }
    }

    private void checkAction(){
        if(input.isKeyPressed(Input.KEY_SPACE)){
            shoot();
        }
    }

    private void checkForWalking() {
        if (input.isKeyDown(Input.KEY_W)) {
            walking = true;
        } else {
            walking = false;
        }
    }

    private void setPointingAngle() {
        targetAng = (float) getTargetAngle(x, y, input.getMouseX(), input.getMouseY());
        img.setRotation(targetAng);
    }

    private void setWalkingDirection(int delta) {
        direction = new Vector2f(input.getMouseX() - x - width / 2, input.getMouseY() - y - height / 2);
        direction.getTheta();
        speed = maxSpeed * delta;
    }

    private void updateServer() {
        Packet04ClientUpdate p = new Packet04ClientUpdate();
        p.x = x;
        p.y = y;
        if (Survivr.details.connection != null)
            Survivr.details.connection.sendTCP(p);
    }

    public double getDegAngleTo(int xE, int yE) {
        int xS = (int) x + (width / 2);
        int yS = (int) y + (height / 2);
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

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
    public int getAmountOfProjectiles(){
        return projectiles.size();
    }
}
