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
    private JProgressBar accuracy_bar;
    private JLabel questionLabel[];
    private JLabel typeLabel;
    private JLabel accuracy_Per;
    private JProgressBar currentType_bar;
    private JProgressBar goalType_bar;
    private JProgressBar highestType_bar;
    private JLabel currentType_Label;
    private JLabel goalType_Label;
    private JLabel highestType_Label;
    private int total = 0;
    private int index = 0;
    private char ch;
    private java.util.List<String> tmp = new ArrayList<String>();
    private java.util.List<String> text = new ArrayList<String>();
    TypeRunnable runnable = new TypeRunnable();
    Thread th = new Thread(runnable);
    public SentensePracticePanel(MainFrame mf, String language){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.WHITE);
        File files = new File("txt/java/sourceFile");
        switch(language){
            case "Java":
            files = new File("txt/java/sourceFile");
            break;
            case "C":
            files = new File("txt/c/sourceFile");
            break;
            case "Python":
            files = new File("txt/python/sourceFile");
            break;
        }
            File[] subFiles = files.listFiles();

            int ran = (int)(Math.random()*subFiles.length);
            File f = subFiles[ran];
            FileReader fin = null;
        try{
            fin = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(fin);
            String line = "";
            while((line = bufReader.readLine()) != null){
                tmp.add(line);
                total++;
            }
            int size = tmp.size();
            for(int i=0; i<size;){
                ran = (int)(Math.random()*tmp.size());
                if(text.contains(tmp.get(ran))) continue;
                if(tmp.get(ran).trim().equals("")) continue;
                if(tmp.get(ran).trim().length()==1)continue;
                else {
                    size--;
                    text.add(tmp.get(ran).trim());
                    i++;
                }
            }
            bufReader.close();
        }catch(FileNotFoundException e){}
        catch(IOException e){
            System.out.println(e);
        }
        
        progress_bar = new JProgressBar(0, 100);
        progress_bar.setSize(80,10);
        progress_bar.setLocation(95,90);
        progress_bar.setForeground(Color.BLACK);
        progress_bar.setBorderPainted(false);
        add(progress_bar);

        progress_Per = new JLabel(Integer.toString(progress)+"%");
        progress_Per.setSize(100,30);
        progress_Per.setLocation(185,80);
        add(progress_Per);

        accuracy_bar = new JProgressBar(0, 100);
        accuracy_bar.setSize(80, 10);
        accuracy_bar.setLocation(265,90);
        accuracy_bar.setForeground(Color.BLACK);
        accuracy_bar.setBorderPainted(false);
        add(accuracy_bar);

        accuracy_Per = new JLabel(Integer.toString(accuracy)+"%");
        accuracy_Per.setSize(100,30);
        accuracy_Per.setLocation(355,80);
        add(accuracy_Per);

        currentType_bar = new JProgressBar(0, 1000);
        currentType_bar.setSize(80, 10);
        currentType_bar.setLocation(450, 90);
        currentType_bar.setForeground(Color.BLACK);
        currentType_bar.setBorderPainted(false);
        add(currentType_bar);
        
        currentType_Label = new JLabel("0");
        currentType_Label.setSize(100,30);
        currentType_Label.setLocation(550,80);
        add(currentType_Label);

        goalType_bar = new JProgressBar(0, 1000);
        goalType_bar.setSize(80, 10);
        goalType_bar.setLocation(640, 90);
        goalType_bar.setForeground(Color.BLACK);
        goalType_bar.setBorderPainted(false);
        add(goalType_bar);

        goalType_Label = new JLabel("0");
        goalType_Label.setSize(100,30);
        goalType_Label.setLocation(740,80);
        add(goalType_Label);

        highestType_bar = new JProgressBar(0, 1000);
        highestType_bar.setSize(80, 10);
        highestType_bar.setLocation(830, 90);
        highestType_bar.setForeground(Color.BLACK);
        highestType_bar.setBorderPainted(false);
        add(highestType_bar);

        highestType_Label = new JLabel("0");
        highestType_Label.setSize(100,30);
        highestType_Label.setLocation(930,80);
        add(highestType_Label);

        questionLabel = new JLabel[5];
        for(int i = 0; i<questionLabel.length; i++){
            questionLabel[i] = new JLabel();
            questionLabel[i].setText(text.get(index+i));
            questionLabel[i].setSize(500,30);
            questionLabel[i].setLocation(350,225+(i*100));
            questionLabel[i].setFont(questionLabel[i].getFont().deriveFont(15.0F));
            questionLabel[i].setHorizontalAlignment(JLabel.CENTER);
            if(i!=0)questionLabel[i].setForeground(Color.GRAY);
            questionLabel[0].setLocation(350,180);
            add(questionLabel[i]);
        }

        typeLabel = new JLabel();
        typeLabel.setSize(500,30);
        typeLabel.setLocation(350,230);
        typeLabel.setFont(typeLabel.getFont().deriveFont(15.0F));
        typeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(typeLabel);

        JButton backBtn = new JButton(new ImageIcon("img/s_back_btn.png"));
        backBtn.setPressedIcon(new ImageIcon("img/s_back_pbtn.png"));
        backBtn.setSize(50,50);
        backBtn.setLocation(30,700);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(new BackToMainBtnEvent());
        add(backBtn);

        JButton helpBtn = new JButton(new ImageIcon("img/s_help_btn.png"));
        helpBtn.setPressedIcon(new ImageIcon("img/s_help_pbtn.png"));
        helpBtn.setSize(50,50);
        helpBtn.setLocation(1110,700);
        helpBtn.setBorderPainted(false);
        helpBtn.setContentAreaFilled(false);
        helpBtn.setFocusPainted(false);
        helpBtn.addActionListener(new helpBtnEvent());
        add(helpBtn);

        JButton exitBtn = new JButton(new ImageIcon("img/s_exit_btn.png"));
        exitBtn.setPressedIcon(new ImageIcon("img/s_exit_pbtn.png"));
        exitBtn.setSize(25,25);
        exitBtn.setLocation(1160,5);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new ExitPro());
        add(exitBtn);

        JButton miniBtn = new JButton(new ImageIcon("img/s_mini_btn.png"));
        miniBtn.setPressedIcon(new ImageIcon("img/s_mini_pbtn.png"));
        miniBtn.setSize(25,25);
        miniBtn.setLocation(1120,5);
        miniBtn.setBorderPainted(false);
        miniBtn.setContentAreaFilled(false);
        miniBtn.setFocusPainted(false);
        miniBtn.addActionListener(new MinimizneWindows());
        add(miniBtn);

        this.addKeyListener(new TypeEvent());
        this.addKeyListener(new BackToMainEvent());
        setFocusable(true);
        setSize(1200,800);
        setVisible(true);
    }
    class TypeRunnable implements Runnable{
        public void run(){
            int n =1000;
            while(true){
                currentType_Label.setText(Integer.toString(n));
                currentType_bar.setValue(n);
                if(n>0){
                    if(n-80<0) n = 0;
                    else n-=80;
                }
                try{
                    Thread.sleep(700);
                }catch(InterruptedException e){
                    return;
                }
            }
        }
    }
    public void paintComponent(Graphics g){
        ImageIcon main_bg = new ImageIcon("img/sen_back.png");
        g.drawImage(main_bg.getImage(),0,0,null);
        setOpaque(false);
        super.paintComponent(g);
    }
    class TypeEvent extends KeyAdapter{
        public void keyTyped(KeyEvent e){
            int key = e.getKeyChar();
            if(key== KeyEvent.VK_BACK_SPACE){ // 백스페이스 입력 시 실행
                
                if(typeLabel.getText().length()>0){ // 입력된 텍스트가 있을 경우
                    typeLabel.setText(typeLabel.getText().substring(0, typeLabel.getText().length()-1)); // 입력창에 입력값 중 마지막 문자 삭제
                    ch = questionLabel[0].getText().charAt(typeLabel.getText().length()); // 다음에 입력할 문자를 다시 받아옴
                }
                if(questionLabel[0].getText().substring(0, typeLabel.getText().length()).equals(typeLabel.getText())) // 한문자씩 올바르게 입력했는지
                    typeLabel.setForeground(Color.BLACK); // 맞으면 입력창 글자색 검정으로 변경
                else
                    typeLabel.setForeground(Color.RED); // 틀리면 입력창 글자색 빨강으로 변경
            }

            else if(key == KeyEvent.VK_ENTER){ // 엔터 입력 시
                th.interrupt();
                if(Integer.parseInt(highestType_Label.getText())<Integer.parseInt(currentType_Label.getText())){
                    highestType_Label.setText(currentType_Label.getText());
                    highestType_bar.setValue(currentType_bar.getValue());
                }
                th = new Thread(runnable);
                index++; //다음 문제 단어 받을 준비함.
                if(questionLabel[0].getText().equals(typeLabel.getText())){
                }
                progress = 100*index/total; // 진행율 계산
                progress_Per.setText(Integer.toString(progress)+"%");
                progress_bar.setValue(progress);

                accuracy = 100-(int)(100*typo/index); //정확도 계산
                accuracy_bar.setValue(accuracy);
                accuracy_Per.setText(Integer.toString(accuracy)+"%");
                if(progress==100){ // 모두 끝 마쳤을 경우
                    progress = 0;
                    JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE);
                    mf.change("BackToMain");
                }else{
                    for(int i = 0; i<questionLabel.length; i++){
                        questionLabel[i].setText(text.get(index+i));
                        typeLabel.setText("");
                    }
                }
            }
            else{
                if(questionLabel[0].getText().length() == typeLabel.getText().length()){  // 문제 문장 텍스트 길이와 입력창 텍스트 길이가 같을 경우
                    e.consume(); // 입력 값을 전달하지 않음
                    e.setKeyChar((char)KeyEvent.VK_ENTER); // 현재 키보드 이벤트를 엔터 눌렀을 때로 변경
                    keyTyped(e); // 이벤트 재실행
                }else{
                    typeLabel.setText(typeLabel.getText()+e.getKeyChar()); // 아닐경우 입력된 값을 입력창에 추가
                    if(th.getState() == Thread.State.NEW) th.start();
                }
                    

                if(questionLabel[0].getText().substring(0, typeLabel.getText().length()).equals(typeLabel.getText())){ // 한문자씩 올바르게 입력했는지
                    typeLabel.setForeground(Color.BLACK); // 맞으면 입력창 글자색 검정으로 변경
                    currentType_bar.setValue(currentType_bar.getValue()+60);
                    currentType_Label.setText(Integer.toString(currentType_bar.getValue()));
                }
                else
                    typeLabel.setForeground(Color.RED); // 틀리면 입력창 글자색 빨강으로 변경
            }
        }
    }

    class BackToMainBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("BackToMain");
        }
    }

    class BackToMainEvent extends KeyAdapter{
        public void keyTyped(KeyEvent e){
            if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
                typeLabel.setText(typeLabel.getText().substring(0, typeLabel.getText().length()-1));
                th.interrupt();
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
                        mf.change("MainToSen");
                    }
                });
                dialog.setVisible(true);
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

