import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class ProfilePanel extends JPanel {
    private MainFrame mf;
    private JLabel settingLabel[] = new JLabel[3];
    private JLabel userLabel = new JLabel();
    private ImageIcon languageIcon[] = { new ImageIcon("img/java_btn.png"), new ImageIcon("img/c_btn.png"),
            new ImageIcon("img/python_btn.png") };
    private JButton languageBtn = new JButton();
    private String nickName;
    private int goalValue;
    private int sum = 0;
    private int accuracyValue;
    private ArrayList<Integer> avgType = new ArrayList<>();
    private TreeMap<String, Integer> recordMap = new TreeMap<>();
    private String temp = "";
    private JLabel recordsLabel;
    private ArrayList<Integer> pointArray = new ArrayList<>();

    public ProfilePanel(MainFrame mf) {
        this.mf = mf; // MainFrame 정보를 저장
        setLayout(null); // 배치 관리자 제거

        avgType = mf.getAvgType(); // 타수 정보를 MainFrame에 있는 getAvgType 함수를 이용하여 가져옴
        for (int i = 0; i < settingLabel.length; i++) { // 평균 타수, 목표 타수, 목표 정확도 Label 생성 및 추가
            settingLabel[i] = new JLabel(Integer.toString(0));
            settingLabel[i].setSize(100, 20);
            settingLabel[i].setLocation(780 + (i * 100), 60);
            settingLabel[i].setHorizontalAlignment(JLabel.CENTER);
            add(settingLabel[i]);
        }
        for (int i = 0; i < avgType.size(); i++) {
            sum += avgType.get(i); // 타수 정보의 합을 구함
        }
        if (avgType.size() == 0)
            settingLabel[0].setText("0");
        else
            settingLabel[0].setText(Integer.toString(sum / avgType.size())); // 평균 타수를 구하여 Label Text로 설정
        settingLabel[1].setText(Integer.toString(mf.getGoalType())); // MainFrame에 있는 getGoalType 함수를 사용하여 목표 타수를 가져와
                                                                     // Label Text로 설정
        settingLabel[2].setText(Integer.toString(mf.getGoalAccuracyValue())); // MainFrame에 있는 getGoalAccuracyValue 함수를
                                                                              // 사용하여 목표 정확도를 가져와 Label Text로 설정
        userLabel.setText(mf.getNickName()); // MainFrame에 있는 getNickName 함수를 사용하여 별명을 가져와 Label Text로 설정
        userLabel.setSize(100, 30);
        userLabel.setLocation(100, 35);
        add(userLabel);

        JButton editBtn = new JButton(new ImageIcon("img/edit_btn.png")); // 프로필 편집 버튼 생성 및 설정
        editBtn.setPressedIcon(new ImageIcon("img/edit_pbtn.png"));
        editBtn.setSize(30, 30);
        editBtn.setLocation(200, 40);
        editBtn.setBorderPainted(false);
        editBtn.setContentAreaFilled(false);
        editBtn.setFocusPainted(false);
        editBtn.addActionListener(new ChangeName());
        editBtn.addKeyListener(new BackToMainEvent());
        add(editBtn);

        switch (mf.getLanguage()) { // MainFrame에 있는 getLanguage 함수를 사용하여 language를 가져옴
        case "Java": // Java일 경우
            languageBtn.setIcon(languageIcon[0]); // 언어변경 버튼 이미지 변경
            break;
        case "C": // C일 경우
            languageBtn.setIcon(languageIcon[1]); // 언어변경 버튼 이미지 변경
            break;
        case "Python": // Python일 경우
            languageBtn.setIcon(languageIcon[2]); // 언어변경 버튼 이미지 변경
            break;
        }

        languageBtn.setSize(130, 30);
        languageBtn.setLocation(100, 80);
        languageBtn.setBorderPainted(false);
        languageBtn.setContentAreaFilled(false);
        languageBtn.setFocusPainted(false);
        languageBtn.addActionListener(new ChangeLanguageEvent()); // 언어변경 버튼 이벤트 추가
        languageBtn.addKeyListener(new BackToMainEvent()); // 언어변경 버튼 키 이벤트 추가
        add(languageBtn);

        recordsLabel = new JLabel();
        recordMap = mf.getMap(mf.getLanguage()); // language의 Map을 가져와 recordMap 저장
        Set<String> keys = recordMap.keySet();
        temp = "<html>";
        keys.forEach(key -> temp += key + " : " + recordMap.get(key) + "<br><br>"); // 데이터들 정리하기
        temp += "</html>";
        recordsLabel.setText(temp); // 정리한 데이터 recordsLabel 텍스트로 설정
        repaint(); // 그래프를 다시 그리기 위한 repaint() 함수
        recordsLabel.setSize(150, 450);
        recordsLabel.setLocation(50, 220);
        recordsLabel.setHorizontalAlignment(JLabel.LEFT);
        recordsLabel.setVerticalAlignment(JLabel.NORTH);
        add(recordsLabel);

        JButton backBtn = new JButton(new ImageIcon("img/p_back_btn.png")); // 뒤로가기 버튼 생성 및 설정
        backBtn.setPressedIcon(new ImageIcon("img/p_back_pbtn.png"));
        backBtn.setSize(50, 50);
        backBtn.setLocation(30, 700);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(new BackToMainBtnEvent()); // 뒤로가기 버튼 이벤트 추가
        add(backBtn);

        JButton helpBtn = new JButton(new ImageIcon("img/p_help_btn.png")); // 도움말 버튼 생성 및 설정
        helpBtn.setPressedIcon(new ImageIcon("img/p_help_pbtn.png"));
        helpBtn.setSize(50, 50);
        helpBtn.setLocation(1110, 700);
        helpBtn.setBorderPainted(false);
        helpBtn.setContentAreaFilled(false);
        helpBtn.setFocusPainted(false);
        helpBtn.addActionListener(new helpBtnEvent()); // 도움말 버튼 이벤트 추가
        add(helpBtn);

        JButton exitBtn = new JButton(new ImageIcon("img/p_exit_btn.png")); // 종료 버튼 생성 및 설정
        exitBtn.setPressedIcon(new ImageIcon("img/p_exit_pbtn.png"));
        exitBtn.setSize(25, 25);
        exitBtn.setLocation(1160, 5);
        exitBtn.setBorderPainted(false);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new ExitPro()); // 종료 버튼 이벤트 추가
        add(exitBtn);

        JButton miniBtn = new JButton(new ImageIcon("img/p_mini_btn.png")); // 최소화 버튼 생성 및 설정
        miniBtn.setPressedIcon(new ImageIcon("img/p_mini_pbtn.png"));
        miniBtn.setSize(25, 25);
        miniBtn.setLocation(1120, 5);
        miniBtn.setBorderPainted(false);
        miniBtn.setContentAreaFilled(false);
        miniBtn.setFocusPainted(false);
        miniBtn.addActionListener(new MinimizneWindows()); // 최소화 버튼 이벤트 추가
        add(miniBtn);

        this.addKeyListener(new BackToMainEvent()); // 패널 키 이벤트 추가
        setSize(1200, 800);
        setVisible(true);
    }

    public void paintComponent(Graphics g) { // 그리기 함수
        ImageIcon main_bg = new ImageIcon("img/profile_back.png");
        g.drawImage(main_bg.getImage(), 0, 0, null); // BackGround 설정을 위한 그리기 함수
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2)); // 선 굵기 설정
        g2.drawLine(300, 230, 300, 630); // y축 선 긋기
        g2.drawLine(300, 630, 1000, 630); // x축 선 긋기
        for (int i = 0; i < recordMap.size(); i++) {
            g2.drawLine(340 + (i * 70), 620, 340 + (i * 70), 640); // 데이터 수 만큼 x축 구분선 긋기
        }
        g.setFont(g.getFont().deriveFont(13.0F));
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        Collection<Integer> values = recordMap.values();
        int cnt = 0;
        for (Integer value : values) {
            g2.setStroke(new BasicStroke(7)); // 선 굵기 설정
            pointArray.add(cnt, 630 - (int) (value * 0.4)); // 포인트별 좌표 pointArray에 저장
            g2.drawLine(340 + (cnt * 70), pointArray.get(cnt), 340 + (cnt * 70), pointArray.get(cnt)); // 포인트 그리기
            g.drawString(Integer.toString(value), 330 + (cnt * 70), pointArray.get(cnt) - 15);
            if (cnt > 0) {
                g2.setStroke(new BasicStroke(2)); // 선 굵기 설정
                g2.drawLine(340 + (cnt - 1) * 70, pointArray.get(cnt - 1), 340 + (cnt * 70), pointArray.get(cnt)); // 포인트
                                                                                                                   // 사잇선
                                                                                                                   // 그리기
            }
            cnt++;
        }

        g.drawString("1000", 255, 230); // y축 최대 구분값 그리기
        g.drawString("500", 255, 430); // y축 중간 구분값 그리기
        setOpaque(false);
        super.paintComponent(g);
    }

    private int showMultipleInputMessageDialog() { // 프로필 변경 버튼 눌렀을 시 실행할 Custom Dialog
        JLabel la1 = new JLabel("변경할 닉네임을 입력해주세요. (8자리 이하)");
        JTextField tf1 = new JTextField(userLabel.getText(), 10);
        JLabel la2 = new JLabel("목표 타수를 입력해주세요. (100이상 1000이하)");
        JTextField tf2 = new JTextField(settingLabel[1].getText(), 10);
        JLabel la3 = new JLabel("목표 정확도를 입력해주세요. (50이상 100이하)");
        JTextField tf3 = new JTextField(settingLabel[2].getText(), 10);

        Object[] inputFields = { la1, tf1, la2, tf2, la3, tf3 }; // Dialog에 넣기 위한 오브젝트들 추가
        int option = JOptionPane.showConfirmDialog(this, inputFields, "사용자 설정", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) { // OK버튼 눌렀을 경우
            nickName = tf1.getText(); // nickName 설정
            if (tf2.getText() == "")
                goalValue = 100; // tf2가 비었을 경우 목표 타수 100 설정
            else
                goalValue = Integer.parseInt(tf2.getText()); // 아닐 경우 목표 타수 설정
            if (tf3.getText() == "")
                accuracyValue = 90; // tf3가 비었을 경우 목표 정확도 90 설정
            else
                accuracyValue = Integer.parseInt(tf3.getText()); // 아닐 경우 목표 정확도 설정
        }
        return option; // option 값 리턴
    }

    class ChangeName implements ActionListener { // 프로필 변경 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            if (showMultipleInputMessageDialog() == 0) // CustomDialog 실행하여 확인 버튼을 눌렀을 경우
            {
                while (true) {
                    if (nickName.length() > 8) { // 별명이 8자리 이상일 경우
                        JOptionPane.showMessageDialog(null, "별명을 8자리 이하로 입력해주세요.", "자릿수 초과",
                                JOptionPane.WARNING_MESSAGE);
                        if (showMultipleInputMessageDialog() != 0)
                            break; // 취소 버튼을 눌렀을 경우 break
                    } else if (nickName.length() == 0) { // 별명을 입력하지 않았을 경우
                        JOptionPane.showMessageDialog(null, "별명을 최소 1글자 이상 입력해주세요.", "자릿수 미만",
                                JOptionPane.WARNING_MESSAGE);
                        if (showMultipleInputMessageDialog() != 0)
                            break; // 취소 버튼을 눌렀을 경우 break
                    } else if (goalValue > 1000) { // 목표 타수가 1000 초과하여 입력했을 경우
                        JOptionPane.showMessageDialog(null, "목표 타수를 1000 이하로 입력해주세요.", "자릿수 초과",
                                JOptionPane.WARNING_MESSAGE);
                        if (showMultipleInputMessageDialog() != 0)
                            break; // 취소 버튼을 눌렀을 경우 break
                    } else if (goalValue < 100) { // 목표 타수가 100 미만으로 입력했을 경우
                        JOptionPane.showMessageDialog(null, "목표 타수를 최소 100 이상 입력해주세요.", "자릿수 미만",
                                JOptionPane.WARNING_MESSAGE);
                        if (showMultipleInputMessageDialog() != 0)
                            break;
                    } else if (accuracyValue > 100) { // 목표 정확도를 100 초과하여 입력했을 경우
                        JOptionPane.showMessageDialog(null, "목표 정확도를 100 이하로 입력해주세요.", "자릿수 초과",
                                JOptionPane.WARNING_MESSAGE);
                        if (showMultipleInputMessageDialog() != 0)
                            break; // 취소 버튼을 눌렀을 경우 break
                    } else if (accuracyValue < 50) { // 목표 정확도를 50 미만으로 입력했을 경우
                        JOptionPane.showMessageDialog(null, "목표 정확도를 50 이상 입력해주세요.", "자릿수 미만",
                                JOptionPane.WARNING_MESSAGE);
                        if (showMultipleInputMessageDialog() != 0)
                            break; // 취소 버튼을 눌렀을 경우 break
                    } else
                        break;
                } // 아무 문제 없을 경우
                userLabel.setText(nickName); // userLabel에 Text를 별명으로 변경
                settingLabel[1].setText(Integer.toString(goalValue)); // 목표 타수 Label Text를 goalValue로 변경
                settingLabel[2].setText(Integer.toString(accuracyValue));// 목표 정확도 Label Text를 accuracyValue 변경
                mf.setNickName(nickName); // MainFrame에 setNickName 함수를 사용하여 nickName 값을 전송
                mf.setGoalType(goalValue); // MainFrame에 setGoalType 함수를 사용하여 goalValue 값을 전송
                mf.setGoalAccuracyValue(accuracyValue); // MainFrame에 setGoalAccuracyValue 함수를 사용하여 accuracyValue 값을 전송
            }
        }
    }

    class ChangeLanguageEvent implements ActionListener { // 언어 변경 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource(); // 입력한 버튼 컴포넌트를 받아옴
            switch (mf.ChangeLanguage()) { // MainFrame에 있는 ChangeLanguage 함수를 이용하여 lanauage 값 받아옴
            case "Java": // 'Java' 일 경우
                b.setIcon(languageIcon[0]); // b 이미지 변경
                invalidate(); // 새로고침
                break;
            case "C": // 'C' 일 경우
                b.setIcon(languageIcon[1]); // b 이미지 변경
                invalidate(); // 새로고침
                break;
            case "Python": // 'Python' 일 경우
                b.setIcon(languageIcon[2]); // b 이미지 변경
                invalidate(); // 새로고침
                break;
            }
            recordMap = mf.getMap(mf.getLanguage()); // language의 Map을 가져와 recordMap 저장
            Set<String> keys = recordMap.keySet();
            temp = "<html>";
            keys.forEach(key -> temp += key + " : " + recordMap.get(key) + "<br><br>"); // 데이터들 정리하기
            temp += "</html>";
            recordsLabel.setText(temp); // 정리한 데이터 recordsLabel 텍스트로 설정
            repaint(); // 그래프를 다시 그리기 위한 repaint() 함수
        }

    }

    class QuestionDialog extends JDialog { // 도움말 Dialog 클래스
        public QuestionDialog(JFrame frame, String title) { // 생성자 함수
            super(frame, title); // 프레임과 타이틀 설정
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 종료 옵션 프로그램 종료가 아닌 창만 닫도록 설정
            ImageIcon bg_img = new ImageIcon("img/p_help_img.png"); // 배경 이미지 설정
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
                        mf.change("MainToProfile"); // Profile Panel을 재생성함.
                    }
                });
                dialog.setVisible(true); // dialog 보이게 설정
            }
        }
    }

    class BackToMainBtnEvent implements ActionListener { // 뒤로가기 버튼 이벤트
        public void actionPerformed(ActionEvent e) {
            mf.change("BackToMain"); // MainFrame에 있는 change 함수를 사용하여 MainPanel로 이동
        }
    }

    class BackToMainEvent extends KeyAdapter { // 패널 키 이벤트
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // 'Esc' 키를 눌렀을 경우
                mf.change("BackToMain"); // MainFrame에 있는 change 함수를 사용하여 MainPanel로 이동

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