package Figures;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by vrusa on 05.11.2017.
 */
public abstract class Figure {
    int top, bot, left, right;
    int vertexSelected;
    boolean focus = true;
    Rectangle2D v0, v1, v2, v3;
    Color color;
    int lineWidth;


    public Figure(){
        top = bot = left = right = 0;
        setFocus(true);
    }

    public Figure(int l, int r, int t, int b){
        top = t;
        bot = b;
        left = l;
        right = r;
        vertexSelected = 3;
        setFocus(true);
    }

    public void create(int l, int r, int t, int b, Color color, int lw){
        top = t;
        bot = b+1;
        left = l;
        right = r+1;
        vertexSelected = 3;
        setFocus(true);
        this.color = color;
        lineWidth = lw;
    }

    public void draw(Graphics2D g){
        if (focus){
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
            v0.setFrame(left - 3, top - 3, 6, 6);
            v1.setFrame(right - 3, top - 3, 6, 6);
            v2.setFrame(left - 3, bot - 3, 6, 6);
            v3.setFrame(right - 3, bot - 3, 6, 6);
            g.draw(v0);
            g.draw(v1);
            g.draw(v2);
            g.draw(v3);
        }
        g.setColor(color);
        g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
    }

    public boolean selectVertex(int x, int y){
        int step = 6;
        if(top >= y - step && left >= x - step && top <= y + step && left <= x + step){
            vertexSelected = 0;
            return true;
        } else if(top >= y - step && right >= x - step && top <= y + step && right <= x + step){
            vertexSelected = 1;
            return true;
        } else if(bot >= y - step && left >= x - step && bot <= y + step && left <= x + step){
            vertexSelected = 2;
            return true;
        } else if(bot >= y - step && right >= x - step && bot <= y + step && right <= x + step){
            vertexSelected = 3;
            return true;
        }
        return false;
    }

    public void moveVertex(int x, int y){
        if(vertexSelected == -1)
            selectVertex(x, y);
        switch (vertexSelected){
            case 0:
                top = y;
                left = x;
                break;
            case 1:
                top = y;
                right = x;
                break;
            case 2:
                bot = y;
                left = x;
                break;
            case 3:
                bot = y;
                right = x;
                break;
            default:
                //System.out.println("god damn it!");
                break;
        }
    }

    public boolean inZone(int x, int y){
        if(y >= top && y <= bot)
            if(x >= left && x<= right)
                return true;
        return false;
    }

    public void loseFocus(){
        vertexSelected = -1;
        focus = false;
        v0 = v1 = v2 = v3 = null;
    }

    public void setFocus(boolean f){
        focus = f;
        v0 = new Rectangle2D.Double();
        v1 = new Rectangle2D.Double();
        v2 = new Rectangle2D.Double();
        v3 = new Rectangle2D.Double();
    }

    public void moveFigure(int xDelta, int yDelta){
        left -= xDelta;
        right -= xDelta;
        top -= yDelta;
        bot -= yDelta;
    }
}
