package classes;
import interfaces.AdminInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin extends User implements AdminInterface {
    
    private Connection con = null;
    
    public Admin(String username, String surname, String pwd,String type) {
        super(username, surname, pwd,"Admin");
 
        con = db.getConnection();
    }
    

    @Override
    public void createUser() {
       //Ask the administrator to insert the user data 
       sc =  new Scanner(System.in); //create scanner
       char confirm,typeConfirm;
       do{
        System.out.println("Please, insert the user name");
        this.username = sc.nextLine();

        System.out.println("Please, insert the user's surname");
        this.surname = sc.nextLine();

        System.out.println("Please, create the user's password");
        this.pwd = sc.nextLine();
        //only allows yes or no in order to know about the user permissions
        do{
         System.out.println("Is the new user an admin? [Y/N]");
         typeConfirm = sc.nextLine().charAt(0);
         typeConfirm = Character.toUpperCase(typeConfirm); //force upper case

         if(typeConfirm != 'Y' && typeConfirm != 'N'){
            System.out.println("Please inform Y for Yes or N for No");
         }
        }while(typeConfirm != 'Y' && typeConfirm !='N');

        //confirm the user data informed
        System.out.println("Check the user info:");
        System.out.println("User name " + username);
        System.out.println("Surname " + surname);
        System.out.println("Password "+ pwd);
       
        if(typeConfirm == 'Y'){
            type = "Admin";
        }else{
            type = "Regular";
        }
         System.out.println("User type: " + type);
        //only allow yes or no
        do{
        System.out.println("Create user? [Y/N]");     
        confirm = sc.nextLine().charAt(0);
        confirm = Character.toUpperCase(confirm); //force upper case
        
        if(confirm != 'Y' && confirm != 'N'){
            System.out.println("Please inform only Y for Yes or N for No");
        }
        }while(confirm != 'Y' && confirm != 'N');
       }while(confirm != 'Y');
       //when the administrator confirms, the user is created on database
       sc.close(); //close scanner
       //insert the information into the database
       
       //the variable query stores the query string
       query = "INSERT into user (Name,Surname,Password,Type) values ('" + username
               + "','" + surname + "','" + pwd + "','" + type + "');";
       //try to execute the query on database
        try {
            stmt = con.createStatement();
            ps = con.prepareStatement(query);
            ps.execute();
            con.close();
            db.closeConnection();
        } catch (SQLException ex) {
        //catch possible errors
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    
    }

    @Override
    public void updateUser(String user) {
        // TODO Auto-generated method stub
        //Including the change of user type ("Reguler user, admin")
        //first, we retrieve the information stored in database
        int option = 0; //variable to be used on menu
        boolean inputError = false; //prevent error on input mismatch
        sc =  new Scanner(System.in); //create scanner
        query = "select * from user where name like '" + user + "%';";
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
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        //data retrieved from database
        System.out.println("Check the user info:");
        System.out.println("User name " + username);
        System.out.println("Surname " + surname);
        System.out.println("Password "+ pwd);
        System.out.println("User type: "+ type);
        do{
            System.out.println("Choose the information you'd like to update");
            System.out.println("1 - Username");
            System.out.println("2 - Surname");
            System.out.println("3 - Password");
            System.out.println("4 - Type");

            try{
            option = sc.nextInt();
            inputError = false; //in first place, we assume the user typed the right value
            }catch(InputMismatchException e){
               //catch if the user input a character instead of a number 
               System.out.println("Please insert a number beetween 1 to 4");
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
                        "' where name like '" + user + "%';"; 
            
                try {
                    stmt = con.createStatement();
                    ps = con.prepareStatement(query);
                    ps.execute();
                    System.out.println("User's name has been changed");
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                break;

            case 2:
                //update the surname
                System.out.println("Please type the new surname");
                surname = sc.nextLine();
                query = "update user set Surname = '" + surname + 
                        "' where name like '" + user + "%';"; 
         
                try {
                    stmt = con.createStatement();
                    ps = con.prepareStatement(query);
                    ps.execute();
                    System.out.println("User's surname has been changed");
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                 //update the password
                System.out.println("Please type the new password");
                pwd = sc.nextLine();
                query = "update user set Password = '" + pwd + 
                        "' where name like '" + user + "%';"; 
         
                try {
                    stmt = con.createStatement();
                    ps = con.prepareStatement(query);
                    ps.execute();
                    System.out.println("User's password has been changed");
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4: 
                //if the user is admin, it will be changed to regular and vice-versa
                if(type.equals("Admin")){
                    query = "update user set type = 'Regular' where name like '"
                            + user + "%';";
                }else{
                    query = "update user set type = 'Admin' where name like '"
                            + user + "%';";
                }
               
                try {
                    stmt = con.createStatement();
                    ps = con.prepareStatement(query);
                    ps.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("User's type has been changed.");
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
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void deleteUser(String user) {
        //remove user from the database
        query = "delete from user where Name like'" + user + "%';";
       
        try {
            stmt = con.createStatement();
            //perform the user deletion
            ps = con.prepareStatement(query);
            ps.execute(); 
            System.out.println("User " + user + " has been deleted!"); //message
            con.close();
            db.closeConnection();
        } catch (SQLException ex) {
        //catch possible errors
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
    }

    @Override
    public void checkUserHistory(String user) {
        //create ArrayList to store all the records from user
        ArrayList<String> records = new ArrayList<String>();

        int id = 0; //user id
        // first we need to get the user id
        query = "select ID from user where Name like '" + user + "%';";
        //run the query
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            //result set get the number of entries in the database
            while(rs.next()){
                id = rs.getInt("ID");
            }
 
        } catch (SQLException ex) {
        //catch possible errors
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        //then, we store in the ArrayList, all user records
        
        query = "select equation, result from operations,operations_equations, operations_results"
                + " where operations.UserID =" + id +" and operations.ResultID = operations_results.id"
                + " and operations.EquationID = operations_equations.id;";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            //result set get the number of entries in the database
            //only prints if there is an entry found
            if(rs.next()){
                while(rs.next()){
                records.add("Expressions: " + rs.getString("Equation") + " Results: " + rs.getString("Result"));
                }
                 //print every item in array
                for(var r: records ){
                    System.out.println(r);
                }
            }else{
                System.out.println("No entries found");
            }
           
        } catch (SQLException ex) {
        //catch possible errors
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
       
        
    }
    
}
