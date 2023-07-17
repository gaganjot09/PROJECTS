package Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.mysql.cj.protocol.a.LocalDateTimeValueEncoder;

import dbcon.DbConnection;

public class CustomerDbOperation
{
	public static ResultSet custLogin(String acc_no,String pass)
	{
		ResultSet rs=null;
		try
		{
			Connection con=DbConnection.getConnection();
			PreparedStatement pst=con.prepareStatement("select * from customer where account_no=? and password=? ");
			pst.setString(1, acc_no);
			pst.setString(2, pass);
			
			rs=pst.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public static boolean transferTheAmount(String from_accno,String to_accno,String transfer_amt,CustomerBean cb)
	{
		LocalDate ld=LocalDate.now();
	    String date1=ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    
	    LocalTime lt=LocalTime.now();
	    String time1=lt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//-----------------------------from account------------------------------------------
	    String comment1=transfer_amt+"rs transferred to "+to_accno;
	    int i1=0,i2=0;
		try
		{
			Connection con=DbConnection.getConnection();
			
			PreparedStatement pst=con.prepareStatement("insert into statement values(?,?,?,?,?,?)");
			pst.setString(1, from_accno);
			pst.setString(2, "withdraw");
			pst.setString(3, transfer_amt);
			pst.setString(4, comment1);
			pst.setString(5, date1);
			pst.setString(6, time1);	
			
			i1=pst.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		---------------------------to account----------------------------------
		 String comment2=transfer_amt+"rs deposited by "+cb.getName()+"from"+from_accno;
			try
			{
				Connection con=DbConnection.getConnection();
				
				PreparedStatement pst=con.prepareStatement("insert into statement values(?,?,?,?,?,?)");
				pst.setString(1, to_accno);
				pst.setString(2, "deposit");
				pst.setString(3, transfer_amt);
				pst.setString(4, comment2);
				pst.setString(5, date1);
				pst.setString(6, time1);
				
				i2=pst.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			boolean main_status=false;
			if(i1>0 && i2>0)
			{
				boolean status1=addAmount(to_accno, transfer_amt);
				boolean status2=subAmount(from_accno,transfer_amt,cb);
				
				if(status1 && status2)
				{
					main_status=true;
				}
				else
				{
					main_status=false;
				}
				
			}
			return main_status;
	}
	
	public static boolean addAmount(String acc_no,String amount)
	{
		String previous_balance=getCustomerBalance(acc_no);
		int new_balance=Integer.parseInt(previous_balance)+Integer.parseInt(amount);
		boolean status=false;
		try
		{
			Connection con=DbConnection.getConnection();
			PreparedStatement pst=con.prepareStatement("update customer set amount=? where account_no=?");
		    pst.setInt(1, new_balance);
		    pst.setString(2, acc_no);
		    
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
		return status;
	}
	public static boolean subAmount(String acc_no,String amount,CustomerBean cb)
	{
		String previous_balance=cb.getAmount();
		int new_balance=Integer.parseInt(previous_balance)-Integer.parseInt(amount);
		
		boolean status=false;
		try
		{
			Connection con=DbConnection.getConnection();
			PreparedStatement pst=con.prepareStatement("update customer set amount=? where account_no=?");
		    pst.setInt(1, new_balance);
		    pst.setString(2, acc_no);
		    
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
		return status;
	}
	
	public static String getCustomerBalance(String acc_no)
	{
		String balance="0";
		try
		{
			Connection con=DbConnection.getConnection();
			
			PreparedStatement pst=con.prepareStatement("select amount from customer where account_no=?");
			pst.setString(1, acc_no);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				balance=rs.getString("amount");
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return balance;
	}
	
	public static ResultSet getMiniStatement(String acc_no)
	{
		ResultSet rs=null;
		try
		{
			Connection con=DbConnection.getConnection();
			
			PreparedStatement pst=con.prepareStatement("select * from statement where account_no=?");
			pst.setString(1, acc_no);
			
			rs=pst.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
}
