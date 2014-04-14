import java.io.*;
import java.util.*;

public class ThreeDMaze {

	private final int ROW = 8;
	private final int COLUMN = 8;
	private final int LEVEL = 8;
	private final int VALID = 1;
	private final int BARRIER = 0;
	private final int VISITED = 3;
	private final int PATH = 7;
	private int[][][] Maze = new int[ROW][COLUMN][LEVEL];
	private ArrayList<Point3D> Answer = new ArrayList<Point3D>();
	private static int numOfFiles = 0;

	public ThreeDMaze() throws IOException{
		Fill();
		printMazeNew();
		Solve();
	}

	public ThreeDMaze(String file) throws IOException{
		try{
			Fill(file);
			SolveFile();
			printAnswerFile();
		} catch (FileNotFoundException e){
			System.out.println("Please enter a valid file.");
		}
	}

	private void Fill(String file) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(file));
		int row = 0;
		int level = 0;
		String current = "";
		while(scan.hasNextLine()){
			current = scan.nextLine();
			if (!current.equals("")){
				Scanner cur = new Scanner(current);
				if(row <= ROW){
					for(int col = 0; cur.hasNext(); col++){
						String next = cur.next();
						// PARSE INT!!
						if(Character.isDigit(next.charAt(0)))
							Maze[level][row][col] = Integer.parseInt(next);
						if(next.equals("")) level++;
					}
				}

				row++;
			
				if(row == ROW){
					row = 0;
					level++;
				}
				if(level == LEVEL)
					break;
			}
		}
	}

	private void Fill(){
		Random r = new Random();
		
		for(int z= 0; z < LEVEL; z++){
			for (int x = 0; x < ROW; x++){
				for (int y = 0; y < COLUMN; y++){
					Maze[z][x][y] = Math.abs(r.nextInt() % 2);
				}
			}
		}

		Maze[0][0][0] = 1;
		Maze[ROW - 1][COLUMN - 1][LEVEL - 1] = 1;
	}
	
	private void printMazeNew() throws IOException{
		String file = "3DMaze_" + numOfFiles + ".txt";
		numOfFiles++;
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter outFile = new PrintWriter(bw);
		
		for(int z = 0; z < LEVEL; z++){
			for(int x = 0; x < ROW; x++){
				for (int y = 0; y < COLUMN; y++){
					outFile.print(Maze[z][x][y] + " ");
				}
				outFile.println();
			}
			outFile.println();
		}
		outFile.close();
	}

	public void printAnswerFile() throws IOException{
		String file =  "InputMazeAnswer.txt";
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter outFile = new PrintWriter(bw);
		if(Answer.isEmpty()){
			outFile.println("Maze is unsolvable.");
		}	
		for(int p = Answer.size() - 1; p >= 0; p--){
			Point3D temp = Answer.get(p);
			outFile.println("(" + temp.getY() + ", " + temp.getX() + ", " + temp.getZ() + ")");
		}
		outFile.close();
	}
	
	public void printAnswer() throws IOException{
		String file = "AnswerFor3DMaze_" + (numOfFiles - 1) + ".txt";
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter outFile = new PrintWriter(bw);
		if(Answer.isEmpty()){
			outFile.println("Maze is unsolvable.");
		}	
		for(int p = Answer.size() - 1; p >= 0; p--){
			Point3D temp = Answer.get(p);
			outFile.println("(" + temp.getY() + ", " + temp.getX() + ", " + temp.getZ() + ")");
		}
		outFile.close();
	}

	public boolean Solve() throws IOException{
		if(Solve(0,0,0)){
			printAnswer();
			return Solve(0,0,0);
		}
		else{
			Fill();
			printMazeNew();
			return Solve();
		}
	}
	
	public boolean SolveFile() throws IOException{
		if(Solve(0,0,0)){
			printAnswerFile();
			return Solve(0,0,0);
		}
		else
			return false;
	}

	private boolean Solve(int z,int x, int y){
		Point3D point = new Point3D(x,y,z);
		if(x < 0 || y < 0 || z < 0 || x > ROW - 1 || y > COLUMN - 1 || z > LEVEL - 1)
			return false;
		if (Maze[z][x][y] != VALID){ //Not on maze?
			return false;
		}

		Maze[z][x][y] = PATH; 

		if (x == ROW - 1 && y == COLUMN - 1 && z == LEVEL - 1){
			Answer.add(point);
			return true;
		}

		if(Solve(z,x,y+1)) {Answer.add(point); return true;} //up
		if(Solve(z,x+1,y)) {Answer.add(point); return true;} //right
		if(Solve(z,x-1,y)) {Answer.add(point); return true;} //left
		//if(Solve(z,x,y+1)) {Answer.add(point); return true;} //up
		if(Solve(z,x,y-1)) {Answer.add(point); return true;} //down
		if(Solve(z+1,x,y)) {Answer.add(point); return true;} //forward
		if(Solve(z-1,x,y)) {Answer.add(point); return true;} //backward

		Maze[z][x][y] = VISITED;
		return false;		
	}

	class Point3D{
		private int x;
		private int y;
		private int z;

		public Point3D(int x, int y, int z){
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		public int getZ(){
			return z;
		}
	}

}
