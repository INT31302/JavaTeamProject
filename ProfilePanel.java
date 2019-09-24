import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ProfilePanel extends JPanel{
    private MainFrame mf;
    public ProfilePanel(MainFrame mf){
        this.mf = mf;
        JLabel name = new JLabel("user");
        this.addKeyListener(new BackToMain());
        add(name);
        setSize(1200,800);
        setVisible(true);
    }

    class BackToMain extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                mf.change("ProfileToMain");

            }
        }
    }
}