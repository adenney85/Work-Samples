//Alex Denney, CISS 241-300, SpiderMan Database
//The purpose of this program is to document a collection of spiderman comics
//which have properties such as changing values, different series, issues and
//conditions. This program creates a database table in access and records all 
//this information. The table is also printed in the console by the program.

package spidermandatabase;

import java.sql.*;
import java.text.DecimalFormat;

public class ex1 
{
	public static void main(String args[])
	{
		DecimalFormat money = new DecimalFormat("$0.00");
		String url = "jdbc:ucanaccess://C:/Users/The Cartographer/Documents/spiderman.accdb";
		Connection con;
		String createstring;
		  createstring = "create table spiderman " +
		       "(ComicName varchar(60), IssueNumber int, " +
		       "IssueDate varchar(30), IssueName varchar(60), IssueValue double, " +
		       "Mint varchar(10))"; //createstring structures how the table will be setup
		Statement stmt;
		String query = "select * from spiderman";
		try
		{
		   con = DriverManager.getConnection(url, "", "");
		   stmt = con.createStatement();
		   stmt.executeUpdate(createstring);//This executes createstring to make the table
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Amazing SM', 89, '10/1/70', 'Doc Ock Lives', 6.50, 'No')");
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Spectacular SM', 92, '7/1/84', 'What Is The Answer', 4.50, 'No')");
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Web Of SM', 35, '2/1/88', 'You Can Go Home Again', 4.00, 'No')");
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Amazing SM', 382, '10/1/93', 'Emerald Rage', 4.00, 'Yes')");
		   //The executeUpdates add the 4 comics to the table with their properties
		   ResultSet rs = stmt.executeQuery(query);
		   System.out.printf("%-15s", "ComicName");
		   System.out.printf("%-12s", "IssueNumber"); 
		   System.out.printf("%-10s", "IssueDate" + " ");
		   System.out.printf("%-25s", "IssueName");
		   System.out.printf("%-11s", "IssueValue");
		   System.out.printf("%5s", "Mint\n");
		   while (rs.next()) 
			   {
			   //This loop will print the table and all the comics in it in the console
			    String ComicName = rs.getString("ComicName");
			    int IssueNumber = rs.getInt("IssueNumber");
			    String IssueDate = rs.getString("IssueDate");
			    String IssueName = rs.getString("IssueName");
			    Double IssueValue = rs.getDouble("IssueValue");
			    String Mint = rs.getString("Mint");
			    System.out.printf("%-15s", ComicName);
			    System.out.printf("%11d", IssueNumber); 
			    System.out.printf("%9s", IssueDate + " ");
			    System.out.printf("  " + "%-25s", IssueName);
			    System.out.print(money.format(IssueValue));
			    System.out.printf("%11s", Mint + "\n");
			   }
	       stmt.close();
		   con.close();
		   System.exit(0);
		}
		catch(SQLException ex)//This catches SQLExceptions in case of error
		{
		   System.out.println("SQLException");
		   System.out.println(ex.getMessage());
		   ex.printStackTrace();
		}
	}
}