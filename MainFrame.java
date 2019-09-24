import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame{
    private static final long serialVersionUID = 1L;
    public MainPanel m_Panel = null;
    public ProfilePanel p_Panel = null;
    public MainFrame(){
        super("입문 개발자를 위한 타자 연습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,800);
        setVisible(true);
    }


    public void change(String panelName){
            getContentPane().removeAll();
        switch(panelName){
            case "MainToProfile":
            getContentPane().add(p_Panel);
            revalidate();
            repaint();
            p_Panel.requestFocusInWindow();
            break;
            case "ProfileToMain":
            getContentPane().add(m_Panel);
            revalidate();
            repaint();
            m_Panel.requestFocusInWindow();
            break;
            
        }
          
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.m_Panel = new MainPanel(mf);
        mf.p_Panel = new ProfilePanel(mf);
        mf.add(mf.m_Panel);
        mf.m_Panel.requestFocusInWindow();
        
    }
}