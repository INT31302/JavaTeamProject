import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import javax.swing.*;

public class MainFrame extends JFrame{
    private static final long serialVersionUID = 1L;
    private MainPanel m_Panel = null;
    private ProfilePanel p_Panel = null;
    private WordPracticePanel w_Panel = null;
    private SentensePracticePanel sen_Panel = null;
    private Point initialClick;
    private int lan_index = 0;
    private String languages[] = {"Java","C","Python"};
    private String language = languages[lan_index];

    public MainFrame(){
        super("입문 개발자를 위한 타자 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane();
        Image icon = Toolkit.getDefaultToolkit().getImage("img/icon.png");
        setIconImage(icon);
        setSize(1200,800);
        setVisible(true);
        //setResizable(false);
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
            w_Panel = new WordPracticePanel(this);
            getContentPane().add(w_Panel);
            revalidate();
            repaint();
            w_Panel.requestFocusInWindow();
            break;
            case "MainToSen":
            sen_Panel = new SentensePracticePanel(this);
            getContentPane().add(sen_Panel);
            revalidate();
            repaint();
            sen_Panel.requestFocusInWindow();

        }
          
    }
    public String getLanguage(){
        return language;
    }
    public String ChangeLanguage(){
        language = languages[lan_index];
        System.out.println(language);
        if(lan_index == languages.length-1) lan_index = 0;
        else lan_index++;
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
            if(num == 0)
            System.exit(0);
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