	package BTL;
	
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.GridLayout;
	import java.awt.Image;
	import java.awt.Point;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	import javax.swing.Icon;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;
	import javax.swing.border.LineBorder;
	
		public class MyGraphics extends JPanel implements ActionListener{
			private static final long serialVersionUID = 1L;
			private int row;
			private int col;
			private int size =50,bound=2;
			private int score = 0;
			private JButton[][] btn;
			private Point p1 = null;
			private Point p2 = null;
			private Algorithm algorithm;
			private Line line;
			private Frame frame;
//			public static int maxScr = 0;
			private Color backGroundColor = Color.LIGHT_GRAY;
			private int item;
		
			public MyGraphics(Frame frame,int row,int col) {
				this.frame = frame;
				this.row = row + 2;
				this.col = col + 2;
				item = row * col /2;
				
				setLayout(new GridLayout(row,col,bound,bound));
				setBackground(backGroundColor);
		//		 thiết lập kích thước mặc định cho lưới bằng (size + bound) * col 
		//		 đơn vị theo chiều ngang và (size + bound) * row đơn vị theo chiều dọc.
				setPreferredSize(new Dimension((size+bound)*col,(size+bound)*row));
				setBorder(new EmptyBorder(10,10,10,10));
				//can giua theo truc y cho luoi
				setAlignmentY(JPanel.CENTER_ALIGNMENT);
				newGame();
			
			}
		
			public void newGame() {
				algorithm = new Algorithm(this.row, this.col);
				addArrayButton();
				
			}
			//them cac nut
			private void addArrayButton() {
				btn = new JButton[row][col];
				for(int i=1;i<row-1;i++) {
					for(int j=1;j<col-1;j++) {
						btn[i][j] = createButton(i+","+j);
						Icon icon = getIcon(algorithm.getMatrix()[i][j]);
						btn[i][j].setIcon(icon);
						add (btn[i][j]);
					}
				}
			}

			private Icon getIcon(int index) {
				int width = 48, hetght = 48;
				Image image = new ImageIcon(getClass().getResource("/BTL/icon/icon" + index + ".jpg")).getImage();
				Icon icon = new ImageIcon(image.getScaledInstance(width, hetght,image.SCALE_SMOOTH));
				return icon;
			}
			//tao cac nut
			private JButton createButton(String action) {
				JButton btn = new JButton();
				btn.setActionCommand(action);
				btn.setBorder(null);
				btn.addActionListener(this);
				return btn;
			}
			//Vô hiệu hóa các nút 
			public void execute(Point p1, Point p2) {
			    System.out.println("delete");
			    setDisable(btn[p1.x][p1.y]);
			    setDisable(btn[p2.x][p2.y]);
			   
			}
	
			private void setDisable(JButton btn) {
				btn.setIcon(null);
				btn.setBackground(backGroundColor);
				btn.setEnabled(false);
			}
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String btnIndex = e.getActionCommand();
				int indexDot = btnIndex.lastIndexOf(",");
				int x = Integer.parseInt(btnIndex.substring(0, indexDot));
				int y = Integer.parseInt(btnIndex.substring(indexDot + 1,
						btnIndex.length()));
				if (p1 == null) {
					p1 = new Point(x, y);
					btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
				} else {
					p2 = new Point(x, y);
					System.out.println("(" + p1.x + "," + p1.y + ")" + " --> " + "("
							+ p2.x + "," + p2.y + ")");
					line = algorithm.checkTwoPoint(p1, p2);
					if (line != null) {
						System.out.println("line != null");
						algorithm.getMatrix()[p1.x][p1.y] = 0;
						algorithm.getMatrix()[p2.x][p2.y] = 0;
						algorithm.showMatrix();
						execute(p1, p2);
						line = null;
						score += 10;
						item--;
						frame.time++;
						frame.getLbScore().setText(score + "");
					}
					btn[p1.x][p1.y].setBorder(null);
					p1 = null;
					p2 = null;
					System.out.println("done");
					if (item == 0) {
//						if(maxScr<score) {
//							maxScr = score;
//							frame.getmaxScore().setText(maxScr+"");
//						}
						frame.showDialogNewGame(
								"You are winer!\nDo you want play again?", "Win",1);
					}
				}
			}

			public int getScore() {
				return score;
			}

			public void setScore(int score) {
				this.score = score;
			}	
		
	}
