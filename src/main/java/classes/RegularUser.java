package classes;

import interfaces.RegularUserInterface;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RegularUser extends User implements RegularUserInterface{

    public RegularUser(String username, String surname, String pwd, String type) {
        super(username, surname, pwd,"Regular");
        //TODO Auto-generated constructor stub
    }

    @Override
    public void solveSystems() {
        int numberExpressions = 0,numberVariables = 0;
        sc = new Scanner(System.in);
        double[][] A; //expressions
        double[] b; //value after equality
        boolean typeError = false;
        Equation eq = new Equation(); //object from Equation
        String expression; //to be read
        ArrayList<String> expressions = new ArrayList<String>(); //store all typed expressions
        ArrayList<String> results = new ArrayList<String>(); //store all calculated results
        //get the number of expressions
        do{
            try{
                System.out.println("Please insert the number of expressions (max 3)");
                numberExpressions = sc.nextInt();
                typeError = false;
                }catch(InputMismatchException e){
                    System.out.println("Please insert a number");
                    typeError = true;
                    sc.nextLine(); //clean buffer
                }
        }while(typeError);
        //get the number of variables
        do{
            try{
                System.out.println("Please insert the number of variables (max 3)");
                numberVariables = sc.nextInt();
                typeError = false;
                }catch(InputMismatchException e){
                    System.out.println("Please insert a number");
                    typeError = true;
                    sc.nextLine(); //clean buffer
                }
        }while(typeError);
        
        sc.nextLine(); //clean buffer
        //Maximum of 3 expressions, minimum 1
       if((numberExpressions <= 0 || numberExpressions > 3) && ()){
           System.out.println("Min of 1  and max of 3");
       }else{
           A = new double[numberExpressions][3]; //always 3 because x,y and z
           b = new double[numberExpressions]; //each expression has one b
           for(int i = 0; i < numberExpressions; i++){
                System.out.println("Enter the equation (variables order: x, y and z)");
                expression  = sc.nextLine();//read the expression
                
               //store in the ArrayList
               expressions.add(expression);
               A[i] = eq.getA(expression);
               b[i] = eq.getb(expression);
           }
           
           //now we solve the system
           Solver solver = new Solver(A,b);
           solver.gaussElimination(solver.getIncompleteMatrix());
           double[] x = solver.solveSystem();
           //x stores the results
           for(int i = 0; i < x.length; i++){
               results.add(Double.toString(x[i])); //convert double to String
           }
       }
       //call the method to store expressions and results in the database
       
       saveSystems(expressions,results);
       sc.close();
    }

    @Override
    public void saveSystems(ArrayList<String> expressions, ArrayList<String> results) {
        for(String e: expressions){
            System.out.println(e);
        }
        for(String r: results){
            System.out.println(r);
        }
        
    }

  
}
