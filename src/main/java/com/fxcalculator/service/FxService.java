/**
 * 
 */
package com.fxcalculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxcalculator.bo.FxBean;
import com.fxcalculator.util.Helper;

/**
 * SpringBoot Service class where Fx Conversion validations are coded  
 * @author Vishnukanth
 *
 */
@Service
public class FxService {
	
	private static final Logger logger = LoggerFactory.getLogger(FxService.class);
		
	/**
	 * Method to validates the user Fx queries for the following,
	 * 		a. If the number of tokens is not 4 (i.e, an ideal query like *AUD 1 in USD* contains only 4 tokens)
	 * 		b. If the length of currencies is not 3 
	 * 		c. If the 3rd token is not 3
	 * 		d. If the amount is not Double parseable 
	 * 
	 * @param fxConvQuery
	 * @return
	 * @throws Exception
	 */
	public String validateFxConversionQuery(String fxConvQuery) throws Exception{
		
		logger.debug("validateFxConversionQuery() of FxService ");
		
		String[] queryTokens = fxConvQuery.split(" ");
		boolean isValid = true;
		String response = "failed";
		
		if(queryTokens.length != 4) {
			isValid = false;
		}else if (queryTokens[0].length() != 3 || queryTokens[3].length() != 3) {
			isValid = false;
		}else if (!queryTokens[2].equalsIgnoreCase("in")) {
			isValid = false;
		}else {
			
			try {
				Double.parseDouble(queryTokens[1]);
			}catch(NumberFormatException e) {
				isValid = false;
			}
		}
		
		if(isValid)
			response = "success";
				
		return response;
	}
	
	/**
	 * Method to check if the given currency in the Query is supported for Fx Conversion
	 * @param fxConvQuery
	 * @return
	 * @throws Exception
	 */
	public String checkCurrencyAvailability(String fxConvQuery) throws Exception{
		
		logger.debug("checkCurrencyAvailability() of FxService ");
		
		String[] queryTokens = fxConvQuery.split(" ");
		String response = " ";
		
		boolean isBaseFound = Helper.baseTermCurrencies.contains(queryTokens[0]); 
		boolean isTermFound = Helper.baseTermCurrencies.contains(queryTokens[3]);
		
		if (!(isBaseFound)  || !(isTermFound))
			response = "Unable to find rate for "+queryTokens[0]+"/"+queryTokens[3];
		
		return response;
	}
	
	/**
	 * Method to trigger Fx calculator of FxBean
	 * @param fxConvQuery
	 * @throws Exception
	 */
	public FxBean calculateFx(String fxConvQuery) throws Exception {
		
		logger.debug("calculateFx() of FxService ");
		
		String[] queryTokens = fxConvQuery.split(" ");
		
		FxBean fxBeanObj = new FxBean(queryTokens[0],queryTokens[3],Double.parseDouble(queryTokens[1]));
		
		return fxBeanObj.calculateFx();
		
	}
	
	public String[] convertCurrencyDoubletoString(FxBean fxBeanObj) throws Exception{
		
		logger.debug("convertCurrencyDoubletoString() of FxService ");
		
		String[] strAmts = new String[2];
		
		int baseDecimal = Helper.decimals.get(fxBeanObj.getBaseCurrency());
		int termDecimal = Helper.decimals.get(fxBeanObj.getTermCurrency());
		
		strAmts[0] = Helper.formatDoubleCurrencyToString(baseDecimal,fxBeanObj.getBaseAmt());
		strAmts[1] = Helper.formatDoubleCurrencyToString(termDecimal,fxBeanObj.getTermAmt());
		
		return strAmts;
	}
		
}
