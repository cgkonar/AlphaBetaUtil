import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JustDialGatherChemists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try
		{
			int	rowctr = 1;
			
			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\Chemists_in_Pune.html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

			Elements lists = doc.select("span.jcn a");
			Elements links = doc.select("a[href]");
	        Elements media = doc.select("[src]");
	        Elements imports = doc.select("link[href]");

	        print("\nlists: (%d)", lists.size());
           	for (Element list : lists) {
                if(rowctr == 1)
                {
	           		System.out.println("rowno:" + rowctr);
	        		print(" * a: <%s>  (%s)", list.attr("href"), trim(list.text(), 35));
	        		// fetch Detail Info
	        		fetchDetailInfo(list.attr("href"));
	            
	        		rowctr++;
                }
	        }

	        /*
	        print("\nMedia: (%d)", media.size());
	        for (Element src : media) {
	            if (src.tagName().equals("img"))
	                print(" * %s: <%s> %sx%s (%s)",
	                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
	                        trim(src.attr("alt"), 20));
	            else
	                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
	        }

	        print("\nImports: (%d)", imports.size());
	        for (Element link : imports) {
	            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
	        }

	        print("\nLinks: (%d)", links.size());
	        for (Element link : links) {
	            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
	        }
	        */
		}
		catch(Exception e)
		{
			System.out.println("exception:" + e);
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
	
	private static void fetchDetailInfo(String url)
	{
		try
		{
			System.out.println("Inside fetchDetailsInfo");
			
			File input = new File("C:\\Personal\\PHP\\DataGather\\tmp\\Chemist_moreinfo.html");
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

//			System.out.println(url + "/?tab=moreinfo");
//			Document doc = Jsoup.connect(url + "/?tab=moreinfo").get();

			System.out.println(doc);
			
//			Elements address = doc.select("div");
			Elements address = doc.select("section.dtcont aside.continfo p");
	        for (Element add : address) {
	            print(" address: %s", add.text());
	        }

		}
		catch(Exception e)
		{
			System.out.print("fetchDetailInfo:" + e);
		}
	}
	    
}
