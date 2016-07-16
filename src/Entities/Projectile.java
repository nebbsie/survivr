package Entities;


import Game.Survivr;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Projectile {

    private float x;
    private float y;
    private Rectangle rect;
    private int size = 5;
    private double angle;
    private double dx;
    private double dy;
    private float velocity = 1;

    public Projectile(float x, float y){
        this.x = x;
        this.y = y;

        this.angle = Math.atan2(Survivr.input.getMouseX() - x, Survivr.input.getMouseY() - y);
        this.dy = (velocity) * Math.cos(angle);
        this.dx = (velocity) * Math.sin(angle);

        rect = new Rectangle(x, y,size, size);
    }

    public void update(int delta){
        x += dx * delta;
        y += dy * delta;
        rect.setLocation(x, y);
    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fill(rect);
        g.setColor(Color.white);
    }

}
