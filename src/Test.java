import java.util.regex.*;

public class Test {
  public static void main(String[] args) {
    String neg = "-123abc";
    String pos = "123abc";
    String non = "abc123";
        /* I'm not sure if this regex is too verbose, but it should be
         * clear. It checks that the string starts with either a series
         * of one or more digits... OR a negative sign followed by 1 or
         * more digits. Anything can follow the digits. Update as you need
         * for things that should not follow the digits or for floating
         * point numbers.
         */
    /*
    Pattern pattern = Pattern.compile("^(\\d+.*|-\\d+.*)");
    Matcher matcher = pattern.matcher(neg);
    if(matcher.matches()) {
        System.out.println("matches negative number");
    }
    matcher = pattern.matcher(pos);
    if (matcher.matches()) {
        System.out.println("positive matches");
    }
    matcher = pattern.matcher(non);
    if (!matcher.matches()) {
        System.out.println("letters don't match :-)!!!");
    }
    
    
    String input = "9RRRRRR";
    pattern = Pattern.compile("[a-z].*");
    matcher = pattern.matcher(input);
    if(!matcher.matches())
    {
    	pattern = Pattern.compile("[A-Z].*");
    	matcher = pattern.matcher(input);
    	if(!matcher.matches())
    		System.out.println("not matches..." + input);
    }
    
    int    qty = 0;
    String input = "(1 Units in a Strip)";
    input = input.substring(1,input.length()-1);
    Pattern pattern = Pattern.compile("(\\d+)(.*)in a(.*)");
    Matcher matcher = pattern.matcher(input);
    if(matcher.matches())
    {
    	System.out.println("1:" + matcher.group(1).trim());
    	System.out.println("2:" + matcher.group(2).trim());
    	System.out.println("3:" + matcher.group(3).trim());
    	
    	qty = Integer.parseInt(matcher.group(1).trim());
    }
    */
    
    for(int i=1; i<=12; i++)
    {
    	System.out.println("i=" + i + " i%4:" + (i%4) + " 4-(i%4):" + (4-(i%4)));
    }
  }

//Testing comment.
}