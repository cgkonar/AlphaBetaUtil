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

import com.emediplus.dto.NonMedicines;
import com.emediplus.dto.Providers;
import com.emediplus.dto.ProvidersCat;
import com.emediplus.util.ConnectionOBJ;

public class JustDialGatherChemists {

    private static Logger logger = Logger.getLogger(JustDialGatherChemists.class);

    // Pune areas....
    private static String city = "Pune";
    private static String[] areas = {"Pimpri","Chinchwad","Kothrud","Aundh","Pashan","Baner","Koregaon Park","Shivaji Nagar","Pune Railway Station",
    								"Swargate","Boat club road","Magarpatta","Daund","Chikhli","Kalewadi","Kasarwadi","Phugewadi","Pimple Saudagar",
    								"Narayan peth","Talegaon","Kasba peth","Shirur","Bhor","Mulshi","Wadgaon","Welhe","Ambegaon","Junnar","Rajgurunagar",
    								"Baramati","Indapur","Purandhar","Bhawani Peth","Erandwana","Ghorpuri Lines","Kalyani Nagar","Kondhwa","Narayan Peth",
    								"Hadapsar","Akurdi"};
    
    // Mumbai areas....
    /* http://www.propertykhazana.com/localities/mumbai
    private static String city = "Mumbai";
    private static String[] areas = {"Mira Road", "Chembur", "Andheri", "Kharghar", "Malad", "Bandra", "Goregoan", "Kandivali", "Ghodbunder Road", 
    				"Navi Mumbai", "Bhandup", "BOrivali", "Panvel", "Khar", "Agripada", "Airoli", "Altamount Road", "Ambarnath", "Amboli", "Antop Hill", 
    				"Badlapur", "Belapur", "Bhayandar", "Bhindi Bazaar", "Bhiwandi", "Bhoiwada", "Bhuleshwar", "Boisar", "Breach Candy", "Byculla", "CBD Belapur", 
    				"Chakala", "Chandivali", "Charkop", "Charni Road", "Chinch Bandar", "Chinchpokli", "Chowpatti", "Chuna Bhatti", "Churchgate", "Colaba", 
    				"Crawford Market", "Cuffe Parade", "Currey Road", "Dadar", "Dahisar", "Deonar", "Dharavi", "Dhobhi Talao", "Dockyard", "Dombivali", 
    				"Dongri", "Elphinstone Road", "Flora Fountain", "Fort", "Gamdevi", "Ghansoli", "Ghatkopar", "Girgaon", "Govandi", "Grant Road", 
    				"Haji Ali", "Jogeshwari", "Juhu", "Kalamboli", "Kalbadevi", "Kalina", "Kalwa", "Kalyan", "Kamothe", "Kanjur Marg", "Kemps Corner", 
    				"Khar", "Khopoli", "Kurla", "LBS Marg", "Lalbaug", "Parel", "Lokhandwala", "Mahalaxmi", "Mahape", "Mahim", "Malabar Hill", 
    				"Aarey Milk Colony", "Oshiwara", "Mazagon Dock", "Mumbai Central", "Prabhadevi", "Ramwadi", "Seepz", "Santacruz", "Sewri", "Versova", 
    				"Wadala", "Woril", "Vasai", "Titawala"};
    */
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection mycon = null;
		ConnectionOBJ obj = new ConnectionOBJ();
		
		String websiteurl = "";
		
		try
		{
			PropertyConfigurator.configure("./config/log4j.properties");
        	logger.info("****************************************************************************");
			
			Providers providers = null;
			String 	  url = "";
			String   name = "";
			String   area = "";
			
			mycon = obj.getConnection();
			mycon.setAutoCommit(false);
			
			int	rowctr = 1;
			
//			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\justdial_page-1.html");
//			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			
			for(int i=0; i<areas.length; i++)
			{
				for(int pageno=1; pageno<=20; pageno++)
				{
					websiteurl = "http://www.justdial.com/" + city + "/Chemists-<near>-" + areas[i] + "/ct-3722/page-"+pageno;
					System.out.println(websiteurl);
					
					try
					{
						logger.info(websiteurl);
						
						Document doc = Jsoup.connect(websiteurl)
								  .data("query", "Java")
								  .userAgent("Mozilla")
								  .cookie("auth", "token")
								  .timeout(3000)
								  .post();
		
						//logger.info(doc);
						
						Elements lists = doc.select("section.jgbg");
				        System.out.println("\nlists:" + lists.size());
			           	for (Element list : lists) 
			           	{
			      //     		if(rowctr ==9)
			       //    		{
			           			System.out.println("\nrowno:" + rowctr);
			           			
			           			providers = null;
			           			
			           			Elements links = list.select("span.jcn a");
			           			for(Element link : links)
			           			{
			           				System.out.println("url: " + link.attr("href"));
			           				url = link.attr("href");
			           				name = link.attr("title");
			           			}
		
			           			/*
			           			Elements locs = list.select("p.jaid");
			           			for(Element loc : locs)
			           			{
			           				System.out.println("loc.text():" + loc.text() + "]]]]]]]");
			           				System.out.println("area: " + loc.text().substring(0, loc.text().indexOf('|')) + "].........");
			           				area = loc.text().substring(0, loc.text().indexOf('|'));
			           			}
								*/
	           		
			           			logger.info(url);
			           			
			           			String providername = name.substring(0, name.indexOf(" in "));
			           			String providerarea = name.substring(name.indexOf(" in ")+4, name.indexOf(", " + city));
			           			
			           			System.out.println(">>>>>>>>>area:" + providerarea + "]");
			           			
			           			if(!Providers.checkProviderExist(logger, mycon, city, providername, providerarea))
			           			{
			           				// fetch Detail Info
			           				providers = fetchDetailInfo(mycon, url, providername, providerarea);
			           				Providers.updateDatabase(logger, mycon, providers);
			           			}
			           			else {
			           				System.out.println("provider already existing in database");
			           			}
			         //  		}
			           	
			        		rowctr++;
				        }
					}
					catch(Exception kk)
					{
						System.out.println("Exception:" +kk);
						logger.info(kk);
						Providers.updateDatabaseForErrors(logger, mycon, websiteurl, kk.toString());
					}
				}
			}
/*				
			Below script works.. when http://www.justdial.com/Pune/Chemists/ct-3722  (without <near> after Chemists word)
			for(int pageno=1; pageno<=1; pageno++)
			{
				String websiteurl = "http://www.justdial.com/Pune/Chemists/ct-3722";
				System.out.println(websiteurl);
				
				Document doc = Jsoup.connect(websiteurl)
						  .data("query", "Java")
						  .userAgent("Mozilla")
						  .cookie("auth", "token")
						  .timeout(3000)
						  .post();

				logger.info(doc);
				
				Elements lists = doc.select("section.jbbg");
		        System.out.println("\nlists:" + lists.size());
	           	for (Element list : lists) 
	           	{
//	           		if(rowctr ==1)
//	           		{
	           			System.out.println("\nrowno:" + rowctr);
	           			
	           			providers = null;
	           			
	           			Elements links = list.select("span.jcn a");
	           			for(Element link : links)
	           			{
	           				System.out.println("url: " + link.attr("href"));
	           				url = link.attr("href");
	           				name = link.text();
	           			}

	           			Elements locs = list.select("p.jaid");
	           			for(Element loc : locs)
	           			{
	           				System.out.println("area: " + loc.text().substring(0, loc.text().indexOf('|')));
	           				area = loc.text().substring(0, loc.text().indexOf('|'));
	           			}

	           			
           				// fetch Detail Info
           				providers = fetchDetailInfo(url, name, area);

           				System.out.println("providers:" + providers.toString());
           				
//	           		}
	           	
	       	//		updateDatabase(mycon, providers);
	        		
	        		rowctr++;
		        }
			}
*/
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
	
	private static Providers fetchDetailInfo(Connection mycon, String url, String name, String area)
	{
		Providers providers = new Providers();

		try
		{
			
//			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\justdial_detail.html");
//			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

						
			url = url + "/?tab=moreinfo";
			logger.info("url:" + url);
			Document doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
			//logger.info(doc);
			
			providers.setUrl(url);
			System.out.println("Name:" + name);			
			providers.setProvider_name(name);
			providers.setArea(area);
           	
           	Elements contacts = doc.select("section.jrcl");
           	for (Element continfo : contacts) {
    	            Elements adds = continfo.select("p");
    	            if(adds.size() > 2)
    	            {
    	            	System.out.println("Phone:" + adds.get(0).text());
    	            	System.out.println("Address:" + adds.get(1).text());
    	            	
    	            	providers.setPhone(adds.get(0).text());
    	            	providers.setAddress(adds.get(1).text());
    	            }
    	            else
    	            {
						System.out.println("Address:" + adds.get(0).text());
    	            	
    	            	providers.setPhone("");
    	            	providers.setAddress(adds.get(0).text());
    	            }
	        }
           	
			Elements lts = doc.select("input#lt");
           	for (Element lt : lts) 
           	{
           		providers.setLt(lt.attr("value"));
           		System.out.println("LT>....." + lt.attr("value"));
           	}

			Elements lns = doc.select("input#ln");
           	for (Element ln : lns) 
           	{
           		providers.setLn(ln.attr("value"));
           		System.out.println("LN>....." + ln.attr("value"));
           	}

			Elements adds = doc.select("input#add");
           	for (Element add : adds) 
           	{
           		providers.setAddress(add.attr("value"));
           		System.out.println("ADD>....." + add.attr("value"));
           	}
           	
           	String listedcategories = "";
           	
           	Elements listedcats = doc.select("section#alsol a");
           	for (Element listedcat : listedcats) {
           		if(listedcategories.length() > 0)
           			listedcategories = listedcategories + "," + listedcat.text();
    	        else
    	        	listedcategories = listedcat.text();
	        }
           	
           	System.out.println("Listed Cateogries:" + listedcategories);
           	providers.setListed_categories((listedcategories.length()>200)?listedcategories.substring(0, 200):listedcategories);
           	
           	Pattern p = Pattern.compile(".*(\\d{6})");
   			Matcher m = p.matcher(providers.getAddress());
   			m.matches();
           	
   			providers.setCity(city);
   			providers.setProvider_type("C");
   			try
   			{
   				providers.setPin_code(m.group(1));
   			}
   			catch(Exception px)
   			{
   			}
   			
   			System.out.println("pincode:" + providers.getPin_code());
   		}
		catch(Exception e)
		{
			System.out.print("fetchDetailInfo:" + e);
			Providers.updateDatabaseForErrors(logger, mycon, url, e.toString());
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
}
