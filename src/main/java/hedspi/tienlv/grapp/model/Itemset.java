package hedspi.tienlv.grapp.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hedspi.tienlv.grapp.model.placeapi.Result;

public class Itemset {
	private List<String> items;

	public Itemset() {
		this.items = new ArrayList<String>();
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}
	
	public List<Integer> getItemsInteger(){
		List<Integer> list = new LinkedList<Integer>();
		for(String s: items){
			list.add(Result.listTypes.indexOf(s));
		}
		return list;
	}

}
