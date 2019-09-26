import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

public class WordPracticePanel extends JPanel{
    private MainFrame mf;
    private int progress = 0;
    private int typo = 0;
    private int accuracy = 0;
    private JLabel progress_Per;
    private JProgressBar progress_bar;
    private JProgressBar typo_bar;
    private JProgressBar accuracy_bar;
    private JLabel questionLabel;
    private JLabel typeLabel;
    private JLabel typo_Cnt;
    private JLabel accuracy_Per;
    private int total = 0;
    private int index = 0;
    private final int max = 100;
    private java.util.List<String> tmp = new ArrayList<String>();
    private java.util.List<String> text = new ArrayList<String>();

    public WordPracticePanel(MainFrame mf){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.WHITE);
        tmp.clear();
        total = tmp.size();
        FileReader fin = null;
        try{
            fin = new FileReader("txt/java.txt");
            BufferedReader bufReader = new BufferedReader(fin);
            String line = "";
            while((line = bufReader.readLine()) != null){
                tmp.add(line);
                total++;
            }
            bufReader.close();
        }catch(FileNotFoundException e){}
        catch(IOException e){
            System.out.println(e);
        }

        for(int i = 0; i<max; i++){
            int rNum = (int)(Math.random()*total);
            text.add(tmp.get(rNum));
        }

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

        questionLabel = new JLabel();
        questionLabel.setText(text.get(index));
        questionLabel.setSize(250,30);
        questionLabel.setLocation(300,200);
        questionLabel.setFont(questionLabel.getFont().deriveFont(15.0F));
        add(questionLabel);

        typeLabel = new JLabel();
        typeLabel.setSize(250,30);
        typeLabel.setLocation(300,250);
        typeLabel.setFont(typeLabel.getFont().deriveFont(15.0F));
        add(typeLabel);

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

        this.addKeyListener(new TypeEvent());
        this.addKeyListener(new BackToMainEvent());
        setFocusable(true);
        setSize(1200,800);
        setVisible(true);
    }
    public void ChangeBtnAnimation(JButton btn,int cnt){
        if(cnt == 0)
            btn.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,1), new SoftBevelBorder(SoftBevelBorder.LOWERED)));
        else
            btn.setBorder(new CompoundBorder(new LineBorder(Color.BLACK,1), new BevelBorder(BevelBorder.RAISED)));
    }

    class BackToMainBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("BackToMain");
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
    class TypeEvent extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key >= KeyEvent.VK_A && key <= KeyEvent.VK_Z|| key== KeyEvent.VK_SPACE || key == KeyEvent.VK_PERIOD || key >= KeyEvent.VK_0 && key<=KeyEvent.VK_9|| 
            e.getKeyChar() == '_'){
                typeLabel.setText(typeLabel.getText()+e.getKeyChar());

                if(questionLabel.getText().substring(0, typeLabel.getText().length()).equals(typeLabel.getText()))
                    typeLabel.setForeground(Color.BLACK);
                else
                    typeLabel.setForeground(Color.RED);
            }
            
            if(key== KeyEvent.VK_BACK_SPACE){
                if(typeLabel.getText().length()>0)
                typeLabel.setText(typeLabel.getText().substring(0, typeLabel.getText().length()-1));
                if(questionLabel.getText().substring(0, typeLabel.getText().length()).equals(typeLabel.getText()))
                    typeLabel.setForeground(Color.BLACK);
                else
                    typeLabel.setForeground(Color.RED);
            }
            if(key == KeyEvent.VK_ENTER){
                index++;
                if(questionLabel.getText().equals(typeLabel.getText())){
                }
                else{
                    typo_bar.setValue(++typo);
                    typo_Cnt.setText(Integer.toString(typo));
                }
                progress = 100*index/max;
                progress_Per.setText(Integer.toString(progress)+"%");
                progress_bar.setValue(progress);
                System.out.println(progress);

                accuracy = 100-(int)(100*typo/index);
                accuracy_bar.setValue(accuracy);
                accuracy_Per.setText(Integer.toString(accuracy)+"%");
                if(progress==100){
                    progress = 0;
                    JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE);
                    mf.change("BackToMain");

                }else{
                    questionLabel.setText(text.get(index));
                    typeLabel.setText("");
                }
                
                
            }
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
}