package Renderer;
//
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

public class LightSource {
    private int x, y, pointX, pointY, lIndex, hIndex;
    public Circle lightRange;
    private double angles[][];
    private double low, high;
    private float inter1x, inter1y, inter2x, inter2y;
    private Polygon shadow;


    public LightSource(int x, int y, int radius){
        this.x = x;
        this.y = y;
        lightRange = new Circle(x, y, radius);

        // shadow vars
        angles = new double[4][4];
        shadow = new Polygon();
        // add four default points to the polygon for changing later
        for(int i = 0; i < 4; i++) {
            // both are i because it removes points that are the same
            shadow.addPoint(i, i);
        }
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

    public void solveShadow(Shape shape){
        // for all four points in the shape (assumes it will be a square)
        for (int j = 0; j < 4; j++) {
            // get the point
            pointX = (int) shape.getPoint(j)[0];
            pointY = (int) shape.getPoint(j)[1];
            // work out the angle from the light source to the point
            angles[j][0] = getDegAngleTo(pointX, pointY);
            angles[j][1] = j;
        }

        // default the highest and lowest angles for comparison
        low = angles[0][0];
        high = angles[0][0];
        lIndex = 0;
        hIndex = 0;

        // for all angles to the shape (again assumes a square)
        for (int j = 1; j < 4; j++) {
            // check if this angle is lower than before, record the index and value
            if (angles[j][0] < low) {
                low = angles[j][0];
                lIndex = j;
            }
            // check if the angle is higher than before, record the index and value
            if (angles[j][0] > high) {
                high = angles[j][0];
                hIndex = j;
            }
        }
        //g.drawLine(sourceX, sourceY, shapeList.get(i).getPoint(lIndex)[0], shapeList.get(i).getPoint(lIndex)[1]);
        //g.drawLine(sourceX, sourceY, shapeList.get(i).getPoint(hIndex)[0], shapeList.get(i).getPoint(hIndex)[1]);

        // find the points at which the angle line intersects with the light range circle
        // low angle intercept
        inter1x = x + (float)Math.sin(Math.toRadians(angles[lIndex][0])) * lightRange.getRadius();
        inter1y = y + (float)Math.cos(Math.toRadians(angles[lIndex][0])) * lightRange.getRadius();

        // high angle intercept
        inter2x = x + (float)Math.sin(Math.toRadians(angles[hIndex][0])) * lightRange.getRadius();
        inter2y = y + (float)Math.cos(Math.toRadians(angles[hIndex][0])) * lightRange.getRadius();

        // reset all polygon points for the next shadow
        shadow = new Polygon();
        shadow.addPoint(inter1x, inter1y);
        shadow.addPoint(inter2x, inter2y);
        shadow.addPoint(shape.getPoint(hIndex)[0], shape.getPoint(hIndex)[1]);
        shadow.addPoint(shape.getPoint(lIndex)[0], shape.getPoint(lIndex)[1]);

        // would be much better but doesn't seem to change the value
//        shadow.getPoint(0)[0] = inter1x;
//        shadow.getPoint(0)[1] = inter1y;
//        shadow.getPoint(1)[0] = inter2x;
//        shadow.getPoint(1)[1] = inter2y;
//        shadow.getPoint(2)[0] = shape.getPoint(hIndex)[0];
//        shadow.getPoint(2)[1] = shape.getPoint(hIndex)[1];
//        shadow.getPoint(3)[0] = shape.getPoint(lIndex)[0];
//        shadow.getPoint(3)[1] = shape.getPoint(lIndex)[1];
    }

    public Shape render(ArrayList<Shape>shapes, Graphics g, Shape alpha){
        //g.setColor(Color.black);
        //g.draw(lightRange);
        alpha = alpha.subtract(lightRange)[0];
        for(int i = 0; i < shapes.size(); i++){
            solveShadow(shapes.get(i));
            alpha = alpha.union(shadow)[0];
        }
        return alpha;
    }
}
