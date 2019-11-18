import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class MainFrame extends JFrame{
    private MainPanel m_Panel = null;
    private ProfilePanel p_Panel = null;
    private WordPracticePanel w_Panel = null;
    private SentensePracticePanel sen_Panel = null;
    private SourcePracticePanel sou_Panel = null;
    private Point initialClick;
    private int lan_index = 0;
    private String languages[] = {"Java","C","Python"};
    private String language;
    private String nickName;
    private int goalType; // 목표 타수
    private int avgValue; // 평균 타수
    private int goalAccuracy; // 목표 정확도
    private ArrayList<Integer> avgType = new ArrayList<>(); // 문장연습 패널에서 가져올 타수
    private File f = new File("c:\\Temp\\init.txt"); // init 파일 경로
    private FileWriter fout = null;
    public MainFrame(){
        super("입문 개발자를 위한 타자 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane();
        Image icon = Toolkit.getDefaultToolkit().getImage("img/icon.png"); // 프로그램 아이콘 설정
        setIconImage(icon);
        setSize(1200,800);
        setVisible(true);
        if(f.exists()){ // init 파일이 존재할 시
                FileReader fin = null;
                try{
                    fin = new FileReader(f); // 파일을 읽음
                    BufferedReader bufReader = new BufferedReader(fin); // 파일을 한줄씩 읽기 위해 BufferedReader 사용
                    String line = "";
                    while((line = bufReader.readLine()) != null){ // 파일을 한줄 씩 읽음
                        if(line.contains("language : ")){ // 문장에 "language : "이 포함 되어 있을 경우
                            for(int i = 0; i<3; i++){ // languages[] 배열 내에 있는 텍스트와 일치하는 값을 찾음
                                if(line.contains(languages[i])) { //찾을 경우 language에 저장
                                    language = languages[i];
                                    lan_index = i; // 추후 언어 변경 때 사용하기 위해 lan_index 저장
                                }
                            }
                        }
                        if(line.contains("nickName : ")){ // 문장에 "nickName : "이 포함 되어 있을 경우
                            String a = "nickName : "; // "nickName : " 뒤의 값을 가져오기 위해 String 변수 설정
                            nickName = line.substring(a.length()); // a 문장 뒤의 값을 nickName으로 저장
                        }
                        if(line.contains("goalValue : ")){ // 문장에 "goalValue" : "이 포함 되어 있을 경우
                            String a = "goalValue : "; // "goalValue : " 뒤의 값을 가져오기 위해 String 변수 설정
                            goalType = Integer.parseInt(line.substring(a.length())); // a 문장 뒤의 값을 goalType(목표 타수)에 저장
                        }
                        if(line.contains("avgTypoValue : ")){ // 문장에 "avgTypoValue"" : "이 포함 되어 있을 경우
                            String a = "avgTypoValue : "; // "avgTypoValue : " 뒤의 값을 가져오기 위해 String 변수 설정
                            avgType.add(Integer.parseInt(line.substring(a.length()))); // a 문장 뒤의 값을 avgType(평균 타수)에 저장
                        }
                        if(line.contains("goalAccuracyValue : ")){ // 문장에 "goalAccuracyValue"" : "이 포함 되어 있을 경우
                            String a= "goalAccuracyValue : "; // "goalAccuracyValue : " 뒤의 값을 가져오기 위해 String 변수 설정
                            goalAccuracy = Integer.parseInt(line.substring(a.length())); // a 문장 뒤의 값을 goalAccuracy(목표 정확도)에 저장
                        }
                    }
                    bufReader.close(); // bufReader 닫음
                }catch(Exception ee){ // 예외상황 발생시
                    System.out.println(ee); //오류 메시지 출력
                }
                
        }
        else{ // 파일이 존재하지 않을 경우
            language = languages[0]; // 현재 언어를 "Java"
            nickName = "user"; // 닉네임을 "user"
            goalType = 100; // 목표 타수를 100
            avgType.add(0); // 평균 타수를 0
            goalAccuracy = 50; // 목표 정확도를 50으로 설정
        }
        this.addMouseListener(new moveWindows()); //윈도우 이동하기 위해 설정
        this.addMouseMotionListener(new moveWindows()); //윈도우 이동하기 위해 설정
    }

    public void change(String panelName){ // 패널 변경하기 위한 함수
            getContentPane().removeAll(); // 현재 생성된 패널들을 모두 삭제시킴
        switch(panelName){ //패널별 생성
            case "BackToMain": //메인으로 돌아갈 시
            getContentPane().add(m_Panel); //메인 패널 추가
            m_Panel.requestFocusInWindow(); // 메인 패널에 포커스 강제 설정
            break;
            case "MainToProfile": //메인에서 프로필 패널로 이동시
            p_Panel = new ProfilePanel(this); // 프로필 패널 생성(현재 JFrame 정보 전달)
            getContentPane().add(p_Panel); // 프로필 패널 추가
            p_Panel.requestFocusInWindow(); // 프로필 패널에 포커스 강제 설정
            break;
            case "MainToWord": // 메인에서 단어 연습 패널로 이동시
            w_Panel = new WordPracticePanel(this, language); // 단어 연습 패널 생성(현재 JFrame 정보와 현재 언어 전달)
            getContentPane().add(w_Panel); // 단어 연습 패널 추가
            w_Panel.requestFocusInWindow(); // 단어 연습 패널에 포커스 강제 설정
            break;
            case "MainToSen": //메인에서 문장 연습 패널로 이동시
            sen_Panel = new SentensePracticePanel(this, language); // 문장 연습 패널 생성(현재 JFrame정보와 현재 언어 전달)
            getContentPane().add(sen_Panel); // 문장 연습 패널 추가
            sen_Panel.requestFocusInWindow(); //문장 연습 패널에 포커스 강제 설정
            break;
            case "MainToSou":
            sou_Panel = new SourcePracticePanel(this, language);
            getContentPane().add(sou_Panel);
            sou_Panel.requestFocusInWindow();
            break;
        }
        revalidate(); // 프레임 새로고침
        repaint(); // 프레임 다시 그리기
    }
    public ArrayList<Integer> getAvgType(){ // 문장 연습 패널에서 받아온 타수 정보를 프로필 패널로 보내주기 위한 함수
        return avgType; // avgType ArrayList을 리턴 시켜줌
    }
    public void setAvgType(int avgType){ // 문장 연습 패널에서 타수 정보를 받아 오기 위한 함수
        this.avgType.add(avgType); // avgType ArrayList에 avgType 값 추가
        for(int i = 0 ; i<this.avgType.size(); i++){ // 입력 받을 때 마다 합을 구해줌
            avgValue += this.avgType.get(i);
        }
        avgValue = avgValue/this.avgType.size(); // 평균을 구함.
    }
    public int getGoalAccuracyValue(){ //목표 정확도를 프로필 패널에 보내주기 위한 함수
        return goalAccuracy; // goalAccuracy을 리턴 시켜줌.
    }
    public void setGoalAccuracyValue(int goalAccuracy){ // 프로필 패널에서 목표 정확도를 받아 오기 위한 함수
        this.goalAccuracy = goalAccuracy; // 전송된 goalAccuracy을 현재 class의 goalAccuracy에 저장
    }
    public String getNickName(){ // 닉네임을 프로필 패널에 보내주기 위한 함수
        return nickName; // nickName을 리턴 시켜줌
    }
    public void setNickName(String nickName){ // 프로필 패널에서 닉네임을 받아 오기 위한 함수
        this.nickName = nickName; // 전송된 nickName을 현재 class의 nickName에 저장
    }
    public int getGoalType(){ // 목표 정확도를 프로필 패널에 보내주기 위한 함수
        return goalType; // goalType을 리턴 시켜줌
    }
    public void setGoalType(int goalType){ // 프로필 패널에서 목표 정확도를 받아 오기 위한 함수
        this.goalType = goalType; // 전송된 goalType을 현재 class의 goalType에 저장
    }
    public String getLanguage(){ // 현재 언어를 프로필 패널에 보내주기 위한 함수
        return language; // language을 리턴 시켜줌
    }
    public String ChangeLanguage(){ // 언어 변경 함수
        if(lan_index == languages.length-1) lan_index = 0; // lan_index 값이 languages 배열 길이 -1와 같을 경우 0으로 초기화
        else lan_index++; // 아닐경우 lan_index 값 1 증가
        language = languages[lan_index]; // language 값을 languages[lan_index] 값으로 저장함 
        return language; // 변경된 language을 리턴시킴
    }
    class moveWindows extends MouseAdapter implements MouseMotionListener{ // 윈도우 창을 이동시키기 위한 클래스
        public void mousePressed(MouseEvent e){ // 마우스를 눌렀을 때
            initialClick = e.getPoint(); // 현재 좌표를 저장함
            getComponentAt(initialClick); // 저장한 좌표를 포함한 컴퍼넌트를 리턴 받음
        }

        public void mouseDragged(MouseEvent e){ // 마우스를 드래그 했을 경우
            JFrame jf = (JFrame)e.getSource(); // 드래그 된 JFrame의 정보를 받아옴
            int thisX = jf.getLocation().x; // jf의 x 값을 저장함
            int thisY = jf.getLocation().y; // jf의 y 값을 저장함

            int xMoved = e.getX() - initialClick.x; // 현재 마우스 위치의 x좌표와 첫 마우스 클릭 위치 x좌표를 빼줌
            int yMoved = e.getY() - initialClick.y; // 현재 마우스 위치의 y좌표와 첫 마우스 클릭 위치 y좌표를 빼줌

            int X = thisX + xMoved; // jf x값과 이동한 x 값을 더함
            int Y = thisY + yMoved; // jf y값과 이동한 y 값을 더함
            jf.setLocation(X, Y); // jf의 위치 변경
        }
        public void mouseMoved(MouseEvent e){} //리스너 사용을 위한 구현
    }

    public void ExitP(){ // 프로그램을 종료시킬 때 사용할 함수
        int num = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION); // 정말 닫을 것인지 Dialog를 통해 물어봄
            if(num == 0){ // 확인을 눌렀을 경우
                try{
                        fout = new FileWriter(f); // 별명, 현재 언어, 목표 타수, 평균타수, 목표 정확도를 파일 출력하여 저장함
                        fout.write("nickName : " + nickName);
                        fout.write("\r\n",0,2);
                        fout.write("language : " + language);
                        fout.write("\r\n",0,2);
                        fout.write("goalValue : " + goalType);
                        fout.write("\r\n",0,2);
                        fout.write("avgTypoValue : " + avgValue);
                        fout.write("\r\n",0,2);
                        fout.write("goalAccuracyValue : " + goalAccuracy);
                        fout.write("\r\n",0,2);
                        fout.close();
                }catch(Exception ee){
                    System.out.println("오류");
                }
                System.exit(0); // 프로그램 강제 종료
            }
    }
    public static void main(String[] args) {
        MainFrame mf = new MainFrame(); // MainFrame 생성
        mf.m_Panel = new MainPanel(mf); // MainPanel 생성
        mf.add(mf.m_Panel); //MainFrame에 MainPanel 추가
        mf.revalidate(); // 프레임 새로 고침
        mf.repaint(); // 프레임 다시 그리기
        mf.m_Panel.requestFocusInWindow(); // MainPanel 포커스 강제 설정
    }
}