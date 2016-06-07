package hedspi.tienlv.grapp.model;

import java.util.ArrayList;
import java.util.List;

public class StayPoint {

	private int id;
	private List<GPSPoint> arr; // list of point
	private Coordinate avgCoordinate = new Coordinate(); // centroid
	private String time;

	public StayPoint() {
		this.arr = new ArrayList<GPSPoint>(); 
	}

	public StayPoint(Coordinate avgCoordinate, String startTime) {
		this.arr = new ArrayList<GPSPoint>(); 
		this.avgCoordinate = avgCoordinate;
		this.time = startTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Coordinate getAvgCoordinate() {
		return avgCoordinate;
	}

	public void setAvgCoordinate(Coordinate avgCoordinate) {
		this.avgCoordinate = avgCoordinate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String startTime) {
		this.time = startTime;
	}

	public void computeAvgCoordinate() {
		if (arr.size() <= 0) {
			return;
		}
		double lat = 0, lng = 0;
		for (Coordinate p : arr) {
			lat += p.getLat();
			lng += p.getLng();
		}
		avgCoordinate.setLat(GPSPoint.round(lat / arr.size(), 5));
		avgCoordinate.setLng(GPSPoint.round(lng / arr.size(), 5));
		setTime(arr.get(0).getTime());
	}

	public List<GPSPoint> getArr() {
		return arr;
	}

	public void setArr(ArrayList<GPSPoint> arr) {
		this.arr = arr;
	}

	public void insertPoint(GPSPoint p) {
		arr.add(p);
	}

	public void showPoints() {
		System.out.println("sp size:" + arr.size());

		for (GPSPoint p : arr) {
			System.out.println("(" + p.getLat() + ", " + p.getLng() + ", " + p.getTime() + ")");
		}
	}

	public int size() {
		return arr.size();
	}

	@Override
	public String toString() {
		return "avgCoordinate=" + avgCoordinate + ", time=" + time;
	}

}
