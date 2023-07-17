package main;

import java.util.Scanner;

import Admin.AdminPanel;
import Customer.CustomerPanel;

public class Main {
   public void StartBankApp()
   {
	     System.out.println("choose one option below");
	     System.out.println("1. Admin Login");
	     System.out.println("2. Customer Login");
	     System.out.println("3. Exit");
	     
	     Scanner sc=new Scanner(System.in);
	    int i= sc.nextInt();
	     
	     if(i==1)
	     {
	    	 AdminPanel a1=new AdminPanel();
	    	 a1.AdminLogin();
	     }
	     
	     else if(i==2)
	     {
	    	 CustomerPanel cp=new CustomerPanel();
	    	 cp.customerLogin();
	     }
	     
	     else if(i==3)
	     {
	    	 System.out.println("Thank you please visit again");
	     }
	     
	     else
	     {
	    	 System.err.println("------------Invalid Input---------------");
	    	 StartBankApp();
	     }
   }
   public static void main(String[] args)
   {
	   System.out.println("-------Welcome To Mukul Bank Application---------");
	   new Main().StartBankApp();
    }
}

