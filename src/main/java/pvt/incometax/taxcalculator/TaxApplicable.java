/**
 * 
 */
package pvt.incometax.taxcalculator;

/**
 * @author Sahil Jain
 *
 */
public class TaxApplicable {
	private float oldTaxAmount;
	private float newTaxAmount;

	private int taxableIncomeOld;
	private int taxableIncomeNew;

	public TaxApplicable(int taxableIncomeOld, int taxableIncomeNew) {
		this.oldTaxAmount = 0F;
		this.newTaxAmount = 0F;
		this.taxableIncomeOld = taxableIncomeOld;
		this.taxableIncomeNew = taxableIncomeNew;
	}

	/**
	 * @return the taxableIncomeOld
	 */
	public int getTaxableIncomeOld() {
		return taxableIncomeOld;
	}

	/**
	 * @param taxableIncomeOld the taxableIncomeOld to set
	 */
	public void setTaxableIncomeOld(int taxableIncomeOld) {
		this.taxableIncomeOld = taxableIncomeOld;
	}

	/**
	 * @return the taxableIncomeNew
	 */
	public int getTaxableIncomeNew() {
		return taxableIncomeNew;
	}

	/**
	 * @param taxableIncomeNew the taxableIncomeNew to set
	 */
	public void setTaxableIncomeNew(int taxableIncomeNew) {
		this.taxableIncomeNew = taxableIncomeNew;
	}

	/**
	 * @return the oldTaxAmount
	 */
	public float getOldTaxAmount() {
		return oldTaxAmount;
	}

	public float updateOldTaxAmount(float addAmount) {
		oldTaxAmount += addAmount;
		return oldTaxAmount;
	}

	/**
	 * @return the newTaxAmount
	 */
	public float getNewTaxAmount() {
		return newTaxAmount;
	}

	public float updateNewTaxAmount(float addAmount) {
		newTaxAmount += addAmount;
		return newTaxAmount;
	}
}
