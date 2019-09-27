import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class SentensePracticePanel extends JPanel{
    private MainFrame mf;
    private int progress = 0;
    private int typo = 0;
    private int accuracy = 0;
    private JLabel progress_Per;
    private JProgressBar progress_bar;
    private JProgressBar typo_bar;
    private JProgressBar accuracy_bar;
    private JLabel questionLabel;
    private JLabel questionLabel2;
    private JLabel typeLabel;
    private JLabel typo_Cnt;
    private JLabel accuracy_Per;
    private int total = 0;
    private int index = 0;
    private final int max = 100;
    public SentensePracticePanel(MainFrame mf){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel progress_Title = new JLabel("진행도");
        progress_Title.setSize(100,30);
        progress_Title.setLocation(50,80);
        add(progress_Title);

        progress_bar = new JProgressBar(0, 100);
        progress_bar.setSize(80,10);
        progress_bar.setLocation(95,90);
        progress_bar.setForeground(Color.BLACK);
        //progress_bar.setBorderPainted(false);
        add(progress_bar);

        progress_Per = new JLabel(Integer.toString(progress)+"%");
        progress_Per.setSize(100,30);
        progress_Per.setLocation(185,80);
        add(progress_Per);

        JLabel typo_Title = new JLabel("오타수");
        typo_Title.setSize(100,30);
        typo_Title.setLocation(220,80);
        add(typo_Title);

        typo_bar = new JProgressBar(0, 100);
        typo_bar.setSize(80, 10);
        typo_bar.setLocation(265, 90);
        typo_bar.setForeground(Color.BLACK);
        //typo_bar.setBorderPainted(false);
        add(typo_bar);
        
        typo_Cnt = new JLabel(Integer.toString(typo));
        typo_Cnt.setSize(100,30);
        typo_Cnt.setLocation(355,80);
        add(typo_Cnt);

        JLabel accuracy_Title = new JLabel("정확도");
        accuracy_Title.setSize(100,30);
        accuracy_Title.setLocation(390,80);
        add(accuracy_Title);

        accuracy_bar = new JProgressBar(0, 100);
        accuracy_bar.setSize(80, 10);
        accuracy_bar.setLocation(435,90);
        //accuracy_bar.setBorderPainted(false);
        add(accuracy_bar);

        accuracy_Per = new JLabel(Integer.toString(accuracy)+"%");
        accuracy_Per.setSize(100,30);
        accuracy_Per.setLocation(525,80);
        add(accuracy_Per);

        JButton backBtn = new JButton(new ImageIcon("img/back_btn.png"));
        backBtn.setPressedIcon(new ImageIcon("img/back_pbtn.png"));
        backBtn.setSize(50,50);
        backBtn.setLocation(30,700);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(new BackToMainBtnEvent());
        add(backBtn);

        JButton helpBtn = new JButton(new ImageIcon("img/help_btn.png"));
        helpBtn.setPressedIcon(new ImageIcon("img/help_pbtn.png"));
        helpBtn.setSize(50,50);
        helpBtn.setLocation(1110,700);
        helpBtn.setBorderPainted(false);
        helpBtn.setContentAreaFilled(false);
        helpBtn.setFocusPainted(false);
        helpBtn.addActionListener(new helpBtnEvent());
        add(helpBtn);

        this.addKeyListener(new BackToMainEvent());
        setFocusable(true);
        setSize(1200,800);
        setVisible(true);
    }

    class BackToMainBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("BackToMain");
        }
    }

    class BackToMainEvent extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(index != 0)
                    JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE);
                mf.change("BackToMain");

            }
        }
    }

    class QuestionDialog extends JDialog{
        public QuestionDialog(JFrame frame, String title){
            super(frame, title);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(600,600);
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
                    }
                });
                dialog.setVisible(true);

            }
        }
    }
}