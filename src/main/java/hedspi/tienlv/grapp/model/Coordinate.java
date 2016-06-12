package hedspi.tienlv.grapp.model;

public class Coordinate {
	private double lat;
	private double lng;

	public Coordinate() {

	}

	public Coordinate(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double lenght() {
		return Math.sqrt(lat * lat + lng * lng);
	}

	/**
	 * calculate distance between 2 points
	 * 
	 * @param x
	 * @param y
	 * @return distance in meter
	 */
	public static double distance(Coordinate x, Coordinate y) {
		double dLat = toRad(y.getLat() - x.getLat());
		double dLng = toRad(y.getLng() - x.getLng());
		int R = 6371000;

		double lat1 = toRad(x.getLat());
		double lat2 = toRad(x.getLat());
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.sin(dLng / 2) * Math.sin(dLng / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}

	private static double toRad(double a) {
		return a * Math.PI / 180;
	}

	public static void main(String[] args) {
		Coordinate x = new Coordinate(39.90732, 116.45353);
		Coordinate y = new Coordinate(39.90732, 116.45354);

		System.out.println(Coordinate.distance(x, y));
	}

	@Override
	public String toString() {
		return lat + "," + lng;
	}

}
