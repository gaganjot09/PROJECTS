package Admin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import main.Main;

public class AdminPanel {
	public void AdminLogin()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Admin Email Id:");
		String email1=sc.next();
		
	    System.out.println("Enter Admin password");
	    String pass1=sc.next();
	    
	    if(email1.equals("admin@gmail.com") && pass1.equals("admin@123"))
	    {
	    	System.out.println("Welcome Admin");
	    	WelcomeAdmin();
	    }
	    else
	    {
	    	System.err.println("Invalid Credentials");
	    	Main m1=new Main();
	    	m1.StartBankApp();
	    }
	}
	
	public void WelcomeAdmin()
	{
		System.out.println("Choose one Of the below");
		System.out.println("1. Open Account");
		System.out.println("2. Close Account");
		System.out.println("3. Deposit");
		System.out.println("4. Withdrawl");
		System.out.println("5. Customer Balance");
		System.out.println("6. Logout");
		
		Scanner sc=new Scanner(System.in);
		int i=sc.nextInt();
		
		switch(i)
		{
			case 1:
				OpenAccount();
				break;
			case 2:
				CloseAccount();
				break;
			case 3:
				DepositAmount();
				break;
			case 4:
				WithdrawlAmount();
				break;
			case 5:
				CustomerBalance();
				break;
			case 6:	
				Logout();
				break;
				default:
					WelcomeAdmin();
					break;	
		}
		
	}
	private void OpenAccount()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Customer Name");
		String cust_name=sc.next();
		cust_name=cust_name.substring(0,1).toUpperCase()+cust_name.substring(1).toLowerCase();
		
		System.out.println("Enter Customer Account No");
		String cust_AccNo=sc.next();
		
		boolean acc_no_status;
		do
		{
			acc_no_status=false;
			if(cust_AccNo.length() !=10)
			{
				System.out.println("Enter correct Account Number it should be greater than 10");
				System.out.println("Enter Customer Account No");
				cust_AccNo=sc.next();
				acc_no_status=true;
			}
			else if(!cust_AccNo.startsWith("100100"))
			{
				System.out.println("Enter correct Account Number it should be start with 100100");
				System.out.println("Enter Customer Account No");
				cust_AccNo=sc.next();
				acc_no_status=true;
			}
		}while(acc_no_status);
		
		System.out.println("Enter Phone No");
		String cust_phone=sc.next();
		
		System.out.println("Enter your Amount");
		int  cust_amt=sc.nextInt();
		
		while(cust_amt < 5000)
		{
			System.out.println("Amount should be greater than 5000");
			System.out.println("Enter your Amount");
			cust_amt=sc.nextInt();
		}
		
		String cust_pass=GeneratePassword(cust_name,cust_AccNo);
		
		boolean status=AdminDbOperation.openCustAcc(cust_name,cust_AccNo,cust_phone,cust_amt,cust_pass);
				if(status)
				{
					System.out.println("Account opened successfully");
					WelcomeAdmin();
				}
				else
				{
					System.err.println("error occured");
					WelcomeAdmin();
				}
				WelcomeAdmin();
	}
	private String GeneratePassword(String name,String AccountNo)
	{
		String sub_name=name.substring(0,3).toLowerCase();
		String sub_acc=AccountNo.substring(6,10);
		String cust_pass=sub_name+sub_acc;
		return cust_pass;
	}
	
	private void CloseAccount()
	{
		Scanner sc=new Scanner(System.in);
	    System.out.println("enter your account no:-");
		String accno=sc.next();
		
		boolean status =AdminDbOperation.closeAcc(accno);
		if(status)
		{
			System.out.println("Account closed Successfully");
		}
		else
		{
			System.err.println("error in accountr closing");
		}
		WelcomeAdmin();
	}
	
	private void Logout()
	{
		Main m1=new Main();
		m1.StartBankApp();
	}
	
	private void DepositAmount()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter customer account no");
		String acc_no=sc.next();
		
		System.out.println("enter the amount you want to deposit");
		String amount=sc.next();
		
		System.out.println("enter the comment");
		String comment=sc.next();
		
		LocalDate ld=LocalDate.now();
		String date1=ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		LocalTime lt=LocalTime.now();
		String time1=lt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		AdminDbOperation.DepositWithdrawl(acc_no, "deposit", amount, comment, date1, time1);
		WelcomeAdmin();
	}
	
	private void WithdrawlAmount()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter customer account no");
		String acc_no=sc.next();
		
		System.out.println("enter the amount you want to Withdraw");
		String amount=sc.next();
		
		System.out.println("enter the comment");
		String comment=sc.next();
		
		LocalDate ld=LocalDate.now();
		String date1=ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		LocalTime lt=LocalTime.now();
		String time1=lt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		AdminDbOperation.DepositWithdrawl(acc_no, "Withdraw", amount, comment, date1, time1);
		WelcomeAdmin();
	}
	
	private void CustomerBalance()
	{
		Scanner sc=new Scanner(System.in);
	    System.out.println("enter customer account number");
		String acc_no=sc.next();
		String balance=AdminDbOperation.getCustomerBalanace(acc_no);
		System.out.println("your balance is:-"+balance);
		WelcomeAdmin();
	}
	
}
