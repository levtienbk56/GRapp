package hedspi.tienlv.grapp.model;

import java.util.ArrayList;
import java.util.List;

import hedspi.tienlv.grapp.model.placeapi.Result;

public class StaypointTag extends GPSPoint {
	private List<String> tags;

	public StaypointTag() {
		latlng = new Coordinate();
		tags = new ArrayList<String>();
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getTagsToString() {
		String str = "";
		for (String t : this.tags) {
			str += t + " ";
		}

		return str;
	}

	public List<Integer> getTagsInteger() {
		List<Integer> rs = new ArrayList<Integer>();
		for (String tag : this.tags) {
			try {
				int a = Result.listTypes.indexOf(tag);
				rs.add(a);
			} catch (Exception e) {
			}
		}
		return rs;
	}

	public String getTagsIntegerToString() {
		String s = "";
		for (String tag : this.tags) {
			try {
				int a = Result.listTypes.indexOf(tag);
				s += a + " ";
			} catch (Exception e) {
			}
		}
		return s;
	}

}
