package com.fxcalculator;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fxcalculator.bo.FxBean;
import com.fxcalculator.service.FxService;

/**
 * SpringBootApplication triggering class. The application is built to run in command line by implementing CommandLineRunner and overriding the run method.
 * @author Vishnukanth
 *
 */

@SpringBootApplication
public class FxCalculatorAppApplication implements CommandLineRunner {

	@Autowired
    private FxService fxService;
	
	private static final Logger logger = LoggerFactory.getLogger(FxCalculatorAppApplication.class);
	
	public static void main(String[] args) throws Exception{
		
		SpringApplication app = new SpringApplication(FxCalculatorAppApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
				
		logger.debug("run() of main class ==> FxCalculatorAppApplication ");
				
		System.out.println("<======= Fx Calculator Application =======>");
		System.out.println("\nEnter the Fx Conversion Query in this format, <ccy1> <amount1> in <ccy2>. E.g: AUD 100.00 in USD :");		
		
		Scanner in = new Scanner(System.in);
        String fxQuery = in.nextLine();
        
        if (fxQuery != null && !fxQuery.trim().isEmpty()) {
        	
        	String newQuery = fxQuery.trim().toUpperCase();
        	
        	String queryValidationResponse = fxService.validateFxConversionQuery(newQuery);
        	String currencyAvailabilityResponse = fxService.checkCurrencyAvailability(newQuery);
        	
        	if((currencyAvailabilityResponse != null && currencyAvailabilityResponse.trim().length() == 0 ) && queryValidationResponse.equals("success")){
        		
        		FxBean fxBeanObj = fxService.calculateFx(newQuery);

        		String[] strAmts = fxService.convertCurrencyDoubletoString(fxBeanObj);
        		
        		System.out.println("\n" +fxBeanObj.getBaseCurrency() +" "+strAmts[0]+ " = " +fxBeanObj.getTermCurrency()+ " "+strAmts[1]);
        		
        	}else if (currencyAvailabilityResponse != null && currencyAvailabilityResponse.trim().length() > 0 ) {
        	
        		System.out.println(currencyAvailabilityResponse);
        		
        	}else if(queryValidationResponse.equals("failed")) {
        		System.out.println("Validation failed, given input is not valid");
        	}
        			
        }else {
        	System.out.println("Input is empty. Please check and re-enter the input");
        }
        in.close();
    }
}
