import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MainPanel.*;
import ProfilePanel.*;
import WordPracticePanel.*;

public class MainFrame extends JFrame{
    private static final long serialVersionUID = 1L;
    private MainPanel m_Panel = null;
    private ProfilePanel p_Panel = null;
    private WordPracticePanel w_Panel = null;

    public MainFrame(){
        super("입문 개발자를 위한 타자 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane();
        setSize(1200,800);
        setVisible(true);
        setResizable(false);
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