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

public class HealthKartPlusGatherMedicines {

    private static Logger logger = Logger.getLogger(HealthKartPlusGatherMedicines.class);

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
			
			int	rowctr = 1;
			
//			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\healthkartplus_crocin-60-ml.html");
//			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			
			for(int medIndex=100001; medIndex<=125000; medIndex++)
			{
				String websiteurl = "http://www.healthkartplus.com/details/drugs/" + medIndex + "/propine-eye-5-ml";
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
				//	logger.info(doc);

					Medicines medicines = new Medicines();
					medicines.setUrl(websiteurl);
					
					Elements uprDtls = doc.select("table#uprDtl");
			        System.out.println("uprDtls:" + uprDtls.size());
		           	for (Element uperDtl : uprDtls) 
		           	{
	           			System.out.println("rowno:" + rowctr);
	           			
	           			Elements medicine = uperDtl.select("h1.mdcnNm");
           				System.out.println("medicine: " + medicine.text());
           				medicines.setMedicine_name(medicine.text());
           				
	           			Elements sizeDtl = uperDtl.select("div.sizDtl");
           				System.out.println("sizeDtl: " + sizeDtl.text());
           				medicines.setSize_detail(sizeDtl.text());
           				
           				Elements mfgNm = uperDtl.select("div.mfgNm a");
           				System.out.println("mfgNm link: " + mfgNm.attr("href").trim());
           				System.out.println("mfgName: " + mfgNm.text().trim());
           				medicines.setMfg_link(mfgNm.attr("href").trim());
           				medicines.setMfg_name(mfgNm.text().trim());
           				
           				Elements qtyOp = uperDtl.select("div.qtyOp div.ofrPrc span");
           				System.out.println("qtyOp: " + qtyOp.first().text().trim());
           				medicines.setQty_price_detail(qtyOp.first().text().trim());
           				
           				Pattern pattern = Pattern.compile("Rs(.*?)per");
           				Matcher matcher = pattern.matcher(qtyOp.first().text().trim());
           				if (matcher.find()) {
           					System.out.println(matcher.group(1));
           					medicines.setPrice(matcher.group(1));
           				}

           				pattern = Pattern.compile("per(.*)");
           				matcher = pattern.matcher(qtyOp.first().text().trim());
           				if (matcher.find()) {
           				    System.out.println(matcher.group(1));
           				    medicines.setMeasure_unit(matcher.group(1));
           				}

			        }

					Elements compositions = doc.select("div#lwrDtl div#inf div table td:gt(0)");
		           	for (Element composition : compositions) 
		           	{
           				System.out.println("composition: " + composition.text());
           				medicines.setComposition(composition.text());
			        }

					Elements addInfos = doc.select("div#lwrDtl div#inf div.addInf");
		           	for (Element addInfo : addInfos) 
		           	{
		           		Elements comp = addInfo.select("h4.title a");
		           		System.out.println("generic: " + comp.text());
		           		medicines.setGeneric(comp.text());
		           		
		           		Elements miscInfos = addInfo.select("div.miscInf p.pdngTB");
			           	for (Element miscInfo : miscInfos) 
			           	{
//				           		System.out.println("miscInfo: " + miscInfo.text());
			           		if(miscInfo.text().indexOf("Typical usage:") == 0) {
			           			System.out.println("Typical usage:: " + miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           			medicines.setTypical_usage(miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           		} else if(miscInfo.text().indexOf("Side Effects:") == 0) {
			           			System.out.println("Side Effects:: " + miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           			medicines.setSide_effect(miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           		} else if(miscInfo.text().indexOf("Drug Interaction:") == 0) {
			           			System.out.println("Drug Interaction:: " + miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           			medicines.setDrug_interaction(miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           		} else if(miscInfo.text().indexOf("Mechanism Of Action:") == 0) {
			           			System.out.println("Mechanism Of Action:: " + miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           			medicines.setMechanism_of_action(miscInfo.text().substring(miscInfo.text().indexOf(":")+1).trim());
			           		}
			           	}
			        }
           			rowctr++;
           			
           			if(medicines.getMedicine_name().length() > 0)
           				Medicines.updateDatabase(logger, mycon, medicines);
           			else
           				logger.info("No data found for:" + medIndex);
				}
				catch(Exception kk)
				{
					System.out.println("Exception:" +kk);
					logger.info(kk);
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
					obj.closeConnection();
			}
			catch(Exception e1)
			{
				
			}
		}
	}
	
	private static Providers fetchDetailInfo(String url, String name, String area)
	{
		Providers providers = new Providers();

		try
		{
			
			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\justdial_detail.html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

/*						
			url = url + "/?tab=moreinfo";
			logger.info("url:" + url);
			Document doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
*/			
//			logger.info(doc);
			
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
           	
   			providers.setCity("Pune");
   			providers.setProvider_type("C");
   			providers.setPin_code(m.group(1));
   			
   			System.out.println("pincode:" + providers.getPin_code());
   		}
		catch(Exception e)
		{
			System.out.print("fetchDetailInfo:" + e);
		}
		
		return providers;
	}
}
