package hedspi.tienlv.grapp.model;

import java.util.List;

public class StaypointTag extends GPSPoint {
	private Itemset tags;

	public StaypointTag() {
		latlng = new Coordinate();
		tags = new Itemset();
	}

	public Itemset getTags() {
		return tags;
	}

	public void setTags(Itemset tags) {
		this.tags = tags;
	}

	public String getTagsToString() {
		return tags.toString();
	}

	/**
	 * list string tag
	 * 
	 * @return
	 */
	public List<String> getStringTags() {
		return tags.getItemsString();
	}
}
