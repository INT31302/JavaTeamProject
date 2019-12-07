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
	private int total = 0; // 총 문제 수
	private final int max = 200;
	private JLabel typeLabel; // 입력 Label
	private java.util.List<String> tmp = new ArrayList<String>();
	private java.util.List<String> text = new ArrayList<String>();
	public static JLabel[] lifeMark = new JLabel[3];
	public static JLabel[] textLabel = new JLabel[13];
	ImageIcon lifeIcon = new ImageIcon("img/life.png");
	Runnable r = new Rain();
	Thread th = new Thread(r);
	public int life = 3; // 초기 라이프 값 = 3
	int levelValue = 1000;
	LevelDialog dialog = null;
	private int cnt = 0;

	public GamePanel(MainFrame mf, String language) {
		this.mf = mf;
		tmp.clear();
		total = tmp.size();
		setLayout(null);
		FileReader fin = null;
		try {
			switch (language) { // 언어별 파일 지정
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
			while ((line = bufReader.readLine()) != null) { // 파일을 한줄씩 읽음
				if (line.length() < 15) {
					tmp.add(line.trim()); // tmp에 한문장씩 추가
					total++; // 총 문제 수 증가
				}
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}

		for (int i = 0; i < max; i++) { // 100 문제 선정
			int rNum = (int) (Math.random() * total); // 문장 수 중에 랜덤 값 지정
			if (text.contains(tmp.get(rNum))) { // 이미 있는 문장일 경우 continue
				i--;
				continue;
			}
			text.add(tmp.get(rNum)); // 새로운 문장일 경우 text에 추가
		}

		typeLabel = new JLabel(); // 입력 Label 생성 및 설정
		typeLabel.setSize(400, 30);
		typeLabel.setLocation(500, 700);
		typeLabel.setFont(typeLabel.getFont().deriveFont(15.0F));
		typeLabel.setHorizontalAlignment(JLabel.CENTER);
		add(typeLabel);

		ImageIcon icon = new ImageIcon("img/g_start_btn.png");
		JButton btn_gamestart = new JButton(icon);
		btn_gamestart.setSize(100, 50);
		btn_gamestart.setLocation(750, 80);
		btn_gamestart.setBorderPainted(false);
		btn_gamestart.setContentAreaFilled(false);
		btn_gamestart.setFocusPainted(false);
		btn_gamestart.setFocusable(false);
		btn_gamestart.addActionListener(new StartEvent());
		add(btn_gamestart);

		ImageIcon icon2 = new ImageIcon("img/g_level_btn.png");
		JButton btn_level = new JButton(icon2);
		btn_level.setSize(100, 50);
		btn_level.setLocation(900, 80);
		btn_level.setBorderPainted(false);
		btn_level.setContentAreaFilled(false);
		btn_level.setFocusPainted(false);
		btn_level.setFocusable(false);
		btn_level.addActionListener(new LevelEvent());
		add(btn_level);

		JButton exitBtn = new JButton(new ImageIcon("img/g_exit_btn.png")); // 종료 버튼 생성 및 설정
		exitBtn.setPressedIcon(new ImageIcon("img/g_exit_pbtn.png"));
		exitBtn.setSize(25, 25);
		exitBtn.setLocation(1160, 5);
		exitBtn.setBorderPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setFocusPainted(false);
		exitBtn.addActionListener(new ExitPro()); // 버튼 이벤트 추가
		add(exitBtn);

		JButton miniBtn = new JButton(new ImageIcon("img/g_mini_btn.png")); // 최소화 버튼 생성 및 설정
		miniBtn.setPressedIcon(new ImageIcon("img/g_mini_pbtn.png"));
		miniBtn.setSize(25, 25);
		miniBtn.setLocation(1120, 5);
		miniBtn.setBorderPainted(false);
		miniBtn.setContentAreaFilled(false);
		miniBtn.setFocusPainted(false);
		miniBtn.addActionListener(new MinimizneWindows()); // 버튼 이벤트 추가
		add(miniBtn);

		JButton backBtn = new JButton(new ImageIcon("img/g_back_btn.png")); // 뒤로가기 버튼 생성 및 설정
		backBtn.setPressedIcon(new ImageIcon("img/g_back_pbtn.png"));
		backBtn.setSize(50, 50);
		backBtn.setLocation(30, 700);
		backBtn.setBorderPainted(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setFocusPainted(false);
		backBtn.addActionListener(new BackToMainBtnEvent()); // 버튼 이벤트 추가
		add(backBtn);
		this.addKeyListener(new TypeEvent());
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	class LevelDialog extends JDialog {
		ButtonGroup group = new ButtonGroup();
		JRadioButton levelButton[] = new JRadioButton[5];
		String level[] = { "level 1", "level 2", "level 3", "level 4", "level 5" };
		JButton okButton = new JButton("확인");

		public LevelDialog(JFrame gamePanel, String title) {
			super(gamePanel, title, true);
			setLayout(new FlowLayout());
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setSize(400, 300);
			for (int i = 0; i < levelButton.length; i++) {
				levelButton[i] = new JRadioButton(level[i]);
				group.add(levelButton[i]);
				this.add(levelButton[i]);
			}
			levelButton[0].setSelected(true);
			this.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (levelButton[0].isSelected()) {
						levelValue = 1000;
					}
					if (levelButton[1].isSelected()) {
						levelValue = 800;
					}
					if (levelButton[2].isSelected()) {
						levelValue = 600;
					}
					if (levelButton[3].isSelected()) {
						levelValue = 400;
					}
					if (levelButton[4].isSelected()) {
						levelValue = 200;
					}
					LevelDialog.this.dispose();
					if (th.isAlive()) {
						th.interrupt();
						th = new Thread(r);
					}
				}
			});
		}
	}

	class LevelEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (dialog == null) {
				dialog = new LevelDialog(mf, "난이도 선택");
				dialog.addWindowListener(new dialogEvent());
				dialog.setVisible(true);
			}

		}
	}

	class dialogEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			dialog = null;
		}

		public void windowOpened(WindowEvent e) {
			switch (levelValue) {
			case 1000:
				dialog.levelButton[0].setSelected(true);
				break;
			case 800:
				dialog.levelButton[1].setSelected(true);
				break;
			case 600:
				dialog.levelButton[2].setSelected(true);
				break;
			case 400:
				dialog.levelButton[3].setSelected(true);
				break;
			case 200:
				dialog.levelButton[4].setSelected(true);
				break;
			}
		}
	}

	public void paintComponent(Graphics g) { // BackGround 설정을 위한 그리기 함수
		ImageIcon main_bg = new ImageIcon("img/game_back.png");
		g.drawImage(main_bg.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}

	class StartEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Collections.shuffle(text);
			if (th.getState() == Thread.State.NEW) {
				life = 3;
				for (int i = 0; i < textLabel.length; i++) {
					textLabel[i] = new JLabel(text.get(cnt));
					textLabel[i].setBounds(0, 0, 200, 30);
					textLabel[i].setFont(textLabel[i].getFont().deriveFont(Font.BOLD));
					textLabel[i].setFont(textLabel[i].getFont().deriveFont(15.0f));
					textLabel[i].setLocation(i * 80 + 30, (int) ((Math.random() * 250) + 10));
					textLabel[i].setVisible(false);
					add(textLabel[i]); // 문제를 패널에 추가
					cnt++;
				}

				for (int i = 0; i < life; i++) { // 라이프 추가
					lifeMark[i] = new JLabel(lifeIcon);
					lifeMark[i].setOpaque(false);
					lifeMark[i].setBounds(500 + (i * 80), 750, 50, 50);
					add(lifeMark[i]);
					lifeMark[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
					lifeMark[i].setVisible(true);

				}
				if (!th.isAlive()) {
					th.start();
				}

			}
		}
	}

	class TypeEvent extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			int key = e.getKeyChar();
			String ta = typeLabel.getText();
			if (key == KeyEvent.VK_ESCAPE) {
				if (th.isAlive()) {// 스레드가 실행 중일 경우
					th.interrupt(); // 스레드 강제 종료
					JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE); // dialog
																											// 띄워줌
				}

				mf.change("BackToMain");
			} else if (key == KeyEvent.VK_BACK_SPACE) { // 백스페이스 입력 시 실행

				if (typeLabel.getText().length() > 0) { // 입력된 텍스트가 있을 경우
					typeLabel.setText(typeLabel.getText().substring(0, typeLabel.getText().length() - 1)); // 입력창에 입력값 중
																											// 마지막 문자 삭제
				}
			} else if (key == KeyEvent.VK_ENTER) {
				for (int i = 0; i < 13; i++) {
					if (ta.equals(textLabel[i].getText())) { // 입력한 값과 textLabel에서 같은 게 있을 경우
						cnt++; // cnt 증가
						if (cnt < text.size()) { // cnt가
							textLabel[i].setLocation(i * 80 + 30, (int) ((Math.random() * 250) + 10));
							textLabel[i].setText(text.get(cnt));
						} else {
							textLabel[i].setLocation(0, 0);
							textLabel[i].setText("");
						}
						textLabel[i].setVisible(false);
						revalidate();
						repaint();
						typeLabel.setText("");
					} else {
						typeLabel.setText("");
					}
				}

			} else {
				typeLabel.setText(typeLabel.getText() + e.getKeyChar());
			}
		}

	}

	public class Rain implements Runnable {

		public void run() {
			while (true) {
				try {
					Thread.sleep(levelValue);
				} catch (InterruptedException e) {
					for (int j = 0; j < textLabel.length; j++) {
						textLabel[j].setVisible(false);
					}
					return;
				}
				for (int j = 0; j < textLabel.length; j++) {
					int x = textLabel[j].getX();
					int y = textLabel[j].getY();
					if (!textLabel[j].getText().equals("")) {
						y += 10; // 좌표를 10씩 증가시킨다
						textLabel[j].setLocation(x, y);
					}
					if (textLabel[j].getY() > 130)
						textLabel[j].setVisible(true);
					if (textLabel[j].isVisible() && textLabel[j].getY() > 650) {
						cnt++; // cnt 증가
						if (cnt < text.size()) { // cnt가
							textLabel[j].setLocation(j * 80 + 30, (int) ((Math.random() * 250) + 10));
							textLabel[j].setText(text.get(cnt));
						} else {
							textLabel[j].setLocation(0, 0);
							textLabel[j].setText("");
						}
						textLabel[j].setVisible(false);
						revalidate();
						repaint();
						life -= 1; // 라이프를 1감소시킨다
						switch (life) { // 라이프 갯수 판별
						case 2:
							lifeMark[2].setVisible(false);
							break;
						case 1:
							lifeMark[1].setVisible(false);
							break;
						case 0:
							lifeMark[0].setVisible(false);
							JOptionPane.showMessageDialog(lifeMark[0], "Game Over", "Game Over",
									JOptionPane.INFORMATION_MESSAGE);
							mf.change("BackToMain");
							return;
						}
					}

				}

			}
		}
	}

	class BackToMainBtnEvent implements ActionListener { // 뒤로가기 버튼 이벤트
		public void actionPerformed(ActionEvent e) {
			if (th.isAlive()) {
				th.interrupt();
				JOptionPane.showMessageDialog(null, "수고하셨습니다.", "종료", JOptionPane.INFORMATION_MESSAGE); // dialog
																										// 띄워줌
			}
			mf.change("BackToMain"); // MainFrame change 함수 사용하여 MainPanel 불러옴
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