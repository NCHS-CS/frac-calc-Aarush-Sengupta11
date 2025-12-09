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
      // TODO: implement this method!
      Scanner parser = new Scanner(input);
      parser.next();
      String operator = parser.next();
      String x = parser.next();
      String num = "0";
      String den = "1";
      String whole = "0";

     /*   if (x.length() == 3) {
         num = Character.toString(x.charAt(0));
         den = Character.toString(x.charAt(2));
      }
      else if (x.length() == 5) {
         whole = Character.toString(x.charAt(0));
         num = Character.toString(x.charAt(2));
         den = Character.toString(x.charAt(4));
      }
         */
      if (x.indexOf('/') == -1) {
         whole = Character.toString(x.charAt(0));
      }
      else {
         //num = Character.toString(x.charAt(x.indexOf('/')-1));
         
         if (x.indexOf('_') != -1) {
            //whole = Character.toString(x.charAt(x.indexOf('_')-1));
            whole = x.substring(0, (x.indexOf('_')));
            num = x.substring((x.indexOf('_')+1), (x.indexOf('/')));
         }
         else {
            num = x.substring(0, x.indexOf('/'));
         }
         
         //den = Character.toString(x.charAt(x.indexOf('/')+1));
         den = x.substring((x.indexOf('/')+1), x.length());

         if (num.charAt(0) == '-' && den.charAt(0) == '-') {
            num = num.substring(1, num.length());
            den = den.substring(1, den.length());
         }
      }
      
      return "Op:" + operator + " Whole:" + whole + " Num:" + num + " Den:" + den;

   }
   

   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      // TODO: Update this help text!
     
      String help = "Welcome to the fraction calculator. Please enter a mathematical expression of your choosing. You can add(+), subtract, multiply(*), and divide(/) whole integers and fractions. Please make sure to space out each term.\n";
      return help;
   }
}

