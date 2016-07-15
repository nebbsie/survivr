package GUI;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tests.SoundTest;

import java.util.ArrayList;

public class MenuSection {

    private Rectangle area;

    private Input input;
    private ScrollBar scrollBar;

    private int finalX;
    private int finalY;

    private float x;
    private float y;

    private int width = 300;
    private int height = 400;

    private boolean isSelected = false;
    private boolean isAnimating = false;
    private boolean showItems = false;

    //Items
    private ArrayList<MenuItem> items;
    private MenuItem item;
    private MenuItem item2;
    private MenuItem item3;
    private MenuItem item4;
    private MenuItem item5;
    private MenuItem item6;
    private MenuItem item7;
    private MenuItem item8;

    private Color colour = new Color(212, 212, 212, 230);

    public MenuSection(int x, int y, GameContainer container){

        input = container.getInput();

        this.finalX = x;
        this.finalY = y;




        this.y = finalY;
        this.x = finalX;

        reset();

        items = new ArrayList<>();
        area = new Rectangle(this.finalX, this.finalY, this.width, this.height);
        scrollBar = new ScrollBar(this);


    }

    public void setUP(){
        //y = finalY;
    }

    public void update(int delta){

        if(input.isKeyPressed(Input.KEY_F7)){
            items.add(new MenuItem(finalX, finalY, 5, 5));
        }


        if(isAnimating){
            showItems = false;
        }else if (!isAnimating && isSelected){
            showItems = true;
        }else{
            showItems = false;
        }

        if(isSelected){
            if(up()){
                y-=1*delta;
                isAnimating = true;
            }else{
                isAnimating = false;
                setUP();
            }
        }else{
            if (!down()){
                y+=1*delta;
                isAnimating = true;

            }else{
                reset();
                isAnimating = false;
            }
        }


        area.setLocation(x, y);

        if(showItems){
            int itX = 0;
            int itY = 0;
            for(MenuItem i : items){
                i.setPos(itX, itY ,this);
                itX++;
                if ( itX % 3 == 0 ) {
                    itY++;
                    itX = 0;
                }
            }
        }

        if(scrollBar.isUp()){
            items.forEach(MenuItem::scrollDown);
        }else if(scrollBar.isDown()){
            items.forEach(MenuItem::scrollUp);
        }


        items.forEach(MenuItem::update);
    }

    public void render(Graphics g){
        g.setColor(colour);
        g.fill(area);

        if(showItems){
            for(MenuItem i : items){
                i.render(g);
            }

        }

    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public void reset(){
        y = finalY + height + 100;
        x = finalX;
        isSelected = false;
        showItems = false;
    }

    public boolean up(){
        if(y <= finalY){
            return false; // returns false if y != if final form
        }else{
            return true; // retursn true id y == final form
        }
    }

    public boolean down(){
        if(y <=finalY + height + 100){
            return false; // returns false if y != if final form
        }else{
            return true; // retursn true id y == final form
        }
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getFinalY() {
        return finalY;
    }
}
