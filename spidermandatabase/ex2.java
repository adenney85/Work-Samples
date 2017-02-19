//Alex Denney, CISS 241-300, SpiderMan Database
//This program updates the previously created database table spiderman by adding
//new comics, updating values, changing names, and deleting sold comics. This
//requires the use of different sql statements and logic to ensure the proper
//files are updated. The table printed also has a wider IssueName field because
//some new comics have longer names.

package spidermandatabase;

import java.sql.*;
import java.text.DecimalFormat;

public class ex2
{
	public static void main(String args[])
	{
		DecimalFormat money = new DecimalFormat("$0.00");
		//prints IssueValue to reflect currency
		String url = "jdbc:ucanaccess://C:/Users/The Cartographer/Documents/spiderman.accdb";
		Connection con;
		Statement stmt;
		String query = "select * from spiderman";
		try
		{
		   con = DriverManager.getConnection(url, "", "");
		   stmt = con.createStatement();
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Amazing SM', 400, '4/1/95', 'A Death In The Family', 5.00, 'Yes')");
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Spectacular SM', 37, '12/1/79', 'Into The Hive', 6.00, 'No')");
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Amazing SM', 194, '7/1/79', "
				   + "'Never Let The Black Cat Cross Your Path', 10.00, 'No')");
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Web Of SM', 125, '6/1/95', 'Searching', 4.00, 'Yes')");
		   stmt.executeUpdate("update spiderman set IssueValue = 7.00 " + 
				   "where IssueNumber = 89 and ComicName = 'Amazing SM'");
		   stmt.executeUpdate("update spiderman set IssueValue = 5.00 " + 
				   "where IssueNumber = 92 and ComicName = 'Spectacular SM'");
		   stmt.executeUpdate("update spiderman set IssueName = 'What Is The Answer?' " + 
				   "where IssueNumber = 92 and ComicName = 'Spectacular SM'");
		   stmt.executeUpdate("delete from spiderman where ComicName = 'Amazing SM' " + 
				   "and IssueNumber = '382'");
		   //And statements check two parameters ensuring the right comic is modified
		   stmt.executeUpdate("insert into spiderman " +
				   "values('Amazing SM', 0, '4/11/15', 'Alex Denney', 5.00, 'Yes')");
		   ResultSet rs = stmt.executeQuery(query);
		   System.out.printf("%-15s", "ComicName");
		   System.out.printf("%-12s", "IssueNumber"); 
		   System.out.printf("%-10s", "IssueDate" + " ");
		   System.out.printf("%-40s", "IssueName");
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
			    System.out.printf("  " + "%-40s", IssueName);
			    //issue name has been expanded to fit larger comic names
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