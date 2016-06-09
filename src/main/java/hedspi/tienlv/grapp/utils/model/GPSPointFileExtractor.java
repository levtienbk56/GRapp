package hedspi.tienlv.grapp.utils.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.utils.file.MyFile;

public class GPSPointFileExtractor extends FileExtractor {
	/**
	 * return list of GPSPoint, extract from file
	 *
	 * @param filePath
	 *            path of File
	 * @return arrayList arrayList of GPSPoint(.
	 * @throws java.io.IOException
	 *
	 */
	public List<GPSPoint> extractFromFile(File file) throws Exception {
		String filePath = file.getAbsolutePath();
		List<GPSPoint> arr = new ArrayList<GPSPoint>();

		String typeFile = MyFile.getTypeOfFile(filePath);
		if (typeFile.equals("plt")) {
			arr = extractFromPltFile(filePath);
		} else if (typeFile.equals("txt")) {
			arr = extractFromFile(filePath);
		}

		System.out.println("number line of file:" + arr.size());

		return arr;
	}

	private ArrayList<GPSPoint> extractFromPltFile(String filePath) throws Exception {
		ArrayList<GPSPoint> arr = new ArrayList<GPSPoint>();
		BufferedReader reader = MyFile.readFile(filePath);
		if (reader == null) {
			return arr;
		}
		String line;
		int i = 0;

		while ((line = reader.readLine()) != null) {
			i++;
			if (i < 7) {
				continue;
			}
			List<String> items = Arrays.asList(line.split("\\s*,\\s*")); // any_space,any_space
			GPSPoint co = new GPSPoint(0, new Double(items.get(0)), new Double(items.get(1)),
					items.get(5) + " " + items.get(6));
			arr.add(co);
		}
		return arr;
	}

}
