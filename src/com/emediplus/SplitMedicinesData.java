package com.emediplus;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.emediplus.dto.Medicines;
import com.emediplus.dto.Providers;
import com.emediplus.dto.ProvidersCat;
import com.emediplus.util.ConnectionOBJ;

public class SplitMedicinesData {

    private static Logger logger = Logger.getLogger(SplitMedicinesData.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection mycon = null;
		ConnectionOBJ obj = new ConnectionOBJ();
		
		try
		{
			PropertyConfigurator.configure("./config/log4j.properties");
//        	logger.info("****************************************************************************");
			
			mycon = obj.getConnection();
			mycon.setAutoCommit(false);
			
			int	medIndex = 0;
			
			new Medicines().fetchData(logger, mycon);
		}
		catch(Exception e)
		{
			System.out.println("exception:" + e);
		}
		finally
		{
			try
			{
				if(mycon != null)
					obj.closeConnection();
			}
			catch(Exception e1)
			{
				
			}
		}
	}
}
