package pvt.incometax.taxcalculator;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.text.DecimalFormat;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/**
 * Hello world!
 *
 */
public class Launcher {
	/**
	 * 
	 */
	private static final int LAC15 = 1500000;
	private static final int LAC12_5 = 1250000;
	private static final int LAC10 = 1000000;
	private static final int LAC7_5 = 750000;
	private static final int LAC5 = 500000;
	private static final int LAC2_5 = 250000;
	/**
	 * 
	 */
	public static final String N_A = "N/A";
	public static final String PIPE_SEPERATOR_WITH_SPACE = " | ";
	public static final String PIPE_SEPERATOR = "|";
	public static final String DASH_SEPERATOR = "-";
	public static final int STANDARD_DEDUCTION = 50000;
	public static final int TOTAL_LENGTH = 90;
	public static final int COLUMN_LENGTH = TOTAL_LENGTH / 3;
	public static final String BORDER = StringUtils.leftPad(EMPTY, TOTAL_LENGTH + 4, DASH_SEPERATOR);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	public static void main(String[] args) {
		try (Scanner scannerObj = new Scanner(System.in)) {
			boolean keepCalculating = true;
			do {
				try {
					System.out.print("Enter Net Income: ");
					int netIncome = scannerObj.nextInt();
					System.out.print("Enter Tax Saving Amount: ");
					int taxSavings = scannerObj.nextInt();
					calculateTax(netIncome, taxSavings);
				} catch (IllegalArgumentException ex) {
				} catch (Exception ex) {
					System.out.println("Invalid Input. Try Again.");
				}
				System.out.println();
				System.out.print("Want to exit? (Y/N) ");
				String next = scannerObj.next();
				if (next.equalsIgnoreCase("N")) {
					System.out.println();
					continue;
				} else {
					System.out.println("Good Bye");
					break;
				}
			} while (keepCalculating);
		}
	}

	public static void calculateTax(int netIncome, int taxSavings) {
		if (netIncome < 0 || taxSavings < 0 || taxSavings > netIncome) {
			System.out.println("You are drunk. Try again.");
			throw new IllegalArgumentException();
		}

		System.out.println(BORDER);

		printRow(EMPTY, "OLD", "NEW");
		printRow("Net Income", String.valueOf(netIncome), String.valueOf(netIncome));
		printRow("Standard Deduction", String.valueOf(STANDARD_DEDUCTION), N_A);
		printRow("Tax Savings", String.valueOf(taxSavings), String.valueOf(taxSavings));

		int taxableIncomeOld = netIncome - STANDARD_DEDUCTION - taxSavings;
		int taxableIncomeNew = netIncome;

		printRow("Taxable Amount", String.valueOf(taxableIncomeOld), String.valueOf(taxableIncomeNew));

		float taxApplicableOld = 0;
		float taxApplicableNew = 0;

		// Slab > 15 Lac
		if (netIncome > LAC15) {
			float amtOld = 0;
			if (taxableIncomeOld > LAC15) {
				int taxableAmtOld = taxableIncomeOld - LAC15;
				amtOld = taxableAmtOld * 0.3F;
				taxApplicableOld += amtOld;
				taxableIncomeOld = LAC15;
			}
			int taxableAmtNew = taxableIncomeNew - LAC15;
			float amtNew = taxableAmtNew * 0.3F;
			taxApplicableNew += amtNew;
			printRow("Deduction (Slab:>15Lac)", DECIMAL_FORMAT.format(amtOld) + " (30%)",
					DECIMAL_FORMAT.format(amtNew) + " (30%)");
			taxableIncomeNew = LAC15;
		}
		// Slab 12.5-15 Lac
		if (netIncome > LAC12_5) {
			float amtOld = 0;
			if (taxableIncomeOld > LAC12_5) {
				int taxableAmtOld = taxableIncomeOld - LAC12_5;
				amtOld = taxableAmtOld * 0.3F;
				taxApplicableOld += amtOld;
				taxableIncomeOld = LAC12_5;
			}
			int taxableAmtNew = taxableIncomeNew - LAC12_5;
			float amtNew = taxableAmtNew * 0.25F;
			taxApplicableNew += amtNew;
			printRow("Deduction (Slab:12.5-15Lac)", DECIMAL_FORMAT.format(amtOld) + " (30%)",
					DECIMAL_FORMAT.format(amtNew) + " (25%)");
			taxableIncomeNew = LAC12_5;
		}
		// Slab 10-12.5 Lac
		if (netIncome > LAC10) {
			float amtOld = 0;
			if (taxableIncomeOld > LAC10) {
				int taxableAmtOld = taxableIncomeOld - LAC10;
				amtOld = taxableAmtOld * 0.3F;
				taxApplicableOld += amtOld;
				taxableIncomeOld = LAC10;
			}
			int taxableAmtNew = taxableIncomeNew - LAC10;
			float amtNew = taxableAmtNew * 0.2F;
			taxApplicableNew += amtNew;
			printRow("Deduction (Slab:10-12.5 Lac)", DECIMAL_FORMAT.format(amtOld) + " (30%)",
					DECIMAL_FORMAT.format(amtNew) + " (20%)");
			taxableIncomeNew = LAC10;
		}
		// Slab 7.5-10 Lac
		if (netIncome > LAC7_5) {
			float amtOld = 0;
			if (taxableIncomeOld > LAC7_5) {
				int taxableAmtOld = taxableIncomeOld - LAC7_5;
				amtOld = taxableAmtOld * 0.2F;
				taxApplicableOld += amtOld;
				taxableIncomeOld = LAC7_5;
			}
			int taxableAmtNew = taxableIncomeNew - LAC7_5;
			float amtNew = taxableAmtNew * 0.15F;
			taxApplicableNew += amtNew;
			printRow("Deduction (Slab:7.5-10Lac)", DECIMAL_FORMAT.format(amtOld) + " (20%)",
					DECIMAL_FORMAT.format(amtNew) + " (15%)");
			taxableIncomeNew = LAC7_5;
		}
		boolean is5LacExemtionApplicable = true;
		// Slab 5-7.5 Lac
		if (netIncome > LAC5) {
			float amtOld = 0;
			if (taxableIncomeOld > LAC5) {
				int taxableAmtOld = taxableIncomeOld - LAC5;
				amtOld = taxableAmtOld * 0.2F;
				taxApplicableOld += amtOld;
				taxableIncomeOld = LAC5;
				is5LacExemtionApplicable = false;
			}
			int taxableAmtNew = taxableIncomeNew - LAC5;
			float amtNew = taxableAmtNew * 0.1F;
			taxApplicableNew += amtNew;
			printRow("Deduction (Slab:5-7.5Lac)", DECIMAL_FORMAT.format(amtOld) + " (20%)",
					DECIMAL_FORMAT.format(amtNew) + " (15%)");
			taxableIncomeNew = LAC5;
		}
		// Slab 2.5-5 Lac
		if (netIncome > LAC2_5 && !is5LacExemtionApplicable) {
			float amtOld = 0;
			if (taxableIncomeOld > LAC2_5) {
				int taxableAmtOld = taxableIncomeOld - LAC2_5;
				amtOld = taxableAmtOld * 0.05F;
				taxApplicableOld += amtOld;
			}
			printRow("Deduction (Slab:2.5-5Lac)", DECIMAL_FORMAT.format(amtOld) + " (5%)",
					DECIMAL_FORMAT.format(0) + " (0%)");
		}
		printRow("Total Tax", DECIMAL_FORMAT.format(taxApplicableOld), DECIMAL_FORMAT.format(taxApplicableNew));
	}

	private static void printRow(String col1, String col2, String col3) {
		StringBuilder stringBuilder = new StringBuilder(PIPE_SEPERATOR).append(EMPTY)
				.append(StringUtils.center(col1, COLUMN_LENGTH)).append(PIPE_SEPERATOR)
				.append(StringUtils.center(col2, COLUMN_LENGTH)).append(PIPE_SEPERATOR)
				.append(StringUtils.center(col3, COLUMN_LENGTH)).append(PIPE_SEPERATOR);
		System.out.println(stringBuilder);
		System.out.println(BORDER);
	}
}
