package com.satya;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

@WebServlet("/Welcome")
public class Welcome extends HttpServlet {
	Connection con1,con2,con3,con4;
	PreparedStatement stmt;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			con3=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			con4=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			stmt=con2.prepareStatement("update customers set balance=? where accno=?");
		}
		
		catch(Exception e) {}
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		res.setContentType("text/html");
		int acno=Integer.parseInt(req.getParameter("acno"));
		String action=req.getParameter("action");
		res.setContentType("text/html");
		if(action.equalsIgnoreCase("depo"))
		{
			long amt=Long.parseLong(req.getParameter("amt"));
			try {
				int cur_bal=0,prev_bal=0;
				Statement st=con1.createStatement();
				ResultSet rs=st.executeQuery("select * from customers where accno= "+acno);
				while(rs.next()) {
					prev_bal=rs.getInt(5);

					cur_bal=(int) (rs.getInt(5)+amt);
				}
				stmt.setInt(1,cur_bal);
				stmt.setInt(2,acno);
				stmt.executeUpdate();
				RequestDispatcher rd=req.getRequestDispatcher("services.html");
				res.getWriter().println("<center><h1><font color=\\\"#9C9695\\\">Amount "+ amt+ " is deposited</font></h3></center>");
				rd.include(req, res);
				
				PrintWriter out=res.getWriter();
				/*out.println("<html><body bgcolor=#AEABAB><center><br><br><br><br><br><h2><em>Amount is Deposited !!!</h2");
				out.println("<br><h3>Previous Balance is :"+prev_bal);
				out.println("<br>Current Balance is :"+cur_bal+"<br><a href=action.html>GoBack</a></em><h3></center></body></html>");
				out.close();*/
			} catch (Exception e) {
				
			}
		}
		else if(action.equalsIgnoreCase("wdra"))
		{
			long amt=Long.parseLong(req.getParameter("amt"));
			try {
				int curr_bal,prev_bal;
				Statement st1=con3.createStatement();
				ResultSet rs1=st1.executeQuery("select * from customers where accno= "+acno);

			while(rs1.next())
			{
				prev_bal=rs1.getInt(5);
				curr_bal=(int)(rs1.getInt(5)-amt);	
			if(curr_bal>=0) {
				stmt.setInt(1,curr_bal);
				stmt.setInt(2, acno);
				stmt.executeUpdate();
				PrintWriter out=res.getWriter();
				RequestDispatcher rd=req.getRequestDispatcher("services.html");
				res.getWriter().println("<center><h1><font color=\\\"#9C9695\\\">Amount "+ amt+ " is Withdrawn</font></h3></center>");
				rd.include(req, res);
				/*out.println("<html><body bgcolor= #AEABAB><center><br><br><br><br><br><h2><em>Amount is WithDrawn !!!</h2");
				out.println("<br><h3>Previous Balance is :"+prev_bal);
				out.println("<br>Current Balance is :"+curr_bal+"<br><a href=action.html>GoBack</a></em><h3></center></body></html>");
				out.close();*/
				
			}
			else
			{	
				RequestDispatcher rd=req.getRequestDispatcher("services.html");
				res.getWriter().println("<center><h1><font color=\\\"#DB472E\\\">Transaction failed !!! No SUFFICENT FUNDS </font></h3></center>");
				rd.include(req, res);
				/*PrintWriter out=res.getWriter();
				out.println("<html><body><center><br><br><br><br><br><h1><em>Transaction failed !!!");
				out.println("<br>No SUFFICENT FUNDS<br><a href=action.html>GoBack</a></em><h1></center></body></html>");
				out.close();*/
			}
			}
			}
			catch(Exception e) {}
		}
		else if(action.equalsIgnoreCase("enquiry")) {
			try {
				int a=0;
				Statement st2=con4.createStatement();
				ResultSet rs=st2.executeQuery("select * from customers where accno="+acno);
				System.out.println("helo");
				while(rs.next()){
					a=rs.getInt(5);
					System.out.println("the value is :"+a);
				}
				PrintWriter out=res.getWriter();
				RequestDispatcher rd=req.getRequestDispatcher("services.html");
				res.getWriter().println("<center><h1><font color=\\\"#9C9695\\\">Current Balance is :"+a+"</font></h3></center>");
				rd.include(req, res);
				/*out.println("<html><body bgcolor=pink><center><br><br><br>");
				out.println("Current Balance is :"+a);
				out.println("<br><br><h3><a href=action.html>GoBack</a?</h3></center><body><html>");
				out.close();*/
				
			}catch(Exception e) {}
		}
	}

}
