package com.fxcalculator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fxcalculator.unit.FxServiceTest;

/**
 * @author Vishnukanth
 *
 */


@SpringBootTest
//@RunWith(SpringRunner.class)
class FxCalculatorAppApplicationTests {
	
	@Autowired
	FxServiceTest fxServiceTest;
	
	@Test
	void contextLoads() throws Exception { 
		
	}
	
}
