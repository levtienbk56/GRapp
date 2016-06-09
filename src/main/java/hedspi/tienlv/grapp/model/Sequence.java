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

	public List<List<Integer>> getItemsetsInteger() {
		List<List<Integer>> list = new LinkedList<List<Integer>>();

		for (Itemset is : itemsets) {
			list.add(is.getItemsInteger());
		}
		return list;
	}
}
