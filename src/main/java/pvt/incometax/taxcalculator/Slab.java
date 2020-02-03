/**
 * 
 */
package pvt.incometax.taxcalculator;

/**
 * @author Sahil Jain
 *
 */
public class Slab {
	public static final float CESS = 1.04F;
	private int slabAmount;
	private float oldTaxPercent;
	private float newTaxPercent;
	private String strMsg;

	public Slab(int slabAmount, float oldTaxPercent, float newTaxPercent, String strMsg) {
		this.slabAmount = slabAmount;
		this.oldTaxPercent = Float.valueOf(Utility.DECIMAL_FORMAT.format(oldTaxPercent * CESS));
		this.newTaxPercent = Float.valueOf(Utility.DECIMAL_FORMAT.format(newTaxPercent * CESS));
		this.strMsg = strMsg;
	}

	/**
	 * @return the strMsg
	 */
	public String getStrMsg() {
		return strMsg;
	}

	/**
	 * @return the slabAmount
	 */
	public int getSlabAmount() {
		return slabAmount;
	}

	/**
	 * @return the oldTaxPercent
	 */
	public float getOldTaxPercent() {
		return oldTaxPercent;
	}

	/**
	 * @return the newTaxPercent
	 */
	public float getNewTaxPercent() {
		return newTaxPercent;
	}
}
