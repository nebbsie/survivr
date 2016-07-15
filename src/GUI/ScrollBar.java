package GUI;


import Game.Survivr;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class ScrollBar {

    private Rectangle scrollBar;

    private int maxY;
    private int minY;

    private int scrollX;
    private int scrollY;

    private int width;
    private int height;

    private int scrollAmount = 0;
    private boolean scrollLimit = false;

    private boolean up = false;
    private boolean down = false;
    private boolean selected = false;


    public ScrollBar(MenuSection menu){
        scrollX = (int) (menu.getX() + menu.getWidth()+ 5);
        scrollY = menu.getFinalY();

        System.out.println(scrollX);
        System.out.println(scrollY);

        width = 20;
        height = 40;

        this.maxY = scrollY;
        this.minY = scrollY + menu.getHeight();

        scrollBar = new Rectangle(scrollX, scrollY, width, height);
    }

    public void update(){

        reset();
        int scroll = Mouse.getDWheel();
        int mouseX = Survivr.input.getAbsoluteMouseX();
        int mouseY = Survivr.input.getAbsoluteMouseY();

        int dx = Mouse.getDX();
        int dy = Mouse.getDY();


        if(isAbove(mouseX, mouseY) && Survivr.input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
            selected = true;

        }if(selected && Survivr.input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){

            if(mouseY + width/2 + 8 < minY && mouseY - width/2 - 8 > maxY){
                scrollY = mouseY - height/2;

            }

        }


        if(scroll > 0){
            if(scrollY > maxY){
                scrollY -= 10;
                up = true;
            }
        }else if(scroll < 0){
            if(scrollY + height < minY) {
                scrollY += 10;
                down = true;
            }
        }



        scrollBar.setLocation(scrollX, scrollY);
    }

    public boolean isAbove(int x, int y){
        if((x >= scrollX && x <= scrollX+width) && (y >= scrollY && y <= scrollY+height)){
            return true;
        }

        return false;
    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.fill(scrollBar);
    }

    public void reset(){
        up = false;
        down = false;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
