package hedspi.tienlv.grapp.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sequence {
	private String date;
	private List<Itemset> itemsets;

	public Sequence() {
		this.date = "";
		this.itemsets = new ArrayList<Itemset>();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Itemset> getItemsets() {
		return itemsets;
	}

	public void setItemsets(List<Itemset> itemsets) {
		this.itemsets = itemsets;
	}

	public List<List<String>> getSequenceString() {
		List<List<String>> list = new LinkedList<List<String>>();

		for (Itemset is : itemsets) {
			list.add(is.getItemsString());
		}
		return list;
	}

	// length is total number of item, not itemset
	public int getLength() {
		int l = 0;
		for (Itemset its : itemsets) {
			l += its.getItems().size();
		}
		return l;
	}
}
