package com.emediplus.dto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class Medicines {

	public String toString()
	{
		String retValue = "";
		
		retValue = "url:" + url + ", medicine_name:" + medicine_name + ", size_detail:" + size_detail + ", mfg_link:" + mfg_link + ", mfg_name:" + mfg_name + 
				", qty_price_detail:" + qty_price_detail + ", price:" + price + ", measure_unit:" + measure_unit + ", composition:" + composition + 
				", generic:" + generic + ", typical_usage:" + typical_usage + ", side_effect:" + side_effect + ", drug_interaction:" + drug_interaction +
				", mechanism_of_action:" + mechanism_of_action;
		
		return retValue;
	}

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = setValues(url);
	}


	public String getMedicine_name() {
		return medicine_name;
	}


	public void setMedicine_name(String medicine_name) {
		this.medicine_name = setValues(medicine_name);
	}


	public String getSize_detail() {
		return size_detail;
	}


	public void setSize_detail(String size_detail) {
		this.size_detail = setValues(size_detail);
	}


	public String getMfg_link() {
		return mfg_link;
	}


	public void setMfg_link(String mfg_link) {
		this.mfg_link = setValues(mfg_link);
	}


	public String getMfg_name() {
		return mfg_name;
	}


	public void setMfg_name(String mfg_name) {
		this.mfg_name = setValues(mfg_name);
	}


	public String getQty_price_detail() {
		return qty_price_detail;
	}


	public void setQty_price_detail(String qty_price_detail) {
		this.qty_price_detail = setValues(qty_price_detail);
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = setValues(price);
	}


	public String getMeasure_unit() {
		return measure_unit;
	}


	public void setMeasure_unit(String measure_unit) {
		this.measure_unit = setValues(measure_unit);
	}


	public String getComposition() {
		return composition;
	}


	public void setComposition(String composition) {
		this.composition = setValues(composition);
	}


	public String getGeneric() {
		return generic;
	}


	public void setGeneric(String generic) {
		this.generic = setValues(generic);
	}


	public String getTypical_usage() {
		return typical_usage;
	}


	public void setTypical_usage(String typical_usage) {
		this.typical_usage = setValues(typical_usage);
	}


	public String getSide_effect() {
		return side_effect;
	}


	public void setSide_effect(String side_effect) {
		this.side_effect = setValues(side_effect);
	}


	public String getDrug_interaction() {
		return drug_interaction;
	}


	public void setDrug_interaction(String drug_interaction) {
		this.drug_interaction = setValues(drug_interaction);
	}


	public String getMechanism_of_action() {
		return mechanism_of_action;
	}


	public void setMechanism_of_action(String mechanism_of_action) {
		this.mechanism_of_action = setValues(mechanism_of_action);
	}

	String url = "";
	String medicine_name = "";
	String size_detail = "";
	String mfg_link = "";
	String mfg_name = "";
	String qty_price_detail = "";
	String price = "";
	String measure_unit = "";
	String composition = "";
	String generic = "";
	String typical_usage = "";
	String side_effect = "";
	String drug_interaction = "";
	String mechanism_of_action = "";
	
	public static void updateDatabase(Logger logger, Connection mycon, Medicines medicines)
	{
       	PreparedStatement myst = null;
       	PreparedStatement myst_insert = null;
       	ResultSet myrs = null;
       	boolean rowfound = false;
       	int insResult = 0;
       	try
       	{
     		String mysql = "select count(*) from medicines where lower(trim(url)) like '%" + medicines.getUrl().trim().toLowerCase() + "%'";
/*       		myst = mycon.prepareStatement(mysql);	
       		myrs = myst.executeQuery();
			while(myrs.next())
			{
				if(myrs.getInt(1) > 0)
					rowfound = true;
	       		
			}

			if(rowfound)
			{
				logger.info("SQL:" + mysql);
				logger.info(medicines.getMedicine_name() + " : found : " + rowfound);
			}
*/			
			if(!rowfound)
			{
				mysql = "insert into medicines(url, medicine_name, size_detail, mfg_link, mfg_name, qty_price_detail, price, "
						+ "measure_unit, composition, generic, typical_usage, side_effect, drug_interaction, mechanism_of_action) " +
						" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				myst_insert = mycon.prepareStatement(mysql);	
				myst_insert.setString(1,  medicines.getUrl());
				myst_insert.setString(2, medicines.getMedicine_name());
				myst_insert.setString(3, medicines.getSize_detail());
				myst_insert.setString(4, medicines.getMfg_link());
				myst_insert.setString(5, medicines.getMfg_name());
				myst_insert.setString(6, medicines.getQty_price_detail());
				myst_insert.setString(7, medicines.getPrice());
				myst_insert.setString(8, medicines.getMeasure_unit());
	       		myst_insert.setString(9, medicines.getComposition());
	       		myst_insert.setString(10, medicines.getGeneric());
	       		myst_insert.setString(11, medicines.getTypical_usage());
	       		myst_insert.setString(12, medicines.getSide_effect());
	       		myst_insert.setString(13, medicines.getDrug_interaction());
	       		myst_insert.setString(14, medicines.getMechanism_of_action());
	       		
	       		insResult = myst_insert.executeUpdate();
	       		
	       		System.out.println("insert Result:" + insResult);
	       		
	       		mycon.commit();
			}
       	}
       	catch (Exception e)
       	{
       		logger.info(medicines.toString());
       		logger.info("updateDatabase Exception:" + e);
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
	
	public static void fetchData(Logger logger, Connection mycon)
	{
       	PreparedStatement myst = null;
       	PreparedStatement myst_insert = null;
       	PreparedStatement myst_insert_med_header = null;
       	PreparedStatement myst_insert_med_detail = null;
       	PreparedStatement myst_insert_med_searches = null;
       	PreparedStatement myst_insert_searches = null;
       	PreparedStatement myst_check = null;
        ResultSet myrs_check = null;
       	ResultSet myrs = null;
       	int medIndex = 0;
       	int searchIndex = 0;
       	
       	String typicalUsage = "";
       	String sideEffect = "";
       	String drugInteraction = "";
       	String mechanismAction  = "";
       	       	
       	try
       	{
     		String mysql = "select id, url, medicine_name, size_detail, mfg_link, mfg_name, qty_price_detail, price, "
						+ "measure_unit, composition, generic, typical_usage, side_effect, drug_interaction, mechanism_of_action from medicines where id > 75333";
      		myst = mycon.prepareStatement(mysql);	
       		myrs = myst.executeQuery();
			while(myrs.next())
			{
				ArrayList keywords = new ArrayList();
				System.out.println("");
				System.out.println("id:" + myrs.getInt("id"));
				System.out.println("medicine_name:" + myrs.getString("medicine_name"));
				System.out.println("size_detail:" + myrs.getString("size_detail"));
				System.out.println("mfg_name:" + myrs.getString("mfg_name"));
				System.out.println("qty_price_detail:" + myrs.getString("qty_price_detail"));
				System.out.println("price:" + myrs.getDouble("price"));
				System.out.println("measure_unit:" + myrs.getString("measure_unit"));
				System.out.println("composition:" + myrs.getString("composition"));
				System.out.println("generic:" + myrs.getString("generic"));
				System.out.println("typical_usage:" + myrs.getString("typical_usage"));
				System.out.println("side_effect:" + myrs.getString("side_effect"));
				System.out.println("drug_interaction:" + myrs.getString("drug_interaction"));
				System.out.println("mechanism_of_action:" + myrs.getString("mechanism_of_action"));
				
				keywords = addKeywords(keywords, myrs.getString("medicine_name"));
				keywords = addKeywords(keywords, myrs.getString("mfg_name"));
				keywords = addKeywords(keywords, myrs.getString("generic"));
				//keywords = addKeywords(keywords, myrs.getString("typical_usage"));
				
				System.out.println("keywords:" + keywords);
				
				medIndex = myrs.getInt("id");
				typicalUsage = myrs.getString("typical_usage");
				sideEffect = myrs.getString("side_effect");
				drugInteraction = myrs.getString("drug_interaction");
				mechanismAction = myrs.getString("mechanism_of_action");
				
				int	   qty = 0;
				String unitmeasure = "";
				String packagingtype = "";
			    String input = myrs.getString("size_detail");
			    input = input.substring(1,input.length()-1);
			    Pattern pattern = Pattern.compile("(\\d+)(.*)in a(.*)");
			    Matcher matcher = pattern.matcher(input);
			    if(matcher.matches())
			    {
			    /*	System.out.println("1:" + matcher.group(1).trim());
			    	System.out.println("2:" + matcher.group(2).trim());
			    	System.out.println("3:" + matcher.group(3).trim()); */
			    	qty = Integer.parseInt(matcher.group(1).trim());
			    	unitmeasure = matcher.group(2).trim();
			    	packagingtype = matcher.group(3).trim();
			    }

				try
				{
					mysql = "insert into medicines_header(medicine_id, medicine_name, mfg_name, price, quantity, unit_measurement, packaging_type, " +
							" composition, generic) " +
							" values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
					myst_insert_med_header = mycon.prepareStatement(mysql);	
					myst_insert_med_header.setInt(1,  medIndex);
					myst_insert_med_header.setString(2,  myrs.getString("medicine_name"));
					myst_insert_med_header.setString(3,  myrs.getString("mfg_name"));
					myst_insert_med_header.setDouble(4,  myrs.getDouble("price"));
					myst_insert_med_header.setInt(5,  qty);
					myst_insert_med_header.setString(6,  unitmeasure);
					myst_insert_med_header.setString(7,  packagingtype);
					myst_insert_med_header.setString(8,  myrs.getString("composition"));
					myst_insert_med_header.setString(9,  myrs.getString("generic"));
					
					int insResult = myst_insert_med_header.executeUpdate();
					System.out.println("inserting into medicines_header :" + insResult);
					
					mysql = "insert into medicines_detail(medicine_id, typical_usage, side_effect, drug_interaction, mechanism_of_action) " +
								" values(?, ?, ?, ?, ?)";
		
					myst_insert_med_detail = mycon.prepareStatement(mysql);	
					myst_insert_med_detail.setInt(1,  medIndex);
					myst_insert_med_detail.setString(2,  typicalUsage);
					myst_insert_med_detail.setString(3,  sideEffect);
					myst_insert_med_detail.setString(4,  drugInteraction);
					myst_insert_med_detail.setString(5,  mechanismAction);
					
					insResult = myst_insert_med_detail.executeUpdate();
					System.out.println("inserting into medicines_detail :" + insResult);
					
					for(int i=0; i<keywords.size(); i++)
					{
						boolean rowfound = false;
						searchIndex = 0;
						
						// check if keyword present in searches
			     		mysql = "select search_id from searches where search_tags = '" + keywords.get(i).toString() + "'";
			     		myst_check = mycon.prepareStatement(mysql);	
     		       		myrs_check = myst_check.executeQuery();
     					while(myrs_check.next())
     					{
     						searchIndex = myrs_check.getInt(1);
   							rowfound = true;
   							System.out.println("search-id:" + searchIndex + " keyword:" + keywords.get(i).toString());
     					}

     					if(!rowfound)
     					{
     						mysql = "insert into searches(search_tags, reference_table_id) " +
     								" values(?, 1)";
				
     						myst_insert_searches = mycon.prepareStatement(mysql);	
     						myst_insert_searches.setString(1,  keywords.get(i).toString());
     						
     						insResult = myst_insert_searches.executeUpdate();
     						System.out.println("inserting into searches:" + insResult);
     					
    			     		mysql = "select search_id from searches where search_tags = '" + keywords.get(i).toString() + "'";
    			     		myst_check = mycon.prepareStatement(mysql);	
         		       		myrs_check = myst_check.executeQuery();
         					while(myrs_check.next())
         					{
         						searchIndex = myrs_check.getInt(1);
       							System.out.println("search-id:" + searchIndex);
       							logger.info("search-id found:" + searchIndex + " medIndex:" + medIndex + " keyword:" + keywords.get(i).toString());
         					}
     					}

     					mysql = "insert into medicines_detail_searches(medicine_id, search_id) " +
     								" values(?, ?)";
				
 						myst_insert_med_searches = mycon.prepareStatement(mysql);	
 						myst_insert_med_searches.setInt(1,  medIndex);
 						myst_insert_med_searches.setInt(2,  searchIndex);
 						
 						insResult = myst_insert_med_searches.executeUpdate();
 						System.out.println("inserting into medicines_detail_searches :" + insResult);
		       		}
		       		
					mycon.commit();
				}
				catch(Exception ex)
				{
					logger.info("fetchData " + medIndex + " Exception:" + ex);
				}
		       	finally
		       	{
		       		try
		       		{
		       			if(myrs_check != null) myrs_check.close();
		       			if(myst_insert_searches != null) myst_insert_searches.close();
		       			if(myst_insert_med_searches != null) myst_insert_med_searches.close();
		       			if(myst_insert_med_detail != null) myst_insert_med_detail.close();
		       			if(myst_insert_med_header != null) myst_insert_med_header.close();
		       		}
		       		catch(Exception ef)
		       		{
		       		}
		       	}
			}
     	}
       	catch (Exception e)
       	{
       		logger.info("fetchData " + medIndex + " Exception:" + e);
       	}
       	finally
       	{
       		try
       		{
       			if(myst != null) myst.close();
       		}
       		catch(Exception ef)
       		{
       			
       		}
       	}
	}
	
	public static ArrayList addKeywords(ArrayList keywords, String str)
	{
		ArrayList keywords_new = keywords;
		String    newStr = "";
		newStr = discardValueInsideBracket(str);
		System.out.println("newStr:" + newStr + "]");
		String[] keyword_str = newStr.split(" "); 
		for(int s=0; s<keyword_str.length; s++)
			if(!ignoreKeywords(keyword_str[s]))
				keywords_new.add(keyword_str[s]);
		
		
		return keywords_new;
		
	}
	
	public static boolean ignoreKeywords(String str)
	{
		String[] ignoreKeywords = {"Pharmaceuticals", "Ltd", "Ltd.", "Ltd)", "Limited", "India", "PvtLtd", "Pharmaceutical", "Industries", "Corporation"};
		
		for(int i=0; i<ignoreKeywords.length; i++)
		{
			if(str.length() <= 3 || str.equalsIgnoreCase(ignoreKeywords[i]))
			{
				System.out.println("str:" + str + " ignored...");
				return true;
			}
			else
			{
			    Pattern pattern = Pattern.compile("[a-z].*");
			    Matcher matcher = pattern.matcher(str);
			    if(!matcher.matches())
			    {
			    	pattern = Pattern.compile("[A-Z].*");
			    	matcher = pattern.matcher(str);
			    	if(!matcher.matches())
			    		return true;
			    }
			}
		}
		
		return false;
		
	}
	
	public static String discardValueInsideBracket(String str)
	{
		int startPos = str.indexOf("(");
		int endPos = str.indexOf(")");
		String newStr = "";
		
		if(startPos != -1)
			newStr = str.substring(0, startPos).trim() + " " + str.substring(endPos + 1, str.length()).trim();
		else
			newStr = str;
		
		newStr = replaceStrings(newStr, ".", "");
		newStr = replaceStrings(newStr, ",", "");
		
		return newStr;
	}
	
	public String setValues(String temp)
	{
		String newtemp = (temp == null)?"":temp.trim();
		
		return newtemp;
	}
	
	public static String replaceStrings(String subject, String search, String replacement) 
	{
		if (search.equals("")) // guard against infinite loop
		    return subject;
		 
		String result = "";
		int oldPos = 0;
		int pos=0;
		 
		while ((pos = subject.indexOf(search, oldPos)) != -1) {
		    result += subject.substring(oldPos, pos) + replacement;
		    oldPos = pos+ search.length();
		}
		 
		result += subject.substring(oldPos);
		 
		return result;
	}
}
