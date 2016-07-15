package GUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;


public class MenuItem {
    private int x;
    private int y;
    private int height = 90;
    private int width = 90;
    private Rectangle item;

    private int padx;
    private int pady;
    private int pos;
    private int scroll;

    private boolean isInView = true;

    private Color colour = new Color(212, 212, 212, 230);

    public MenuItem(int x, int y, int padx, int pady){

        this.x = x;
        this.y = y;
        this.padx = padx;
        this.pady = pady;

        item = new Rectangle(this.x + this.padx, this.y + this.pady, height, width);
    }

    public void update(){
        item.setLocation(x, y);
    }

    public void render(Graphics g){

        if(isInView){
            g.setColor(colour);
            g.fill(item);
        }


    }

    public void scrollUp(){
        scroll -= 10;
    }

    public void scrollDown(){
        scroll += 10;
    }

    public void setPos(int posx, int posy ,MenuSection section){
            x = (int) (section.getX() + ((posx*width) + (padx*posx)) + padx*2);
            y = (int) (section.getY() + ((posy*height)  + (pady*posy)) + pady*2 + scroll);

        if(y > section.getY() + section.getHeight() || y + height > section.getY() + section.getHeight()
                || y < section.getY()){
            isInView = false;
        }
        else{
            isInView = true;
        }

        item.setLocation(x, y);
    }
}
