package hedspi.tienlv.grapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import hedspi.tienlv.grapp.utils.DoubleHelper;

public class GPSPoint {
	protected int id;
	protected String time;
	protected Coordinate latlng;

	public GPSPoint() {
		super();
		this.latlng = new Coordinate();
	}

	public GPSPoint(int userId, double lat, double lng, String time) {
		this.id = userId;
		this.time = time;
		this.latlng = new Coordinate(lat, lng);
	}

	public Coordinate getLatlng() {
		return latlng;
	}

	public void setLatlng(Coordinate latlng) {
		this.latlng = latlng;
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
		return "(" + this.id + "," + this.time + "," + this.latlng.toString() + ")";
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
		double lat1 = DoubleHelper.round(this.getLatlng().getLat(), 6);
		double lng1 = DoubleHelper.round(this.getLatlng().getLng(), 6);
		double lat2 = DoubleHelper.round(other.getLatlng().getLat(), 6);
		double lng2 = DoubleHelper.round(other.getLatlng().getLng(), 6);
		if (!(lat1 == lat2) || !(lng1 == lng2)) {
			return false;
		}
		return true;
	}
}
