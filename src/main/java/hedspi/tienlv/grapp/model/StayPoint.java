package hedspi.tienlv.grapp.model;

import java.util.ArrayList;
import java.util.List;

import hedspi.tienlv.grapp.utils.DoubleHelper;

public class Staypoint extends GPSPoint {

	private List<GPSPoint> arr; // list of point

	public Staypoint() {
		this.arr = new ArrayList<GPSPoint>();
	}

	public Staypoint(Coordinate avgCoordinate, String startTime) {
		this.arr = new ArrayList<GPSPoint>();
		this.latlng = avgCoordinate;
		this.time = startTime;
	}

	public void computeAvgCoordinate() {
		if (arr.size() <= 0) {
			return;
		}
		double lat = 0.0, lng = 0.0;
		for (GPSPoint p : arr) {
			lat += p.getLatlng().getLat();
			lng += p.getLatlng().getLng();
		}
		this.latlng.setLat(DoubleHelper.round(lat / arr.size(), 5));
		this.latlng.setLng(DoubleHelper.round(lng / arr.size(), 5));
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
		for (GPSPoint p : arr) {
			System.out.println("(" + p.getLatlng().getLat() + ", " + p.getLatlng().getLng() + ", " + p.getTime() + ")");
		}
	}

	public int size() {
		return arr.size();
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
