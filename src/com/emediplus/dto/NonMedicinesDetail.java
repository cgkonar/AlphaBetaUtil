package com.emediplus.dto;

public class NonMedicinesDetail {
	String prod_number = "";
	public String getProd_number() {
		return prod_number;
	}

	public void setProd_number(String prod_number) {
		this.prod_number = setValues(prod_number);
	}

	public String getProd_dtl_type() {
		return prod_dtl_type;
	}

	public void setProd_dtl_type(String prod_dtl_type) {
		this.prod_dtl_type = setValues(prod_dtl_type);
	}

	public String getProd_dtl_name() {
		return prod_dtl_name;
	}

	public void setProd_dtl_name(String prod_dtl_name) {
		this.prod_dtl_name = setValues(prod_dtl_name);
	}

	public String getProd_dtl_value() {
		return prod_dtl_value;
	}

	public void setProd_dtl_value(String prod_dtl_value) {
		this.prod_dtl_value = setValues(prod_dtl_value);
	}

	String prod_dtl_type = "";
	String prod_dtl_name = "";
	String prod_dtl_value = "";
	
	public String setValues(String temp)
	{
		String newtemp = (temp == null)?"":temp.trim();
		
		return newtemp;
	}
}
