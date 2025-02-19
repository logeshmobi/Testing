package com.mobiversa.payment.util;

import org.springframework.web.util.HtmlUtils;

public class MobiHtmlUtils {
	
	/*public final static String tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";

    public final static String tagEnd = "\\</\\w+\\>";

    public final static String tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";

    public final static String htmlEntity = "&[a-zA-Z][a-zA-Z0-9]+;";

    public final static Pattern htmlPattern = Pattern.compile("(" + tagStart + ".*" + tagEnd + ")|(" + tagSelfClosing
            + ")|(" + htmlEntity + ")", Pattern.DOTALL);*/

   /* public static boolean isHtml(String s) {
        boolean ret = false;
        if (s != null) {
            ret = htmlPattern.matcher(s).find();
        }
        return ret;
    }*/
    
    public static boolean isHtml(String input) {
        boolean isHtml = false;
        input = input.replace("&", "");
        input = input.replace("'", "");
        
        if (input != null&& !input.equals("null")) {
        	
        	 System.out.println(" data : "+input);
        	
            if (!input.equals(HtmlUtils.htmlEscape(input))) {
                isHtml = true;
            }
        }
        
        
        return isHtml;
    }
    
    public static boolean isHtmlIs(Object in) {
        String input = String.valueOf(in);
        
        input = input.replace("&", "");
       // input = input.replace("\", "");
        input = input.replace("'", "");
        
        boolean isHtml = false;
        if (input != null && !input.equals("null")) {
            System.out.println(" data : "+input);
            if (!input.equals(HtmlUtils.htmlEscape(input))) {
                isHtml = true;
            }
        }
        return isHtml;
    }

}
