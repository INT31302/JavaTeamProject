import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainPanel extends JPanel{
    private MainFrame mf;
    public MainPanel(MainFrame mf){
        this.mf = mf;
        JButton remove = new JButton("remove");
        add(remove);
        this.addKeyListener(new ExitProGram());
        remove.addActionListener(new deletePanel());
    }
    class deletePanel implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("MainToProfile");
        }
    }
    class ExitProGram extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                int num = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
                if(num == 0)
                System.exit(0);
            }
        }
    }
}