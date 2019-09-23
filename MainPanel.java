import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainPanel extends JPanel{
    private MainFrame mf;
    public MainPanel(MainFrame mf){
        this.mf = mf;
        JButton remove = new JButton("remove");
        add(remove);
        remove.addActionListener(new deletePanel());
        setVisible(true);
    }
    class deletePanel implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("MainToProfile");
        }
    }
}