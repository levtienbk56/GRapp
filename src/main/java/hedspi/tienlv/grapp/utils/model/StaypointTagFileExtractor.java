package hedspi.tienlv.grapp.utils.model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import hedspi.tienlv.grapp.model.StaypointTag;
import hedspi.tienlv.grapp.utils.file.MyFile;

public class StaypointTagFileExtractor extends FileExtractor {
	public List<StaypointTag> extractFromTxtFile(String filePath) throws Exception {
		List<StaypointTag> arr = new ArrayList<StaypointTag>();
		BufferedReader reader = MyFile.readFile(filePath);
		if (reader == null) {
			return arr;
		}
		String line;

		// check first lind for header
		line = reader.readLine();
		if (line != null) {
			List<String> items = Arrays.asList(line.split("\\s*,\\s*"));

			// remove header
			if (!items.get(0).equals("name") && !items.get(0).equals("id")) {
				StaypointTag sp = new StaypointTag();
				sp.setId(getID(items.get(0)));
				sp.setTime(items.get(1));

				// split tag
				String str = items.get(2).trim();
				String[] tagArr = str.split("");
				List<String> tags = new LinkedList<String>();
				for (int i = 0; i < tagArr.length; i++) {
					String tag = tagArr[i];
					if (tag != null && tag.length() > 1 && !tag.equals("")) {
						tags.add(tag);
					}
				}

				sp.setTags(tags);
				arr.add(sp);
			}

			// continue with other line
			while ((line = reader.readLine()) != null) {
				items = Arrays.asList(line.split("\\s*,\\s*"));
				if (items.size() != 5) {
					break;
				}
				StaypointTag sp = new StaypointTag();
				sp.setId(getID(items.get(0)));
				sp.setTime(items.get(1));

				// split tag
				String str = items.get(2).trim();
				String[] tagArr = str.split("");
				List<String> tags = new LinkedList<String>();
				for (int i = 0; i < tagArr.length; i++) {
					String tag = tagArr[i];
					if (tag != null && tag.length() > 1 && !tag.equals(" ")) {
						tags.add(tag);
					}
				}

				sp.setTags(tags);
				arr.add(sp);
			}
		}
		return arr;
	}
}
