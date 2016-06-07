package hedspi.tienlv.grapp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class GPSPoint extends Coordinate {
	private int id;
	private String time;

	public GPSPoint() {
		super();
	}

	public GPSPoint(int userId, double lat, double lng, String time) {
		super(lat, lng);
		this.id = userId;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int userId) {
		this.id = userId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * input: String format "yyyy-MM-dd HH:mm:ss"
	 *
	 * @param time1
	 * @param time2
	 * @return interval time in second
	 */
	public static long interval(String time1, String time2) {
		try {
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = fm.parse(time1);
			Date date2 = fm.parse(time2);
			return (date2.getTime() - date1.getTime()) / 1000;
		} catch (Exception ex) {
		}

		return 0;
	}

	@Override
	public String toString() {
		return super.toString() + ", time=" + time;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 13 * hash + Objects.hashCode(this.time);
		return hash;
	}

	/**
	 * dùng để compare related_points. giảm số request phải làm. các
	 * related_point có khoảng cách dưới 0.8m, vẫn cho là bằng nhau
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GPSPoint other = (GPSPoint) obj;

		// get only 4 decimals (~0.8 meter)
		// +-0.8m still equals
		double lat1 = round(this.getLat(), 5);
		double lng1 = round(this.getLng(), 5);
		double lat2 = round(other.getLat(), 5);
		double lng2 = round(other.getLng(), 5);
		if (!(lat1 == lat2) || !(lng1 == lng2)) {
			return false;
		}
		return true;
	}

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
