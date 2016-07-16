package Entities;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;


public class Tile {

    private int width = 64;
    private int height = 64;
    private Rectangle rect;
    private Image img;
    private int x;
    private int y;

    public Tile(int x, int y, Image img){
        this.img = img;
        this.x = x;
        this.y = y;
        rect = new Rectangle(x, y, width, height);
    }

    public void update(){

    }

    public void render(Graphics g){
        g.drawImage(img, x, y);
    }
}
