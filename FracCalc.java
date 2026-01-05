// Aarush Sengupta
// Period 6
// Fraction Calculator Project

import java.util.*;

// TODO: Description of what this program does goes here.
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      // TODO: Implement this method
      System.out.print("Enter:   ");
      String s = console.nextLine();
      return s;

   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4
   public static String processExpression(String input) {
      //Uses a scanner to parse the input into two terms and the operator
      Scanner parser = new Scanner(input);
      String term1 = parser.next();
      String operator = parser.next();
      String term2 = parser.next();
      parser.close();
      //Finds and declares each part of the expression as an int (numerators, denominators, whole numbers)
      int num1 = findNum(term1);
      int num2 = findNum(term2);
      int den1 = findDen(term1);
      int den2 = findDen(term2);
      int whole1 = findWhole(term1);
      int whole2 = findWhole(term2);
      

      //Change any mixed numbers to improper fractions
      if (whole1 != 0) {
         if (whole1 < 0) {
            num1 = (den1 * whole1) - num1;
         } else {
            num1 = (den1 * whole1) + num1;
         }
         
         whole1 = 0;       
      }
      if (whole2 != 0) {
         if (whole2 < 0) {
            num2 = (den2 * whole2) - num2;
         } else {
            num2 = (den2 * whole2) + num2;
         }
         
         whole2 = 0;
         
      }

      //If the denominator is less than zero then multiply both numerator and denominator by -1 (also gets rid of negatives if negatives are present on both the numerator and the denominator)
      if (den1 < 0) {
         num1 *= -1;
         den1 *= -1;
      }
      if (den2 < 0) {
         num2 *= -1;
         den2 *= -1;
      }
      
     //Find numResult and den result. Set wholeResult to 0 for now
      String result =  "";
      int numResult = findNumResult(num1, den1, num2, den2, operator);
      int denResult = findDenResult(num1, den1, num2, den2, operator);
      int wholeResult = 0;

      //if any denominator is zero or den result is zero then result should be "Can't divide by zero!"
      if (den1 == 0 || den2 == 0 || operator.equals("/") && denResult == 0) {
         result = "Can't divide by zero!";
      }
      //else simplify and format the results
      else {
         result = Simplify(numResult, denResult, wholeResult, result);
      }
     
      

      
      //return the result of the expression
      return result;
   }

   //Finds the whole number in the expression
   public static int findWhole(String x) {
     
     String whole = "0";
     //If the expression doesn't include a fraction set the second term to the entire thing
     if (x.indexOf('/') == -1) {
         whole = x.substring(0, x.length());
      }
      //If the expression does includes a fraction and has an underscore set the whole number to the text before the '_'
      else {
         if (x.indexOf('_') != -1) {
            whole = x.substring(0, (x.indexOf('_')));
         }
      }
      return Integer.parseInt(whole);

   }

   //Finds the numerator in the expression
   public static int findNum(String x) {
      String num = "0";
      //If the expression does include a fraction and has an underscore set the numerator to the text between the '_' and the '/'
      if (x.indexOf('/') != -1) {
         
         if (x.indexOf('_') != -1) {
            num = x.substring((x.indexOf('_')+1), (x.indexOf('/')));
         }
         //If the expression doesn't have an underscore set the numerator to all text before the '/' sign
         else {
            num = x.substring(0, x.indexOf('/'));
         }
              
      }
      return Integer.parseInt(num);
   }

   //Finds the denominator in the expression
   public static int findDen(String x) {
      String den = "1";
      //If the expression does include a fraction set the denominator to all text after the '/' sign
      if (x.indexOf('/') != -1) {
         den = x.substring((x.indexOf('/')+1), x.length());
   }
      return Integer.parseInt(den);
   }

   
   
   //Finds the result of the numerators
   public static int findNumResult(int num1, int den1, int num2, int den2, String operator) {
      int numResult = 0;
      //If adding then cross multiply the numerators and denominators and add
      if (operator.equals("+")) {
         numResult = (num1 * den2) + (num2 * den1);
         }
      //If subtracting then cross multiply the numerators and denominators and subtract
      else if (operator.equals("-")) {
         numResult = (num1 * den2) - (num2 * den1);
      }
      //If multiplying then just multiply the numerators across from each other
      else if (operator.equals("*")) {
         numResult = num1 * num2;
      }

      //If dividing then multiply the first numerator and second denominator
      else if (operator.equals("/")) {
         numResult = num1 * den2;
         
      }
      return numResult;

   }

   //Finds the denominator results
   public static int findDenResult(int num1, int den1, int num2, int den2,  String operator) {
      int denResult = 0;
      //If dividing then multiply the first denominator and second numerator
      if (operator.equals("/")) {
         denResult = den1 * num2;
      }
      //Else always just multiply both denominators
      else {
         denResult = den1 * den2;
      } 
      return denResult;
   }
   
   //Finds the GCD
   public static int findGCD(int numResult, int denResult) {
      //Get absolute values of the numerators and the denominators
      if (numResult < 0) {
         numResult *= -1;
      }
      if (denResult < 0) {
         denResult *= -1;
      }

      //While the denominator is not zero denResult would be the numResult mod denResult and numResult would be the previous denResult
      while (denResult != 0) {
         int x = denResult;
         denResult = numResult % denResult;
         numResult = x;
      }
      //Return numResult as GCD
      return numResult;
   }

   //Simplifies and formats the end result
   public static String Simplify(int numResult, int denResult, int wholeResult, String result) {
         //If the denominator has a negative remove it and set the numerator negative
         if (denResult < 0) {
            numResult *= -1;
            denResult *= -1;
         }

         //Get GCD and divide the numerator and denominator by it
         int gcd = findGCD(numResult, denResult);
         numResult /= gcd;
         denResult /= gcd;
         
         int sign = 1;
         if (numResult < 0) {
            sign = -1;
            numResult *= -1;
         }

         //If improper fraction change to mixed number
         if (numResult >= denResult) {
            wholeResult = numResult / denResult;
            numResult = numResult % denResult;
         }
         
         if(wholeResult == 0) {
            numResult *= sign;
         }
         else {
            wholeResult *= sign;
         }
         
         //If there is no numerator the result would be the wholeResult
         if (numResult == 0) {
            result = Integer.toString(wholeResult);
         }
         //If there is no whole number then the result would just be the numerator over the denominator
         else if(wholeResult == 0) {
            result = numResult + "/" + denResult;
         }

         //Else the result would be the whole reuslt, numResult, and denResult
         else {
            result = wholeResult + " " + numResult + "/" + denResult; 
         }


         //Return the result to process expression
         return result;
      }
      
   
 
   

 
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      String help = "Welcome to the fraction calculator. Please enter a mathematical expression of your choosing. \nYou can add(+), subtract, multiply(*), and divide(/) whole integers and fractions, mixed included. \nPlease make sure to space out each term. \nIf you wish to operate on mixed fractions please seperate the whole number and the fraction with an underscore (_).\n";
      return help;
   }
}

