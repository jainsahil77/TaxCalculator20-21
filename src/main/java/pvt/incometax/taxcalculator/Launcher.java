package pvt.incometax.taxcalculator;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static pvt.incometax.taxcalculator.Utility.BORDER;
import static pvt.incometax.taxcalculator.Utility.COLUMN_LENGTH;
import static pvt.incometax.taxcalculator.Utility.DECIMAL_FORMAT;
import static pvt.incometax.taxcalculator.Utility.N_A;
import static pvt.incometax.taxcalculator.Utility.PIPE_SEPERATOR;
import static pvt.incometax.taxcalculator.Utility.STANDARD_DEDUCTION;

import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import pvt.incometax.taxcalculator.Utility.slabsEnum;

public class Launcher {

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

		TaxApplicable taxApplicable = new TaxApplicable((netIncome - STANDARD_DEDUCTION - taxSavings), netIncome);
		printRow("Taxable Amount", String.valueOf(taxApplicable.getTaxableIncomeOld()),
				String.valueOf(taxApplicable.getTaxableIncomeNew()));
		Map<slabsEnum, Slab> taxSlab = Utility.getTaxSlab();
		// Slab > 15 Lac
		processTax(netIncome, taxApplicable, taxSlab, slabsEnum.LAC15);
		// Slab 12.5-15 Lac
		processTax(netIncome, taxApplicable, taxSlab, slabsEnum.LAC12_5);
		// Slab 10-12.5 Lac
		processTax(netIncome, taxApplicable, taxSlab, slabsEnum.LAC10);
		// Slab 7.5-10 Lac
		processTax(netIncome, taxApplicable, taxSlab, slabsEnum.LAC7_5);
		boolean is5LacExemtionApplicable = true;
		processTax5LacSlab(netIncome, taxApplicable, is5LacExemtionApplicable, taxSlab);
		printRow("Total Tax", DECIMAL_FORMAT.format(taxApplicable.getOldTaxAmount()),
				DECIMAL_FORMAT.format(taxApplicable.getNewTaxAmount()));
	}

	private static void processTax5LacSlab(int netIncome, TaxApplicable taxApplicable, boolean is5LacExemtionApplicable,
			Map<slabsEnum, Slab> slabMap) {
		// Slab 5-7.5 Lac
		Slab slabLac5 = slabMap.get(slabsEnum.LAC5);
		int slabAmountLac5 = slabLac5.getSlabAmount();
		if (netIncome > slabAmountLac5) {
			float amtOld = 0;
			if (taxApplicable.getTaxableIncomeOld() > slabAmountLac5) {
				int taxableAmtOld = taxApplicable.getTaxableIncomeOld() - slabAmountLac5;
				amtOld = taxableAmtOld * slabLac5.getOldTaxPercent() / 100;
				taxApplicable.updateOldTaxAmount(amtOld);
				taxApplicable.setTaxableIncomeOld(slabAmountLac5);
				is5LacExemtionApplicable = false;
			}
			int taxableAmtNew = taxApplicable.getTaxableIncomeNew() - slabAmountLac5;
			float amtNew = taxableAmtNew * 0.1F;
			taxApplicable.updateNewTaxAmount(amtNew);
			printRow(slabLac5.getStrMsg(), DECIMAL_FORMAT.format(amtOld) + " (" + slabLac5.getOldTaxPercent() + ")",
					DECIMAL_FORMAT.format(amtNew) + " (" + slabLac5.getNewTaxPercent() + ")");
			taxApplicable.setTaxableIncomeNew(slabAmountLac5);
		}
		// Slab 2.5-5 Lac
		Slab slabLac2_5 = slabMap.get(slabsEnum.LAC2_5);
		int slabAmountLac2_5 = slabLac2_5.getSlabAmount();
		if (netIncome > slabAmountLac2_5 && !is5LacExemtionApplicable) {
			float amtOld = 0;
			if (taxApplicable.getTaxableIncomeOld() > slabAmountLac2_5) {
				int taxableAmtOld = taxApplicable.getTaxableIncomeOld() - slabAmountLac2_5;
				amtOld = taxableAmtOld * slabLac2_5.getNewTaxPercent() / 100;
				taxApplicable.updateOldTaxAmount(amtOld);
			}
			printRow(slabLac2_5.getStrMsg(), DECIMAL_FORMAT.format(amtOld) + " (" + slabLac2_5.getOldTaxPercent() + ")",
					DECIMAL_FORMAT.format(0) + " (" + slabLac2_5.getOldTaxPercent() + ")");
		}
	}

	private static void processTax(int netIncome, TaxApplicable taxApplicable, Map<slabsEnum, Slab> slabMap,
			slabsEnum slabEnum) {
		Slab slab = slabMap.get(slabEnum);
		int slabAmount = slab.getSlabAmount();
		if (netIncome > slabAmount) {
			float amtOld = 0;
			if (taxApplicable.getTaxableIncomeOld() > slabAmount) {
				int taxableAmtOld = taxApplicable.getTaxableIncomeOld() - slabAmount;
				amtOld = taxableAmtOld * slab.getOldTaxPercent() / 100;
				taxApplicable.updateOldTaxAmount(amtOld);
				taxApplicable.setTaxableIncomeOld(slabAmount);
			}
			int taxableAmtNew = taxApplicable.getTaxableIncomeNew() - slabAmount;
			float amtNew = taxableAmtNew * slab.getNewTaxPercent() / 100;
			taxApplicable.updateNewTaxAmount(amtNew);
			printRow(slab.getStrMsg(), DECIMAL_FORMAT.format(amtOld) + " (" + slab.getOldTaxPercent() + ")",
					DECIMAL_FORMAT.format(amtNew) + " (" + slab.getNewTaxPercent() + ")");
			taxApplicable.setTaxableIncomeNew(slabAmount);
		}
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
