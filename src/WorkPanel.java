import Figures.EllipseFigure;
import Figures.Figure;
import Figures.PolyFigure;
import Figures.RectFigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class WorkPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    MainWindow mainWindow;
    public ArrayList<Figure> figures;
    public Color color;
    public BufferedImage image;
    public String imagePath;
    public int figureSelected = -1; //-1 - draw mode
    public int figureDraw = 0; // 0 - rect, 1 - ellipse, 2 - poly
    public int mode = 0; // 0 - await, 1 - move, 2 - vertex, 3 - draw
    int prevX, prevY;
    int lineWidth = 1;

    public WorkPanel(MainWindow mw){
        mainWindow = mw;
        addMouseListener(this);
        addMouseMotionListener(this);
        figures = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();

        if (mode == 0) {
            int tmp = figureSelected;
            figureSelected = findFigure(e);
            if(tmp != -1 && tmp == figureSelected){
                if(figures.get(figureSelected).selectVertex(e.getX(), e.getY())){
                    mode = 2;
                }
                else {
                    mode = 1;
                }
            } else if (figureSelected != -1) {
                if(tmp != -1) {
                    figures.get(tmp).loseFocus();
                }
                figures.get(figureSelected).setFocus(true);
                //System.out.println(figures.get(figureSelected).selectVertex(e.getX(), e.getY()));
                if(figures.get(figureSelected).selectVertex(e.getX(), e.getY())){
                    mode = 2;
                }
                else {
                    mode = 1;
                }
            }
        } else if (mode == 1) {
            figureSelected = findFigure(e);
        } else if (mode == 2) {

        } else if (mode == 3) {
            drawFigure(e);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mode = 0;
        //figureSelected = -1;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mode == 2 || mode == 3){
            figures.get(figureSelected).moveVertex(e.getX(), e.getY());
        } else if(mode == 1 && figureSelected >= 0){
            figures.get(figureSelected).moveFigure(prevX - e.getX(), prevY - e.getY());
            prevX = e.getX();
            prevY = e.getY();
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("X: " + e.getX() + " Y: " + e.getY());
    }

    protected int findFigure(MouseEvent e) {
        int i = 0;
        if (figureSelected != -1 && figures.get(figureSelected).inZone(e.getX(), e.getY())) {
            return figureSelected;
        }
        for (Figure f : figures) {
            if (f.inZone(e.getX(), e.getY())) {
                return i;
            }
            i++;
        }
        i = 0;
        for (Figure f : figures) {
            if (f.selectVertex(e.getX(), e.getY())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(image != null) {
            int height, width, x ;
            if(getHeight() <= getWidth()) {
                x = image.getHeight() >= getHeight() ? image.getHeight() / getHeight() : getHeight() / image.getHeight();
                if (x < 1) x = 1;
                width = image.getHeight() >= getHeight() ? image.getWidth() / x : image.getWidth() * x;
                height = image.getHeight() >= getHeight() ? image.getHeight() / x : image.getHeight() * x;
            } else {
                x = image.getWidth() >= getWidth() ? image.getWidth() / getWidth() : getWidth() / image.getWidth();
                if (x < 1) x = 1;
                width = image.getHeight() >= getHeight() ? image.getWidth() / x : image.getWidth() * x;
                height = image.getHeight() >= getHeight() ? image.getHeight() / x : image.getHeight() * x;
            }
            ((Graphics2D) g).drawImage(image, 0, 0, width, height, new ImageObserver() {
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }
                    });
        }

        Graphics2D graphics2D = (Graphics2D) g;
        if (figures.size() > 0)
            for (Figure f : figures) {
                f.draw(graphics2D);
            }

    }

    private void drawFigure(MouseEvent e){
        //mode = 3;
        switch (figureDraw){
            case 0:
                figures.add(new RectFigure());
                figureSelected = figures.size() - 1;
                figures.get(figureSelected).create(e.getX(), e.getX(), e.getY(), e.getY(), color, lineWidth);
                break;
            case 1:
                figures.add(new EllipseFigure());
                figureSelected = figures.size() - 1;
                figures.get(figureSelected).create(e.getX(), e.getX(), e.getY(), e.getY(), color, lineWidth);
                break;
            case 2:
                figures.add(new PolyFigure());
                figureSelected = figures.size() - 1;
                figures.get(figureSelected).create(e.getX(), e.getX(), e.getY(), e.getY(), color, lineWidth);
                break;
            default:
                break;
        }
    }

    public void loseFocus()
    {
        if(figureSelected != -1) {
            figures.get(figureSelected).loseFocus();
            figureSelected = -1;
        }
    }

    public void addLineWidth(){
        if(lineWidth < 15){
            lineWidth++;
        }
    }

    public void deLineWidth(){
            if(lineWidth > 1){
                lineWidth--;
            }

    }
}
