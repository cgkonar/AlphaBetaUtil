package com.emediplus;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.emediplus.dto.Providers;
import com.emediplus.dto.ProvidersCat;
import com.emediplus.util.ConnectionOBJ;


public class DownloadImage {

	public static void main(String[] args) {
		try
		{
			int totalProducts = 0;
			int totalPages = 0;
			int perPage = 48;

/*			String str = "Showing 1 - 24 of 146 results";
			
			Pattern pattern = Pattern.compile(".*of(.*)results");
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				totalProducts = Integer.parseInt(matcher.group(1).trim());
			    System.out.println("totalProducts:" + totalProducts);
			    totalPages = (totalProducts/perPage);
			    if((totalProducts % perPage) > 0)
			    	totalPages++;
			    System.out.println("totalPages:" + totalPages);
			}
*/
			String str = "9999a";
			Pattern pattern = Pattern.compile("[a-zA-Z](.*)");
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				System.out.println("str:" + str + " found");
			}
			else
			{
				System.out.println("str:" + str + " not found");
			}
			
/*			String[] prodCategory = new String[100];
			String[] prodCategoryURL = new String[100];
          	
			try
			{
				FileInputStream fstream = new FileInputStream("healthkart.txt");
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				int    lineNo = 0;
				while ((strLine = br.readLine()) != null)   {
					prodCategory[lineNo] = strLine.substring(0, strLine.indexOf(','));
					prodCategoryURL[lineNo] = strLine.substring(strLine.indexOf(',')+1, strLine.length());
					System.out.println ("cat:" + prodCategory[lineNo] + " url:" + prodCategoryURL[lineNo]);
				}
				//Close the input stream
				in.close();
			}catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
		    }
*/			
			/*
			String[] images = {"http://imshopping.rediff.com/imgshop/180-180/shopping/pixs/12564/f/Fa_12264425._fair--lovely-advanced-multivitamin-total-clear.jpg", "http://imshopping.rediff.com/imgshop/150-150/shopping/pixs/4764/p/Purifying_Cleanser._kaya-purifying-cleanser.JPG", "http://imshopping.rediff.com/imgshop/180-180/shopping/pixs/11150/s/stives._st-ives-invigorating-apricot-scrub-tub-283gms.jpg"};
			for(int i=0; i<images.length; i++)
			{
				System.out.println("images:" + images[i]);
				
				URL url = new URL(images[i]);
				InputStream in = new BufferedInputStream(url.openStream());
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
				
				FileOutputStream fos = new FileOutputStream("./tmp/images/" + i + ".jpg");
				fos.write(response);
				fos.close();
			} */
		}
		catch(Exception e)
		{
			System.out.println("exception:" + e);
		}
		finally
		{
		}
	}
}
