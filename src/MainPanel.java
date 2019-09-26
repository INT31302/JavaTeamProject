import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MainPanel extends JPanel{
    private MainFrame mf;
    private JButton []menuBtn = new JButton[4];
    public MainPanel(MainFrame mf){
        this.mf = mf;

        setBackground(Color.WHITE);
        setLayout(null);

        JButton profileBtn = new JButton("Profile");
        profileBtn.setSize(100,30);
        profileBtn.setLocation(45,30);
        //profileBtn.setBorderPainted(false);
        profileBtn.setContentAreaFilled(false);
        profileBtn.setFocusPainted(false);
        profileBtn.addActionListener(new MainToProfileBtnEvent());
        add(profileBtn);

        for(int i = 0; i< menuBtn.length; i++){
            menuBtn[i] = new JButton(new ImageIcon("img/main_btn.png"));
            menuBtn[i].setPressedIcon(new ImageIcon("img/main_Pbtn.png"));
            menuBtn[i].setBorderPainted(false);
            menuBtn[i].setContentAreaFilled(false);
            menuBtn[i].setFocusPainted(false);
            menuBtn[i].setSize(250,500);
            menuBtn[i].setLocation(45+(i*280),150);
            add(menuBtn[i]);
        }
        menuBtn[0].addActionListener(new MainToWordBtnEvent());

        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(new ExitProgram());
    }
    class MainToProfileBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("MainToProfile");
        }
    }

    class MainToWordBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("MainToWord");
        }
    }

    class ExitProgram extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                int num = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
                if(num == 0)
                System.exit(0);
            }
        }
    }
}