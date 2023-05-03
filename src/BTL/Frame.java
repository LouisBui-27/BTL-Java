package BTL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Frame  extends JFrame implements ActionListener,Runnable{
	private static final long serialVersionUID = 1L;
//	private static int maxScore=0;
	private String author = "Bùi Phương Nam - 211211737";
	private int maxTime = 300;
	public int time = maxTime;
	private int row = 10;
	private int col = 10;
	private int width = 800;
	private int height = 650;
//	private JLabel maxScore;
	private JLabel lbScore;
	private JProgressBar progressTime;
	private JButton btnNewGame;
	private MyGraphics graphicsPanel;
	private JPanel mainPanel;
	
	public Frame() {
		add(mainPanel = createMainPanel());
		setTitle("POKEMON");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createGraphicsPanel(),BorderLayout.CENTER);
		panel.add(createControlPanel(),BorderLayout.EAST);
		panel.add(createStatusPanel(),BorderLayout.PAGE_END);
		return panel;
	}
	public JPanel createGraphicsPanel() {
		graphicsPanel = new MyGraphics(this, row, col);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.GRAY);
		panel.add(graphicsPanel);
		return panel;
	}
	
	private JPanel createControlPanel() {
//		maxScore = new JLabel("0");
		lbScore = new JLabel("0");
		progressTime = new JProgressBar(0,100);
		progressTime.setValue(100);
		
		//tạo điều khiển lbscore vs proTime dọc nhau mỗi phần cách nhau 5 đơn vị
		JPanel panelLeft = new JPanel(new GridLayout(0,1,5,5));
//		panelLeft.add(new JLabel("maxScore:"));
		panelLeft.add(new JLabel("Score: "));
		panelLeft.add(new JLabel("Time:"));
		
		JPanel panelCenter = new JPanel(new GridLayout(0,1,5,5));
//		panelCenter.add(maxScore);
		panelCenter.add(lbScore);
		panelCenter.add(progressTime);
		
		JPanel panelScoreAndTime = new JPanel(new BorderLayout(0,5));
		panelScoreAndTime.add(panelLeft,BorderLayout.WEST);
		panelScoreAndTime.add(panelCenter,BorderLayout.CENTER);
		
		JPanel panelControl = new JPanel(new BorderLayout(10,10));
		panelControl.setBorder(new EmptyBorder(10,3,5,3));
		panelControl.add(panelScoreAndTime);
		panelControl.add(btnNewGame = createButton("New Game"),BorderLayout.PAGE_END);
	
		Icon icon = new ImageIcon(getClass().getResource("/BTL/icon/pokemon.png"));
		
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Status"));
		panel.add(panelControl,BorderLayout.PAGE_START);
		panel.add(new JLabel(icon),BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createStatusPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.darkGray);
		JLabel lbAuthor = new JLabel(author);
		lbAuthor.setForeground(Color.green);
		panel.add(lbAuthor);
		return panel;
	}	
	
	//tạo nút 
	private JButton createButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressTime.setValue((int) ((double) time / maxTime * 100));
		}
		
	}

	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}

	public JLabel getLbScore() {
		return lbScore;
	}


	public void setLbScore(JLabel lbScore) {
		this.lbScore = lbScore;
	}


	public JProgressBar getProgressTime() {
		return progressTime;
	}


	public void setProgressTime(JProgressBar progressTime) {
		this.progressTime = progressTime;
	}

	public void newGame() {
		time = maxTime;
		graphicsPanel.removeAll();
		mainPanel.add(createGraphicsPanel(),BorderLayout.CENTER);
		mainPanel.validate();
		mainPanel.setVisible(true);
		lbScore.setText("0");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewGame) {
			showDialogNewGame("Bạn có muốn game mới?", "Warning",0);
		}
	}
	public void showDialogNewGame(String message, String title,int t) {
		int select = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(select == 0 ) {
			newGame();
		}else {
			if(t==1) {
				System.exit(0);
			}
			
		}
	}

}
