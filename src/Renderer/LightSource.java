package Renderer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

public class LightSource {
    private int x, y, pointX, pointY, lIndex, hIndex;
    private Circle lightRange;
    private double angles[][];
    private double low, high;
    private float inter1x, inter1y, inter2x, inter2y;


    public LightSource(int x, int y, int radius){
        this.x = x;
        this.y = y;
        lightRange = new Circle(x, y, radius);

        // shadow vars
        angles = new double[4][4];

    }

    public void update(int x, int y){
        this.x = x;
        this.y = y;
        this.lightRange.setCenterX(x);
        this.lightRange.setCenterY(y);
    }

    public double getDegAngleTo(int xE, int yE) {
        return Math.toDegrees(Math.atan2(xE - x, yE - y));
    }

    public Polygon solveShadow(Shape shape){
        // solve line angles from light source to points
        for (int j = 0; j < 4; j++) {
            pointX = (int) shape.getPoint(j)[0];
            pointY = (int) shape.getPoint(j)[1];
            angles[j][0] = getDegAngleTo(pointX, pointY);
            angles[j][1] = j;
        }

        low = angles[0][0];
        high = angles[0][0];
        lIndex = 0;
        hIndex = 0;
        for (int j = 1; j < 4; j++) {
            if (angles[j][0] < low) {
                low = angles[j][0];
                lIndex = j;
            }
            if (angles[j][0] > high) {
                high = angles[j][0];
                hIndex = j;
            }
        }
        //g.drawLine(sourceX, sourceY, shapeList.get(i).getPoint(lIndex)[0], shapeList.get(i).getPoint(lIndex)[1]);
        //g.drawLine(sourceX, sourceY, shapeList.get(i).getPoint(hIndex)[0], shapeList.get(i).getPoint(hIndex)[1]);

        // get circle intercepts
        // low angle intercept
        inter1x = x + (float)Math.sin(Math.toRadians(angles[lIndex][0])) * lightRange.getRadius();
        inter1y = y + (float)Math.cos(Math.toRadians(angles[lIndex][0])) * lightRange.getRadius();

        // high angle intercept
        inter2x = x + (float)Math.sin(Math.toRadians(angles[hIndex][0])) * lightRange.getRadius();
        inter2y = y + (float)Math.cos(Math.toRadians(angles[hIndex][0])) * lightRange.getRadius();

        Polygon shadow = new Polygon();
        shadow.addPoint(inter1x, inter1y);
        shadow.addPoint(inter2x, inter2y);
        shadow.addPoint(shape.getPoint(hIndex)[0], shape.getPoint(hIndex)[1]);
        shadow.addPoint(shape.getPoint(lIndex)[0], shape.getPoint(lIndex)[1]);

        return shadow;
    }

    public void render(ArrayList<Shape>shapes, Graphics g){
        g.setColor(Color.black);
        g.draw(lightRange);
        for(int i = 0; i < shapes.size(); i++){
            g.fill(solveShadow(shapes.get(i)));
        }
    }
}
