import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ProfilePanel extends JPanel{
    private MainFrame mf;
    public ProfilePanel(MainFrame mf){
        this.mf = mf;
        J Label name = new JLabel("user");
        this.addKeyListener(new BackToMain());
        add(name);
        setSize(1200,800);
        setVisible(true);
    }

    class BackToMain extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            System.out.println("hi");
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                int num = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까???", "종료", JOptionPane.YES_NO_OPTION);
                if(num == 0)
                System.exit(0);
            }
        }
    }
}