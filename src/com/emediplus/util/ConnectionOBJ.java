package com.emediplus.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOBJ
{
	private Connection con;
	
	public ConnectionOBJ()
	{
	}		
	public Connection getConnection() throws Exception
	{
	  	try
		{
			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost:3306/emediplus_db";
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			con = DriverManager.getConnection (url, userName, password);
			System.out.println ("Database connection established");
		}
		catch (Exception e)
		{
		   	System.err.println ("Cannot connect to database server"+ e);
		}
		
		return con;		
	}
	
	protected void finalize() throws Exception
	{		
		if(con!= null) con.close();
			con = null;
	}
	
	public void closeConnection() throws Exception
	{
		if(con!= null) con.close();
			con = null;
	}
}
