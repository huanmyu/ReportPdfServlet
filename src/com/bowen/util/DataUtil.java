package com.bowen.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DataUtil { 
	 public static String formatFloatNumber(double value) {
	        if(value != 0.00){
	            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
	            return df.format(value);
	        }else{
	            return "0.00";
	        }

	    }
		 
		 public static String getNumKb(Number number){  
		    NumberFormat formatter = new DecimalFormat("#,###");  
		    return formatter.format(number);  
		}  
		 
		 public static String getNumKb(Number number, Integer decimalNumber){ 
			 String format = "#,###";
			 if(decimalNumber != null && decimalNumber > 0){
				 format += ".";
				 while(decimalNumber-->0) {
					 format += "#";
				 }
			 }
		    NumberFormat formatter = new DecimalFormat(format);  
		    return formatter.format(number);  
		} 
		 
		 public static boolean isNumeric(String str){ 
			 Pattern pattern = Pattern.compile("[0-9]+"); 
			   Matcher isNum = pattern.matcher(str);
			   if( !isNum.matches() ){
			       return false; 
			   } 
			   return true; 
			}
}
