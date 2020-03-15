/**
 * 
 */
package pvt.incometax.taxcalculator;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Sahil Jain
 *
 */
public class Utility {
	public enum slabsEnum {
		LAC15, LAC12_5, LAC10, LAC7_5, LAC5, LAC2_5
	}

	public static final int LAC15 = 1500000;
	public static final int LAC12_5 = 1250000;
	public static final int LAC10 = 1000000;
	public static final int LAC7_5 = 750000;
	public static final int LAC5 = 500000;
	public static final int LAC2_5 = 250000;
	public static final String N_A = "N/A";
	public static final String PIPE_SEPERATOR_WITH_SPACE = " | ";
	public static final String PIPE_SEPERATOR = "|";
	public static final String DASH_SEPERATOR = "-";
	public static final int STANDARD_DEDUCTION = 50000;
	public static final int TOTAL_LENGTH = 90;
	public static final int COLUMN_LENGTH = TOTAL_LENGTH / 3;
	public static final String BORDER = StringUtils.leftPad(EMPTY, TOTAL_LENGTH + 4, DASH_SEPERATOR);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
	public static final String CLOSE_BRACKET_PERCENT = "%*)";
	public static final String OPEN_BRACKET = " (";

	public static Map<slabsEnum, Slab> taxSlab;

	public static Map<slabsEnum, Slab> getTaxSlab() {
		if (Objects.isNull(taxSlab)) {
			taxSlab = new EnumMap<>(slabsEnum.class);
			taxSlab.put(slabsEnum.LAC2_5, new Slab(LAC2_5, 5, 0, "Deduction (Slab:2.5-5Lac)"));
			taxSlab.put(slabsEnum.LAC5, new Slab(LAC5, 20, 10, "Deduction (Slab:5-7.5Lac)"));
			taxSlab.put(slabsEnum.LAC7_5, new Slab(LAC7_5, 20, 15, "Deduction (Slab:7.5-10Lac)"));
			taxSlab.put(slabsEnum.LAC10, new Slab(LAC10, 30, 20, "Deduction (Slab:10-12.5Lac)"));
			taxSlab.put(slabsEnum.LAC12_5, new Slab(LAC12_5, 30, 25, "Deduction (Slab:12.5-15Lac)"));
			taxSlab.put(slabsEnum.LAC15, new Slab(LAC15, 30, 30, "Deduction (Slab:>15Lac)"));
		}
		return taxSlab;
	}
}