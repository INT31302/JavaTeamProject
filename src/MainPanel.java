import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

class MainPanel extends JPanel {
    private MainFrame mf;
    private JButton[] menuBtn = new JButton[4]; // 패널 이동하기 위한 버튼 추가

    public MainPanel(MainFrame mf) {
        this.mf = mf; // MainFrame 정보를 저장
        setLayout(null); // 배치 관리자 제거
        JButton profileBtn = new JButton(new ImageIcon("img/pro_btn.png")); // 프로필 패널 이동 버튼 생성 및 설정
        profileBtn.setPressedIcon(new ImageIcon("img/pro_pbtn.png"));
        profileBtn.setSize(120, 48);
        profileBtn.setLocation(45, 30);
        profileBtn.setBorderPainted(false);
        profileBtn.setContentAreaFilled(false);
        profileBtn.setFocusPainted(false);
        profileBtn.addActionListener(new MainToProfileBtnEvent()); // 버튼 이벤트 추가
        add(profileBtn);

        for (int i = 0; i < menuBtn.length; i++) { // 메뉴 버튼 생성 및 추가
            menuBtn[i] = new JButton();
            menuBtn[i].setBorderPainted(false);
            menuBtn[i].setContentAreaFilled(false);
            menuBtn[i].setFocusPainted(false);
            menuBtn[i].setSize(250, 500);
            menuBtn[i].setLocation(45 + (i * 280), 150);
            add(menuBtn[i]);
        }
        menuBtn[0].setIcon(new ImageIcon("img/word_btn.png")); // 단어 연습 버튼 이미지 설정
        menuBtn[0].setPressedIcon(new ImageIcon("img/word_pbtn.png"));
        menuBtn[1].setIcon(new ImageIcon("img/sen_btn.png")); // 문장 연습 버튼 이미지 설정
        menuBtn[1].setPressedIcon(new ImageIcon("img/sen_pbtn.png"));
        menuBtn[2].setIcon(new ImageIcon("img/so_btn.png")); // 소스 연습 버튼 이미지 설정
        menuBtn[2].setPressedIcon(new ImageIcon("img/so_pbtn.png"));
        menuBtn[3].setIcon(new ImageIcon("img/game_btn.png")); // 게임 버튼 이미지 설정
        menuBtn[3].setPressedIcon(new ImageIcon("img/game_pbtn.png"));
        menuBtn[0].addActionListener(new MainToWordBtnEvent()); // 단어 연습 버튼 이벤트 추가
        menuBtn[1].addActionListener(new MainToSenBtnEvent());// 문장 연습 버튼 이벤트 추가
        menuBtn[2].addActionListener(new MainToSouBtnEvent());// 소스 연습 버튼 이벤트 추가
        menuBtn[3].addActionListener(new MainToGameBtnEvent()); // 게임 버튼 이벤트 추가

        JButton exitBtn = new JButton(new ImageIcon("img/exit_btn.png")); // 닫기 버튼 생성 및 설정
        exitBtn.setPressedIcon(new ImageIcon("img/exit_pbtn.png"));
        exitBtn.setSize(25, 25);
        exitBtn.setLocation(1160, 5);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new ExitPro()); // 닫기 버튼 이벤트 추가
        add(exitBtn);

        JButton miniBtn = new JButton(new ImageIcon("img/mini_btn.png")); // 최소화 버튼 생성 및 설정
        miniBtn.setPressedIcon(new ImageIcon("img/mini_pbtn.png"));
        miniBtn.setSize(25, 25);
        miniBtn.setLocation(1120, 5);
        miniBtn.setBorderPainted(false);
        miniBtn.setContentAreaFilled(false);
        miniBtn.setFocusPainted(false);
        miniBtn.addActionListener(new MinimizneWindows()); // 최소화 버튼 이벤트 추가
        add(miniBtn);
        this.setFocusable(true); // 패널 포커스 활성화
        this.requestFocus(); // 패널 강제 포커스 설정
        this.addKeyListener(new ExitProgram()); // 패널 키 이벤트 추가
    }

    public void paintComponent(Graphics g) { // BackGround 설정을 위한 그리기 함수
        ImageIcon main_bg = new ImageIcon("img/main_back.png");
        g.drawImage(main_bg.getImage(), 0, 0, null);
        setOpaque(false);
        super.paintComponent(g);
    }

    class MainToProfileBtnEvent implements ActionListener { // 프로필 이동 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.change("MainToProfile"); // MainFrame에 있는 change 함수를 사용하여 Profile Panel로 이동
        }
    }

    class MainToWordBtnEvent implements ActionListener { // 단어 연습 이동 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.change("MainToWord"); // MainFrame에 있는 change 함수를 사용하여 WordPractice Panel로 이동
        }
    }

    class MainToSenBtnEvent implements ActionListener { // 문장 연습 이동 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.change("MainToSen"); // MainFrame에 있는 change 함수를 사용하여 SentensePractice Panel로 이동
        }
    }

    class MainToSouBtnEvent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String language = mf.getLanguage();
            if (showSelectTextFileDialog(language) == JOptionPane.OK_OPTION)
                mf.change("MainToSou");
        }
    }

    class MainToGameBtnEvent implements ActionListener { // 문장 연습 이동 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.change("MainToGame"); // MainFrame에 있는 change 함수를 사용하여 SentensePractice Panel로 이동
        }
    }

    private int showSelectTextFileDialog(String language) { // 프로필 변경 버튼 눌렀을 시 실행할 Custom Dialog
        ArrayList<String> list = new ArrayList<String>();
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
        for (int i = 0; i < subFiles.length; i++) {
            list.add(subFiles[i].getName());
        }
        JLabel la1 = new JLabel("연습하고 싶은 소스를 선택하여 주세요");
        // JScrollPane sp =
        JList<Object> li1 = new JList<>(list.toArray());

        Object[] inputFields = { la1, li1 }; // Dialog에 넣기 위한 오브젝트들 추가
        int option = JOptionPane.showConfirmDialog(this, inputFields, "소스 선택", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) { // OK버튼 눌렀을 경우
        }
        return option; // option 값 리턴
    }

    class MinimizneWindows implements ActionListener { // 최소화 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.setState(1); // MainFrame 상태를 최소화 시킨다
        }
    }

    class ExitPro implements ActionListener { // 닫기 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.ExitP(); // MainFrame에 있는 ExitP 함수를 사용하여 프로그램 종료
        }
    }

    class ExitProgram extends KeyAdapter { // 패널 키 이벤트
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {// 'Esc' 키를 눌렀을 경우
                mf.ExitP(); // MainFrame에 있는 ExitP 함수를 사용하여 프로그램 종료
            }
        }
    }
}