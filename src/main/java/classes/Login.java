package classes;

import interfaces.LoginInterface;
import java.io.Console;
import java.util.Scanner;

public class Login implements LoginInterface{

    @Override
    public void signIn() {
        String login;
        char[] pwd;
        boolean logged = false;
        Console console = System.console();
        Scanner sc = new Scanner(System.in);

        do{
            System.out.println("Please insert your username");
            login = sc.nextLine();
            System.out.println("Please insert your password");
            pwd = console.readPassword();

            //search user and password in database
            //check if exists
            //check if the password matches

            if(login.equals("Claydson") && String.valueOf(pwd).equals("12345")){
                logged = true;
                System.out.println("User is logged in!");
            }else{
                System.out.println("User or password are incorrect. Please check your credentials and try again.");
            }
        }while(!logged);
        sc.close();
    }

    @Override
    public String getUserType(String username) {
        return null;
        
    }

    @Override
    public void signUp(){
        String login,pwd;
        char[] tempPwd;
        Console console = System.console();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Insert your username");
        login = sc.nextLine();

        //check if user already exists in db
        //if exists, ask for type again
        //if not, ask for password
        System.out.println("Type your password");
        tempPwd = console.readPassword();
        pwd = String.valueOf(tempPwd);

        //store new user in database
    }
    
}
