package classes;

import java.util.Scanner;
import interfaces.EquaInterface;

public class Equation implements EquaInterface {
   
    @Override
    public String removeWhiteSpaces(String expression) {
        return expression.replaceAll("\\s+","");
    }

    @Override
    public double[] getA(String expression) {
      expression = removeWhiteSpaces(expression);
      double[] A = null;  

      //if there is no variable inputed, print a warning
      if(!expression.contains("x") && !expression.contains("y") && !expression.contains("z") && !expression.contains("=")) {
        System.out.println("Please, input a equation with variables to be calculated. Equals (=) is mandatory.");
      }else{
        //identify where the variables are and store them in an array
        A = new double[3];
        
          if(expression.indexOf("x") >= 0){
            //if there is a x in the equation, try to get the number, if no number is specified, replace with 1
            try{
              A[0] = Integer.parseInt(expression.substring(0, expression.indexOf("x")));
            }catch(NumberFormatException e){
              if(expression.substring(0, expression.indexOf("x")).equals("-")){
                A[0] = -1;
              }else{
                A[0] = 1;
              }
            }
          }
          if(expression.indexOf("y") >= 0){
            //if there is a y in the equation, try to get the number, if no number is specified, replace with 1
            try{
              A[1] = Integer.parseInt(expression.substring(expression.indexOf("x") + 1, expression.indexOf("y")));
            }catch(NumberFormatException e ){
              if(expression.substring(expression.indexOf("x") + 1, expression.indexOf("y")).equals("-")){
                A[1] = -1;
              }else{
                A[1] = 1;
              }
            }
          }
          if(expression.indexOf("z") >= 0){
            //if there is a z in the equation, try to get the number, if no number is specified, replace with 1
            try{
              A[2] = Integer.parseInt(expression.substring(expression.indexOf("y") + 1, expression.indexOf("z")));
            }catch(NumberFormatException e){
              if(expression.substring(expression.indexOf("y") + 1, expression.indexOf("z")).equals("-")){
                A[2] = -1;
              }else{
                A[2] = 1;
              }
            }  
          }
        } 
      return A;
    }

    
    @Override
    public double getb(String expression) {
        double b;
        expression = removeWhiteSpaces(expression);

         //gets the number of b (after equality)
          //first, we remove all numbers which are multiplying the incognitos from the string
          if(expression.contains("x")){
            expression = removeSubstring(expression, 0, expression.indexOf("x") + 1);
          }
          if(expression.contains("y")){
            expression = removeSubstring(expression, 0, expression.indexOf("y") + 1);
          }
          if(expression.contains("z")){
            expression = removeSubstring(expression, 0, expression.indexOf("z") + 1);
          }

          //after, we check if the equals symbol is the first character
          if(expression.indexOf("=") == 0){
            //if is the first, we assign the value to b variable with no modification
            b = Integer.parseInt(expression.substring(1));
          }else{
            //if not, we change the signal once it will move after equality
            expression = removeSubstring(expression, expression.indexOf("="), expression.length()-1);
            b = Double.parseDouble(expression);
            b *= -1;
            b /= 10; //divide by 10 in order to eliminate 0
          }
        return b;
    }

    private String removeSubstring(String text, int startIndex, int endIndex) {
        if (endIndex < startIndex) {
            startIndex = endIndex;
        }
  
        String a = text.substring(0, startIndex);
        String b = text.substring(endIndex);
        return a + b;
    }
    
}
