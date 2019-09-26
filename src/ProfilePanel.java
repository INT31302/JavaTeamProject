import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class ProfilePanel extends JPanel{
    private MainFrame mf;
    private String sName[] = {"평균타수", "목표타수", "정확도"};
    private JLabel settingLabel[] = new JLabel[3];
    private JLabel userLabel = new JLabel("user");
    private ImageIcon languageIcon[] = {};
    private JButton languageBtn = new JButton("change");

    public ProfilePanel(MainFrame mf){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.WHITE);

        for(int i = 0; i<sName.length; i++){
            JLabel la = new JLabel(sName[i]);
            la.setSize(100,20);
            la.setLocation(800+(i*100),30);
            add(la);
            settingLabel[i] = new JLabel(Integer.toString(0));
            settingLabel[i].setSize(100,20);
            settingLabel[i].setLocation(820+(i*100),60);
            add(settingLabel[i]);
        }
        userLabel.setSize(100,30);
        userLabel.setLocation(100,35);
        add(userLabel);

        JButton editBtn = new JButton();
        editBtn.setSize(30,30);
        editBtn.setLocation(200,40);
        //editBtn.setBorderPainted(false);
        editBtn.setContentAreaFilled(false);
        editBtn.setFocusPainted(false);
        editBtn.addActionListener(new ChangeName());
        add(editBtn);

        languageBtn.setSize(130,30);
        languageBtn.setLocation(100,80);
        //languageBtn.setBorderPainted(false);
        languageBtn.setContentAreaFilled(false);
        languageBtn.setFocusPainted(false);
        add(languageBtn);


        JLabel recordLabel = new JLabel("언어별 기록"); 
        recordLabel.setFont(recordLabel.getFont().deriveFont(15.0f));
        recordLabel.setSize(100,20);
        recordLabel.setLocation(70,250);
        add(recordLabel);

        JButton backBtn = new JButton(new ImageIcon("img/test.png"));
        backBtn.setSize(50,50);
        backBtn.setLocation(30,700);
        backBtn.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,1), new BevelBorder(BevelBorder.RAISED)));
        //backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(new BackToMainBtnEvent());
        backBtn.addMouseListener(new BtnAnimationEvent());
        add(backBtn);

        JButton questionBtn = new JButton(new ImageIcon("img/test.png"));
        questionBtn.setSize(50,50);
        questionBtn.setLocation(1110,700);
        questionBtn.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,1), new BevelBorder(BevelBorder.RAISED)));
        //questionBtn.setBorderPainted(false);
        questionBtn.setContentAreaFilled(false);
        questionBtn.setFocusPainted(false);
        questionBtn.addActionListener(new QuestionBtnEvent());
        questionBtn.addMouseListener(new BtnAnimationEvent());
        add(questionBtn);

        this.addKeyListener(new BackToMainEvent());
        setSize(1200,800);
        setVisible(true);
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
                if(name!=null)
                    userLabel.setText(name);
            }catch(Exception ex){

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
        }
    }

    

    class QuestionBtnEvent implements ActionListener{
        private QuestionDialog dialog = null;
        public void actionPerformed(ActionEvent e){
            if(dialog == null){
                dialog = new QuestionDialog(mf, "도움말");
                dialog.addWindowListener(new WindowAdapter(){
                    public void windowClosed(WindowEvent e){
                        dialog = null;
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
}