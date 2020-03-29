/**
 * 
 */
package com.fxcalculator.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to store the given inputs into appropriate data structures
 * @author Vishnukanth
 *
 */
public final class Helper {
	
	// Cross via matrix values are decided to be stored in an 2-D array. For complex operations Arrays are light weight and efficient compared Collections 
	public static final String[][] crossViaMatrix = {
			{"1:1","USD","USD","USD","USD","USD","USD","USD","USD","USD","D"},
			{"USD","1:1","USD","USD","USD","USD","USD","USD","USD","USD","D"},
			{"USD","USD","1:1","USD","USD","USD","USD","USD","USD","USD","D"},
			{"USD","USD","USD","1:1","EUR","Inv","USD","USD","EUR","USD","EUR"},
			{"USD","USD","USD","EUR","1:1","Inv","USD","USD","EUR","USD","EUR"},
			{"USD","USD","USD","D","D","1:1","USD","USD","D","USD","D"},
			{"USD","USD","USD","USD","USD","USD","1:1","USD","USD","USD","D"},
			{"USD","USD","USD","USD","USD","USD","USD","1:1","USD","USD","Inv"},
			{"USD","USD","USD","EUR","EUR","Inv","USD","USD","1:1","USD","EUR"},
			{"USD","USD","USD","USD","USD","USD","USD","USD","USD","1:1","D"},
			{"Inv","Inv","Inv","EUR","EUR","Inv","Inv","D","EUR","Inv","1:1"},
	};
	
	// Base/ Term currencies are stored in ArrayList as order of currency insertion is required to be retrieved during business runs
	public static final List<String> baseTermCurrencies = new ArrayList<> (Arrays.asList(
			"AUD",
			"CAD",
			"CNY",
			"CZK",
			"DKK",
			"EUR",
			"GBP",
			"JPY",
			"NOK",
			"NZD",
			"USD"));
	
	public static final Map<String,Integer> decimals = new HashMap<> (); 
		static {
			decimals.put("AUD",2);
			decimals.put("CAD",2);
			decimals.put("CNY",2);
			decimals.put("CZK",2);
			decimals.put("DKK",2);
			decimals.put("EUR",2);
			decimals.put("GBP",2);
			decimals.put("JPY",0);
			decimals.put("NOK",2);
			decimals.put("NZD",2);
			decimals.put("USD",2);
		};

	public static final Map<String,Double> currencyPairRates = new HashMap<> (); 
		static {
			currencyPairRates.put("AUDUSD",0.8371);
			currencyPairRates.put("CADUSD",0.8711);
			currencyPairRates.put("USDCNY",6.1715);
			currencyPairRates.put("EURUSD",1.2315);
			currencyPairRates.put("GBPUSD",1.5683);
			currencyPairRates.put("NZDUSD",0.7750);
			currencyPairRates.put("USDJPY",119.95);
			currencyPairRates.put("EURCZK",27.6028);
			currencyPairRates.put("EURDKK",7.4405);
			currencyPairRates.put("EURNOK",8.6651);
		};
		
		/**
		 * Method to format a Double value into an String
		 * @param decimals
		 * @param amt
		 * @return
		 */
		public static String formatDoubleCurrencyToString(int decimals, double amt) {
			
			String decimalPattern = "0.";
			String strAmt = "";
			
			if(decimals>0) {

				for (int i =0 ; i < decimals; ++i) { // generate pattern at runtime
					decimalPattern += "0";
				}
				
				strAmt = new DecimalFormat(decimalPattern).format(amt);

			}else if (decimals == 0) {
				strAmt = new DecimalFormat("#").format(amt);
			}
			return strAmt;
		}	
		
}
