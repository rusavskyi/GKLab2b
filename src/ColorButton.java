import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorButton extends JButton {
    Color color;
    WorkPanel wp;

    public ColorButton(Color c, WorkPanel workPanel){
        color = c;
        wp = workPanel;
        setPreferredSize(new Dimension(25, 25));
        setBackground(color);
        setBorderPainted(false);
        setFocusPainted(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wp.color = color;
            }
        });
    }

}
