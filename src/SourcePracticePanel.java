import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.filechooser.*;
import java.io.*;

public class SourcePracticePanel extends JPanel {
    private MainFrame mf;
    private int progress = 0; // 진행률
    private int typo = 0; // 타수
    private int accuracy = 0; // 정확도
    private JLabel progress_Per; // 진행률 Label
    private JLabel accuracy_Per; // 정확도 Label
    private JLabel currentType_Label; // 현재타수 Label
    private JLabel goalType_Label; // 목표타수 Label
    private JLabel highestType_Label; // 최고타수 Label
    private JProgressBar progress_bar; // 진행률 Progress Bar
    private JProgressBar accuracy_bar; // 정확도 Progress Bar
    private JProgressBar currentType_bar; // 현재타수 Progress Bar
    private JProgressBar goalType_bar; // 목표 타수 Progress Bar
    private JProgressBar highestType_bar; // 최고타수 Progress Bar
    private JLabel questionLabel[]; // 문제 Label 배열
    private JLabel typeLabel; // 입력창
    private int inaccuracy_cnt = 0; // 오타수
    private int total = 0; // 총 문제 수
    private int index = 0; // 현재 문제 수
    private char ch; // 입력한 값
    private int back; // 백스페이스 입력 수
    private int n; // 현재타수 값
    private int file_Index = -1; // 선택한 파일 Index
    private ArrayList<String> tmp = new ArrayList<String>(); // 파일에서 문장을 입력받는 ArrayList
    private ArrayList<String> text = new ArrayList<String>(); // 수정된 문장을 입력 받는 ArrayList
    private ArrayList<String> saveTmp = new ArrayList<String>();
    TypeRunnable runnable = new TypeRunnable(); // 현재타수 계산 Runnable
    Thread th = new Thread(runnable); // 스레드에 현재타수 계산 Runnable 추가
    private long beforeTime; // 스레드 시작한 시간 저장 변수
    private String language; // 현재 언어
    private int tab_Cnt = 0; // tab 입력 cnt
    private int openBrace_Cnt = 0; // '{' 갯수 계산용 변수
    private int empty_Cnt = 0;
    private String empty;
    private ArrayList<String> list;
    private showSelectTextFileDialog dialog = null;
    private boolean save;

    public void AddSave() { // 파일 저장 함수
        String fne = "";
        save = true;
        switch (language) { // 언어별 파일 변경
        case "Java":
            fne = "java";
            break;
        case "C":
            fne = "c";
            break;
        case "Python":
            fne = "py";
            break;
        }
        JFileChooser chooser = new JFileChooser(); // 객체 생성
        chooser.setAcceptAllFileFilterUsed(false); // 파일 필터 중 'All Files' 제거
        FileNameExtensionFilter filter = new FileNameExtensionFilter("." + fne, fne); // 파일 필터 생성
        chooser.setFileFilter(filter); // chooser에 filter 설정

        int open = chooser.showOpenDialog(this); // 열기창 정의
        if (open != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File openFile = chooser.getSelectedFile(); // 파일을 가져옴
        String saveName = chooser.getSelectedFile().getName(); // 저장할 폴더의 경로
        try {
            SourceEdit(openFile); // 문장 다듬기 알고리즘 실행
            File saveFile = new File("txt/" + language + "/sourceFile/" + saveName);
            FileInputStream fis = new FileInputStream(openFile); // openFile을 이용하여 스트림 연결
            FileWriter fw = new FileWriter(saveFile); // 현재 언어의 소스 폴더에
                                                      // 소스 파일 저장

            for (int i = 0; i < text.size(); i++) {
                fw.write(text.get(i));
                fw.write("\r\n", 0, 2);
            }

            fis.close();
            fw.close();

            JOptionPane.showMessageDialog(this, "저장이 완료되었습니다.", "System", JOptionPane.INFORMATION_MESSAGE);
            list.add(saveName); // 저장한 파일을 리스트에 추가
            save = false;
        } catch (Exception ex) {
        }
    }

    public void SourceEdit(File f) { // 문장 다듬기 알고리즘 함수
        try {
            tmp = new ArrayList<String>();
            text = new ArrayList<String>();
            FileReader fin = null;
            fin = new FileReader(f); // f 파일을 읽음
            BufferedReader bufReader = new BufferedReader(fin);
            String line = "";
            while ((line = bufReader.readLine()) != null) { // 파일을 한줄씩 읽음
                tmp.add(line);
            }
            openBrace_Cnt = 0;

            int size = tmp.size();
            for (int i = 0; i < size - 1; i++) {
                empty_Cnt = 0;
                empty = "";
                if (tmp.get(i + 1).trim().length() == 1 && tmp.get(i + 1).contains("{") == true) {// tmp(i+1)에
                                                                                                  // 글자수가
                                                                                                  // 1자이고,{
                                                                                                  // 가 포함
                                                                                                  // 되있을 경우
                    tmp.set(i, tmp.get(i).concat("{")); // 현재 문장의 끝에 { 를 포함시킴
                    tmp.set(i + 1, tmp.get(i + 1).replace("{", "")); // tmp(i+1)의 { 를 공백으로 수정
                }
                if (i > 0 && tmp.get(i - 1).contains("{")) // 이전 문장에 '{' 가 있을 경우
                    openBrace_Cnt++; // openBrace_Cnt 값 증가
                for (int j = 0; j < tmp.get(i).length(); j++) {
                    if (tmp.get(i).charAt(j) == '\t') { // 들여 쓰기 부분에 '\t'가 있을경우
                        tmp.set(i, tmp.get(i).replaceAll("\t", "    ")); // 각 공백 4개로 수정
                        empty_Cnt++;
                    } else if (tmp.get(i).charAt(j) == ' ') // 공백이 있을 경우
                        empty_Cnt++; // empty_Cnt 증가
                    else // 들여쓰기 부분이 끝났을 경우
                        break; // for문 종료
                }

                if (tmp.get(i).contains(("}"))) // 현재 문장에 '}' 가 있을 경우
                    openBrace_Cnt--; // openBrace_Cnt 값 감소
                if (empty_Cnt > openBrace_Cnt * 4) { // 현재 문장이 들여쓰기가 더 많이 되있을 경우
                    tmp.set(i, tmp.get(i).substring(empty_Cnt - (openBrace_Cnt * 4))); // 들여쓰기 줄여줌
                } else if (empty_Cnt < openBrace_Cnt * 4) { // 들여쓰기가 덜 되있을 경우
                    for (int j = 0; j < openBrace_Cnt * 4 - empty_Cnt; j++) {
                        empty += " "; // 들여쓰기 추가
                    }
                    tmp.set(i, empty + tmp.get(i));
                }
                if (tmp.get(i).trim().equals(""))
                    continue; // 빈 문장일 경우 continue
                if (!save)
                    tmp.set(i, tmp.get(i).replace("    ", "^^")); // 문장에 공백이 4개 있을 경우 "^^" 으로 대체
                text.add(tmp.get(i)); // text에 문장 추가
            }
            if (!save) // 파일 저장중이 아니라면
                tmp.set(size - 1, tmp.get(size - 1).replace("    ", "^^")); // 문장에 공백이 4개 있을 경우 "^^" 으로 대체
            text.add(tmp.get(size - 1)); // 마지막 문장을 text에 추가

            bufReader.close();
            total = text.size(); // 현재 문장 수를 총 문제로 저장
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("" + e);
        }
    }

    class showSelectTextFileDialog extends JDialog { // 프로필 변경 버튼 눌렀을 시 실행할 Custom Dialog
        JScrollPane p;

        public showSelectTextFileDialog(String langauge) {
            super(mf, "소스 선택", true);
            setLayout(null);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            File files = new File("txt/java/sourceFile"); // 기본 파일을 java 파일로 설정
            switch (language) { // 언어별 파일 변경
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
            list = new ArrayList<String>();
            File[] subFiles = files.listFiles(); // 폴더 내 파일 리스트 불러옴
            for (int i = 0; i < subFiles.length; i++) {
                list.add(subFiles[i].getName()); // list에 파일 리스트 텍스트 저장
            }
            JLabel la1 = new JLabel("연습하고 싶은 소스를 선택하여 주세요");
            la1.setSize(220, 20);
            la1.setLocation(10, 10);
            add(la1);
            JList<Object> li1 = new JList<>(list.toArray()); // list에 저장한 값을 JList에 저장
            JScrollPane p = new JScrollPane(li1); // li1을 JScrollPane에 추가
            p.setSize(200, 200);
            p.setLocation(10, 30);
            add(p);
            JButton inputSou = new JButton("소스추가"); // 소스 추가 버튼 생성 및 추가
            inputSou.setSize(90, 30);
            inputSou.setLocation(230, 30);
            inputSou.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showSelectTextFileDialog.this.dispose(); // 다이얼로그 제거
                    dialog = new showSelectTextFileDialog(language); // 새로운 다이얼로그 생성
                }
            }); // 버튼 이벤트 추가
            inputSou.addActionListener(new AddSource()); // 버튼 이벤트 추가
            add(inputSou);

            JButton ok_Btn = new JButton("ok");
            ok_Btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!li1.isSelectionEmpty()) { // li1에서 선택된 아이템이 있을 경우
                        file_Index = li1.getSelectedIndex(); // file_Index에 선택된 index 저장
                        dialog = null; // dialog = null 처리
                        showSelectTextFileDialog.this.dispose(); // dialog 제거
                    }
                }
            }); // 버튼 이벤트 추가
            ok_Btn.setSize(90, 30);
            ok_Btn.setLocation(230, 70);
            add(ok_Btn);

            JButton cancel_Btn = new JButton("cancel"); // 취소 버튼 생성 및 추가
            cancel_Btn.setSize(90, 30);
            cancel_Btn.setLocation(230, 110);
            cancel_Btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showSelectTextFileDialog.this.dispose(); // dialog 제거
                }
            }); // 버튼 이벤트 추가
            add(cancel_Btn);
            setSize(350, 300);
            setVisible(true);
        }
    }

    public SourcePracticePanel(MainFrame mf, String language) {
        this.mf = mf;
        this.language = language;
        dialog = new showSelectTextFileDialog(language); // dialog 생성
        if (file_Index > -1) { // 선택된 파일이 있을 경우
            setLayout(null);
            File files = new File("txt/java/sourceFile"); // 기본 파일을 java 파일로 설정
            switch (language) { // 언어별 파일 변경
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
            File[] subFiles = files.listFiles(); // 폴더 내 파일 리스트 불러옴
            File f = subFiles[file_Index]; // 파일 선정

            SourceEdit(f); // 문장 다듬기 알고리즘 실행

            JLabel guide_label = new JLabel("※ Tab키를 누르면 \"^^\"가 입력됩니다.");
            guide_label.setSize(300, 30);
            guide_label.setLocation(10, 150);
            add(guide_label);
            progress_bar = new JProgressBar(0, 100); // 진행률 bar 생성 및 설정
            progress_bar.setSize(80, 10);
            progress_bar.setLocation(95, 90);
            progress_bar.setForeground(Color.BLACK);
            progress_bar.setBorderPainted(false);
            add(progress_bar);

            progress_Per = new JLabel(Integer.toString(progress) + "%"); // 진행률 Label 생성 및 설정
            progress_Per.setSize(100, 30);
            progress_Per.setLocation(185, 80);
            add(progress_Per);

            accuracy_bar = new JProgressBar(0, 100); // 정확도 bar 생성 및 설정
            accuracy_bar.setSize(80, 10);
            accuracy_bar.setLocation(265, 90);
            accuracy_bar.setForeground(Color.BLACK);
            accuracy_bar.setBorderPainted(false);
            add(accuracy_bar);

            accuracy_Per = new JLabel(Integer.toString(accuracy) + "%"); // 정확도 Label 생성 및 설정
            accuracy_Per.setSize(100, 30);
            accuracy_Per.setLocation(355, 80);
            add(accuracy_Per);

            currentType_bar = new JProgressBar(0, 1000); // 현재 타수 bar 생성 및 설정
            currentType_bar.setSize(80, 10);
            currentType_bar.setLocation(450, 90);
            currentType_bar.setForeground(Color.BLACK);
            currentType_bar.setBorderPainted(false);
            add(currentType_bar);

            currentType_Label = new JLabel("0"); // 현재 타수 Label 생성 및 설정
            currentType_Label.setSize(100, 30);
            currentType_Label.setLocation(550, 80);
            add(currentType_Label);

            goalType_bar = new JProgressBar(0, 1000); // 목표 타수 bar 생성 및 설정
            goalType_bar.setSize(80, 10);
            goalType_bar.setLocation(640, 90);
            goalType_bar.setForeground(Color.BLACK);
            goalType_bar.setBorderPainted(false);
            add(goalType_bar);

            goalType_Label = new JLabel(Integer.toString(mf.getGoalType())); // 목표 타수 Label 생성 및 설정
            goalType_Label.setSize(100, 30);
            goalType_Label.setLocation(740, 80);
            add(goalType_Label);
            goalType_bar.setValue(mf.getGoalType()); // MainFrame에 있는 getGoalType 함수를 이용하여 값을 불러옴

            highestType_bar = new JProgressBar(0, 1000); // 최고 타수 bar 생성 및 설정
            highestType_bar.setSize(80, 10);
            highestType_bar.setLocation(830, 90);
            highestType_bar.setForeground(Color.BLACK);
            highestType_bar.setBorderPainted(false);
            add(highestType_bar);

            highestType_Label = new JLabel("0"); // 최고 타수 Label 생성 및 설정
            highestType_Label.setSize(100, 30);
            highestType_Label.setLocation(930, 80);
            add(highestType_Label);

            questionLabel = new JLabel[7]; // 문제 Label 생성 및 설정
            for (int i = 0; i < questionLabel.length; i++) {
                questionLabel[i] = new JLabel();
                questionLabel[i].setText(text.get(index + i));
                questionLabel[i].setSize(500, 30);
                questionLabel[i].setLocation(350, 160 + (i * 50));
                questionLabel[i].setFont(questionLabel[i].getFont().deriveFont(15.0F));
                questionLabel[i].setHorizontalAlignment(JLabel.LEFT);
                if (i != 0)
                    questionLabel[i].setForeground(Color.DARK_GRAY);
                questionLabel[0].setLocation(350, 160);
                add(questionLabel[i]);
            }

            typeLabel = new JLabel(); // 입력 Label 생성 및 설정
            typeLabel.setSize(500, 30);
            typeLabel.setLocation(350, 550);
            typeLabel.setFont(typeLabel.getFont().deriveFont(15.0F));
            typeLabel.setHorizontalAlignment(JLabel.LEFT);
            add(typeLabel);

            JButton backBtn = new JButton(new ImageIcon("img/sou_back_btn.png")); // 뒤로가기 버튼 생성 및 설정
            backBtn.setPressedIcon(new ImageIcon("img/sou_back_pbtn.png"));
            backBtn.setSize(50, 50);
            backBtn.setLocation(30, 700);
            backBtn.setBorderPainted(false);
            backBtn.setContentAreaFilled(false);
            backBtn.setFocusPainted(false);
            backBtn.addActionListener(new BackToMainBtnEvent()); // 버튼 이벤트 추가
            add(backBtn);

            JButton helpBtn = new JButton(new ImageIcon("img/sou_help_btn.png")); // 도움말 버튼 생성 및 설정
            helpBtn.setPressedIcon(new ImageIcon("img/sou_help_pbtn.png"));
            helpBtn.setSize(50, 50);
            helpBtn.setLocation(1110, 700);
            helpBtn.setBorderPainted(false);
            helpBtn.setContentAreaFilled(false);
            helpBtn.setFocusPainted(false);
            helpBtn.addActionListener(new helpBtnEvent()); // 버튼 이벤트 추가
            add(helpBtn);

            JButton exitBtn = new JButton(new ImageIcon("img/sou_exit_btn.png")); // 종료 버튼 생성 및 설정
            exitBtn.setPressedIcon(new ImageIcon("img/sou_exit_pbtn.png"));
            exitBtn.setSize(25, 25);
            exitBtn.setLocation(1160, 5);
            exitBtn.setBorderPainted(false);
            exitBtn.setContentAreaFilled(false);
            exitBtn.setFocusPainted(false);
            exitBtn.addActionListener(new ExitPro()); // 버튼 이벤트 추가
            add(exitBtn);

            JButton miniBtn = new JButton(new ImageIcon("img/sou_mini_btn.png")); // 최소화 버튼 생성 및 설정
            miniBtn.setPressedIcon(new ImageIcon("img/sou_mini_pbtn.png"));
            miniBtn.setSize(25, 25);
            miniBtn.setLocation(1120, 5);
            miniBtn.setBorderPainted(false);
            miniBtn.setContentAreaFilled(false);
            miniBtn.setFocusPainted(false);
            miniBtn.addActionListener(new MinimizneWindows()); // 버튼 이벤트 추가
            add(miniBtn);

            this.addKeyListener(new TypeEvent()); // 패널 키 이벤트 추가
            this.addKeyListener(new BackToMainEvent()); // 패널 키 이벤트 추가 (우선순위 더 높음)
            setFocusable(true); // 포커스 설정 가능하게 설정
            setFocusTraversalKeysEnabled(false);
            setSize(1200, 800);
            setVisible(true);
        } else {
            mf.change("BackToMain");
        }
    }

    class TypeRunnable implements Runnable {
        public void run() { // 스레드 실행 함수
            while (true) {
                try {
                    Thread.sleep(100); // 0.1초(100ms)마다 실행
                } catch (InterruptedException e) {
                    return;
                }
                long afterTime = System.nanoTime(); // 현재 시간을 nanoTime으로 가져옴
                long secDiffTime = (afterTime - beforeTime) / 100000000; // 현재 시간과 스레드 시작시간을 빼서 초 단위로 저장
                n = (int) ((typo - back * 2) * 78 / secDiffTime * 6); // 현재 타수 알고리즘
                if (n < 0)
                    n = 0; // n이 0 미만일 경우 n 초기화
                if (n > 1000)
                    n = 1000; // n이 1000 초과할 경우 n 초기화
                currentType_Label.setText(Integer.toString(n)); // 현재 타수 Label Text 변경
                currentType_bar.setValue(n); // 현재 타수 Bar 값 변경
            }
        }
    }

    public void paintComponent(Graphics g) { // BackGround 설정을 위한 그리기 함수
        ImageIcon main_bg = new ImageIcon("img/sou_back.png");
        g.drawImage(main_bg.getImage(), 0, 0, null);
        setOpaque(false);
        super.paintComponent(g);
    }

    class TypeEvent extends KeyAdapter implements KeyListener { // 패널 키 이벤트

        public void keyTyped(KeyEvent e) {
            try {
                int key = e.getKeyChar();

                if (tab_Cnt != 0) {
                    tab_Cnt--;
                    e.setKeyChar('^'); // 현재 키보드 이벤트를 엔터 눌렀을 때로 변경t
                }
                if (key == KeyEvent.VK_TAB) {
                    tab_Cnt += 1;
                    e.consume(); // 입력 값을 전달하지 않음
                    e.setKeyChar('^'); // 현재 키보드 이벤트를 엔터 눌렀을 때로 변경
                    keyTyped(e); // 이벤트 재실행
                }

                typeLabel.setText(typeLabel.getText().replace("\t", ""));

                if (key == KeyEvent.VK_BACK_SPACE) { // 백스페이스 입력 시 실행
                    back++;
                    if (typeLabel.getText().length() > 0) { // 입력된 텍스트가 있을 경우
                        ch = questionLabel[0].getText().charAt(typeLabel.getText().length() - 1); // 현재 입력한 문장 길이와 같은
                                                                                                  // 인덱스의 문제 문자를 가져옴
                        if (typeLabel.getText().charAt(typeLabel.getText().length() - 1) != ch) { // 현재 입력한 마지막 문자와 ch가
                                                                                                  // 다를 경우
                            inaccuracy_cnt--; // 오타 수 증가
                            accuracy = 100 - (int) ((double) inaccuracy_cnt / typeLabel.getText().length() * 100); // 정확도
                                                                                                                   // 재계산
                            accuracy_bar.setValue(accuracy); // 정확도 bar 값 변경
                            accuracy_Per.setText(Integer.toString(accuracy) + "%"); // 정확도 Label Text 변경
                        }
                        typeLabel.setText(typeLabel.getText().substring(0, typeLabel.getText().length() - 1)); // 입력창에
                                                                                                               // 입력값 중
                                                                                                               // 마지막 문자
                                                                                                               // 삭제
                        ch = questionLabel[0].getText().charAt(typeLabel.getText().length()); // 다음에 입력할 문자를 다시 받아옴

                    }

                    if (questionLabel[0].getText().substring(0, typeLabel.getText().length())
                            .equals(typeLabel.getText())) // 한문자씩 올바르게 입력했는지
                        typeLabel.setForeground(Color.BLACK); // 맞으면 입력창 글자색 검정으로 변경

                    else
                        typeLabel.setForeground(Color.RED); // 틀리면 입력창 글자색 빨강으로 변경
                }

                else if (key == KeyEvent.VK_ENTER) { // 엔터 입력 시
                    th.interrupt(); // 스레드 강제 종료
                    if (Integer.parseInt(highestType_Label.getText()) < Integer.parseInt(currentType_Label.getText())) { // 현재타수가
                                                                                                                         // 최고타수보다
                                                                                                                         // 높을
                                                                                                                         // 경우
                        highestType_Label.setText(currentType_Label.getText()); // 최고타수 Labal Text 변경
                        highestType_bar.setValue(currentType_bar.getValue()); // 최고타수 Bar Value 변경
                    }
                    mf.setAvgType(Integer.parseInt(currentType_Label.getText()), language); // MainFrame setAvgType 함수를
                                                                                            // 이용하여 현재 타수
                    // 전송
                    th = new Thread(runnable); // 스레드 재설정
                    index++; // 다음 문제 단어 받을 준비함.
                    progress = 100 * index / total; // 진행율 계산
                    progress_Per.setText(Integer.toString(progress) + "%"); // 진행률 Label Text 변경
                    progress_bar.setValue(progress); // 진행률 Label Bar Value 변경
                    inaccuracy_cnt = 0; // 오타수 초기화
                    if (progress == 100) { // 모두 끝 마쳤을 경우
                        progress = 0; // 진행률 초기화
                        JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE); // Dialog
                                                                                                                // 띄워줌
                        mf.change("BackToMain"); // MainFrame change 함수를 이용하여 MainPanel을 불러옴
                    } else { // 끝마치지 않은 경우
                        for (int i = 0; i < questionLabel.length; i++) { // 문제 문장들을 한칸씩 떙겨옴
                            if (index + i < total) // 문제가 남아 있을 경우
                                questionLabel[i].setText(text.get(index + i)); // 문제 Label 변경
                            else // 남은 문제가 없을 경우
                                questionLabel[i].setText(""); // 문제 Label 공백 처리
                            typeLabel.setText("");
                        }
                    }
                } else {
                    if (questionLabel[0].getText().length() == typeLabel.getText().length()) { // 문제 문장 텍스트 길이와 입력창 텍스트
                                                                                               // 길이가 같을 경우
                        e.consume(); // 입력 값을 전달하지 않음
                        e.setKeyChar((char) KeyEvent.VK_ENTER); // 현재 키보드 이벤트를 엔터 눌렀을 때로 변경
                        keyTyped(e); // 이벤트 재실행
                    } else {
                        typeLabel.setText(typeLabel.getText() + e.getKeyChar()); // 아닐경우 입력된 값을 입력창에 추가
                        if (th.getState() == Thread.State.NEW) { // 스레드가 실행 중이 아닐 경우
                            th.start(); // 스레드 실행
                            beforeTime = System.nanoTime(); // 스레드 실행 시간 저장
                        }
                        if (questionLabel[0].getText()
                                .substring(typeLabel.getText().length() - 1, typeLabel.getText().length())
                                .equals(typeLabel.getText().substring(typeLabel.getText().length() - 1)))
                            // 현재 입력한 문자와 문제의 문자와 같을 경우
                            typo++; // 타수 증가
                        else
                            inaccuracy_cnt++; // 아닐 경우 오타수 증가
                        accuracy = 100 - (int) ((double) inaccuracy_cnt / typeLabel.getText().length() * 100); // 정확도 계산
                        accuracy_bar.setValue(accuracy); // 정확도 Bar Value 변경
                        accuracy_Per.setText(Integer.toString(accuracy) + "%"); // 정확도 Label Text 변경

                    }

                    if (questionLabel[0].getText().substring(0, typeLabel.getText().length())
                            .equals(typeLabel.getText())) { // 한문자씩 올바르게 입력했는지
                        typeLabel.setForeground(Color.BLACK); // 맞으면 입력창 글자색 검정으로 변경
                        currentType_bar.setValue(currentType_bar.getValue()); // 현재 타수 Bar Value 변경
                        currentType_Label.setText(Integer.toString(currentType_bar.getValue())); // 현재 타수 Label Text 변경
                    } else
                        typeLabel.setForeground(Color.RED); // 틀리면 입력창 글자색 빨강으로 변경

                }
            } catch (Exception ex) {
            }
        }
    }

    class BackToMainBtnEvent implements ActionListener { // 뒤로가기 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.change("BackToMain"); // MainFrame change 함수를 이용하여 MainPanel 불러옴
        }
    }

    class AddSource implements ActionListener { // 소스추가 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            AddSave();
            // SourceEdit(filePath);
        }
    }

    class BackToMainEvent extends KeyAdapter { // 패널 키 이벤트
        public void keyTyped(KeyEvent e) { // 키 입력했을 경우
            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) { // 'Esc' 키일 경우
                typeLabel.setText(typeLabel.getText().substring(0, typeLabel.getText().length() - 1)); // 입력창에 'Esc' 키
                                                                                                       // 입력 된 것을 지워줌
                th.interrupt(); // 스레드 강제 종료
                if (index != 0) // 진행중이였을 경우
                    JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE); // dialog
                                                                                                            // 띄워줌
                mf.change("BackToMain"); // MainFrame change 함수를 이용하여 MainPanel 불러옴

            }
        }
    }

    class QuestionDialog extends JDialog { // 도움말 Dialog 클래스
        public QuestionDialog(JFrame frame, String title) { // 생성자 함수
            super(frame, title); // 프레임과 타이틀 설정
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 종료 옵션 프로그램 종료가 아닌 창만 닫도록 설정
            ImageIcon bg_img = new ImageIcon("img/sou_help_img.png"); // 배경 이미지 설정
            JLabel bg = new JLabel(bg_img); // 이미지 레이블 생성
            bg.setSize(600, 600);
            bg.setLocation(0, 10);
            add(bg);
            setSize(610, 610);
            addKeyListener(new KeyAdapter() { // Dialog 키 이벤트 추가
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // 'Esc' 키 눌렀을 경우
                        QuestionDialog.this.dispose(); // Dialog 창 종료되게 설정
                    }
                }
            });
        }
    }

    class helpBtnEvent implements ActionListener { // 도움말 버튼 이벤트
        private QuestionDialog dialog = null;

        public void actionPerformed(ActionEvent e) {
            if (dialog == null) { // 도움말 Dialog가 생성되지 않은 상태일 경우
                dialog = new QuestionDialog(mf, "도움말"); // dialog 생성
                dialog.addWindowListener(new WindowAdapter() { // dialog Window이벤트 추가
                    public void windowClosed(WindowEvent e) { // dialog이 닫히고 난 후
                        dialog = null; // dialog 값을 null로 바꾸어 줌
                        mf.change("MainToSou"); // Profile Panel을 재생성함.
                    }
                });
                dialog.setVisible(true); // dialog 보이게 설정
            }
        }
    }

    class MinimizneWindows implements ActionListener { // 최소화 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.setState(1); // 현재 Frame을 최소화시킴
        }
    }

    class ExitPro implements ActionListener { // 종료 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.ExitP(); // MainFrame에 있는 ExitP 함수를 사용하여 프로그램 종료시킴
        }
    }
}