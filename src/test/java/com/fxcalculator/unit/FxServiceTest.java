/**
 * 
 */
package com.fxcalculator.unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import com.fxcalculator.bo.FxBean;
import com.fxcalculator.service.FxService;

/**
 * @author Vishnukanth
 *
 */
@Component
@SpringBootTest
public class FxServiceTest {
	
	FxService fxService = new FxService();
	
	/**
	 * Test method for {@link com.fxcalculator.service.FxService#validateFxConversionQuery(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testValidateFxConversionQuery() throws Exception {
		
		assertEquals("failed", fxService.validateFxConversionQuery("AUD  1 in JPY "));
		assertEquals("failed", fxService.validateFxConversionQuery("AUD4  2 in JPYdfd "));
		assertEquals("failed", fxService.validateFxConversionQuery(""));
		assertEquals("failed", fxService.validateFxConversionQuery(","));
		assertEquals("failed", fxService.validateFxConversionQuery("AUD 100 in in USD"));
		assertEquals("failed", fxService.validateFxConversionQuery("AUD 1 for USD"));
		assertEquals("success", fxService.validateFxConversionQuery("AUD 1 in JPY"));
	}

	/**
	 * Test method for {@link com.fxcalculator.service.FxService#checkCurrencyAvailability(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCheckCurrencyAvailability() throws Exception {
		
		assertEquals("Unable to find rate for KRW/FJD", fxService.checkCurrencyAvailability("KRW 1000.00 in FJD"));
		assertEquals("Unable to find rate for XXX/YYY", fxService.checkCurrencyAvailability("XXX 1000.00 in YYY"));
		assertEquals(" ", fxService.checkCurrencyAvailability("AUD 1 in JPY"));
	}

	 
	/**
	 * Test method for {@link com.fxcalculator.service.FxService#calculateFx(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCalculateFx() throws Exception {
		
		FxBean obj1 = new FxBean("AUD", "DKK", 100.00);
		obj1.setTermAmt(505.76066179455944);
		
		assertEquals(obj1, fxService.calculateFx("AUD 100.00 in DKK"));
				
		FxBean obj2 = new FxBean("AUD", "USD", 100.00);
		obj2.setTermAmt(83.71);
		
		assertEquals(obj2, fxService.calculateFx("AUD 100.00 in USD"));
				
		FxBean obj3 = new FxBean("AUD", "JPY", 1.00);
		obj3.setTermAmt(100.410145);
		
		assertEquals(obj3, fxService.calculateFx("AUD 1 in JPY"));
		
	}

	@Test
	public void testConvertCurrencyDoubletoString() throws Exception {
		
		FxBean obj1 = new FxBean("AUD", "DKK", 100.00);
		obj1.setTermAmt(505.76066179455944);
				
		String[] strResults1 = new String[2];
		strResults1[0] = "100.00";
		strResults1[1] = "505.76";		
		
		assertArrayEquals(strResults1, fxService.convertCurrencyDoubletoString(obj1));
		
		FxBean obj2 = new FxBean("AUD", "USD", 100.00);
		obj2.setTermAmt(83.71);
				
		String[] strResults2 = new String[2];
		strResults2[0] = "100.00";
		strResults2[1] = "83.71";		
		
		assertArrayEquals(strResults2, fxService.convertCurrencyDoubletoString(obj2));
		
		FxBean obj3 = new FxBean("AUD", "JPY", 1.00);
		obj3.setTermAmt(100.410145);
				
		String[] strResults3 = new String[2];
		strResults3[0] = "1.00";
		strResults3[1] = "100";		
		
		assertArrayEquals(strResults3, fxService.convertCurrencyDoubletoString(obj3));
	}	
	
}
