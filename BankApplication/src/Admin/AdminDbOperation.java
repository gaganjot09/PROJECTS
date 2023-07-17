package Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.xdevapi.InsertStatement;
import com.mysql.cj.xdevapi.Result;

import dbcon.DbConnection;

public class AdminDbOperation {
static boolean openCustAcc(String name,String accNo,String phoneNo,int amount,String pass)
 {
	  boolean status=false;
	 try
	 (
		 Connection con=DbConnection.getConnection();
		 PreparedStatement ps=con.prepareStatement("insert into customer values(?,?,?,?,?,?)");
	 )
				 {
			          ps.setString(1, name);
			          ps.setString(2, accNo);
			          ps.setString(3, phoneNo);
			          ps.setInt(4, amount);	
			          ps.setString(5, pass); 
			          ps.setString(6, "true");
			          
			          int i=ps.executeUpdate();
			          if(i>0)
			          {
			        	  status=true;
			          }
			          else
			          {
			        	  status=false;
			          }
				 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 return status;
 }
public static boolean closeAcc(String accNo)
{
	boolean status=false;
	try
	{
		Connection con=DbConnection.getConnection();
		PreparedStatement pst=con.prepareStatement("update customer set active='false' where account_no=?");
		pst.setString(1, accNo);
		
		int i=pst.executeUpdate();
		if(i>0)
		{
			status=true;
		}
		else
		{
			status=false;
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return status ;
}

public static void DepositWithdrawl(String acc_no,String dept_with,String amount,String comment,String date1,String time1)
{
	if(dept_with.equals("deposit"))
	{
		boolean active=checkAccountIsActive(acc_no);
		if(active)
		{
			updateDepositedAmount(acc_no, amount);
		    insertstatement(acc_no,dept_with,amount,comment,date1,time1);
		}
		else
		{
			System.err.println("the account is not active");
		}
	}
	else if(dept_with.equals("Withdraw"))
	{
		boolean active=checkAccountIsActive(acc_no);
		if(active)
		{
			if(canWithdrawlAmt(acc_no, amount))
			{
				updateWithdrawAmt(acc_no,amount);
				insertstatement(acc_no, dept_with, amount, comment, date1, time1);
			}
			else
			{
				System.err.println("insufficient balance");
			}
		}
		else
		{
			System.err.println("the account is not active");
		}
	}
}

public static  void insertstatement(String acc_no,String dept_with,String amount,String comment,String date1,String time1)
{
	try {
	Connection con=DbConnection.getConnection();
	PreparedStatement pst=con.prepareStatement("insert into statement values(?,?,?,?,?,?)");
	pst.setString(1, acc_no);
	pst.setString(2, dept_with);
	pst.setString(3, amount);
	pst.setString(4, comment);
	pst.setString(5, date1);
	pst.setString(6, time1);
	
	int i=pst.executeUpdate();
	if(i>0)
	{
		System.out.println("customer statement updated successfully");
	}
	else
	{
		System.err.println("customer statement not updated successfully due to some error");
	}
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
}
public static boolean checkAccountIsActive(String acc_no)
{
	boolean status=false;
	try
	{
		Connection con=DbConnection.getConnection();
		PreparedStatement ps=con.prepareStatement("select active from customer where account_no=?");
		ps.setString(1, acc_no);
		
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			String active=rs.getString("active");
			if(active.equals("true"))
			{
				status=true;
			}
			else if(active.equals("false"))
			{
				status=false;
			}
		}
		else
		{
			status=false;
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return status;
}


public static void updateDepositedAmount(String acc_no,String amount)
{
	String old_balance=getCustomerBalanace(acc_no);
	int new_amt=Integer.parseInt(old_balance)+Integer.parseInt(amount);
	
	try
	{
		Connection con=DbConnection.getConnection();
		PreparedStatement ps=con.prepareStatement("update customer set amount=? where account_no=?");
		ps.setString(1, String.valueOf(new_amt));
		ps.setString(2, acc_no);
		
		int i=ps.executeUpdate();
		if(i>0)
		{
			
			System.out.println("customer amount updated successfully");
		}
		else
		{
			System.err.println("customer amount not updated successfully due to some error");
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
}

public static String getCustomerBalanace(String acc_no)
{
	
	String amount="0";
	try
	{
		Connection con=DbConnection.getConnection();
		PreparedStatement ps=con.prepareStatement("select amount from customer where account_no=?");
		ps.setString(1, acc_no);
		
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			amount=rs.getString("amount");
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return amount;
}
public static boolean canWithdrawlAmt(String acc_no,String with_amt)
{
	boolean status=false;
	String amount="0";
	try
	{
		Connection con=DbConnection.getConnection();
		PreparedStatement ps=con.prepareStatement("select amount from customer where account_no=?");
		ps.setString(1, acc_no);
		
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			amount=rs.getString("amount");
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	if(Integer.parseInt(amount)>=(Integer.parseInt(with_amt)))
			{
		         status=true;
			}
	else
	{
		status=false;
	}
	return status;
}

public static boolean updateWithdrawAmt(String acc_no,String with_amt)
{
	String old_balance=getCustomerBalanace(acc_no);
	int new_amt=Integer.parseInt(old_balance)-Integer.parseInt(with_amt);
	
	boolean status=false;
	try
	{
		Connection con=DbConnection.getConnection();
		PreparedStatement ps=con.prepareStatement("update customer set amount=? where account_no=?");
		ps.setString(1, String.valueOf(new_amt));
		ps.setString(2, acc_no);
		
		int i=ps.executeUpdate();
		if(i>0)
		{
			
			System.out.println("customer amount updated successfully");
		}
		else
		{
			System.err.println("customer amount not updated successfully due to some error");
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return status;
}
}

