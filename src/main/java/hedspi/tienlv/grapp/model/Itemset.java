package hedspi.tienlv.grapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hedspi.tienlv.grapp.model.placeapi.Result;

public class Itemset {
	// an item is Integer
	// an itemset is set of Integer (a,b,c,d)
	private List<Integer> items;

	public Itemset() {
		this.items = new ArrayList<Integer>();
	}

	public Itemset(List<String> stringArr) {
		this.items = new ArrayList<Integer>();
		for (String s : stringArr) {
			int i = Result.listTypes.indexOf(s);
			if (i != -1) {
				items.add(i);
			}
		}
	}

	public List<Integer> getItems() {
		return items;
	}

	public void setItems(List<Integer> items) {
		Collections.sort(items);
		this.items = items;
	}

	public List<String> getItemsString() {
		List<String> list = new LinkedList<String>();
		for (Integer i : items) {
			if (i > 0 && i < Result.listTypes.size()) {
				list.add(Result.listTypes.get(i));
			}
		}
		return list;
	}

	public String toString() {
		String s = "";
		for (Integer i : items) {
			s += i + " ";
		}
		return s;
	}

}
