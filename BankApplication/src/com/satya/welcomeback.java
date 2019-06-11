package com.satya;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class welcomeback
 */
@WebServlet("/welcomeback")
public class welcomeback extends HttpServlet {
	Connection con;
	PreparedStatement stmt;
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			stmt=con.prepareStatement("insert into customers(accno,name,password,balance)values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		}
		catch(Exception e) {}
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		String name=req.getParameter("name");
		String pass=req.getParameter("pass");
		long cid=Long.parseLong(req.getParameter("cid"));
		long bal=Long.parseLong(req.getParameter("bal"));
		try {
			
			stmt.setLong(1,cid);
			stmt.setString(2,name);
			stmt.setString(3,pass);
			stmt.setLong(4,bal);
			stmt.executeUpdate();
			ResultSet rs=stmt.getGeneratedKeys();
			PrintWriter out=res.getWriter();
			if(rs.next())
			{ 
				RequestDispatcher rd=req.getRequestDispatcher("signin.html");
				res.getWriter().println("<center><h1><font color=\"black\">Account has been created</font></h1></center>");
				rd.include(req, res);
				/*out.println("<html><body bgcolor=pink><br><br><br><br><center>");
				out.println("Welcome to <u>ANDHRA BANK</u>   <b><i> "+name);
				out.println("</i></b><br><br><br>Your Cusomer id is :<u>"+cid);
				out.println("<br><br><a href=index.html><b>LOGIN<b></a>");
				out.println("</center></body><html>");
				out.close();*/
			}
			else {
				RequestDispatcher rd=req.getRequestDispatcher("sigin.html");
				res.getWriter().println("OOPS try again!!");
				rd.include(req,res);		
			}
			
		}
		catch(Exception e) {}
	}
		public void destroy() {
			try {
			con.close();
			}
			catch(Exception e) {}
			// TODO Auto-generated method stub
			
	}

}
