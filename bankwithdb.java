package database_choicetable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;

public class  bankwithdb {
    String one, two,six;
    Scanner input = new Scanner(System.in);
    String username="", password="",table_name="";
    double banking_amt=0.0;
            //table_name;
    double c, bal = 20000;
    int i = 0,a;
  //  int j;
 //   double[] arr = new double[6];
    int b;
    String three,four;
    String trans_tbl,new_password,new_username;
public  void tableone() throws SQLException, IOException {
    System.out.println("enter the table name");
    table_name = input.next();

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
    DatabaseMetaData db1=conn.getMetaData();
    ResultSet tab =db1.getTables(null,null, table_name,null);
    if(tab.next()){
        check_username();
        conn.close();
        //a=input.nextInt();
    }
    else{
        System.out.println("table not found");
    }
}


    public void check_username() throws SQLException, IOException {
    
        System.out.println("enter the username");
        username = input.next();
        System.out.println("enter the pin");
        password = input.next();
        two = "select * from " + table_name + " where Username='" + username + "' and "+"Password='" + password + "';";
        Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
        Statement stmt1 = conn1.createStatement();
        ResultSet rs = stmt1.executeQuery(two);
        while(rs.next())
        {
        	 //System.out.println(rs.getString("Username"));
        	 //System.out.println(rs.getString("Password"));
        	 banking_amt = rs.getDouble("Amount");
        	 if(username.equals(rs.getString("Username")) && password.equals(rs.getString("Password")) && rs.getString("status").equals("1"))
        	 {
        		 check_pin();
        	 }
        	 else
        	 {
        		 System.out.println("Invalid User");
        	 }
        }
        conn1.close();  
   
    }
    public void check_pin() throws IOException, SQLException {
    	System.out.println("----------------------------------------------------------");
        System.out.println("Select your choice \n 1. Withdrawal \n 2. Balance \n 3. Deposit \n 4. Mini Statement \n 5. Change Password \n 6. Change username");//user select in any option 1-4int b = input.nextInt();
        b = input.nextInt();
        switch (b) {
            case 1:
                choice_withdraw();//calling method
                check_pin();
                break;

            case 2:
                choice_balance();
                check_pin();
                break;

            case 3:
                choice_deposit();
                check_pin();
                break;

            case 4:
                choice_ministatement();
                check_pin();
                break;
            case 5:
            	 change_username();
            	 check_pin();
            	 break;
            case 6:
            	 change_password();
            	 check_pin();
                 break;
            default:
                System.out.println("not found");
                System.out.println("Sorry! Invalid PIN Number, Try Again");                
        }
        System.out.println("----------------------------------------------------------");
    }
    public void choice_withdraw() throws SQLException {
     trans_tbl=table_name+"_trans ";
     String trans_type="1";

            System.out.println("Enter the amount");
            c = input.nextDouble();
            if (c<=banking_amt) 
            {
            	banking_amt = banking_amt - c;
              //  arr[i] = banking_amt;
                //    i++;
                System.out.println("Kindly Collect the Cash");
                three = "insert into " +trans_tbl+  "(Username,Trans_type,Amount,Balance) values ('"+username+"','"+trans_type+"',"+c+","+banking_amt+");";
                System.out.println("syntax is " + three);
                 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
                 Statement stmt = conn.createStatement();
                 stmt.executeUpdate(three);
                 conn.close();
                 three = "update " +table_name+  " set Amount = "+banking_amt+" where Username='"+username+"';";
                 System.out.println("syntax is " + three);
                  Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
                  Statement stmt1 = conn1.createStatement();
                  stmt1.executeUpdate(three);
                  conn1.close();
             }
            else {
            	System.out.println("Insufficient balance");
            }
            
    }
                

     public void choice_balance() throws SQLException {

            try {
                System.out.println("Your balance amount is: " + banking_amt);

                System.out.println("PLEASE TAKE YOUR SLIP");
            } catch (Exception ae) {
               //ae System.out.println(+ae);
                ae.printStackTrace();
            }
              }
            public void choice_deposit() throws SQLException {
            	   trans_tbl=table_name+"_trans ";
            	  String trans_type="2";
                System.out.println("Enter the amount");
                c = input.nextDouble();
                banking_amt = banking_amt + c;
                three = "insert into " +trans_tbl+  "(Username,Trans_type,Amount,Balance) values ('"+username+"','"+trans_type+"',"+c+","+banking_amt+");";
                System.out.println("syntax is " + three);
                 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
                 Statement stmt = conn.createStatement();
                 stmt.executeUpdate(three);
                 conn.close();
                 three = "update " +table_name+  " set Amount = "+banking_amt+" where Username='"+username+"';";
                 System.out.println("syntax is " + three);
                  Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
                  Statement stmt1 = conn1.createStatement();
                  stmt1.executeUpdate(three);
                  conn1.close();
               /*( System.out.println("Deposited Successfully! ");
                System.out.println("\nYour Balance is: " + bal);
                System.out.println("\n THANKYOU !!");
                three = "insert into " + "banking"+ "(" + "deposit" + ")" + "values" + "(" + c + ");";
                System.out.println("syntax is " + three);
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
                  Statement stmt = conn.createStatement();
                        stmt.executeUpdate(three);
                        conn.close();*/
                    }
                
    
       public void choice_ministatement() throws SQLException {
    	   System.out.println("ID \t Trans_type \t Trans_Amount \t Balance");
    	   two = "select*from "+table_name+"_trans where Username='"+username+"' order by id desc limit 10;";
           Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
           Statement stmt1 = conn1.createStatement();
           ResultSet rs = stmt1.executeQuery(two);
           String ds="";
           while(rs.next())
           {
        	   if(rs.getString("Trans_type").equals("1"))
        		   ds = "Withdraw";
        	   else
        		   ds = "Deposit";
           	 System.out.println(""+rs.getInt("id")+" \t "+ds+" \t  "+ rs.getDouble("Amount")+" \t "+rs.getDouble("Balance")+"");
           	 
           }
           conn1.close();  
    	   /*
    	   six="select*from" +"banking "+";";
    	   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
                   Statement stmt = conn.createStatement();
                         stmt.executeUpdate(six);
                         conn.close();
    	   */
    	   
                }
       public void change_username()throws SQLException, IOException {
    	   trans_tbl=table_name+"_trans ";
    	   System.out.println("enter the new username");
    	   new_username=input.next();
    	   three = "update " +table_name+  " set Username= '"+new_username+"' where status='"+'1'+"';";
           System.out.println("syntax is " + three);
            Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
            Statement stmt1 = conn1.createStatement();
            stmt1.executeUpdate(three);
            conn1.close();
            four = "update " +trans_tbl+  " set Username=' "+new_username+"' where id='"+"1"+"';";
            System.out.println("syntax is " + four);
             Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
             Statement stmt2 = conn2.createStatement();
             stmt2.executeUpdate(four);
             conn2.close();
          
       }
       public void change_password()throws SQLException, IOException {
    	   System.out.println("enter the new password");
    	   new_password=input.next();
    	   trans_tbl=table_name+"_trans ";
    	   three = "update " +table_name+  " set Password= '"+new_password+"' where status ='"+'1'+"';";
           System.out.println("syntax is " + three);
            Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost/vaishu", "root", "root");
            Statement stmt1 = conn1.createStatement();
            stmt1.executeUpdate(three);
            conn1.close();
         
          
       }

            public static void main(String[] args) throws SQLException, IOException {
                //  public static void main (String args[]) throws SQLException {
               // Scanner in = new Scanner(System.in);
                bankwithdb db = new bankwithdb();
                db.tableone();
             //   db.check_username();
               // db.check_pin();

            }


}

































































