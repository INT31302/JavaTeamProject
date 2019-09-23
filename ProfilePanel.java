import java.awt.*;
import javax.swing.*;

class ProfilePanel extends JPanel{
    private MainFrame mf;
    public ProfilePanel(MainFrame mf){
        this.mf = mf;
        JLabel name = new JLabel("user");
        add(name);
        setSize(1200,800);
        setVisible(true);
    }
}