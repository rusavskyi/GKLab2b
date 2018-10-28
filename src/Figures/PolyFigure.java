package Figures;

import java.awt.*;

public class PolyFigure extends Figure{
    Polygon polygon;

    @Override
    public void create(int l, int r, int t, int b, Color color, int lw) {
        super.create(l, r, t, b, color, lw);
        polygon = new Polygon();

    }

    @Override
    public void draw(Graphics2D g) {
        g.draw(polygon);
    }

    @Override
    public void moveVertex(int x, int y){
        super.moveVertex(x, y);
        //polygon.setFrame(left, top, Math.abs(right - left), Math.abs(top - bot));
    }
}
