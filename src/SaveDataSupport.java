import Figures.Figure;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class SaveDataSupport implements Serializable {
    ArrayList<Figure> figures;
    String filePath, imagePath;

    public SaveDataSupport(WorkPanel workPanel){
        figures = workPanel.figures;
        imagePath = workPanel.imagePath;
    }
}
