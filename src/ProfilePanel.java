import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class ProfilePanel extends JPanel{
    private MainFrame mf;
    private JLabel settingLabel[] = new JLabel[3];
    private JLabel userLabel = new JLabel();
    private ImageIcon languageIcon[] = {new ImageIcon("img/java_btn.png"), new ImageIcon("img/c_btn.png"), new ImageIcon("img/python_btn.png")};
    private JButton languageBtn = new JButton();

    public ProfilePanel(MainFrame mf){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.WHITE);

        for(int i = 0; i<settingLabel.length; i++){
            settingLabel[i] = new JLabel(Integer.toString(0));
            settingLabel[i].setSize(100,20);
            settingLabel[i].setLocation(820+(i*100),60);
            add(settingLabel[i]);
        }
        userLabel.setText(mf.getNickName());
        userLabel.setSize(100,30);
        userLabel.setLocation(100,35);
        add(userLabel);

        JButton editBtn = new JButton(new ImageIcon("img/edit_btn.png"));
        editBtn.setPressedIcon(new ImageIcon("img/edit_pbtn.png"));
        editBtn.setSize(30,30);
        editBtn.setLocation(200,40);
        editBtn.setBorderPainted(false);
        editBtn.setContentAreaFilled(false);
        editBtn.setFocusPainted(false);
        editBtn.addActionListener(new ChangeName());
        editBtn.addKeyListener(new BackToMainEvent());
        add(editBtn);

        switch(mf.getLanguage()){
            case "Java":
            languageBtn.setIcon(languageIcon[0]);
            break;
            case "C":
            languageBtn.setIcon(languageIcon[1]);
            break;
            case "Python":
            languageBtn.setIcon(languageIcon[2]);
            break;
        }

        languageBtn.setSize(130,30);
        languageBtn.setLocation(100,80);
        languageBtn.setBorderPainted(false);
        languageBtn.setContentAreaFilled(false);
        languageBtn.setFocusPainted(false);
        languageBtn.addActionListener(new ChangeLanguageEvent());
        languageBtn.addKeyListener(new BackToMainEvent());
        add(languageBtn);

        JLabel recordLabel = new JLabel("언어별 기록"); 
        recordLabel.setFont(recordLabel.getFont().deriveFont(15.0f));
        recordLabel.setSize(100,20);
        recordLabel.setLocation(70,250);
        add(recordLabel);

        JButton backBtn = new JButton(new ImageIcon("img/w_back_btn.png"));
        backBtn.setPressedIcon(new ImageIcon("img/w_back_pbtn.png"));
        backBtn.setSize(50,50);
        backBtn.setLocation(30,700);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(new BackToMainBtnEvent());
        add(backBtn);

        JButton helpBtn = new JButton(new ImageIcon("img/w_help_btn.png"));
        helpBtn.setPressedIcon(new ImageIcon("img/w_help_pbtn.png"));
        helpBtn.setSize(50,50);
        helpBtn.setLocation(1110,700);
        helpBtn.setBorderPainted(false);
        helpBtn.setContentAreaFilled(false);
        helpBtn.setFocusPainted(false);
        helpBtn.addActionListener(new helpBtnEvent());
        add(helpBtn);

        JButton exitBtn = new JButton(new ImageIcon("img/w_exit_btn.png"));
        exitBtn.setPressedIcon(new ImageIcon("img/w_exit_pbtn.png"));
        exitBtn.setSize(25,25);
        exitBtn.setLocation(1160,5);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new ExitPro());
        add(exitBtn);

        JButton miniBtn = new JButton(new ImageIcon("img/w_mini_btn.png"));
        miniBtn.setPressedIcon(new ImageIcon("img/w_mini_pbtn.png"));
        miniBtn.setSize(25,25);
        miniBtn.setLocation(1120,5);
        miniBtn.setBorderPainted(false);
        miniBtn.setContentAreaFilled(false);
        miniBtn.setFocusPainted(false);
        miniBtn.addActionListener(new MinimizneWindows());
        add(miniBtn);

        this.addKeyListener(new BackToMainEvent());
        setSize(1200,800);
        setVisible(true);
    }
    public void paintComponent(Graphics g){
        ImageIcon main_bg = new ImageIcon("img/profile_back.png");
        g.drawImage(main_bg.getImage(),0,0,null);
        setOpaque(false);
        super.paintComponent(g);
    }

    class ChangeName implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String name = JOptionPane.showInputDialog("변경할 닉네임을 입력해주세요. (8자리 이하)");
                while(true){
                    if(name.length()>8){
                        JOptionPane.showMessageDialog(null, "8자리 이하로 입력해주세요.", "자릿수 초과", JOptionPane.WARNING_MESSAGE);
                        name = JOptionPane.showInputDialog("변경할 닉네임을 입력해주세요. (8자리 이하)");
                    }
                    else if(name.length()==0){
                        JOptionPane.showMessageDialog(null, "최소 1글자 이상 입력해주세요.", "자릿수 미만", JOptionPane.WARNING_MESSAGE);
                        name = JOptionPane.showInputDialog("변경할 닉네임을 입력해주세요. (8자리 이하)");
                    }
                    else break;
                }
                if(name!=null){
                    userLabel.setText(name);
                    mf.setNickName(name);
                }
                    
            }catch(Exception ex){

            }
        }
    }
    class ChangeLanguageEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton b = (JButton)e.getSource();
            Container p = (Container)e.getSource();
            switch(mf.ChangeLanguage()){
                case "Java":
                b.setIcon(languageIcon[0]);
                invalidate();
                break;
                case "C":
                b.setIcon(languageIcon[1]);
                invalidate();
                break;
                case "Python":
                b.setIcon(languageIcon[2]);
                invalidate();
                break;
            }
        }

    }
    public void ChangeBtnAnimation(JButton btn,int cnt){
        if(cnt == 0)
            btn.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,1), new SoftBevelBorder(SoftBevelBorder.LOWERED)));
        else
            btn.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,1), new BevelBorder(BevelBorder.RAISED)));
    }
    
    class QuestionDialog extends JDialog{
        public QuestionDialog(JFrame frame, String title){
            super(frame, title);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(600,600);
            addKeyListener(new KeyAdapter(){
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                        QuestionDialog.this.dispose();
                    }
                }
            });
        }
    }

    class helpBtnEvent implements ActionListener{
        private QuestionDialog dialog = null;
        public void actionPerformed(ActionEvent e){
            if(dialog == null){
                dialog = new QuestionDialog(mf, "도움말");
                dialog.addWindowListener(new WindowAdapter(){
                    public void windowClosed(WindowEvent e){
                        dialog = null;
                        mf.change("MainToProfile");
                    }
                });
                dialog.setVisible(true);
            }
        }
    }

    class BtnAnimationEvent extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            JButton btn = (JButton)e.getSource();
            ChangeBtnAnimation(btn, 0);
        }

        public void mouseReleased(MouseEvent e){
            JButton btn = (JButton)e.getSource();
            ChangeBtnAnimation(btn, 1);
        }
    }
    
    class BackToMainBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("BackToMain");
        }
    }

    class BackToMainEvent extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                mf.change("BackToMain");

            }
        }
    }
    class MinimizneWindows implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.setState(1);
        }
    }
    class ExitPro implements ActionListener{
        public void actionPerformed(ActionEvent e){
           mf.ExitP();
        }
    }
}