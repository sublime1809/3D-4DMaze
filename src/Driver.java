import java.io.IOException;
import java.util.Scanner;


public class Driver {
	
	public static void main(String args[]){
		boolean error = false;
		boolean again = false;
		Scanner user = new Scanner(System.in);
		Object temp = null;

		do{
			System.out.println("3D or 4D? (3/4)");
				String answer = user.next();
				if(answer.equalsIgnoreCase("3")){
					try{
						System.out.println("From a file? (y/n)");
						if(user.next().equalsIgnoreCase("y")){
							System.out.println("Enter a file name: ");
							String file = user.next();
							temp = new ThreeDMaze(file);
							System.out.println("Done.");
						} else {
							temp = new ThreeDMaze();
							System.out.println("Done.");
						}
					} catch (IOException e){
						System.out.println("Not a valid file.");
						error = true;
					}
					System.out.println("Again? (y/n)");
					if(user.next().equalsIgnoreCase("y")){
						again = true;
					}
				}
				else if(answer.equalsIgnoreCase("4")){
					try{
						temp = new FourDMaze();
						System.out.println("Done.");
					} catch (IOException e){
						System.out.println("Not a valid file.");
					}
					System.out.println("Again? (y/n)");
					if(user.next().equalsIgnoreCase("y")){
						again = true;
					}
				}
		} while(error == true || again == true);
	}
}
