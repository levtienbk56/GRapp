package hedspi.tienlv.grapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import hedspi.tienlv.grapp.model.Itemset;
import hedspi.tienlv.grapp.model.Sequence;
import hedspi.tienlv.grapp.model.SequentialPattern;
import hedspi.tienlv.grapp.model.StaypointTag;
import hedspi.tienlv.grapp.model.placeapi.Result;
import hedspi.tienlv.grapp.utils.file.MyFile;

@Service
public class SPMService {
	public static final Logger logger = LogManager.getLogger(SPMService.class);

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

		return sequences;
	}

	public List<Sequence> loadSequenceFromFile(File file) throws Exception {
		String filePath = file.getAbsolutePath();
		List<Sequence> list = new ArrayList<Sequence>();
		BufferedReader reader = MyFile.readFile(filePath);
		if (reader == null) {
			return list;
		}
		String line;

		// each line is a sequence
		// ex: 86 88 89 -1 92 94 -1 -2
		while ((line = reader.readLine()) != null) {
			// retrive sequence
			Sequence sequence = new Sequence();
			// split by itemset
			List<String> strs = Arrays.asList(line.split("-1"));
			for (String str : strs) {
				if (str.equals(null) || str.equals("") || str.equals(" ") || str.equals("-2")) {
					break;
				}

				// retrive itemset
				Itemset itemset = new Itemset();

				// split by item
				String[] its = str.split(" ");
				for (String s : its) {
					if (!s.equals(null) && !s.equals("") && !s.equals(" ") && !s.equals("-2")) {
						try {
							int a = Integer.parseInt(s);
							if (a > 0 && a < Result.array.length) {
								itemset.addTag(a);
							}
						} catch (Exception e) {
						}
					}
				}
				if (itemset.getItems().size() > 0) {
					sequence.getItemsets().add(itemset);
				}
			}

			list.add(sequence);
		}
		return list;
	}

	public List<SequentialPattern> loadPatternFromFile(File file) throws Exception {
		List<SequentialPattern> patterns = new ArrayList<SequentialPattern>();
		BufferedReader reader = MyFile.readFile(file.getAbsolutePath());
		if (reader == null) {
			return patterns;
		}
		String line;

		// each line is a sequence pattern
		// ex: 81 89 -1 92 -1 #SUP: 5
		while ((line = reader.readLine()) != null) {
			SequentialPattern pattern = new SequentialPattern();
			Itemset itemset = new Itemset();

			List<String> items = Arrays.asList(line.split(" "));
			for (String s : items) {
				if (s.equals("-1")) {
					pattern.getItemsets().add(itemset);
					itemset = new Itemset();
					continue;
				} else if (s.equals("#SUP:")) {
					break;
				} else {
					if (!s.equals("") && !s.equals(" ")) {
						itemset.addTag(Integer.parseInt(s));
					}
				}
			}
			// last part is support count
			pattern.setSup(Integer.parseInt(items.get(items.size() - 1)));
			patterns.add(pattern);
		}
		return patterns;
	}

	public void writeSequencesToFile(File file, List<Sequence> sequences) {
		try {
			MyFile.cleanFile(file);
			for (Sequence sq : sequences) {
				String str = "";
				for (Itemset its : sq.getItemsets()) {
					str += its.toString();
					str += "-1 ";
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
		int minsup = (int) Math.ceil(Math.log(sSize)) + 1;
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
		int maxl = (int) Math.ceil(Math.sqrt(sLengthAvg) + 1);
		System.out.println("Maxlength=" + maxl);
		return maxl;
	}
}
