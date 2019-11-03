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
    private Point initialClick;
    private int lan_index = 0;
    private String languages[] = {"Java","C","Python"};
    private String language;
    private String nickName;
    private int goalType;
    private int avgValue;
    private ArrayList<Integer> avgType = new ArrayList<>();
    private File f = new File("c:\\Temp\\test.txt");
    private FileWriter fout = null;
    public MainFrame(){
        super("입문 개발자를 위한 타자 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane();
        Image icon = Toolkit.getDefaultToolkit().getImage("img/icon.png");
        setIconImage(icon);
        setSize(1200,800);
        setVisible(true);
        if(f.exists()){
                FileReader fin = null;
                try{
                    fin = new FileReader(f);
                    BufferedReader bufReader = new BufferedReader(fin);
                    String line = "";
                    while((line = bufReader.readLine()) != null){
                        if(line.contains("language : ")){
                            for(int i = 0; i<3; i++){
                                if(line.contains(languages[i])) {
                                    language = languages[i];
                                    lan_index = i;
                                }
                            }
                        }
                        if(line.contains("nickName : ")){
                            String a = "nickName : ";
                            nickName = line.substring(a.length());
                        }
                        if(line.contains("goalValue : ")){
                            String a = "goalValue : ";
                            goalType = Integer.parseInt(line.substring(a.length()));
                        }
                        if(line.contains("avgTypoValue : ")){
                            String a = "avgTypoValue : ";
                            avgType.add(Integer.parseInt(line.substring(a.length())));
                        }
                    }
                    bufReader.close();
                }catch(Exception ee){
                    System.out.println(ee);
                }
                
        }
        else{
            language = languages[0];
            nickName = "user";
            goalType = 100;
            avgType.add(0);
        }
        this.addMouseListener(new moveWindows());
        this.addMouseMotionListener(new moveWindows());
    }

    public void change(String panelName){
            getContentPane().removeAll();
        switch(panelName){
            case "BackToMain":
            getContentPane().add(m_Panel);
            revalidate();
            repaint();
            m_Panel.requestFocusInWindow();
            break;
            case "MainToProfile":
            p_Panel = new ProfilePanel(this);
            getContentPane().add(p_Panel);
            revalidate();
            repaint();
            p_Panel.requestFocusInWindow();
            break;
            case "MainToWord":
            w_Panel = new WordPracticePanel(this, language);
            getContentPane().add(w_Panel);
            revalidate();
            repaint();
            w_Panel.requestFocusInWindow();
            break;
            case "MainToSen":
            sen_Panel = new SentensePracticePanel(this, language);
            getContentPane().add(sen_Panel);
            revalidate();
            repaint();
            sen_Panel.requestFocusInWindow();

        }
          
    }
    public ArrayList<Integer> getAvgType(){
        return avgType;
    }
    public void setAvgType(int avgType){
        this.avgType.add(avgType);
        for(int i = 0 ; i<this.avgType.size(); i++){
            avgValue += this.avgType.get(i);
        }
        avgValue = avgValue/this.avgType.size();
    }
    public String getNickName(){
        return nickName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }
    public int getGoalType(){
        return goalType;
    }
    public void setGoalType(int goalType){
        this.goalType = goalType;
    }
    public String getLanguage(){
        return language;
    }
    public String ChangeLanguage(){
        if(lan_index == languages.length-1) lan_index = 0;
        else lan_index++;
        language = languages[lan_index];
        return language;
    }
    class moveWindows extends MouseAdapter implements MouseMotionListener{
        public void mousePressed(MouseEvent e){
            initialClick = e.getPoint();
            getComponentAt(initialClick);
        }

        public void mouseDragged(MouseEvent e){
            JFrame jf = (JFrame)e.getSource();
            int thisX = jf.getLocation().x;
            int thisY = jf.getLocation().y;

            int xMoved = e.getX() - initialClick.x;
            int yMoved = e.getY() - initialClick.y;

            int X = thisX + xMoved;
            int Y = thisY + yMoved;
            jf.setLocation(X, Y);
        }
        public void mouseMoved(MouseEvent e){}
    }

    public void ExitP(){
        int num = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
            if(num == 0){
                try{
                        fout = new FileWriter(f);
                        fout.write("nickName : " + nickName);
                        fout.write("\r\n",0,2);
                        fout.write("language : " + language);
                        fout.write("\r\n",0,2);
                        fout.write("goalValue : " + goalType);
                        fout.write("\r\n",0,2);
                        fout.write("avgTypoValue : " + avgValue);
                        fout.write("\r\n",0,2);
                        fout.close();
                }catch(Exception ee){
                    System.out.println("오류");
                }
                System.exit(0);
            }
    }
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.m_Panel = new MainPanel(mf);
        mf.add(mf.m_Panel);
        mf.revalidate();
        mf.repaint();
        mf.m_Panel.requestFocusInWindow();
    }
}