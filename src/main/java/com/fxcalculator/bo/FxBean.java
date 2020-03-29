/**
 * 
 */
package com.fxcalculator.bo;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxcalculator.FxCalculatorAppApplication;
import com.fxcalculator.util.Helper;

/**
 * @author Vishnukanth
 *
 */
public class FxBean {

	String baseCurrency;
	String termCurrency;
	Double baseAmt;
	Double termAmt;
/*	String strBaseAmt;
	String strTermAmt;*/

	private static final Logger logger = LoggerFactory.getLogger(FxCalculatorAppApplication.class);
	
	/**
	 * @param baseCurrency
	 * @param termCurrency
	 * @param baseAmt
	 */
	public FxBean(String baseCurrency, String termCurrency, Double baseAmt) {
		super();
		this.baseCurrency = baseCurrency;
		this.termCurrency = termCurrency;
		this.baseAmt = baseAmt;
	}

	/**
	 * @return the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}
	/**
	 * @param baseCurrency the baseCurrency to set
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	/**
	 * @return the termCurrency
	 */
	public String getTermCurrency() {
		return termCurrency;
	}
	/**
	 * @param termCurrency the termCurrency to set
	 */
	public void setTermCurrency(String termCurrency) {
		this.termCurrency = termCurrency;
	}
	/**
	 * @return the baseAmt
	 */
	public Double getBaseAmt() {
		return baseAmt;
	}
	/**
	 * @param baseAmt the baseAmt to set
	 */
	public void setBaseAmt(Double baseAmt) {
		this.baseAmt = baseAmt;
	}
	/**
	 * @return the termAmt
	 */
	public Double getTermAmt() {
		return termAmt;
	}
	/**
	 * @param termAmt the termAmt to set
	 */
	public void setTermAmt(Double termAmt) {
		this.termAmt = termAmt;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseAmt == null) ? 0 : baseAmt.hashCode());
		result = prime * result + ((baseCurrency == null) ? 0 : baseCurrency.hashCode());
		result = prime * result + ((termAmt == null) ? 0 : termAmt.hashCode());
		result = prime * result + ((termCurrency == null) ? 0 : termCurrency.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FxBean other = (FxBean) obj;
		if (baseAmt == null) {
			if (other.baseAmt != null)
				return false;
		} else if (!baseAmt.equals(other.baseAmt))
			return false;
		if (baseCurrency == null) {
			if (other.baseCurrency != null)
				return false;
		} else if (!baseCurrency.equals(other.baseCurrency))
			return false;
		if (termAmt == null) {
			if (other.termAmt != null)
				return false;
		} else if (!termAmt.equals(other.termAmt))
			return false;
		if (termCurrency == null) {
			if (other.termCurrency != null)
				return false;
		} else if (!termCurrency.equals(other.termCurrency))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FxBean [baseCurrency=" + baseCurrency + ", termCurrency=" + termCurrency + ", baseAmt=" + baseAmt
				+ ", termAmt=" + termAmt + "]";
	}
	
	/**
	 * Method to convert currencies depending on rate value and D or Inv key from the matrix
	 * @param currencyPair
	 * @param amt
	 * @param directOrInv
	 * @return
	 */
	private double convertCurrency(String currencyPair, double amt, String directOrInv) {

		double rate = Helper.currencyPairRates.get(currencyPair);
		double convAmt = 0.00;

		if (directOrInv.equals("D")) {

			convAmt = rate * amt;

		}else if (directOrInv.equals("Inv")) {

			convAmt = amt/ rate;
		}

		return convAmt;
	}
	
	/**
	 * Method to recursively calculate if the Base and Term currency are meeting the CROSS scenario
	 * @param amt
	 * @param baseCurrency
	 * @param termCurrency
	 * @param matrixValue
	 * @return
	 */
	private double recursiveCalculator(double amt, String baseCurrency, String termCurrency, String matrixValue) {
		
		
		if (matrixValue.equals("D") || matrixValue.equals("Inv")) {
			
			String currencyPair = "";
			
			if(matrixValue.equals("D")) 
				currencyPair = baseCurrency.concat(termCurrency);
			else if(matrixValue.equals("Inv"))
				currencyPair = termCurrency.concat(baseCurrency);
			
			amt = convertCurrency(currencyPair,amt,matrixValue);

		}else {
			
			int baseIndex = Helper.baseTermCurrencies.indexOf(baseCurrency);
			int newMidTermIndex1 = Helper.baseTermCurrencies.indexOf(matrixValue);
			
			String newMatrixValue1 = Helper.crossViaMatrix[baseIndex][newMidTermIndex1];
			
			amt = recursiveCalculator(amt, baseCurrency, matrixValue, newMatrixValue1);
			
			int termIndex = Helper.baseTermCurrencies.indexOf(termCurrency);
			String newMatrixValue2 = Helper.crossViaMatrix[newMidTermIndex1][termIndex];
			
			amt = recursiveCalculator(amt, matrixValue,termCurrency,newMatrixValue2);
		}
		
		return amt;
	}
	
	/**
	 * Method calculates the Fx based on cross matrix value
	 * @return
	 */
	public FxBean calculateFx() throws Exception {

		DecimalFormat df = new DecimalFormat("#.##");   
		df.setRoundingMode(RoundingMode.DOWN);

		int baseIndex = Helper.baseTermCurrencies.indexOf(this.getBaseCurrency()); 
		int termIndex = Helper.baseTermCurrencies.indexOf(this.getTermCurrency());

		double calcAmt = 0.0;

		String matrixValue = Helper.crossViaMatrix[baseIndex][termIndex];

		logger.debug("based Index = "+baseIndex+ " , term Index = "+termIndex+" , matrix Value = "+matrixValue);

		if (matrixValue.equals("1:1")) {
			
			calcAmt = this.getBaseAmt();

		}else {

			calcAmt = recursiveCalculator(this.getBaseAmt(), this.getBaseCurrency(), this.getTermCurrency(), matrixValue);
		}
		
		this.termAmt = calcAmt;
							
		return this;
	}
	

}
