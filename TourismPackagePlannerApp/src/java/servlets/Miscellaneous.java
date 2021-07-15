/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lab-DIS
 */
public class Miscellaneous {

    public static long getDateDifference(String departureDate) throws ParseException{
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = formatter.parse(departureDate);
        long difference = Math.abs(today.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        return differenceDates+1;
    }
    
    public static String decimalNormalizer(Double d){
        DecimalFormat df = new DecimalFormat(".##");
        return df.format(d);
    }
    
    public static String currencySymbol(String currency){
        switch(currency){
            case "Euros":
                return "€";
            case "Dollars":
                return "$";
            case "Pounds":
                return "£";
            default:
                return currency;
        }
    }
}
