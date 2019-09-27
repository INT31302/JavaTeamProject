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

        JButton profileBtn = new JButton(new ImageIcon("img/pro_btn.png"));
        profileBtn.setPressedIcon(new ImageIcon("img/pro_pbtn.png"));
        profileBtn.setSize(120,48);
        profileBtn.setLocation(45,30);
        profileBtn.setBorderPainted(false);
        profileBtn.setContentAreaFilled(false);
        profileBtn.setFocusPainted(false);
        profileBtn.addActionListener(new MainToProfileBtnEvent());
        add(profileBtn);

        for(int i = 0; i< menuBtn.length; i++){
            menuBtn[i] = new JButton();
            menuBtn[i].setBorderPainted(false);
            menuBtn[i].setContentAreaFilled(false);
            menuBtn[i].setFocusPainted(false);
            menuBtn[i].setSize(250,500);
            menuBtn[i].setLocation(45+(i*280),150);
            add(menuBtn[i]);
        }
        menuBtn[0].setIcon(new ImageIcon("img/word_btn.png"));
        menuBtn[0].setPressedIcon(new ImageIcon("img/word_pbtn.png"));
        menuBtn[1].setIcon(new ImageIcon("img/sen_btn.png"));
        menuBtn[1].setPressedIcon(new ImageIcon("img/sen_pbtn.png"));
        menuBtn[2].setIcon(new ImageIcon("img/so_btn.png"));
        menuBtn[2].setPressedIcon(new ImageIcon("img/so_pbtn.png"));
        menuBtn[3].setIcon(new ImageIcon("img/game_btn.png"));
        menuBtn[3].setPressedIcon(new ImageIcon("img/game_pbtn.png"));
        menuBtn[0].addActionListener(new MainToWordBtnEvent());
        menuBtn[1].addActionListener(new MainToSenBtnEvent());

        JButton exitBtn = new JButton(new ImageIcon("img/exit_btn.png"));
        exitBtn.setPressedIcon(new ImageIcon("img/exit_pbtn.png"));
        exitBtn.setSize(25,25);
        exitBtn.setLocation(1160,5);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new ExitPro());
        add(exitBtn);

        JButton miniBtn = new JButton(new ImageIcon("img/mini_btn.png"));
        miniBtn.setPressedIcon(new ImageIcon("img/mini_pbtn.png"));
        miniBtn.setSize(25,25);
        miniBtn.setLocation(1120,5);
        miniBtn.setBorderPainted(false);
        miniBtn.setContentAreaFilled(false);
        miniBtn.setFocusPainted(false);
        miniBtn.addActionListener(new MinimizneWindows());
        add(miniBtn);

        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(new ExitProgram());
    }
    public void paintComponent(Graphics g){
        ImageIcon main_bg = new ImageIcon("img/main_back.png");
        g.drawImage(main_bg.getImage(),0,0,null);
        setOpaque(false);
        super.paintComponent(g);
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
    class MainToSenBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("MainToSen");
        }
    }

    class MinimizneWindows implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.setState(1);
        }
    }
    public void ExitP(){
        int num = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
            if(num == 0)
            System.exit(0);
    }
    class ExitPro implements ActionListener{
        public void actionPerformed(ActionEvent e){
            ExitP();
        }
    }
    class ExitProgram extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                ExitP();
            }
        }
    }
}