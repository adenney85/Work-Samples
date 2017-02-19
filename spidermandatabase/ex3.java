//Alex Denney, CISS 241-300, SpiderMan Database
//The purpose of this program is to make two separate queries based on
//certain properties of the comics. The queries will print objects that
//match the queries conditions into a table, structured like the other
//programs but only showing the values selected by the query.

package spidermandatabase;

import java.sql.*;
import java.text.DecimalFormat;

public class ex3
{
	public static void main(String args[])
	{
		DecimalFormat money = new DecimalFormat("$0.00");
		//prints IssueValue to reflect currency
		String url = "jdbc:ucanaccess://C:/Users/The Cartographer/Documents/spiderman.accdb";
		Connection con;
		Statement stmt;
		try
		{
		   con = DriverManager.getConnection(url, "", "");
		   stmt = con.createStatement();
		   String query = 
		   "select ComicName, IssueDate, IssueNumber, IssueValue from spiderman where"
		   + " IssueValue < 5.00 order by IssueValue desc";
		   //query will display comics worth more than 5 dollars in descending order
		   ResultSet rs = stmt.executeQuery(query);
		    System.out.printf("%-15s", "ComicName");
		    System.out.printf("%-10s", "IssueDate" + " ");
			System.out.printf("%-12s", "IssueNumber");
			System.out.printf("%-11s", "IssueValue\n");
		   while (rs.next()) 
		   {
		    String ComicName = rs.getString("ComicName");
		    String IssueDate = rs.getString("IssueDate");
		    int IssueNumber = rs.getInt("IssueNumber");
		    double IssueValue = rs.getDouble("IssueValue");
		    System.out.printf("%-15s", ComicName);
		    System.out.printf("%-10s", IssueDate + " ");
			System.out.printf("%-12s", IssueNumber);
			System.out.print(money.format(IssueValue) + "\n");
		   }
		   //print methods will print results that match conditions
		   query = "select IssueName, ComicName, IssueNumber, IssueDate from spiderman where"
				   + " ComicName = 'Amazing SM' and IssueNumber < 200";
		   //query will display Amzazing SM comics earlier than issue 200
				   rs = stmt.executeQuery(query);
				    System.out.printf("%-42s", "IssueName");
				    System.out.printf("%-15s", "ComicName");
					System.out.printf("%-12s", "IssueNumber");
					System.out.printf("%-10s", "IssueDate\n");
				   while (rs.next()) 
				   {
				    String ComicName = rs.getString("ComicName");
				    String IssueDate = rs.getString("IssueDate");
				    int IssueNumber = rs.getInt("IssueNumber");
				    String IssueName = rs.getString("IssueName");
				    System.out.printf("%-42s", IssueName);
				    System.out.printf("%-15s", ComicName);
					System.out.printf("%-12s", IssueNumber);
					System.out.printf("%-10s", IssueDate);
					System.out.println("");
				   }
	       stmt.close();
		   con.close();
		   System.exit(0);
		   //closes and exits prevent resource leaks
		}
		catch(SQLException ex)//This catches SQLExceptions in case of error
		{
		   System.out.println("SQLException");
		   System.out.println(ex.getMessage());
		   ex.printStackTrace();
		}
	}
}