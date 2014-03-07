package com.emediplus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.emediplus.dto.Medicines;
import com.emediplus.dto.NonMedicines;
import com.emediplus.dto.NonMedicinesDetail;
import com.emediplus.dto.Providers;
import com.emediplus.util.ConnectionOBJ;

public class HealthKartGatherNonMedicines {
	
    private static Logger logger = Logger.getLogger(HealthKartGatherNonMedicines.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection mycon = null;
		ConnectionOBJ obj = new ConnectionOBJ();

		try
		{
			PropertyConfigurator.configure("./config/log4j.properties");
			
			mycon = obj.getConnection();
			mycon.setAutoCommit(false);

			
			NonMedicines nonmedicines = new NonMedicines();
			int	medIndex = 14196;
			int totalProducts = 0;
			int totalPages = 1;
			int perPage = 48;
			String prodCategory = "";
			String prodUrl = "";
			
			String[] prodCategories = new String[25];
			String[] prodCategoriesURL = new String[25];
          	
			try
			{
				FileInputStream fstream = new FileInputStream("healthkart.txt");
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				int    lineNo = 0;
				while ((strLine = br.readLine()) != null)   {
					prodCategories[lineNo] = strLine.substring(0, strLine.indexOf(','));
					prodCategoriesURL[lineNo] = strLine.substring(strLine.indexOf(',')+1, strLine.length());
					//System.out.println ("cat:" + prodCategories[lineNo] + " url:" + prodCategoriesURL[lineNo]);
					lineNo++;
				}
				//Close the input stream
				in.close();
			}catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
		    }

			for(int lineNo=0; (lineNo<prodCategories.length & prodCategories[lineNo] !=null); lineNo++)
			{
				logger.info("lineNo:" + lineNo + " prodCategoriesURL:" + prodCategoriesURL[lineNo]);
				
				for(int currentPage=1; currentPage<=totalPages; currentPage++)
				{
	//				String websiteurl = "http://www.healthkart.com/nutrition/sports-nutrition/proteins/whey-proteins?navKey=SCT-snt-pt-wp&catPrefix=snt-pt-wp~&brands=&fl=&pageNo=" + currentPage + "&perPage=" + perPage + "&sortBy=RANK&sortType=ASC&minPrice=235.0&maxPrice=12409.0&";
	//				String websiteurl = "http://www.healthkart.com/sports-nutrition/protein/protein-bars?navKey=CL-283&catPrefix=snt-pt-pb~&brands=&fl=&pageNo="+ currentPage + "&perPage=" + perPage + "&sortBy=RANK&sortType=ASC&minPrice=88.0&maxPrice=2999.0&";
	//				prodCategory = "proteins/protein-bars";
					
					String websiteurl = prodCategoriesURL[lineNo] + "pageNo="+ currentPage + "&";
					prodCategory = prodCategories[lineNo];	
					System.out.println(websiteurl + ", prodCategory:" + prodCategory);
	
					try
					{
	/*					prodCategory = "face-care/cleansars-n-toners";
						nonmedicines = fetchProductInfo("/sv/nivea-roll-on/SP-3268?navKey=VRNT-5053", "", prodCategory, medIndex);
	
	           			if(nonmedicines.getProd_full_name().length() > 0)
	           				NonMedicines.updateDatabase(logger, mycon, nonmedicines);
	           			else
	           				logger.info("No data found for:" + medIndex);
	
	*/					
						//fetchProductInfo("/sv/accu-chek-active-blood-glucose-monitoring-system-with10-free-strips/SP-4304?navKey=VRNT-6699", "", prodCategory, rowctr);
	
	//					logger.info(websiteurl);
						
						Document doc = Jsoup.connect(websiteurl)
								  .data("query", "Java")
								  .userAgent("Mozilla")
								  .cookie("auth", "token")
								  .timeout(3000)
								  .post();
						//logger.info(doc);
		
						if(currentPage == 1)
						{
							Element topPager = doc.select("div#topPager span").first();
							System.out.println("topPager:" + topPager.text());
							
							Pattern pattern = Pattern.compile(".*of(.*)results");
							Matcher matcher = pattern.matcher(topPager.text());
							if (matcher.find()) {
								totalProducts = Integer.parseInt(matcher.group(1).trim());
							    logger.info("totalProducts:" + totalProducts);
							    totalPages = (totalProducts/perPage);
							    if((totalProducts % perPage) > 0)
							    	totalPages++;
							    logger.info("totalPages:" + totalPages);
							}
						}
						
						Elements prodDtls = doc.select("div.varnt-cont");
				        System.out.println("prodDtls:" + prodDtls.size());
			           	for (Element prodDtl : prodDtls) 
			           	{
			           		nonmedicines = new NonMedicines();
			           		
			           		System.out.println("medIndex:" + medIndex);
		           			Elements prodLink = prodDtl.select("div.img-box a");
		           			System.out.println("prodlink: " + prodLink.attr("href"));
		           			prodUrl = prodLink.attr("href");
		       				
		       				Elements imgLink = prodLink.select("img");
		       				System.out.println("imglink: " + imgLink.attr("src"));
		       				
		       				saveImage(medIndex + "_small", imgLink.attr("src"), prodCategory);

		       				nonmedicines = fetchProductInfo(prodLink.attr("href"), "", prodCategory, medIndex);
		       				
		       				if(nonmedicines.getProd_full_name().length() > 0)
	           					NonMedicines.updateDatabase(logger, mycon, nonmedicines);
	           				else
	           				{
	           					logger.info("No data found for:" + medIndex + ", prodCategory:" + prodCategory);
	           					NonMedicines.updateDatabase(logger, mycon, medIndex, prodCategory, prodLink.attr("href"), "No detail data found");
	           				}
		       				
			           		medIndex++;
		       	        }
		       			
					}
					catch(Exception kk)
					{
						System.out.println("Exception:" +kk);
						logger.info("medIndex:" + medIndex + ", prodCategory:" + prodCategory + " " + kk);
						NonMedicines.updateDatabase(logger, mycon, medIndex, prodCategory, prodUrl, kk.toString());
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("exception:" + e);
		}
		finally
		{
		}
	}
	
	public static void saveImage(String filename, String url, String pathToSave)
	{
		String dir = "./tmp/images/" + pathToSave; 
		File saveDir = new File(dir);
		//Here comes the existence check
		if(!saveDir.exists())
		   saveDir.mkdirs();
		
		try
		{
			URL imageURL = new URL(url);
			InputStream in = new BufferedInputStream(imageURL.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf)))
			{
				out.write(buf, 0, n);
			}	
			out.close();
			in.close();
			byte[] response = out.toByteArray();
	
			FileOutputStream fos = new FileOutputStream(dir + "/" + filename + ".jpg");
			fos.write(response);
			fos.close();
		}
		catch(Exception e)
		{
			logger.info("saveImage: error saving image " + dir + "/" + filename + ".jpg" + ":" + e);
		}
	}
	
	public static NonMedicines fetchProductInfo(String url, String name, String category, int indexNumber)
	{
		NonMedicines nonmedicines = new NonMedicines();
		
		try
		{
			
			url = "http://www.healthkart.com" + url;
			logger.info("url:" + url);
			Document doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();

			//logger.info(doc);
			System.out.println("url:" + url);
			nonmedicines.setUrl(url);
			nonmedicines.setProduct_category(category);
			nonmedicines.setProd_number(""+indexNumber);
			
           	Elements prodFullName = doc.select("div.ttl-cntnr h1");
           	System.out.println("prod Full Name:" + prodFullName.get(0).text());
           	nonmedicines.setProd_full_name(prodFullName.get(0).text());
			
           	Elements companyName = doc.select("div.marginBox div a");
           	// System.out.println("company Full Name:" + companyName.get(0).attr("title"));
           	
			Pattern pattern = Pattern.compile(".*from(.*)");
			Matcher matcher = pattern.matcher(companyName.get(0).attr("title"));
			if (matcher.find()) {
				System.out.println("company name:" + matcher.group(1).trim());
				nonmedicines.setCompany_name(matcher.group(1).trim());
			}

			// Get large and small images.
			Elements images = doc.select("div.span6.clearContainers div img");
			System.out.println("images..large.." + images.first().attr("src"));
			System.out.println("images..extra large.." + images.first().attr("data-zoom-image"));
			
			saveImage(indexNumber + "_large", images.first().attr("src"), category);
			saveImage(indexNumber + "_xlarge", images.first().attr("data-zoom-image"), category);
			
			
			Elements prodprice = doc.select("div.row div.span6 span");
			pattern = Pattern.compile(".*Rs.(.*)");
			matcher = pattern.matcher(prodprice.text());
			if (matcher.find()) {
				System.out.println("price:" + matcher.group(1).trim());
				nonmedicines.setPrice(matcher.group(1).trim());
			}
			
		/*	String price = prodprice.first().text().trim();
			String price2 = price.substring(4);
          	System.out.println("prodprice:" + price2.trim() + "]");
          	nonmedicines.setPrice(price2.trim());
		*/	
			Elements prodattrs = doc.select("div.variant-diff-attr-div div.diff-attr.mrgn-b-10 label");
			System.out.println("prodattrs:" + prodattrs.size());
			int attr = 1;
	        for (Element prodattr : prodattrs) 
	        {
	        	if(attr == 1)
	        	{
	        		nonmedicines.setProd_attr1(prodattr.attr("for"));
	        		nonmedicines.setProd_value1(prodattr.select("span").text());
	        	}
	        	else if(attr == 2)
	        	{
	        		nonmedicines.setProd_attr2(prodattr.attr("for"));
	        		nonmedicines.setProd_value2(prodattr.select("span").text());
	        	}
	        	else if(attr == 3)
	        	{
	        		nonmedicines.setProd_attr3(prodattr.attr("for"));
	        		nonmedicines.setProd_value3(prodattr.select("span").text());
	        	}
	        	else if(attr == 4)
	        	{
	        		nonmedicines.setProd_attr4(prodattr.attr("for"));
	        		nonmedicines.setProd_value4(prodattr.select("span").text());
	        	}
	        	else if(attr == 5)
	        	{
	        		nonmedicines.setProd_attr5(prodattr.attr("for"));
	        		nonmedicines.setProd_value5(prodattr.select("span").text());
	        	}
	        	System.out.println("prodattr-name:" + prodattr.attr("for"));
	        	System.out.println("prodattr-value:" + prodattr.select("span").text());
	        	
	        	attr++;
	        }
        			
	        Elements proddescs = doc.select("div.span5 li");
			System.out.println("proddesc:" + proddescs.size());
			int desc = 1;
	        for (Element proddesc : proddescs) 
	        {
	        	if(desc == 1)
	        		nonmedicines.setProd_desc1(proddesc.text());
	        	else if(desc == 2)
	        		nonmedicines.setProd_desc2(proddesc.text());
	        	else if(desc == 3)
	        		nonmedicines.setProd_desc3(proddesc.text());
	        	else if(desc == 4)
	        		nonmedicines.setProd_desc4(proddesc.text());
	        	else if(desc == 5)
	        		nonmedicines.setProd_desc5(proddesc.text());
	        		
	        	System.out.println("proddesc:" + proddesc.text());
	        	desc++;
	        }
        	
	        ArrayList nonmeddetails = new ArrayList();
	        
	        Elements prodDtls = doc.select("div#detailsContainer div.mrgn-bt-10 table");
			System.out.println("prodDtls:" + prodDtls.size());
	        for (Element prodDtl : prodDtls) 
	        {
	        	//System.out.println("prodDtl:" + prodDtl.text());
	        	Elements specsHdr = prodDtl.select("tr.specs-hdr-tr");
	        	System.out.println("specsHdr:" + specsHdr.text());
	        	String prod_dtl_type = specsHdr.text();
	        	String prod_dtl_name = "";
	        	String prod_dtl_value = "";
	        	int rowno = 1;
	        	Elements varntSpecs = prodDtl.select("tr.varnt-specs-tr td");
	        	for(Element varntSpec : varntSpecs)
	        	{
	        		System.out.println("varntSpecs:" + varntSpec.text());
	        		if(rowno == 1)
	        			prod_dtl_name = varntSpec.text();
	        		else if(rowno == 2)
	        		{
	        			prod_dtl_value = varntSpec.text();
	        			NonMedicinesDetail nonMedDetail = new NonMedicinesDetail();
	        			nonMedDetail.setProd_number(nonmedicines.getProd_number());
	        			nonMedDetail.setProd_dtl_type(prod_dtl_type);
	        			nonMedDetail.setProd_dtl_name(prod_dtl_name);
	        			nonMedDetail.setProd_dtl_value(prod_dtl_value);
	        			nonmeddetails.add(nonMedDetail);
	        			
	        			rowno = 0;
	        		}
	        		rowno++;
	        	}
	        	
	        	nonmedicines.setProductdetails(nonmeddetails);
	        }
	        
	        Element descDtl = doc.select("div.vds div#variantVideoCont").first();
	        //System.out.println("descDtl:" + descDtl.select("p").html());
	        nonmedicines.setProd_desc_long(descDtl.select("p").html());
	   	}
		catch(Exception e)
		{
			System.out.print("fetchDetailInfo:" + e);
		}
		
		return nonmedicines;
	}

}
