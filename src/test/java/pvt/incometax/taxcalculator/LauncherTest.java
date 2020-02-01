package pvt.incometax.taxcalculator;

import org.junit.Test;

/**
 * Unit test for simple Launcher.
 */
public class LauncherTest {
	@Test
	public void taxCalculator() {
		Launcher.calculateTax(2000000, 150000);
	}
	@Test(expected = IllegalArgumentException.class)
	public void taxCalculatorIllegalArgumentException() {
		Launcher.calculateTax(500, 700);
	}
}
