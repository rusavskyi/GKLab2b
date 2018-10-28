import Figures.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MainWindow implements ActionListener{

    JFrame frame;
    JPanel topPanel;
    JPanel rightPanel;
    JPanel bottomPanel;
    WorkPanel workSpace;
    JButton addRectangleButton;
    JButton addEllipceButton;
    JButton loadButton;
    JButton saveButton;
    JButton openImageButton;
    JFileChooser fileChooser;

    ArrayList<Figure> figures;

    public MainWindow(){
        frame = new JFrame();
        topPanel = new JPanel();
        rightPanel = new JPanel();
        bottomPanel = new JPanel();
        workSpace = new WorkPanel(this);
        addEllipceButton = new JButton("Ellipse");
        addRectangleButton = new JButton("Rectangle");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        openImageButton = new JButton("Open");
        figures = new ArrayList<>();
        fileChooser = new JFileChooser();

        openImageButton.setBackground(Color.gray);
        openImageButton.setBorderPainted(false);
        openImageButton.setFocusPainted(false);
        openImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory()){
                            return true;
                        } else {
                            String fileName = f.getName().toLowerCase();
                            return fileName.endsWith(".bmp") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
                        }
                    }

                    @Override
                    public String getDescription() {
                        return "Images (.bmp, .jpg)";
                    }
                });
                if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    try {
                        File f = fileChooser.getSelectedFile();
                        workSpace.imagePath = f.getPath();
                        workSpace.image = ImageIO.read(f);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    workSpace.figures = new ArrayList<>();
                    workSpace.repaint();
                }
            }
        });

        addRectangleButton.setBackground(Color.gray);
        addRectangleButton.setBorderPainted(false);
        addRectangleButton.setFocusPainted(false);
        addRectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workSpace.figureDraw = 0;
                workSpace.mode = 3;
                workSpace.loseFocus();
            }
        });

        addEllipceButton.setBackground(Color.gray);
        addEllipceButton.setBorderPainted(false);
        addEllipceButton.setFocusPainted(false);
        addEllipceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workSpace.figureDraw = 1;
                workSpace.mode = 3;
                workSpace.loseFocus();
            }
        });

        loadButton.setBackground(Color.gray);
        loadButton.setBorderPainted(false);
        loadButton.setFocusPainted(false);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory()){
                            return true;
                        } else {
                            String fileName = f.getName().toLowerCase();
                            return fileName.endsWith(".sds");
                        }
                    }

                    @Override
                    public String getDescription() {
                        return "SDS files (.sds)";
                    }
                });
                if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    try {
                        File f = fileChooser.getSelectedFile();
                        FileInputStream fileInputStream = new FileInputStream(f);
                        ObjectInputStream ois = new ObjectInputStream(fileInputStream);
                        SaveDataSupport sds = (SaveDataSupport) ois.readObject();
                        workSpace.image = ImageIO.read(new File(sds.imagePath));
                        workSpace.figures = sds.figures;
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                workSpace.repaint();
            }
        });

        saveButton.setBackground(Color.gray);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory()){
                            return true;
                        } else {
                            String fileName = f.getName().toLowerCase();
                            return fileName.endsWith(".sds");
                        }
                    }

                    @Override
                    public String getDescription() {
                        return "SDS files (.sds)";
                    }
                });

                    try {
                            File f = new File("D\\Desktop\\test.sds");
                            FileOutputStream fos = new FileOutputStream(f);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            SaveDataSupport sds = new SaveDataSupport(workSpace);
                            oos.writeObject(sds);

                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

        });

        topPanel.setBackground(Color.black);
        topPanel.add(openImageButton, BorderLayout.WEST, 0);
        topPanel.add(addRectangleButton, BorderLayout.WEST, 1);
        topPanel.add(addEllipceButton, BorderLayout.WEST, 2);
        topPanel.add(loadButton, BorderLayout.WEST, 3);
        topPanel.add(saveButton, BorderLayout.WEST, 4);

        rightPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setLayout(new GridLayout(15, 4, 5, 5));
        Color[] colors = new Color[52];
        int c = 0;
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                colors[c] = new Color(i * 21, j * 55, c * 4);
                c++;
            }
        }
        for(int i = 0; i < 52; i++){
            rightPanel.add(new ColorButton(colors[i], workSpace));
        }
        JButton tmp = new JButton("W +");
        tmp.setBorderPainted(false);
        tmp.setBackground(Color.gray);
        tmp.setFocusPainted(false);
        tmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workSpace.addLineWidth();
            }
        });
        rightPanel.add(tmp);

        bottomPanel.setBackground(Color.black);

        tmp = new JButton("W -");
        tmp.setBorderPainted(false);
        tmp.setBackground(Color.gray);
        tmp.setFocusPainted(false);
        tmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workSpace.deLineWidth();
            }
        });
        rightPanel.add(tmp);

        workSpace.setBackground(Color.white);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GK Lab 2");
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(workSpace, BorderLayout.CENTER);

        frame.setSize(1280,840);
        frame.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void onButtonMove_Click() {
        workSpace.mode = 1;
    }
}
