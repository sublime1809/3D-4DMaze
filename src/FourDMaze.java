import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FourDMaze {
	
   private final int ROW = 8;
   private final int COLUMN = 8;
   private final int LEVEL = 8;
   private final int FOURTH = 8;
   private final int VALID = 1;
   private final int BARRIER = 0;
   private final int VISITED = 3;
   private final int PATH = 7;
   private int[][][][] Maze = new int[ROW][COLUMN][LEVEL][FOURTH];
   private ArrayList<Point4D> Answer = new ArrayList<Point4D>();
   private static int numOfFiles = 0;
	
	public FourDMaze() throws IOException{
		Fill();
		printMaze();
		Solve();
	}
	
	private void Fill(){
		Random r = new Random();
		for(int z= 0; z < LEVEL; z++){
			for (int x = 0; x < ROW; x++){
				for (int y = 0; y < COLUMN; y++){
					for (int w = 0; w < FOURTH; w++){
						Maze[z][x][y][w] = Math.abs(r.nextInt() % 2);
					}
				}
			}
		}
	 
		Maze[0][0][0][0] = 1;
		Maze[ROW - 1][COLUMN - 1][LEVEL - 1][FOURTH - 1] = 1;
    }
	
    public void printMaze() throws IOException{
    	String file = "4DMaze_" + numOfFiles + ".txt";
    	numOfFiles++;
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter outFile = new PrintWriter(bw);
		
    	for(int z = 0; z < LEVEL; z++){
    		for(int x = 0; x < ROW; x++){
    			for(int w = 0; w < FOURTH; w++){
    				for (int y = 0; y < COLUMN; y++){
    					outFile.print(Maze[z][x][y][w] + " ");
    				}
    				outFile.print("\t");
    			}
    			outFile.println();
    		}
    		outFile.println();
    	}
    	outFile.close();
    }
	
    public void printAnswer() throws IOException{
    	String file = "AnswerFor4DMaze_" + (numOfFiles - 1) + ".txt";
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter outFile = new PrintWriter(bw);
    	if(Answer.isEmpty()){
    		Fill();
    		Solve();
    	}	
    	for(int p = Answer.size() - 1; p >= 0; p--){
    		Point4D temp = Answer.get(p);
    		outFile.println("(" + temp.getY() + ", " + temp.getX() + ", " + temp.getZ() + ", " + temp.getW() + ")");
    	}
    	outFile.close();
    }
	
    public boolean Solve() throws IOException{
    	if(Solve(0,0,0,0)){
    		printAnswer();
    		return Solve(0,0,0,0);
    	} else{
    		System.out.println("There were unsolvable 4D mazes generated.");
    		Fill();
    		printMaze();
    		return Solve();
    	}
    }
	
    private boolean Solve(int z,int x, int y, int w){
    	Point4D point = new Point4D(x,y,z,w);
      	if(x < 0 || y < 0 || z < 0 || w < 0 || x > ROW - 1 || y > COLUMN - 1 || z > LEVEL - 1 || w > FOURTH - 1)
      		return false;
 		if (Maze[z][x][y][w] != VALID) //Not on maze?
 			return false;
	
        Maze[z][x][y][w] = PATH; 
	
        if (x == ROW - 1 && y == COLUMN - 1 && z == LEVEL - 1 && w == FOURTH - 1){
        	Answer.add(point);
        	return true;
        }
	 
        if(Solve(z,x+1,y,w)) {Answer.add(point); return true;} 		//right
        if(Solve(z,x-1,y,w)) {Answer.add(point); return true;}		//left
        if(Solve(z,x,y+1,w)) {Answer.add(point); return true;} 		//up
        if(Solve(z,x,y-1,w)) {Answer.add(point); return true;} 		//down
        if(Solve(z+1,x,y,w)) {Answer.add(point); return true;} 		//forward
        if(Solve(z-1,x,y,w)) {Answer.add(point); return true;} 		//backward
        if(Solve(z,x,y,w+1)) {Answer.add(point); return true;} 	//up-dimension
        if(Solve(z,x,y,w-1)) {Answer.add(point); return true;} 	//back-dimension
        
	
        Maze[z][x][y][w] = VISITED;
        return false;		
	}
    
    class Point4D{
    	private int x;
    	private int y;
    	private int z;
    	private int w;
    	
    	public Point4D(int x, int y, int z, int w){
    		this.x = x;
    		this.y = y;
    		this.z = z;
    		this.w = w;
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
    	public int getW(){
    		return w;
    	}
    }
    
}
