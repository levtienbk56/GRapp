package hedspi.tienlv.grapp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleHelper {
	/**
	 * round(11.11111,2) return 11.11
	 *
	 * @param value
	 *            the number
	 * @param places
	 *            number decimal
	 * @return a double number
	 */
	public static double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
