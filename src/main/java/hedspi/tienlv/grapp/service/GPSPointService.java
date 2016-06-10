package hedspi.tienlv.grapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.utils.file.MyFile;

public class GPSPointService {
	/**
	 * file format <id>,<date time>,<longitude>,<latitude>
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public List<GPSPoint> extractPointFromFile(File file) throws Exception {
		String filePath = file.getAbsolutePath();
		List<GPSPoint> arr = new ArrayList<GPSPoint>();
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
				GPSPoint co = new GPSPoint(getID(items.get(0)), new Double(items.get(3)), new Double(items.get(2)),
						items.get(1));
				arr.add(co);
			}

			// continue with other line
			while ((line = reader.readLine()) != null) {
				items = Arrays.asList(line.split("\\s*,\\s*"));
				if (items.size() != 4) {
					break;
				}
				GPSPoint co = new GPSPoint(getID(items.get(0)), new Double(items.get(3)), new Double(items.get(2)),
						items.get(1));
				arr.add(co);
			}
		}

		System.out.println("POINT list size=" + arr.size());
		return arr;
	}

	static int getID(String s) {
		int a;
		try {
			a = Integer.parseInt(s);
		} catch (Exception ex) {
			a = 0;
		}
		return a;
	}
}
