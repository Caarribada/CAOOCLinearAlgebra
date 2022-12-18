package classes;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    String username,surname, pwd, type;
    Scanner sc;
    
    //db variables
    private Connection con = null;
    Statement stmt;
    PreparedStatement ps;
    ResultSet rs;
    String query; 
    dbConnector db;
    

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getSurname() {
        return surname;
    }

    public void setSurrname(String surname) {
        this.surname = surname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public User(String username, String surname, String pwd, String type) {
        this.username = username;
        this.surname = surname;
        this.pwd = pwd;
        this.type = type;
        
        //db variables
        this.con = null;
        stmt = null;
        ps = null;
        rs = null;
        query = null;
        db = new dbConnector();
    }

    public void updateProfile(){
        int option = 0; //variable to be used on menu
        boolean inputError = false; //prevent error on input mismatch
        sc =  new Scanner(System.in); //create scanner
        query = "select * from user where name like '" + this.username + "%';";
        //run the query
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            //result set get the number of entries in the database
            while(rs.next()){
                username = rs.getString("Name");
                surname = rs.getString("Surname");
                pwd = rs.getString("Password");
                type = rs.getString("Type");
            }
 
        } catch (SQLException ex) {
        //catch possible errors
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        //data retrieved from database
        System.out.println("Check the user info:");
        System.out.println("User name " + username);
        System.out.println("Surname " + surname);
        System.out.println("User type: "+ type);
        do{
            System.out.println("Choose the information you'd like to update");
            System.out.println("1 - Username");
            System.out.println("2 - Surname");
            System.out.println("3 - Password");

            try{
            option = sc.nextInt();
            inputError = false; //in first place, we assume the user typed the right value
            }catch(InputMismatchException e){
               //catch if the user input a character instead of a number 
               System.out.println("Please insert a number beetween 1 to 3");
               sc.nextLine(); //clean the buffer
               inputError = true;
            }
            
        }while(inputError);
        //treat the value inputed
        sc.nextLine(); //clean buffer
        switch(option){
            case 1:
                //update the user name
                System.out.println("Please type the new username");
                username = sc.nextLine();
                query = "update user set Name = '" + username + 
                        "' where name like '" + username + "%';"; 
            
                try {
                    stmt = con.createStatement();
                    ps = con.prepareStatement(query);
                    ps.execute();
                    System.out.println("Your name has been changed");
                } catch (SQLException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                break;

            case 2:
                //update the surname
                System.out.println("Please type the new surname");
                surname = sc.nextLine();
                query = "update user set Surname = '" + surname + 
                        "' where name like '" + username + "%';"; 
         
                try {
                    stmt = con.createStatement();
                    ps = con.prepareStatement(query);
                    ps.execute();
                    System.out.println("Your surname has been changed");
                } catch (SQLException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                 //update the password
                Console console = System.console();
                char[] pwdtemp; //create temporary char array
                System.out.println("Please type the new password");
                pwdtemp = console.readPassword();
                pwd = String.valueOf(pwdtemp); //convert to String
                query = "update user set Password = '" + pwd + 
                        "' where name like '" + username + "%';"; 
         
                try {
                    stmt = con.createStatement();
                    ps = con.prepareStatement(query);
                    ps.execute();
                    System.out.println("Your password has been changed");
                } catch (SQLException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            
            default:
                System.out.println("Invalid option!");
        }
        sc.close(); //close the scanner
        //close the connections
        try {
            ps.close();
            rs.close();
            con.close();
            db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
