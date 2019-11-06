import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class WordPracticePanel extends JPanel{
    private final char key_lay1[] = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'}; // 키 매핑을 위한 배열
    private final char key_lay2[] = {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'}; // 키 매핑을 위한 배열
    private final char key_lay3[] = {'z', 'x', 'c', 'v', 'b', 'n', 'm'}; // 키 매핑을 위한 배열
    private final char key_lay4[] = {'1','2','3','4','5','6','7','8','9','0','_'}; // 키 매핑을 위한 배열
    private MainFrame mf;
    private int progress = 0; // 진행율
    private int typo = 0; // 오타수
    private int accuracy = 0; // 정확도
    private JLabel progress_Per; // 진행률 Label
    private JProgressBar progress_bar; // 진행률 Bar
    private JProgressBar typo_bar; // 오타수 Bar
    private JProgressBar accuracy_bar; // 정확도 Bar
    private JLabel questionLabel; // 문제 Label
    private JLabel questionLabel2; // 다음 문제 Label
    private JLabel typeLabel; // 입력 Label
    private JLabel doneLabel; // 지난 문제 Label
    private JLabel typo_Cnt; // 오타수 Label
    private JLabel accuracy_Per; // 정확도 Label
    private int total = 0; // 총 문제 수
    private int index = 0; // 현재 문제 index
    private final int max = 100; // max 값 지정
    private JLabel charImage; // 문자 매핑 이미지
    private JLabel shiftImage; // Shift 키 매핑 이미지
    private char ch;
    private java.util.List<String> tmp = new ArrayList<String>(); // 파일에서 문장을 입력받는 ArrayList
    private java.util.List<String> text = new ArrayList<String>(); // 랜덤 순서로 문장을 입력 받는 ArrayList

    public WordPracticePanel(MainFrame mf, String language){
        this.mf = mf;
        setLayout(null);
        tmp.clear(); //tmp 초기화
        total = tmp.size(); // 총 문제 수 초기화
        FileReader fin = null;
        try{
            switch(language){ // 언어별 파일 지정
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
            while((line = bufReader.readLine()) != null){  // 파일을 한줄씩 읽음
                tmp.add(line); // tmp에 한문장씩 추가
                total++; // 총 문제 수 증가
            }
            bufReader.close();
        }catch(FileNotFoundException e){}
        catch(IOException e){
            System.out.println(e);
        }

        for(int i = 0; i<max; i++){ // 100 문제 선정
            int rNum = (int)(Math.random()*total); // 문장 수 중에 랜덤 값 지정
            if(text.contains(tmp.get(rNum))){ // 이미 있는 문장일 경우 continue
                i--;
                continue;
            }
            text.add(tmp.get(rNum)); // 새로운 문장일 경우 text에 추가
        }

        progress_bar = new JProgressBar(0, 100); // 진행률 Bar 생성 및 설정
        progress_bar.setSize(80,10);
        progress_bar.setLocation(95,90);
        progress_bar.setForeground(Color.BLACK);
        progress_bar.setBorderPainted(false);
        add(progress_bar);

        progress_Per = new JLabel(Integer.toString(progress)+"%"); // 진행률 Lable 생성 및 설정
        progress_Per.setSize(100,30);
        progress_Per.setLocation(180,80);
        add(progress_Per);


        typo_bar = new JProgressBar(0, 100); // 오타율 Bar 생성 및 설정
        typo_bar.setSize(80, 10);
        typo_bar.setLocation(265, 90);
        typo_bar.setForeground(Color.BLACK);
        typo_bar.setBorderPainted(false);
        add(typo_bar);
        
        typo_Cnt = new JLabel(Integer.toString(typo)); // 오타율 Label 생성 및 설정
        typo_Cnt.setSize(100,30);
        typo_Cnt.setLocation(355,80);
        add(typo_Cnt);


        accuracy_bar = new JProgressBar(0, 100); // 정확도 Bar 생성 및 설정
        accuracy_bar.setSize(80, 10);
        accuracy_bar.setLocation(435,90);
        accuracy_bar.setForeground(Color.BLACK);
        accuracy_bar.setBorderPainted(false);
        add(accuracy_bar);

        accuracy_Per = new JLabel(Integer.toString(accuracy)+"%"); // 정확도 Label 생성 및 설정
        accuracy_Per.setSize(100,30);
        accuracy_Per.setLocation(525,80);
        add(accuracy_Per);

        questionLabel = new JLabel(); // 문제 Label 생성 및 설정
        questionLabel.setText(text.get(index));
        questionLabel.setSize(400,30);
        questionLabel.setLocation(400,180);
        questionLabel.setFont(questionLabel.getFont().deriveFont(15.0F));
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        add(questionLabel);

        questionLabel2 = new JLabel();// 다음 문제 Label 생성 및 설정
        questionLabel2.setText(text.get(index+1));
        questionLabel2.setForeground(Color.GRAY);
        questionLabel2.setSize(250,30);
        questionLabel2.setLocation(830,180);
        questionLabel2.setFont(questionLabel.getFont().deriveFont(15.0F));
        questionLabel2.setHorizontalAlignment(JLabel.CENTER);
        add(questionLabel2);

        doneLabel = new JLabel(); // 지난 문제 Label 생성 및 설정
        doneLabel.setForeground(Color.GRAY);
        doneLabel.setSize(250,30);
        doneLabel.setLocation(100,180);
        doneLabel.setFont(questionLabel.getFont().deriveFont(15.0F));
        doneLabel.setHorizontalAlignment(JLabel.CENTER);
        add(doneLabel);

        typeLabel = new JLabel(); // 입력 Label 생성 및 설정
        typeLabel.setSize(400,30);
        typeLabel.setLocation(400,240);
        typeLabel.setFont(typeLabel.getFont().deriveFont(15.0F));
        typeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(typeLabel);

        JButton backBtn = new JButton(new ImageIcon("img/w_back_btn.png")); // 뒤로가기 버튼 생성 및 설정
        backBtn.setPressedIcon(new ImageIcon("img/w_back_pbtn.png"));
        backBtn.setSize(50,50);
        backBtn.setLocation(30,700);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(new BackToMainBtnEvent()); // 버튼 이벤트 추가
        add(backBtn);

        JButton helpBtn = new JButton(new ImageIcon("img/w_help_btn.png")); // 도움말 버튼 생성 및 설정
        helpBtn.setPressedIcon(new ImageIcon("img/w_help_pbtn.png"));
        helpBtn.setSize(50,50);
        helpBtn.setLocation(1110,700);
        helpBtn.setBorderPainted(false);
        helpBtn.setContentAreaFilled(false);
        helpBtn.setFocusPainted(false);
        helpBtn.addActionListener(new helpBtnEvent()); // 버튼 이벤트 추가
        add(helpBtn);

        charImage = new JLabel(new ImageIcon("img/keyPress.png")); // 문자 매핑 이미지 생성 및 설정
        charImage.setSize(25,25);
        add(charImage);

        shiftImage = new JLabel(new ImageIcon("img/keyPress.png")); // Shift 매핑 이미지 생성 및 설정
        shiftImage.setSize(25,25);
        shiftImage.setLocation(115,535);
        shiftImage.setVisible(false);
        add(shiftImage);

        JLabel keyBoardImg = new JLabel(new ImageIcon("img/keyboard.png")); // 키보드 이미지 생성 및 설정
        keyBoardImg.setSize(1200,800);
        keyBoardImg.setLocation(1,80);
        add(keyBoardImg);

        JButton exitBtn = new JButton(new ImageIcon("img/w_exit_btn.png")); // 종료 버튼 생성 및 설정
        exitBtn.setPressedIcon(new ImageIcon("img/w_exit_pbtn.png"));
        exitBtn.setSize(25,25);
        exitBtn.setLocation(1160,5);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new ExitPro()); // 버튼 이벤트 추가
        add(exitBtn);

        JButton miniBtn = new JButton(new ImageIcon("img/w_mini_btn.png")); // 최소화 버튼 생성 및 설정
        miniBtn.setPressedIcon(new ImageIcon("img/w_mini_pbtn.png"));
        miniBtn.setSize(25,25);
        miniBtn.setLocation(1120,5);
        miniBtn.setBorderPainted(false);
        miniBtn.setContentAreaFilled(false);
        miniBtn.setFocusPainted(false);
        miniBtn.addActionListener(new MinimizneWindows()); // 버튼 이벤트 추가
        add(miniBtn);
        
        ch = questionLabel.getText().charAt(0); // 문제 첫 글자 가져옴
        KeyImageChange(ch); // KeyImageChange 함수 실행
        this.addKeyListener(new TypeEvent()); // 패널 키 이벤트 추가
        this.addKeyListener(new BackToMainEvent()); // 패널 키 이벤트 추가(우선순위 높음)
        setFocusable(true); // 포커스 설정 가능
        setSize(1200,800);
        setVisible(true);
    }

    public void paintComponent(Graphics g){ // BackGround 설정을 위한 그리기 함수
        ImageIcon main_bg = new ImageIcon("img/word_back.png");
        g.drawImage(main_bg.getImage(),0,0,null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public void KeyImageChange(char ch){ // 키 매핑을 위한 함수
        for(int i =0; i< key_lay4.length; i++){
            if(ch == key_lay4[i]){ // 문자가 key_lay4에 있을 경우
                charImage.setLocation(180+(i*70),340); // charImage 이미지 위치 변경
                return; // 종료
            }
        }
        if(ch+32 >='a' && ch+32 <='z') shiftImage.setVisible(true); //'A' ~ 'Z' 가 입력 되었을 경우 shiftImage 보이게 설정
        else shiftImage.setVisible(false); // 아닐 경우 shiftImage 안보이게 설정

        for(int i = 0; i< key_lay1.length; i++){
            if(ch>='A' && ch<='Z'){
                if(ch + 32 == key_lay1[i]){ // 대문자를 소문자로 변환한 문자가 key_lay3에 있을 경우
                    charImage.setLocation(216+(i*70),400); // charImage 이미지 위치 변경
                    return; // 종료
                }
            }else if(ch>='a' && ch<='z'){ 
                if(ch == key_lay1[i]){ // 문자가 key_lay1에 있을 경우
                    charImage.setLocation(216+(i*70),400); // charImage 이미지 위치 변경
                    return; // 종료
                }
            }
        }
        for(int i = 0; i< key_lay2.length; i++){
            if(ch>='A' && ch<='Z'){
                if(ch + 32 == key_lay2[i]){ // 대문자를 소문자로 변환한 문자가 key_lay2에 있을 경우
                    charImage.setLocation(231+(i*70),470); // charImage 이미지 위치 변경
                    return; // 종료
                }
            }else if(ch>='a' && ch<='z'){
                if(ch == key_lay2[i]){ // 문자가 key_lay2에 있을 경우
                    charImage.setLocation(231+(i*70),470); // charImage 이미지 위치 변경
                    return; // 종료
                }
            }
        }
        for(int i = 0; i< key_lay3.length; i++){
            if(ch>='A' && ch<='Z'){
                if(ch + 32 == key_lay3[i]){ // 대문자를 소문자로 변환한 문자가 key_lay3에 있을 경우
                    charImage.setLocation(265+(i*70),535); // charImage 이미지 위치 변경
                    return; // 종료
                }
            }else if(ch>='a' && ch<='z'){
                if(ch == key_lay3[i]){ // 문자가 key_lay3에 있을 경우
                    charImage.setLocation(265+(i*70),535); // charImage 이미지 위치 변경
                    return; // 종료
                }
            }
        }
    }

    class BackToMainBtnEvent implements ActionListener{ // 뒤로가기 버튼 이벤트
        public void actionPerformed(ActionEvent e){ 
            mf.change("BackToMain"); // MainFrame change 함수 사용하여 MainPanel 불러옴
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
                    mf.change("BackToMain"); // MainFrame change 함수 사용하여 MainPanel 불러옴
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
    class BackToMainEvent extends KeyAdapter{ // 패널 키 이벤트
        public void keyPressed(KeyEvent e){ // 키 입력했을 경우
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){ // 'Esc' 키일 경우
                if(index != 0) // 진행중이였을 경우
                    JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE); // dialog 띄워줌
                mf.change("BackToMain"); // MainFrame change 함수를 이용하여 MainPanel 불러옴
            }
        }
    }
    class QuestionDialog extends JDialog{ // 도움말 Dialog 클래스
        public QuestionDialog(JFrame frame, String title){ // 생성자 함수
            super(frame, title); //프레임과 타이틀 설정
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 종료 옵션 프로그램 종료가 아닌 창만 닫도록 설정
            ImageIcon bg_img = new ImageIcon("img/w_help_img.png"); // 배경 이미지 설정
            JLabel bg = new JLabel(bg_img); // 이미지 레이블 생성
            bg.setSize(600,600);
            bg.setLocation(0, 10);
            add(bg);
            setSize(610,610);
            addKeyListener(new KeyAdapter(){ // Dialog 키 이벤트 추가
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE){ //'Esc' 키 눌렀을 경우
                        QuestionDialog.this.dispose(); // Dialog 창 종료되게 설정
                    }
                }
            });
        }
    }

    class helpBtnEvent implements ActionListener{ // 도움말 버튼 이벤트
        private QuestionDialog dialog = null;
        public void actionPerformed(ActionEvent e){
            if(dialog == null){ // 도움말 Dialog가 생성되지 않은 상태일 경우
                dialog = new QuestionDialog(mf, "도움말"); // dialog 생성
                dialog.addWindowListener(new WindowAdapter(){ // dialog Window이벤트 추가
                    public void windowClosed(WindowEvent e){ // dialog이 닫히고 난 후
                        dialog = null; // dialog 값을 null로 바꾸어 줌
                        mf.change("MainToWord"); // Profile Panel을 재생성함.
                    }
                });
                dialog.setVisible(true); // dialog 보이게 설정
            }
        }
    }

    class MinimizneWindows implements ActionListener{ // 최소화 버튼 이벤트
        public void actionPerformed(ActionEvent e){
            mf.setState(1); // 현재 Frame을 최소화시킴
        }
    }
    class ExitPro implements ActionListener{ // 종료 버튼 이벤트
        public void actionPerformed(ActionEvent e){
           mf.ExitP(); // MainFrame에 있는 ExitP 함수를 사용하여 프로그램 종료시킴
        }
    }
}