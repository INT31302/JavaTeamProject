import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ProfilePanel extends JPanel{
    private MainFrame mf;
    private String sName[] = {"평균타수", "목표타수", "정확도"};
    private JLabel settingLabel[] = new JLabel[3];
    
    public ProfilePanel(MainFrame mf){
        this.mf = mf;
        setLayout(null);
        setBackground(Color.GREEN);
        for(int i = 0; i<sName.length; i++){
            JLabel la = new JLabel(sName[i]);
            la.setSize(100,20);
            la.setLocation(800+(i*100),30);
            add(la);
            settingLabel[i] = new JLabel(Integer.toString(0));
            settingLabel[i].setSize(100,20);
            settingLabel[i].setLocation(820+(i*100),60);
            add(settingLabel[i]);
        }

        JButton editBtn = new JButton();
        editBtn.setSize(100,20);
        //editBtn.setLocation
        JButton backBtn = new JButton("back");
        this.addKeyListener(new BackToMain());
        backBtn.setSize(100,30);
        backBtn.setLocation(30,700);
        backBtn.addActionListener(new BackToMainBtn());
        add(backBtn);

        setSize(1200,800);
        setVisible(true);
    }
    class BackToMainBtn implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mf.change("BackToMain");
        }
    }
    class BackToMain extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                mf.change("BackToMain");

            }
        }
    }
}
