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
        addKeyListener(new MyKeyActionListener());
        setSize(1200,800);
        setVisible(true);
        requestFocus();
        setFocusable(true);
    }
    class MyKeyActionListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                int num = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
                if(num == 0)
                System.exit(0);
            }
        }
    }

    public void change(String panelName){
        if(panelName.equals("mainToProfile")){
            getContentPane().removeAll();
            getContentPane().add(p_Panel);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.m_Panel = new MainPanel(mf);
        mf.p_Panel = new ProfilePanel(mf);
        mf.add(mf.m_Panel);
        
    }
}