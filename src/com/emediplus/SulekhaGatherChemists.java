package com.emediplus;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.emediplus.dto.Providers;
import com.emediplus.dto.ProvidersCat;
import com.emediplus.util.ConnectionOBJ;


public class SulekhaGatherChemists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection mycon = null;
		
		try
		{
			Providers providers = null;
			ConnectionOBJ obj = new ConnectionOBJ();
			mycon = obj.getConnection();
			mycon.setAutoCommit(false);
			
			
			
			
			int	rowctr = 1;
			
//			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\pharmacies_pune.html");
//			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			
			for(int pageno=1; pageno<=50; pageno++)
			{
				String websiteurl = "http://yellowpages.sulekha.com/orthopaedics-hospitals_pune_" + pageno;
				Document doc = Jsoup.connect(websiteurl)
						  .data("query", "Java")
						  .userAgent("Mozilla")
						  .cookie("auth", "token")
						  .timeout(3000)
						  .post();
	
				Elements lists = doc.select("div#divcontent div#listingtabcontent div.list.flt.ov div.clr.mado h3 a");
		        print("\nlists: (%d)", lists.size());
	           	for (Element list : lists) {
	           		providers = null;
	           		System.out.println("\nrowno:" + rowctr);
	        		print(" * a: <%s>  (%s)", list.attr("href"), list.text());
	        		String url = list.attr("href");
	        		String listname = list.text();
	        		// fetch Detail Info
	       			providers = fetchDetailInfo(list, url, listname);
	       			
	       			updateDatabase(mycon, providers);
	        		
	        		rowctr++;
		        }
			}

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
					mycon.close();
			}
			catch(Exception e1)
			{
				
			}
		}
	}
	   private static void print(String msg, Object... args) {
	        System.out.println(String.format(msg, args));
	    }

	    private static String trim(String s, int width) {
	        if (s.length() > width)
	            return s.substring(0, width-1) + ".";
	        else
	            return s;
	    }
	
	private static Providers fetchDetailInfo(Element list, String url, String name)
	{
		Providers providers = new Providers();
		System.out.println("Inside fetchDetailsInfo");
	
		try
		{
			
//			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\sulekha_detail.html");
//			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

			url = "http://yellowpages.sulekha.com" + url;
			Document doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
           	Elements listnames = doc.select("ul.adre.plist.tp15.flt li");
           	print("\nlistnames: (%d)", listnames.size());
           	for (Element listname : listnames) {
        		providers = splitValuePair(listname.text(), providers);
	        }
           	
           	providers.setUrl(url);
           	providers.setProvider_name(name);
           	
           	Pattern p = Pattern.compile(".*(\\d{6})");
   			Matcher m = p.matcher(providers.getAddress());
   			m.matches();
   			
   			providers.setCity("Pune");
   		    providers.setPin_code(m.group(1));
		}
		catch(Exception e)
		{
			System.out.print("fetchDetailInfo:" + e);
		}
		
		return providers;
	}

	private static Providers splitValuePair(String str, Providers providers)
	{
		int colonPos = str.indexOf(':');
		String 	key  = str.substring(0, colonPos);
		String  value= str.substring(colonPos+1, str.length());
		//System.out.println("key:" + key + " value:" + value);
		
		if(key.equals("Mobile"))
			providers.setMobile(value);
		else if (key.equals("Contact Person"))
			providers.setContact_person(value);
		else if (key.equals("Area"))
			providers.setArea(value);
		else if (key.equals("Address"))
			providers.setAddress(value);
		else if (key.equals("Landmark"))
			providers.setLandmark(value);
		else if (key.equals("Working Hours"))
			providers.setWorking_hours(value);
		else if (key.equals("Phone"))
			providers.setPhone(value);
		else if (key.equals("Categories"))
			providers.setListed_categories(value);

		return providers;
	}

	
	private static void splitDetails(String str)
	{
		Pattern p = Pattern.compile("Mobile:(.*)Contact Person:(.*)Area:(.*)Address:(.*)Landmark:(.*)Working Hours:(.*)Categories:(.*)");
		Matcher m = p.matcher(str);
		System.out.println("does it match:" + m.matches());
		
	    System.out.println("Mobile:" + m.group(1));
	    System.out.println("Contact Person:" + m.group(2));
	    System.out.println("Area:" + m.group(3));
	    System.out.println("Address:" + m.group(4));
	    System.out.println("Landmark:" + m.group(5));
	    System.out.println("Working Hours:" + m.group(6));
	    System.out.println("Categories:" + m.group(7));
	    
	    String[] cateogries = m.group(7).split(",");
		for(int i=0; i<cateogries.length; i++)
			System.out.println("cateogries[" + i + "]:" + cateogries[i]);
	}
	 
	
	private static void getDetailInfo(String str, String detailType)
	{
		Pattern p = Pattern.compile(detailType + ":(.*)");
		Matcher m = p.matcher(str);
		System.out.println("does it match:" + m.matches());
		
	    System.out.println("Mobile:" + m.group(1));
	    System.out.println("Contact Person:" + m.group(2));
	    System.out.println("Area:" + m.group(3));
	    System.out.println("Address:" + m.group(4));
	    System.out.println("Landmark:" + m.group(5));
	    System.out.println("Working Hours:" + m.group(6));
	    System.out.println("Categories:" + m.group(7));
	    
	    String[] cateogries = m.group(7).split(",");
		for(int i=0; i<cateogries.length; i++)
			System.out.println("cateogries[" + i + "]:" + cateogries[i]);
	}
	
	
	private static void updateDatabase(Connection mycon, Providers providers)
	{
       	System.out.println(providers.toString());

       	PreparedStatement myst = null;
       	PreparedStatement myst_insert = null;
       	ResultSet myrs = null;
       	boolean rowfound = false;
       	int insResult = 0;
       	try
       	{
       		String mysql = "select count(*) from providers where lower(trim(url)) like '%" + providers.getUrl().trim().toLowerCase() + "%'";
       		System.out.println("SQL:" + mysql);
       		myst = mycon.prepareStatement(mysql);	
       		myrs = myst.executeQuery();
			while(myrs.next())
			{
				if(myrs.getInt(1) > 0)
					rowfound = true;
			}
			
			System.out.println(providers.getProvider_name() + " : found : " + rowfound);
			
			if(!rowfound)
			{
				mysql = "insert into providers(url, provider_type, provider_name, website, mobile, phone, contact_person, area, address, city, pin_code, landmark, working_hours, listed_categories) " +
						" values(?, 'H', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				myst_insert = mycon.prepareStatement(mysql);	
				myst_insert.setString(1,  providers.getUrl());
				myst_insert.setString(2, providers.getProvider_name());
				myst_insert.setString(3, providers.getWebsite());
				myst_insert.setString(4, providers.getMobile());
				myst_insert.setString(5, providers.getPhone());
				myst_insert.setString(6, providers.getContact_person());
				myst_insert.setString(7, providers.getArea());
	       		myst_insert.setString(8, providers.getAddress());
	       		myst_insert.setString(9, providers.getCity());
	       		myst_insert.setString(10, providers.getPin_code());
	       		myst_insert.setString(11, providers.getLandmark());
	       		myst_insert.setString(12, providers.getWorking_hours());
	       		myst_insert.setString(13, providers.getListed_categories());
	       		
	       		insResult = myst_insert.executeUpdate();
	       		
	       		System.out.println("insert Result:" + insResult);
	       		
	       		mycon.commit();
			}
			else
			{
				System.out.println("...................................................row already found.................");
			}
       	}
       	catch (Exception e)
       	{
       		System.out.println("updateDatabase Exception:" + e);
       	}
       	finally
       	{
       		try
       		{
       			if(myst != null) myst.close();
       			if(myst_insert != null) myst_insert.close();
       		}
       		catch(Exception ef)
       		{
       			
       		}
       	}
	}

}
