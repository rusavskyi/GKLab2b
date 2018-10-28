package Figures;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RectFigure extends Figure{
    Rectangle2D rectangle2D;

    @Override
    public void create(int l, int r, int t, int b, Color color, int lw) {
        super.create(l, r, t, b, color, lw);
        rectangle2D = new Rectangle2D.Double();
        rectangle2D.setFrame(left, top, Math.abs(right - left), Math.abs(top - bot));
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.draw(rectangle2D);
    }

    @Override
    public void moveVertex(int x, int y){
        super.moveVertex(x, y);
        rectangle2D.setFrame(left <= right ? left : right , top <= bot ? top : bot, Math.abs(right - left), Math.abs(top - bot));
    }

    @Override
    public void moveFigure(int xDelta, int yDelta){
        super.moveFigure(xDelta, yDelta);
        rectangle2D.setFrame(left, top,Math.abs(right - left), Math.abs(top - bot));
    }
}
