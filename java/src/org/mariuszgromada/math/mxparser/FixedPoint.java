package org.mariuszgromada.math.mxparser;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class FixedPoint {
	//this is the main program that uses method newtonMethod()
	public static void main (String [] args) {
		Scanner input = new Scanner(System.in);

		System.out.print("Enter f(x): ");
		@SuppressWarnings("unused")
		String justForDisplay = input.nextLine();//just for display
		System.out.print("Enter g(x): ");
		String function = input.nextLine();//input function
		System.out.print("Enter value of x: ");
		String valueX = input.nextLine();//your x0 but in string
		System.out.print("Enter value of Ea: ");
		double z = input.nextDouble();//absolute error 		

		Function f = new Function ("f(x) = "+function); 
		Argument x = new Argument ("x = "+valueX);
		Expression f0 = new Expression ("f(x)", f, x);//parsing original function
		Expression f1 = new Expression ("der("+function+", x, "+valueX+")");//parsing first derivative of the function

		double x0 = Double.valueOf(valueX);
		double x1 =  round (f0.calculate(),4);
		double Ea = Math.abs(round(x1, 4) - round(x0, 4));

		//Test for Convergence
		double testForConvergence = Math.abs(round( f1.calculate(),4));

		if (testForConvergence<1&&testForConvergence>0) {	
			System.out.println("\nCONVERGENCE TESTING");
			System.out.println("\n|f'("+valueX+")| = "+ testForConvergence+", hence, it is convergent.");

			System.out.print("\n----------------------------------------------------" );
			System.out.printf("\n%5s%11s%15s%15s\n", "Iteration", "xn", "xn+1", "Ea");
			System.out.print("----------------------------------------------------\n" );			

			System.out.printf("%5s%15s%15s%15s\n", 1, round((x0),4), round (x1,4), round (Ea,4));			
			for (int i = 2; Ea>z; i++) {
				x = new Argument ("x = "+String.valueOf(x1));
				f0 = new Expression ("f(x)", f, x);//parsing original function

				x0 = x1;
				x1 =  round (f0.calculate(),4);
				Ea = Math.abs(round(x1, 4) - round(x0, 4));

				if (i>100) break;
				if (Ea == 0)break;
				System.out.printf("%5s%15s%15s%15s\n", i, round((x0),4), round (x1,4), round (Ea,4));
			}
			System.out.print("----------------------------------------------------" );
			System.out.println("\nROOT is "+round (x1,4));
		}

		if (testForConvergence >=1) {			
			System.out.println("|f'("+valueX+") = "+ testForConvergence+", hence, it is divergent");
			System.out.println("Try another g(x). Please rerun the program.");
		}

		input.close();

	}	
	//a static method to round decimals in a specific place
	static double round(double value, int places) {
		BigDecimal bd = new BigDecimal(Double.toString(value));		
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}