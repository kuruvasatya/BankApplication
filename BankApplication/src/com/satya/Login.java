package com.satya;
import javax.servlet.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

@WebServlet("/Login")
public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uname=req.getParameter("usname");
		String pass=req.getParameter("pass");
		res.setContentType("text/html");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			PreparedStatement stmt=con.prepareStatement("select * from customers where name=? and password=?");
			stmt.setString(1,uname);
			stmt.setString(2,pass);
			ResultSet rs=stmt.executeQuery();
			if(rs.next())
			{
				RequestDispatcher rd=req.getRequestDispatcher("action.html");
				rd.forward(req, res);
			}
			else
			{
				RequestDispatcher rd=req.getRequestDispatcher("index.html");
				res.getWriter().println("<center><h3><font color=\"#ff9800\">Invalid UserName or Password</font></h3></center>");
				rd.include(req,res);
			}
		}
		catch(Exception e) {}
		//doGet(request, response);
	}

}
