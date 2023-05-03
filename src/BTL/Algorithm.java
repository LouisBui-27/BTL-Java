package BTL;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Icon;

public class Algorithm {
	private int row;
	private int col;
	private int notBarrier = 0;
	private int[][] matrix;
	
	public Algorithm(int row, int col) {
		this.row = row;
		this.col = col;
		System.out.println(row + "," + col);
		createMatrix();
		showMatrix();
	}
	
	public void showMatrix() {
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				System.out.printf("%3d", matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	
	private void createMatrix() {
		matrix = new int[row][col];
		for(int i=0;i<col;i++) {
			matrix[0][i]= matrix[row-1][i]  = 0;
		}
		for(int i=0;i<row;i++) {
			matrix[i][0] = matrix[i][col-1] = 0;
		}
		
		Random rand = new Random();
		int imgNumber = 37;
		int maxDouble = imgNumber /4;
		int imgArr[] = new int[imgNumber+1];
		ArrayList<Point> listPoint = new ArrayList<Point>();
		for(int i=1;i<row-1;i++) {
			for(int j=1;j<col-1;j++) {
				listPoint.add(new Point(i,j));
			}
		}
		
		int i=0;
		do {
			int imgIndex = rand.nextInt(imgNumber)+1;
			if(imgArr[imgIndex]< maxDouble) {
				imgArr[imgIndex] +=2;
				for(int j=0;j<2;j++) {
					try {
						int size = listPoint.size();
						int pointIndex = rand.nextInt(size);
						matrix[listPoint.get(pointIndex).x][listPoint.get(pointIndex).y] 
								= imgIndex;
						listPoint.remove(pointIndex);
						
					} catch (Exception e) {
					}
					
				}
				i++;
			}
		}while(i < row * col /2);
	}
	
	//check hàng x trên cột y1 đến y2
	private boolean checkLineX(int y1, int y2, int x) {
		System.out.println("check line x");
		// tìm max và min giữa y1 và y2
		int min = Math.min(y1, y2);
		int max = Math.max(y1, y2);
		// run column
		for (int y = min + 1; y < max; y++) {
			if (matrix[x][y] > notBarrier) { // if see barrier then die
				System.out.println("die: " + x + " " + y);
				return false;
			}
			System.out.println("ok: " + x + "" + y);
		}
		// not die -> success
		return true;
	}
	
	private boolean checkLineY(int x1,int x2,int y) {
		System.out.println("check line y");
		
		int min= Math.min(x1, x2);
		int max = Math.max(x1, x2);
		for(int x=min +1;x<max;x++) {
			if(matrix[x][y] >notBarrier) {
				System.out.println("die: "+ x +" "+y);
				return false;
			}
			System.out.println("oke");
		}
		return true;
	}
	
	private int checkRectX(Point p1,Point p2) {
		System.out.println("Check Rect X:");
		
		Point pMiny = p1, pMaxy = p2;
		if(p1.y>p2.y){
			pMiny = p2;
			pMaxy = p1;
		}
		for(int y=pMiny.y ; y<=pMaxy.y ; y++) {
			if(y>pMiny.y && matrix[pMiny.x][y]>notBarrier) {
				return -1;
			}
			
			if((matrix[pMaxy.x][y] == notBarrier || y== pMaxy.y)
					&& checkLineY(pMiny.x, pMaxy.x, y)
					&& checkLineX(y, pMaxy.y, pMaxy.x)) {
				System.out.println("Rect X");
				System.out.println("(" + pMiny.x+ "," + pMiny.y + ") -> (" +
						pMiny.x + "," + y + ") -> (" + pMaxy.x + "," + y +
						") -> (" + pMaxy.x + "," + pMaxy.y+")");
				return y;
			}
		}
		return -1;
	}
	
	private int checkRectY(Point p1,Point p2) {
		System.out.println("Check Rect Y:");
		
		Point pMinx = p1, pMaxx = p2;
		if(p1.x>p2.x){
			pMinx = p2;
			pMaxx = p1;
		}
		for(int x=pMinx.x ; x<=pMaxx.x ; x++) {
			if(x>pMinx.x && matrix[x][pMinx.y]>notBarrier) {
				return -1;
			}
			
			if((matrix[x][pMaxx.y] == notBarrier || x== pMaxx.x)
					&& checkLineX(pMinx.y, pMaxx.y, x)
					&& checkLineY(x, pMaxx.x, pMaxx.y)) {
				System.out.println("Rect Y");
				System.out.println("(" + pMinx.x+ "," + pMinx.y + ") -> (" +
						x + "," + pMinx.y + ") -> (" + x + "," + pMaxx.y +
						") -> (" + pMaxx.x + "," + pMaxx.y+")");
				return x;
			}
		}
		return -1;
	}
	
	private int CheckMoreLineX(Point p1,Point p2,int type) {
		System.out.println("Check more X");
		
		Point pMinY = p1, pMaxY = p2;
		if(p1.y>p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		
		int y = pMaxY.y + type;
		int row = pMinY.x;
		int colFinish = pMaxY.y;
		if(type == -1) {
			colFinish = pMinY.y;
			row = pMaxY.x;
			y = pMinY.y +type;
			System.out.println("colFinish = " +colFinish);
		}
		
		if((matrix[row][colFinish] == notBarrier || pMinY.y ==pMaxY.y)
				&& checkLineX(pMinY.y, pMaxY.y, row)) {
			while (matrix[pMinY.x][y] ==notBarrier && matrix[pMaxY.x][y]==notBarrier) {
				if(checkLineY(pMinY.x, pMaxY.x, y)) {
					System.out.println("Th X, type =  "+type);
					System.out.println("("+ pMinY.x + "," + pMinY.y+")->(" 
							+ pMinY.x +","+ y +")->(" + pMaxY.x+","+ y+")->(" + pMaxY.x
							+ ","+ pMaxY.y+")");
					return y;
				}
				y+=type;
			}
		}
		return -1;
		
	}
	
	private int CheckMoreLineY(Point p1, Point p2, int type) {
		System.out.println("Check more Y");
		Point pMinX= p1, pMaxX = p2;
		if(p1.x> p2.x) {
			pMaxX =p1;
			pMinX = p2;
		}
		int x = pMaxX.x +type;
		int col = pMinX.y;
		int rowFinish = pMaxX.x;
		if(type == -1) {
			rowFinish= pMinX.x;
			x = pMinX.x + type;
			col = pMaxX.y;
			System.out.println("rowFinish = "+rowFinish);
		}
		
		if((matrix[rowFinish][col] == notBarrier || pMinX.x == pMaxX .x)
				&&checkLineY(pMinX.x, pMaxX.x, col)) {
			while(matrix[x][pMinX.y] ==notBarrier
					&& matrix[x][pMaxX.y] == notBarrier) {
				if(checkLineX(pMinX.y, pMaxX.y, x)) {
					System.out.println("TH y, type =:"+type);
					System.out.println("("+ pMinX.x+","+pMinX.y+")->("
					+ x+ ","+ pMinX.y+")->("+ x +"," +pMaxX.y+")->("+ pMaxX.x
					+"," + pMaxX.y +")");
					return x;
				}
				x+=type;
			}
		}
		return -1;
	}
	  
	public Line checkTwoPoint(Point p1,Point p2) {
	    if(!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]) {
			if(p1.x == p2.x) {
				System.out.println("Line X");
				if(checkLineX(p1.y, p2.y, p1.x)) {
					return new Line(p1, p2);
				}
			}
			if(p1.y == p2.y) {
				System.out.println("Line Y");
				if(checkLineY(p1.x, p2.x, p1.y)) {
					return new Line(p1, p2);
				}
			}
			int t=-1;
			if((t = checkRectX(p1, p2))!=-1){
				System.out.println("Rect X");
				return new Line(new Point(p1.x,t),new Point(p2.x,t));
			}
			
			if((t=checkRectY(p1, p2))!=-1) {
				System.out.println("Rect Y");
				return new Line(new Point(t,p1.y), new Point(t,p2.y));
			}
			if((t= CheckMoreLineX(p1, p2, 1))!=-1) {
				System.out.println("Check right");
				return new Line(new Point(p1.x,t),new Point(p2.x,t));
			}
			
			if((t= CheckMoreLineX(p1, p2, -1))!=-1) {
				System.out.println("Check left");
				return new Line(new Point(p1.x,t),new Point(p2.x,t));
			}
			
			if((t=CheckMoreLineY(p1, p2, 1))!=-1) {
				System.out.println("Check down");
				return new Line(new Point(t,p1.y), new Point(t,p2.y));
			}
			
			if((t= CheckMoreLineY(p1, p2, -1))!=-1) {
				System.out.println("Check up");
				return new Line(new Point(t,p1.y),new Point(t,p2.y));
			}
		}
		return null;
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	public void updateMatrix() {
	    // Duyệt qua mỗi cột từ trái sang phải
		for (int i=1; i <row-1; i++) {
	        // Duyệt qua mỗi dòng từ dưới lên trên
	        for (int j = 1; j <col - 1; j++) {
	            // Nếu ô hình ở dòng i-1, cột j trống
	            if (matrix[i][j] == 0) {
	                // Đổi chỗ ô hình ở dòng i và ô trống ở dòng i-1
	                matrix[i][j] = matrix[i+1][j];
	                matrix[i+1][j] = 0;
	            }
	        }
	    }	
	    showMatrix();
	}  
}
