package Renderer;

import Game.Survivr;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.opengl.pbuffer.FBOGraphics;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FBORenderer {

    private FBOGraphics fbo;
    private Image img;
    private float shift = 1f;


    public FBORenderer() {

        try {
            img = new Image(Survivr.V_WIDTH, Survivr.V_HEIGHT);
            fbo = new FBOGraphics(img);

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void update(int delta){
        shift += delta/1000f;
    }

    public void setPixel(int posx, int posy, Color color){
        try{
            Graphics g2 = img.getGraphics();
            g2.setColor(color);
            g2.fillRect(posx,posy,1,1);
            g2.flush();
        }catch (SlickException s){
            s.printStackTrace();
        }
    }

    public void render(){

        Survivr.app.getGraphics().drawImage(img, 0, 0);
    }
    public void renderBlur() throws SlickException, IOException {
        Survivr.app.getGraphics().drawImage(convertFast(), 0, 0);
    }

    public void draw(Shape shape, Color color){
        fbo.setColor(color);
        fbo.fill(shape);
    }

    public Image getIMG(){
        return img;
    }

    public void destroy(){
        fbo.destroy();
    }

    // do not use very slow -45secs
    public void convert() throws SlickException, IOException {
        BufferedImage buff = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();

        for(int x = 0 ; x < 1280; x ++){
            for(int y = 0; y < 720; y++){

                Color Rcol = g.getPixel(x, y);
                int red = Rcol.getRed();
                int green = Rcol.getGreen();
                int blue = Rcol.getBlue();

                int col = (red << 16) | (green << 8) | blue;
                buff.setRGB(x, y, col);
            }
        }

        ImageIO.write(buff, "JPEG", new File("test.jpg"));
    }

    public Image convertFast() throws SlickException, IOException {
        File file = new File("img.PNG");
        FileOutputStream stream = new FileOutputStream(file);

        ImageIOWriter writer = new ImageIOWriter();
        writer.saveImage(img, "PNG", stream, false);
        stream.close();

        BufferedImage buff;
        buff = ImageIO.read(new File("img.PNG"));

        ImageIO.write(blur(buff), "PNG", new File("blurred.PNG"));

        Image blur = new Image("blurred.PNG");

        return blur;

    }

    public BufferedImage blur(BufferedImage buff){
        int radius = 4;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage i = op.filter(buff, null);

        return i;
    }
}