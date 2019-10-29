import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class WordPracticePanel extends JPanel{
    private final char key_lay1[] = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'};
    private final char key_lay2[] = {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'};
    private final char key_lay3[] = {'z', 'x', 'c', 'v', 'b', 'n', 'm'};
    private final char key_lay4[] = {'1','2','3','4','5','6','7','8','9','0','_'};
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
    private JLabel doneLabel;
    private JLabel typo_Cnt;
    private JLabel accuracy_Per;
    private int total = 0;
    private int index = 0;
    private final int max = 100;
    private JLabel charImage;
    private JLabel shiftImage;
    private char ch;
    private java.util.List<String> tmp = new ArrayList<String>();
    private java.util.List<String> text = new ArrayList<String>();

    public WordPracticePanel(MainFrame mf, String language){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.WHITE);
        tmp.clear();
        total = tmp.size();
        FileReader fin = null;
        try{
            switch(language){
                case "Java":
                fin = new FileReader("txt/java/word.txt");
                break;
                case "C":
                fin = new FileReader("txt/c/word.txt");
                break;
                case "Python":
                fin = new FileReader("txt/python/word.txt");
                break;
            }
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
            if(text.contains(tmp.get(rNum))){
                i--;
                continue;
            }
            text.add(tmp.get(rNum));
        }

        progress_bar = new JProgressBar(0, 100);
        progress_bar.setSize(80,10);
        progress_bar.setLocation(95,90);
        progress_bar.setForeground(Color.BLACK);
        progress_bar.setBorderPainted(false);
        add(progress_bar);

        progress_Per = new JLabel(Integer.toString(progress)+"%");
        progress_Per.setSize(100,30);
        progress_Per.setLocation(180,80);
        add(progress_Per);


        typo_bar = new JProgressBar(0, 100);
        typo_bar.setSize(80, 10);
        typo_bar.setLocation(265, 90);
        typo_bar.setForeground(Color.BLACK);
        typo_bar.setBorderPainted(false);
        add(typo_bar);
        
        typo_Cnt = new JLabel(Integer.toString(typo));
        typo_Cnt.setSize(100,30);
        typo_Cnt.setLocation(355,80);
        add(typo_Cnt);


        accuracy_bar = new JProgressBar(0, 100);
        accuracy_bar.setSize(80, 10);
        accuracy_bar.setLocation(435,90);
        accuracy_bar.setForeground(Color.BLACK);
        accuracy_bar.setBorderPainted(false);
        add(accuracy_bar);

        accuracy_Per = new JLabel(Integer.toString(accuracy)+"%");
        accuracy_Per.setSize(100,30);
        accuracy_Per.setLocation(525,80);
        add(accuracy_Per);

        questionLabel = new JLabel();
        questionLabel.setText(text.get(index));
        questionLabel.setSize(400,30);
        questionLabel.setLocation(400,180);
        questionLabel.setFont(questionLabel.getFont().deriveFont(15.0F));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        add(questionLabel);

        questionLabel2 = new JLabel();
        questionLabel2.setText(text.get(index+1));
        questionLabel2.setForeground(Color.GRAY);
        questionLabel2.setSize(250,30);
        questionLabel2.setLocation(830,180);
        questionLabel2.setFont(questionLabel.getFont().deriveFont(15.0F));
        questionLabel2.setHorizontalAlignment(JLabel.CENTER);
        add(questionLabel2);

        doneLabel = new JLabel();
        doneLabel.setForeground(Color.GRAY);
        doneLabel.setSize(250,30);
        doneLabel.setLocation(100,180);
        doneLabel.setFont(questionLabel.getFont().deriveFont(15.0F));
        doneLabel.setHorizontalAlignment(JLabel.CENTER);
        add(doneLabel);

        typeLabel = new JLabel();
        typeLabel.setSize(400,30);
        typeLabel.setLocation(400,240);
        typeLabel.setFont(typeLabel.getFont().deriveFont(15.0F));
        typeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(typeLabel);

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

        charImage = new JLabel(new ImageIcon("img/keyPress.png"));
        charImage.setSize(25,25);
        add(charImage);

        shiftImage = new JLabel(new ImageIcon("img/keyPress.png"));
        shiftImage.setSize(25,25);
        shiftImage.setLocation(115,535);
        shiftImage.setVisible(false);
        add(shiftImage);

        JLabel keyBoardImg = new JLabel(new ImageIcon("img/keyboard.png"));
        keyBoardImg.setSize(1200,800);
        keyBoardImg.setLocation(1,80);
        add(keyBoardImg);

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
        
        ch = questionLabel.getText().charAt(0);
        KeyImageChange(ch);
        this.addKeyListener(new TypeEvent());
        this.addKeyListener(new BackToMainEvent());
        setFocusable(true);
        setSize(1200,800);
        setVisible(true);
    }

    public void paintComponent(Graphics g){
        ImageIcon main_bg = new ImageIcon("img/word_back.png");
        g.drawImage(main_bg.getImage(),0,0,null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public void KeyImageChange(char ch){
        for(int i =0; i< key_lay4.length; i++){
            if(ch == key_lay4[i]){
                charImage.setLocation(180+(i*70),340);
                return;
            }
        }

        if(ch+32 >='a' && ch+32 <='z') shiftImage.setVisible(true);
        else shiftImage.setVisible(false);

        for(int i = 0; i< key_lay1.length; i++){
            if(ch>='A' && ch<='Z'){
                if(ch + 32 == key_lay1[i]){
                    charImage.setLocation(216+(i*70),400);
                    return;}
            }else if(ch>='a' && ch<='z'){
                if(ch == key_lay1[i]){
                    charImage.setLocation(216+(i*70),400);
                    return;}
            }
        }
        for(int i = 0; i< key_lay2.length; i++){
            if(ch>='A' && ch<='Z'){
                if(ch + 32 == key_lay2[i]){
                    charImage.setLocation(231+(i*70),470);
                    return;}
            }else if(ch>='a' && ch<='z'){
                if(ch == key_lay2[i]){
                    charImage.setLocation(231+(i*70),470);
                    return;}
            }
        }
        for(int i = 0; i< key_lay3.length; i++){
            if(ch>='A' && ch<='Z'){
                if(ch + 32 == key_lay3[i]){
                    charImage.setLocation(265+(i*70),535);
                    return;
                }
            }else if(ch>='a' && ch<='z'){
                if(ch == key_lay3[i]){
                    charImage.setLocation(265+(i*70),535);
                    return;
                }
            }
        }
    }

    class BackToMainBtnEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("BackToMain");
        }
    }

    class TypeEvent extends KeyAdapter{
        public void keyTyped(KeyEvent e){
            int key = e.getKeyChar();
            if(key== KeyEvent.VK_BACK_SPACE){ // 백스페이스 입력 시 실행
                
                if(typeLabel.getText().length()>0){ // 입력된 텍스트가 있을 경우
                    typeLabel.setText(typeLabel.getText().substring(0, typeLabel.getText().length()-1)); // 입력창에 입력값 중 마지막 문자 삭제
                    ch = questionLabel.getText().charAt(typeLabel.getText().length()); // 다음에 입력할 문자를 다시 받아옴
                    KeyImageChange(ch); // 키보드 하이라이트 이미지 위치 변경
                }
                if(questionLabel.getText().substring(0, typeLabel.getText().length()).equals(typeLabel.getText())) // 한문자씩 올바르게 입력했는지
                    typeLabel.setForeground(Color.BLACK); // 맞으면 입력창 글자색 검정으로 변경
                else
                    typeLabel.setForeground(Color.RED); // 틀리면 입력창 글자색 빨강으로 변경
            }

            else if(key == KeyEvent.VK_ENTER){ // 엔터 입력 시
                index++; //다음 문제 단어 받을 준비함.
                if(questionLabel.getText().equals(typeLabel.getText())){
                }
                else{ // 문제 단어와 입력창 같이 다를 경우
                    typo_bar.setValue(++typo); // 오타수 증가시킨 후 progress_bar 값 증가
                    typo_Cnt.setText(Integer.toString(typo)); // 오타수 Label Text 재지정
                }
                progress = 100*index/max; // 진행율 계산
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
                    doneLabel.setText(questionLabel.getText());
                    questionLabel.setText(questionLabel2.getText()); // 아직 진행중일 경우
                    if(index==max-1) questionLabel2.setText(""); //마지막 문제일 경우 다음 문제 단어 표시 안함
                    else questionLabel2.setText(text.get(index+1)); //마지막 문제가 아닐 경우 다음 문제 단어 표시
                    typeLabel.setText(""); // 재입력 받을 준비함.
                    KeyImageChange(questionLabel.getText().charAt(0)); //다음 문제 입력 첫 문자를 키보드 하이라이트에 표시
                }
            }
            else{
                 
                if(questionLabel.getText().length() == typeLabel.getText().length()){ // 문제 단어 텍스트 길이와 입력창 텍스트 길이가 같을 경우
                    e.consume(); // 입력 값를 전달하지 않음.
                    e.setKeyChar((char)KeyEvent.VK_ENTER); // 현재 키보드 이벤트를 엔터 눌렀을 때로 변경
                    keyTyped(e); // 이벤트 재실행
                }else
                    typeLabel.setText(typeLabel.getText()+e.getKeyChar()); // 길이가 다를 경우 입력된 값을 입력창에 추가

                if(questionLabel.getText().length() > typeLabel.getText().length()) // 끝나는 문자인지 아닌지 판별
                    ch = questionLabel.getText().charAt(typeLabel.getText().length()); // 다음에 입력할 문자를 가져옴
                    KeyImageChange(ch); //끝나는 문자가 아닐 경우 키보드 하이라이트 이미지 위치 변경

                if(questionLabel.getText().substring(0, typeLabel.getText().length()).equals(typeLabel.getText())) // 한문자씩 올바르게 입력했는지
                    typeLabel.setForeground(Color.BLACK); // 맞으면 입력창 글자색 검정으로 변경
                else
                    typeLabel.setForeground(Color.RED); // 틀리면 입력창 글자색 빨강으로 변경
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
                        mf.change("MainToWord");
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