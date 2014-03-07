package com.emediplus.dto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class NonMedicines {

	public String getProduct_category() {
		return product_category;
	}


	public void setProduct_category(String product_category) {
		this.product_category = setValues(product_category);
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = setValues(url);
	}


	public String getProd_number() {
		return prod_number;
	}


	public void setProd_number(String prod_number) {
		this.prod_number = setValues(prod_number);
	}


	public String getProd_full_name() {
		return prod_full_name;
	}


	public void setProd_full_name(String prod_full_name) {
		this.prod_full_name = setValues(prod_full_name);
	}


	public String getCompany_name() {
		return company_name;
	}


	public void setCompany_name(String company_name) {
		this.company_name = setValues(company_name);
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = setValues(price);
	}


	public String getProd_attr1() {
		return prod_attr1;
	}


	public void setProd_attr1(String prod_attr1) {
		this.prod_attr1 = setValues(prod_attr1);
	}


	public String getProd_value1() {
		return prod_value1;
	}


	public void setProd_value1(String prod_value1) {
		this.prod_value1 = setValues(prod_value1);
	}


	public String getProd_attr2() {
		return prod_attr2;
	}


	public void setProd_attr2(String prod_attr2) {
		this.prod_attr2 = setValues(prod_attr2);
	}


	public String getProd_value2() {
		return prod_value2;
	}


	public void setProd_value2(String prod_value2) {
		this.prod_value2 = setValues(prod_value2);
	}


	public String getProd_attr3() {
		return prod_attr3;
	}


	public void setProd_attr3(String prod_attr3) {
		this.prod_attr3 = setValues(prod_attr3);
	}


	public String getProd_value3() {
		return prod_value3;
	}


	public void setProd_value3(String prod_value3) {
		this.prod_value3 = setValues(prod_value3);
	}


	public String getProd_attr4() {
		return prod_attr4;
	}


	public void setProd_attr4(String prod_attr4) {
		this.prod_attr4 = setValues(prod_attr4);
	}


	public String getProd_value4() {
		return prod_value4;
	}


	public void setProd_value4(String prod_value4) {
		this.prod_value4 = setValues(prod_value4);
	}


	public String getProd_attr5() {
		return prod_attr5;
	}


	public void setProd_attr5(String prod_attr5) {
		this.prod_attr5 = setValues(prod_attr5);
	}


	public String getProd_value5() {
		return prod_value5;
	}


	public void setProd_value5(String prod_value5) {
		this.prod_value5 = setValues(prod_value5);
	}


	public String getProd_desc_long() {
		return prod_desc_long;
	}


	public void setProd_desc_long(String prod_desc_long) {
		if(setValues(prod_desc_long).length() > 10000)
			this.prod_desc_long = setValues(prod_desc_long).substring(0, 10000);
		else
			this.prod_desc_long = setValues(prod_desc_long);
	}


	public ArrayList getProductdetails() {
		return productdetails;
	}


	public void setProductdetails(ArrayList productdetails) {
		this.productdetails = productdetails;
	}

	String product_category = "";
	String url = "";
	String prod_number = "";
	String prod_full_name = "";
	String company_name = "";
	String price = "";
	String prod_attr1 = "";
	String prod_value1 = "";
	String prod_attr2 = "";
	String prod_value2 = "";
	String prod_attr3 = "";
	String prod_value3 = "";
	String prod_attr4 = "";
	String prod_value4 = "";
	String prod_attr5 = "";
	String prod_value5 = "";
	String prod_desc1 = "";
	public String getProd_desc1() {
		return prod_desc1;
	}


	public void setProd_desc1(String prod_desc1) {
		this.prod_desc1 = setValues(prod_desc1);
	}


	public String getProd_desc2() {
		return prod_desc2;
	}


	public void setProd_desc2(String prod_desc2) {
		this.prod_desc2 = setValues(prod_desc2);
	}


	public String getProd_desc3() {
		return prod_desc3;
	}


	public void setProd_desc3(String prod_desc3) {
		this.prod_desc3 = setValues(prod_desc3);
	}


	public String getProd_desc4() {
		return prod_desc4;
	}


	public void setProd_desc4(String prod_desc4) {
		this.prod_desc4 = setValues(prod_desc4);
	}


	public String getProd_desc5() {
		return prod_desc5;
	}


	public void setProd_desc5(String prod_desc5) {
		this.prod_desc5 = setValues(prod_desc5);
	}

	String prod_desc2 = "";
	String prod_desc3 = "";
	String prod_desc4 = "";
	String prod_desc5 = "";
	String prod_desc_long = "";
	
	ArrayList productdetails = new ArrayList();
	
	public String toString()
	{
		String retValue = "";
		
		retValue = "url:" + url + ", product_category:" + product_category + ", prod_number:" + prod_number + ", prod_full_name:" + prod_full_name + ", company_name:" + company_name + 
				", price:" + price + ", prod_attr1:" + prod_attr1 + ", prod_value1:" + prod_value1 + ", prod_attr2:" + prod_attr2 + 
				", prod_value2:" + prod_value2 + ", prod_attr3:" + prod_attr3 + ", prod_value3:" + prod_value3 + ", prod_attr4:" + prod_attr4 +
				", prod_value4:" + prod_value4 + ", prod_attr5:" + prod_attr5 + ", prod_value5:" + prod_value5 + ", prod_desc_long:" + prod_desc_long + " ";
		
		for(int i=0; i<productdetails.size(); i++)
		{
			NonMedicinesDetail productdetail = (NonMedicinesDetail) productdetails.get(i);
			retValue += "\n[" + i + "] prod_dtl_type:" + productdetail.getProd_dtl_type() + ", prod_dtl_name:" + productdetail.getProd_dtl_name() + ", prod_dtl_value:" + productdetail.getProd_dtl_value() + " ";
		}
		
		return retValue;
	}

	
	public static void updateDatabase(Logger logger, Connection mycon, NonMedicines nonmedicines)
	{
       	PreparedStatement myst = null;
       	PreparedStatement myst_insert = null;
       	PreparedStatement myst_insert_det = null;
       	ResultSet myrs = null;
       	int insResult = 0;
       	try
       	{
 			String mysql = "insert into non_medicines_dump(product_category, url, prod_number, prod_full_name, company_name, price, "
					+ "prod_attr1, prod_value1, prod_attr2, prod_value2, prod_attr3, prod_value3, prod_attr4, prod_value4 ,prod_attr5, prod_value5, prod_desc1, prod_desc2, prod_desc3, prod_desc4, prod_desc5, prod_desc_long) " +
					" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			myst_insert = mycon.prepareStatement(mysql);	
			myst_insert.setString(1,  nonmedicines.getProduct_category());
			myst_insert.setString(2, nonmedicines.getUrl());
			myst_insert.setString(3, nonmedicines.getProd_number());
			myst_insert.setString(4, nonmedicines.getProd_full_name());
			myst_insert.setString(5, nonmedicines.getCompany_name());
			myst_insert.setString(6, nonmedicines.getPrice());
			myst_insert.setString(7, nonmedicines.getProd_attr1());
			myst_insert.setString(8, nonmedicines.getProd_value1());
			myst_insert.setString(9, nonmedicines.getProd_attr2());
			myst_insert.setString(10, nonmedicines.getProd_value2());
			myst_insert.setString(11, nonmedicines.getProd_attr3());
			myst_insert.setString(12, nonmedicines.getProd_value3());
			myst_insert.setString(13, nonmedicines.getProd_attr4());
			myst_insert.setString(14, nonmedicines.getProd_value4());
			myst_insert.setString(15, nonmedicines.getProd_attr5());
			myst_insert.setString(16, nonmedicines.getProd_value5());
			myst_insert.setString(17, nonmedicines.getProd_desc1());
			myst_insert.setString(18, nonmedicines.getProd_desc2());
			myst_insert.setString(19, nonmedicines.getProd_desc3());
			myst_insert.setString(20, nonmedicines.getProd_desc4());
			myst_insert.setString(21, nonmedicines.getProd_desc5());
			myst_insert.setString(22, nonmedicines.getProd_desc_long());
       		
       		insResult = myst_insert.executeUpdate();
       		System.out.println("insert non_medicines_dump:" + insResult);
       		
       		for(int i=0; i< nonmedicines.getProductdetails().size(); i++)
       		{
       			mysql = "insert into non_medicines_detail_dump(prod_number, prod_dtl_type, prod_dtl_name, prod_dtl_value) " +
    					" values(?, ?, ?, ?)";
    			NonMedicinesDetail nonmed_det = (NonMedicinesDetail) nonmedicines.getProductdetails().get(i);
    			myst_insert_det = mycon.prepareStatement(mysql);	
    			myst_insert_det.setString(1,  nonmed_det.getProd_number());
    			myst_insert_det.setString(2, nonmed_det.getProd_dtl_type());
    			myst_insert_det.setString(3, nonmed_det.getProd_dtl_name());
    			myst_insert_det.setString(4, nonmed_det.getProd_dtl_value());
           		
           		insResult = myst_insert_det.executeUpdate();
           		System.out.println("insert non_medicines_detail_dump:" + insResult);
       		}
       		
       		mycon.commit();
       	}
       	catch (Exception e)
       	{
       		try
       		{
       			mycon.rollback();
       		}
       		catch(Exception r)
       		{
       		}
       		logger.info(nonmedicines.toString());
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

	public static void updateDatabase(Logger logger, Connection mycon, int medIndex, String prodCateogry, String prodUrl, String ex)
	{
       	PreparedStatement myst = null;
       	PreparedStatement myst_insert = null;
       	int insResult = 0;
       	try
       	{
 			String mysql = "insert into non_medicines_missing_dump(prod_number, prod_category, prod_url, prod_exception) " +
					" values(?, ?, ?, ?)";
			
			myst_insert = mycon.prepareStatement(mysql);	
			myst_insert.setInt(1,  medIndex);
			myst_insert.setString(2, prodCateogry);
			myst_insert.setString(3, prodUrl);
			myst_insert.setString(4, ex);
       		
       		insResult = myst_insert.executeUpdate();
       		System.out.println("insert non_medicines_missing_dump:" + insResult);
       		
       		mycon.commit();
       	}
       	catch (Exception e)
       	{
       		try
       		{
       			mycon.rollback();
       		}
       		catch(Exception r)
       		{
       		}
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

	
	public String setValues(String temp)
	{
		String newtemp = (temp == null)?"":temp.trim();
		
		return newtemp;
	}
}
