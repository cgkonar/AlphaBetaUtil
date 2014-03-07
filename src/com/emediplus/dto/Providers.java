package com.emediplus.dto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Providers {
	public String getProvider_type() {
		return provider_type;
	}

	public void setProvider_type(String provider_type) {
		this.provider_type = provider_type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = (url == null)?"":url.trim();
	}

	public String getProvider_name() {
		return provider_name;
	}

	public void setProvider_name(String provider_name) {
		this.provider_name = (provider_name == null)?"":provider_name.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = (mobile == null)?"":mobile.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = (phone == null)?"":phone.trim();
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = (contact_person == null)?"":contact_person.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = (area == null)?"":area.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = (address == null)?"":address.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = (city == null)?"":city.trim();
	}

	public String getPin_code() {
		return pin_code;
	}

	public void setPin_code(String pin_code) {
		this.pin_code = (pin_code == null)?"":pin_code.trim();
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = (landmark == null)?"":landmark.trim();
	}

	public String getWorking_hours() {
		return working_hours;
	}

	public void setWorking_hours(String working_hours) {
		this.working_hours = (working_hours == null)?"":working_hours.trim();
	}

	public String toString()
	{
		String retValue = "";
		
		retValue = "provider_type:" + provider_type + ", url:" + url + ", provider_name:" + provider_name + ", website:" + website + ", mobile:" + mobile + ", phone:" + phone + ", contact_person:" + contact_person + ", area:" + area + ", address:" + address + ", city:" + city + ", pin_code:" + pin_code + ", landmark:" + landmark + ", working_hours:" + working_hours + ", listed_categories:" + listed_categories;
		
		return retValue;
	}
	
	
	String provider_type = "C";
	String url = "";
	String provider_name = "";
	String website = "";
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = (website == null)?"":website.trim();
	}


	String mobile = "";
	String phone = "";
	String contact_person = "";
	String area = "";
	String address = "";
	String city = "";
	String pin_code = "";
	String landmark = "";
	String working_hours = "";
	String listed_categories = "";
	
	public String getListed_categories() {
		return listed_categories;
	}

	public void setListed_categories(String listed_categories) {
		this.listed_categories = (listed_categories == null)?"":listed_categories.trim();
	}
	
	
	public static void updateDatabase(Logger logger, Connection mycon, Providers providers)
	{
       	PreparedStatement myst = null;
       	PreparedStatement myst_insert = null;
       	ResultSet myrs = null;
       	boolean rowfound = false;
       	int insResult = 0;
       	try
       	{
       		String mysql = "select count(*) from providers where lower(trim(url)) like '%" + providers.getUrl().trim().toLowerCase() + "%'";
       		myst = mycon.prepareStatement(mysql);	
       		myrs = myst.executeQuery();
			while(myrs.next())
			{
				if(myrs.getInt(1) > 0)
					rowfound = true;
	       		
			}

			if(rowfound)
			{
				logger.info("SQL:" + mysql);
				logger.info(providers.getProvider_name() + " : found : " + rowfound);
			}
			
			if(!rowfound)
			{
				mysql = "insert into providers(url, provider_type, provider_name, website, mobile, phone, contact_person, area, address, city, pin_code, landmark, working_hours, listed_categories) " +
						" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				myst_insert = mycon.prepareStatement(mysql);	
				myst_insert.setString(1,  providers.getUrl());
				myst_insert.setString(2, providers.getProvider_type());
				myst_insert.setString(3, providers.getProvider_name());
				myst_insert.setString(4, providers.getWebsite());
				myst_insert.setString(5, providers.getMobile());
				myst_insert.setString(6, providers.getPhone());
				myst_insert.setString(7, providers.getContact_person());
				myst_insert.setString(8, providers.getArea());
	       		myst_insert.setString(9, providers.getAddress());
	       		myst_insert.setString(10, providers.getCity());
	       		myst_insert.setString(11, providers.getPin_code());
	       		myst_insert.setString(12, providers.getLandmark());
	       		myst_insert.setString(13, providers.getWorking_hours());
	       		myst_insert.setString(14, providers.getListed_categories());
	       		
	       		insResult = myst_insert.executeUpdate();
	       		
	       		System.out.println("insert Result:" + insResult);
	       		
	       		mycon.commit();
			}
       	}
       	catch (Exception e)
       	{
       		logger.info(providers.toString());
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
}
