package Customer;

import java.sql.ResultSet;

import java.util.Scanner;

import dbcon.DbConnection;
import main.Main;

public class CustomerPanel 
{
	public void customerLogin()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("enter the account number");
		String acc_no=sc.next();
		
		System.out.println("enter the password");
		String pass=sc.next();
		
		ResultSet rs=CustomerDbOperation.custLogin(acc_no, pass);
		try
		{
			if(rs.next())
			{
				String name1=rs.getString("name");
				String phone1=rs.getString("phone");
				String amount1=rs.getString("amount");
				String active1=rs.getString("active");
				
				CustomerBean cb=new CustomerBean();
				cb.setName(name1);
				cb.setAcc_no(acc_no);
				cb.setPhone(phone1);
				cb.setAmount(amount1);
				cb.setActive(active1);
				
				System.out.println("----------welcome"+name1+"------------------");
				startCustomerPanel(cb);
			}
			else
			{
				System.err.println("invalid credentials");
				Main m1=new Main();
				m1.StartBankApp();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void startCustomerPanel(CustomerBean cb)
	{
		System.out.println("choose one option below");
		System.out.println("1.Transfer Amount ");
		System.out.println("2.Check Balance");
		System.out.println("3.Statement");
		System.out.println("4.Logout");
		
		Scanner sc=new Scanner(System.in);
	    int i=sc.nextInt();
		
		switch(i)
		{
		case 1:
			transferAmount(cb);
			break;
		case 2:
			checkBalance(cb);
			break;
		case 3:
			statement(cb);
			break;
		case 4:
			Main m=new Main();
			m.StartBankApp();
			break;
			default:
				System.out.println("invalid input");
		}
	}
	
	public void transferAmount(CustomerBean cb)
	{
		String from_accno=cb.getAcc_no();
		String balance=cb.getAmount();
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("enter the customer account number in whioch you wnat to transfer the amount");
		String to_accno=sc.next();
		
		System.out.println("enter the amount you want to transfer");
		String transfer_amount=sc.next();
		
		if(Integer.parseInt(balance)>=Integer.parseInt(transfer_amount))
		{
			boolean status=CustomerDbOperation.transferTheAmount(from_accno,to_accno,transfer_amount,cb);
		    if(status)
		    {
		    	System.out.println("Amount has been transferred successfully");
		    	int new_balance=Integer.parseInt(balance)-Integer.parseInt(transfer_amount);
		    	cb.setAmount(String.valueOf(new_balance));
		    }
		    else
		    {
		    	System.err.println("amount has not been transferred due to some error");
		    }
		}
		else
		{
			System.out.println("you have insufficient balance");
			System.out.println("your balance is:-"+balance+"rs");
		}
		startCustomerPanel(cb);
	}
	
	public void statement(CustomerBean cb)
	{
        ResultSet rs=CustomerDbOperation.getMiniStatement(cb.getAcc_no());
		
		try
		{
			System.out.println("Account No.\tDeposit | Withdrawl\tAmount\tDetails \t\t\tDate \t\tTime");
			while(rs.next())
			{
				String accno=rs.getString("account_no");
				String dept_with=rs.getString("dept_width");
				String amount=rs.getString("amount");
				String comment=rs.getString("comment");
				String date1=rs.getString("date1");
				String time1=rs.getString("time1");
				
				System.out.println(accno+" \t"+dept_with+" \t\t"+amount+" \t"+comment+" \t\t\t\t"+date1+" \t"+time1);
			}
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		startCustomerPanel(cb);
	}
	
	public void checkBalance(CustomerBean cb)
	{
		System.out.println("Your current balance is : "+cb.getAmount()+"rs");
		
		startCustomerPanel(cb);
	}
	
}
