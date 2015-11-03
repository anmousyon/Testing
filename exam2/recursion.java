import java.util.*;
import java.lang.Math;

public class recursion{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter a large power of 2");
		double userInput = input.nextDouble();
		double answer = recursionFunction(userInput);
		if (answer == 1){
			System.out.println("The Number is of form 2^2n");
		}
		else {
			System.out.println("The number is not of the form 2^2n");
		}
	}

	public static double recursionFunction(double number){
		double sqrt = Math.sqrt(number);
		if (sqrt>2){
			return recursionFunction(sqrt);
		}
		else if (sqrt == 2){
			return 1;
		}
		else{
			return 0;
		}
		
	}
}
