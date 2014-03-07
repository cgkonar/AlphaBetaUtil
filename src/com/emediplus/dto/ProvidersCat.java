package com.emediplus.dto;
import java.util.ArrayList;


public class ProvidersCat {
	String category_id = "";
	String category_desc = "";

	public String getCategory_desc() {
		return category_desc;
	}

	public void setCategory_desc(String category_desc) {
		this.category_desc = (category_desc == null)?"":category_desc.trim();
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
}
