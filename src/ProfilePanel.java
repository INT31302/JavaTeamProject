import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;

public class ProfilePanel extends JPanel{
    private MainFrame mf;
    private JLabel settingLabel[] = new JLabel[3];
    private JLabel userLabel = new JLabel();
    private ImageIcon languageIcon[] = {new ImageIcon("img/java_btn.png"), new ImageIcon("img/c_btn.png"), new ImageIcon("img/python_btn.png")};
    private JButton languageBtn = new JButton();
    private String nickName;
    private int goalValue;
    private int sum = 0;
    private int accuracyValue;
    private ArrayList<Integer> avgType = new ArrayList<>();

    public ProfilePanel(MainFrame mf){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.WHITE);
        avgType = mf.getAvgType();
        for(int i = 0; i<settingLabel.length; i++){
            settingLabel[i] = new JLabel(Integer.toString(0));
            settingLabel[i].setSize(100,20);
            settingLabel[i].setLocation(780+(i*100),60);
            settingLabel[i].setHorizontalAlignment(JLabel.CENTER);
            add(settingLabel[i]);
        }
        for(int i = 0; i<avgType.size(); i++){
            sum += avgType.get(i);
        }
        settingLabel[0].setText(Integer.toString(sum/avgType.size()));
        settingLabel[1].setText(Integer.toString(mf.getGoalType()));
        settingLabel[2].setText(Integer.toString(mf.getGoalAccuracyValue()));
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


        JButton backBtn = new JButton(new ImageIcon("img/p_back_btn.png"));
        backBtn.setPressedIcon(new ImageIcon("img/p_back_pbtn.png"));
        backBtn.setSize(50,50);
        backBtn.setLocation(30,700);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(new BackToMainBtnEvent());
        add(backBtn);

        JButton helpBtn = new JButton(new ImageIcon("img/p_help_btn.png"));
        helpBtn.setPressedIcon(new ImageIcon("img/p_help_pbtn.png"));
        helpBtn.setSize(50,50);
        helpBtn.setLocation(1110,700);
        helpBtn.setBorderPainted(false);
        helpBtn.setContentAreaFilled(false);
        helpBtn.setFocusPainted(false);
        helpBtn.addActionListener(new helpBtnEvent());
        add(helpBtn);

        JButton exitBtn = new JButton(new ImageIcon("img/p_exit_btn.png"));
        exitBtn.setPressedIcon(new ImageIcon("img/p_exit_pbtn.png"));
        exitBtn.setSize(25,25);
        exitBtn.setLocation(1160,5);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new ExitPro());
        add(exitBtn);

        JButton miniBtn = new JButton(new ImageIcon("img/p_mini_btn.png"));
        miniBtn.setPressedIcon(new ImageIcon("img/p_mini_pbtn.png"));
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
    private int showMultipleInputMessageDialog(){
        JLabel la1 = new JLabel("변경할 닉네임을 입력해주세요. (8자리 이하)");
        JTextField tf1 = new JTextField(userLabel.getText() ,10);
        JLabel la2 = new JLabel("목표 타수를 입력해주세요. (100이상 1000이하)");
        JTextField tf2 = new JTextField(settingLabel[1].getText(), 10);
        JLabel la3 = new JLabel("목표 정확도를 입력해주세요. (50이상 100이하)");
        JTextField tf3 = new JTextField(settingLabel[2].getText(), 10);

        Object[] inputFields = {la1, tf1, la2, tf2, la3, tf3};
        int option = JOptionPane.showConfirmDialog(this,inputFields,"사용자 설정", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(option == JOptionPane.OK_OPTION){
            nickName = tf1.getText();
            if(tf2.getText() == "") goalValue = 100;
            else goalValue = Integer.parseInt(tf2.getText());
            if(tf3.getText() == "")  accuracyValue = 90;
            else accuracyValue = Integer.parseInt(tf3.getText());
        }
        return option;
    }
    class ChangeName implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(showMultipleInputMessageDialog()==0)
            {
                while(true){
                    if(nickName.length()>8){
                        JOptionPane.showMessageDialog(null, "별명을 8자리 이하로 입력해주세요.", "자릿수 초과", JOptionPane.WARNING_MESSAGE);
                        if(showMultipleInputMessageDialog()!=0) break;
                    }
                    else if(nickName.length()==0){
                        JOptionPane.showMessageDialog(null, "별명을 최소 1글자 이상 입력해주세요.", "자릿수 미만", JOptionPane.WARNING_MESSAGE);
                        if(showMultipleInputMessageDialog()!=0) break;
                    }
                    else if(goalValue > 1000){
                        JOptionPane.showMessageDialog(null, "목표 타수를 1000 이하로 입력해주세요.", "자릿수 초과", JOptionPane.WARNING_MESSAGE);
                        if(showMultipleInputMessageDialog()!=0) break;
                    }
                    else if(goalValue < 100){
                        JOptionPane.showMessageDialog(null, "목표 타수를 최소 100 이상 입력해주세요.", "자릿수 미만", JOptionPane.WARNING_MESSAGE);
                        if(showMultipleInputMessageDialog()!=0) break;
                    }
                    else if(accuracyValue > 100){
                        JOptionPane.showMessageDialog(null, "목표 정확도를 100 이하로 입력해주세요.", "자릿수 초과", JOptionPane.WARNING_MESSAGE);
                        if(showMultipleInputMessageDialog()!=0) break;
                    }
                    else if(accuracyValue < 50){
                        JOptionPane.showMessageDialog(null, "목표 정확도를 50 이상 입력해주세요.", "자릿수 미만", JOptionPane.WARNING_MESSAGE);
                        if(showMultipleInputMessageDialog()!=0) break;
                    }
                    else break;
                }
                userLabel.setText(nickName);
                settingLabel[1].setText(Integer.toString(goalValue));
                settingLabel[2].setText(Integer.toString(accuracyValue));
                mf.setNickName(nickName);
                mf.setGoalType(goalValue);
                mf.setGoalAccuracyValue(accuracyValue);
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
            ImageIcon bg_img = new ImageIcon("img/p_help_img.png");
            JLabel bg = new JLabel(bg_img);
            bg.setSize(600,600);
            bg.setLocation(0, 10);
            add(bg);
            setSize(610,610);
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