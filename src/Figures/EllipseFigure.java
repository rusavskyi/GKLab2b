package Figures;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class EllipseFigure extends Figure{
    Ellipse2D ellipse2D;

    @Override
    public void create(int l, int r, int t, int b, Color color, int lw) {
        super.create(l, r, t, b, color, lw);
        ellipse2D = new Ellipse2D.Double();
        ellipse2D.setFrame(top, left, Math.abs(right - left), Math.abs(top - bot));
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.draw(ellipse2D);
    }

    @Override
    public void moveVertex(int x, int y){
        super.moveVertex(x, y);
        ellipse2D.setFrame(left <= right ? left : right , top <= bot ? top : bot, Math.abs(right - left), Math.abs(top - bot));
    }

    @Override
    public void moveFigure(int xDelta, int yDelta){
        super.moveFigure(xDelta, yDelta);
        ellipse2D.setFrame(left, top,Math.abs(right - left), Math.abs(top - bot));
    }
}
