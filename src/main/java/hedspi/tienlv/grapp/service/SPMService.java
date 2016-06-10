package hedspi.tienlv.grapp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hedspi.tienlv.grapp.model.Itemset;
import hedspi.tienlv.grapp.model.Sequence;
import hedspi.tienlv.grapp.model.SequentialPattern;
import hedspi.tienlv.grapp.model.StaypointTag;
import hedspi.tienlv.grapp.utils.file.MyFile;

public class SPMService {

	// TODO: processSPM
	public List<SequentialPattern> processSPM(List<Sequence> sequences) {
		return null;
	}

	public List<Sequence> createSequence(List<StaypointTag> spts) {
		List<Sequence> sequences = new ArrayList<Sequence>();

		// group staypoint tag by DATE
		String curDate = "";
		List<Itemset> its = new ArrayList<Itemset>();
		for (StaypointTag spt : spts) {
			// lấy ra ngày, mỗi ngày là 1 dòng
			String date = spt.getTime().split(" ")[0];
			if (curDate.equals("")) {
				curDate = date;
			}
			if (!date.equals(curDate)) {
				Sequence sequence = new Sequence();
				sequence.setDate(curDate);
				sequence.setItemsets(its);
				sequences.add(sequence);

				// update curdate
				curDate = date;
				its = new ArrayList<Itemset>();
			}
			// join tags to sequence 21
			its.add(spt.getTags());
		}
		// save sequence 21
		Sequence sequence = new Sequence();
		sequence.setDate(curDate);
		sequence.setItemsets(its);
		sequences.add(sequence);

		System.out.println("SEQUENCE list size=" + sequences.size());
		return sequences;
	}

	// TODO: loadSequenceFromFile
	public List<Sequence> loadSequenceFromFile(File file) {
		return null;
	}

	// TODO: writeSequenceToFile
	public List<SequentialPattern> loadPatternFromFile(File file) {
		return null;
	}

	public void writeSequencesToFile(File file, List<Sequence> sequences) {
		try {
			MyFile.cleanFile(file);
			for (Sequence sq : sequences) {
				String str = "";
				for (Itemset its : sq.getItemsets()) {
					str += its.toString();
					if (its.getItems().size() > 1) {
						str += "-1 ";
					}
				}
				str += "-2\n";
				MyFile.writeToFile(file, str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writePatternToFile(File file, List<SequentialPattern> patterns) {
		try {
			MyFile.cleanFile(file);
			for (SequentialPattern pattern : patterns) {
				String str = "";
				for (Itemset its : pattern.getItemsets()) {
					str += its.toString();
					if (its.getItems().size() > 1) {
						str += "-1 ";
					}
				}
				str += "#SUP: " + pattern.getSup() + "\n";
				MyFile.writeToFile(file, str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int estimateMinsup(List<Sequence> sequences) {
		int sSize = sequences.size();
		int minsup = (int) Math.ceil(Math.log(sSize))+1;
		System.out.println("Minsup=" + minsup);
		return minsup;

	}

	public int estimateMaxLength(List<Sequence> sequences) {
		int sSize = sequences.size();
		int sLengthAvg = 0;
		for (Sequence sq : sequences) {
			sLengthAvg += sq.getLength();
		}
		if (sSize == 0) {
			return 0;
		}
		sLengthAvg /= sSize;
		int maxl = (int) Math.ceil(Math.sqrt(sLengthAvg));
		System.out.println("Maxlength=" + maxl);
		return maxl;
	}
}
