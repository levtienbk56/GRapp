package hedspi.tienlv.grapp.utils.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.utils.file.MyFile;

public class GPSPointExtractor {
	/**
	 * return list of GPSPoint, extract from file
	 *
	 * @param filePath
	 *            path of File
	 * @return arrayList arrayList of GPSPoint(.
	 * @throws java.io.IOException
	 *
	 */
	public List<GPSPoint> extractFromFile(String filePath) throws Exception {
		List<GPSPoint> arr = new ArrayList<GPSPoint>();

		String typeFile = MyFile.getTypeOfFile(filePath);
		if (typeFile.equals("plt")) {
			arr = extractFromPltFile(filePath);
		} else if (typeFile.equals("txt")) {
			arr = extractFromTxtFile(filePath);
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

	/**
	 * file format <id>,<date time>,<longitude>,<latitude>
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private List<GPSPoint> extractFromTxtFile(String filePath) throws Exception {
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
				GPSPoint co = new GPSPoint(getID(items.get(0)), new Double(items.get(3)), new Double(items.get(2)),
						items.get(1));
				arr.add(co);
			}
		}
		return arr;
	}

	/**
	 * file format <id>,<date time>,<latitude>,<longitude>
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private List<GPSPoint> extractFromTxt2File(String filePath) throws Exception {
		List<GPSPoint> arr = new ArrayList<GPSPoint>();
		BufferedReader reader = MyFile.readFile(filePath);
		if (reader == null) {
			return arr;
		}
		String line;

		line = reader.readLine();
		if (line != null) {
			List<String> items = Arrays.asList(line.split("\\s*,\\s*"));

			// remove header
			if (!items.get(0).equals("name")) {
				GPSPoint co = new GPSPoint(getID(items.get(0)), new Double(items.get(3)), new Double(items.get(2)),
						items.get(1));
				arr.add(co);
			}

			// continue with other line
			while ((line = reader.readLine()) != null) {
				items = Arrays.asList(line.split("\\s*,\\s*"));
				GPSPoint co = new GPSPoint(getID(items.get(0)), new Double(items.get(2)), new Double(items.get(3)),
						items.get(1));
				arr.add(co);
			}
		}
		return arr;
	}

	private static int getID(String s) {
		int a;
		try {
			a = Integer.parseInt(s);
		} catch (Exception ex) {
			a = 0;
		}
		return a;
	}

}