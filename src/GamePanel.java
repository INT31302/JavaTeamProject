import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class GamePanel extends JPanel {
	private MainFrame mf;
	private int progress = 0; // ������
	private int typo = 0; // ��Ÿ��
	private int accuracy = 0; // ��Ȯ��
	private JLabel progress_Per; // ����� Label
	private JProgressBar progress_bar; // ����� Bar
	private JProgressBar typo_bar; // ��Ÿ�� Bar
	private JProgressBar accuracy_bar; // ��Ȯ�� Bar
	private JLabel typo_Cnt; // ��Ÿ�� Label
	private JLabel accuracy_Per; // ��Ȯ�� Label
	private int total = 0; // �� ���� ��
	private int index = 0; // ���� ���� ��
	private final int max = 200;
	private JTextField answer; // ����ڰ� �ܾ �Է��ϴ� â
	private java.util.List<String> tmp = new ArrayList<String>();
	private java.util.List<String> text = new ArrayList<String>();
	public static JLabel[] lifeMark = new JLabel[3];
	public static JLabel[] textLabel = new JLabel[13];
	private String fallingword = null;
	ImageIcon lifeIcon = new ImageIcon("img/life.png");
	Rain r = new Rain();
	Thread th = new Thread(r);
	public int life = 3; // �ʱ� ������ �� = 3

	public GamePanel(MainFrame mf, String language) {
		this.mf = mf;
		tmp.clear();
		total = tmp.size();
		setLayout(null);
		FileReader fin = null;
		try {
			switch (language) { // �� ���� ����
			case "Java":
				fin = new FileReader("txt/java/word.txt");
				break;
			case "C":
				fin = new FileReader("txt/c/word.txt");
				break;
			case "Python":
				fin = new FileReader("txt/python/word.txt");
				break;
			}
			BufferedReader bufReader = new BufferedReader(fin);
			String line = "";
			while ((line = bufReader.readLine()) != null) { // ������ ���پ� ����
				if (line.length() > 15)
					continue;
				tmp.add(line.trim()); // tmp�� �ѹ��徿 �߰�
				total++; // �� ���� �� ����
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}

		for (int i = 0; i < max; i++) { // 100 ���� ����
			int rNum = (int) (Math.random() * total); // ���� �� �߿� ���� �� ����
			if (text.contains(tmp.get(rNum))) { // �̹� �ִ� ������ ��� continue
				i--;
				continue;
			}
			text.add(tmp.get(rNum)); // ���ο� ������ ��� text�� �߰�
		}

		progress_bar = new JProgressBar(0, 100); // ����� Bar ���� �� ����
		progress_bar.setSize(80, 10);
		progress_bar.setLocation(95, 90);
		progress_bar.setForeground(Color.BLACK);
		progress_bar.setBorderPainted(false);
		add(progress_bar);

		progress_Per = new JLabel(Integer.toString(progress) + "%"); // ����� Label ���� �� ����
		progress_Per.setSize(100, 30);
		progress_Per.setLocation(180, 80);
		add(progress_Per);

		typo_bar = new JProgressBar(0, 100); // ��Ÿ�� Bar ���� �� ����
		typo_bar.setSize(80, 10);
		typo_bar.setLocation(265, 90);
		typo_bar.setForeground(Color.BLACK);
		typo_bar.setBorderPainted(false);
		add(typo_bar);

		typo_Cnt = new JLabel(Integer.toString(typo)); // ��Ÿ�� Label ���� �� ����
		typo_Cnt.setSize(100, 30);
		typo_Cnt.setLocation(355, 80);
		add(typo_Cnt);

		accuracy_bar = new JProgressBar(0, 100); // ��Ȯ�� Bar ���� �� ����
		accuracy_bar.setSize(80, 10);
		accuracy_bar.setLocation(435, 90);
		accuracy_bar.setForeground(Color.BLACK);
		accuracy_bar.setBorderPainted(false);
		add(accuracy_bar);

		accuracy_Per = new JLabel(Integer.toString(accuracy) + "%"); // ��Ȯ�� Label ���� �� ����
		accuracy_Per.setSize(100, 30);
		accuracy_Per.setLocation(525, 80);
		add(accuracy_Per);

		answer = new JTextField(15);
		answer.setSize(200, 30);
		answer.setLocation(500, 700);
		answer.addActionListener(new AnswerEvent());
		add(answer);

		JButton btn_gamestart = new JButton("Start");
		btn_gamestart.setSize(100, 50);
		btn_gamestart.setLocation(750, 80);
		btn_gamestart.addActionListener(new StartEvent());
		add(btn_gamestart);

		JButton exitBtn = new JButton(new ImageIcon("img/w_exit_btn.png")); // ���� ��ư ���� �� ����
		exitBtn.setPressedIcon(new ImageIcon("img/w_exit_pbtn.png"));
		exitBtn.setSize(25, 25);
		exitBtn.setLocation(1160, 5);
		exitBtn.setBorderPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setFocusPainted(false);
		exitBtn.addActionListener(new ExitPro()); // ��ư �̺�Ʈ �߰�
		add(exitBtn);

		JButton miniBtn = new JButton(new ImageIcon("img/w_mini_btn.png")); // �ּ�ȭ ��ư ���� �� ����
		miniBtn.setPressedIcon(new ImageIcon("img/w_mini_pbtn.png"));
		miniBtn.setSize(25, 25);
		miniBtn.setLocation(1120, 5);
		miniBtn.setBorderPainted(false);
		miniBtn.setContentAreaFilled(false);
		miniBtn.setFocusPainted(false);
		miniBtn.addActionListener(new MinimizneWindows()); // ��ư �̺�Ʈ �߰�
		add(miniBtn);

		JButton backBtn = new JButton(new ImageIcon("img/w_back_btn.png")); // �ڷΰ��� ��ư ���� �� ����
		backBtn.setPressedIcon(new ImageIcon("img/w_back_pbtn.png"));
		backBtn.setSize(50, 50);
		backBtn.setLocation(30, 700);
		backBtn.setBorderPainted(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setFocusPainted(false);
		backBtn.addActionListener(new BackToMainBtnEvent()); // ��ư �̺�Ʈ �߰�
		add(backBtn);
	}

	public void paintComponent(Graphics g) { // BackGround ������ ���� �׸��� �Լ�
		ImageIcon main_bg = new ImageIcon("img/word_back.png");
		g.drawImage(main_bg.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}

	public void stopThread() {
		th.interrupt();
		JOptionPane.showMessageDialog(lifeMark[0], "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		mf.change("BackToMain");
	}

	class AnswerEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String ta = answer.getText();
			for (int i = 0; i < 13; i++) {
				if (ta.equals(textLabel[i].getText())) {
					textLabel[i].setVisible(false);
					revalidate();
					repaint();
					answer.setText("");
				} else {
					answer.setText("");
				}
			}
		}
	}

	class Rain implements Runnable {

		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("����!!!!!");
					return;
				}
				for (int j = 0; j < textLabel.length; j++) {
					int x = textLabel[j].getX();
					int y = textLabel[j].getY();
					y += 10; // ��ǥ�� 10�� ������Ų��
					textLabel[j].setLocation(x, y);
					if (textLabel[j].isVisible() && textLabel[j].getY() > 650) {
						textLabel[j].setVisible(false);
						life -= 1; // �������� 1���ҽ�Ų��

					}

				}
				switch (life) { // ������ ���� �Ǻ�
				case 2:
					lifeMark[2].setVisible(false);
					break;
				case 1:
					lifeMark[1].setVisible(false);
					break;
				case 0:
					lifeMark[0].setVisible(false);
					stopThread();
					break;
				}
			}
		}
	}

	class StartEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Collections.shuffle(text);
			if (th.getState() == Thread.State.NEW) {
				for (int i = 0; i < textLabel.length; i++) {
					textLabel[i] = new JLabel(text.get(i));
					textLabel[i].setBounds(0, 0, 200, 30);
					textLabel[i].setFont(new Font("Arial", Font.BOLD, 15));
					textLabel[i].setLocation(i * 70, (int) (Math.random() * 250) + 10);
					add(textLabel[i]); // ������ �гο� �߰�
				}

				for (int i = 0; i < lifeMark.length; i++) { // ������ �߰�
					lifeMark[i] = new JLabel(lifeIcon);
					lifeMark[i].setOpaque(false);
					lifeMark[i].setBounds(500 + (i * 80), 750, 50, 50);
					add(lifeMark[i]);
					lifeMark[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
					lifeMark[i].setVisible(true);
				}
				th.start();
				answer.requestFocus();
			}
		}
	}

	class BackToMainBtnEvent implements ActionListener { // �ڷΰ��� ��ư �̺�Ʈ
		public void actionPerformed(ActionEvent e) {
			mf.change("BackToMain"); // MainFrame change �Լ� ����Ͽ� MainPanel �ҷ���
		}
	}

	class MinimizneWindows implements ActionListener { // �ּ�ȭ ��ư �̺�Ʈ
		public void actionPerformed(ActionEvent e) {
			mf.setState(1); // ���� Frame�� �ּ�ȭ��Ŵ
		}
	}

	class ExitPro implements ActionListener { // ���� ��ư �̺�Ʈ
		public void actionPerformed(ActionEvent e) {
			mf.ExitP(); // MainFrame�� �ִ� ExitP �Լ��� ����Ͽ� ���α׷� �����Ŵ
		}
	}
}
