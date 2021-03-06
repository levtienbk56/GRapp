package hedspi.tienlv.grapp.model;

public class SequentialPattern extends Sequence {
	private int sup;

	public SequentialPattern() {
		super();
	}

	public int getSup() {
		return sup;
	}

	public void setSup(int sup) {
		this.sup = sup;
	}

	public String toString() {
		String str = "(" + this.sup + ",[";
		for (Itemset i : this.itemsets) {
			str += i.toString() + ",";
		}
		str += "])";
		return str;
	}

}
