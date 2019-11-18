import java.awt.*;
import javax.swing.*;

public class SourcePracticePanel extends JPanel{
    MainFrame mf;
    String language;
    public SourcePracticePanel(MainFrame mf, String language){
        this.mf = mf;
        this.language = language;
        setLayout(null);

        setFocusable(true); // 포커스 설정 가능
        setSize(1200,800);
        setVisible(true);
    }

    
}
