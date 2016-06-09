package hedspi.tienlv.grapp.utils.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hedspi.tienlv.grapp.model.Coordinate;
import hedspi.tienlv.grapp.model.Staypoint;
import hedspi.tienlv.grapp.utils.file.MyFile;

public class StaypointFileExtractor extends FileExtractor{
	/**
	 * file format <id>,<date time>,<longitude>,<latitude>
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public List<Staypoint> extractFromTxtFile(String filePath) throws Exception {
		List<Staypoint> arr = new ArrayList<Staypoint>();
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
				Staypoint sp = new Staypoint();
				sp.setId(getID(items.get(0)));
				sp.setTime(items.get(1));
				sp.setLatlng(new Coordinate(new Double(items.get(2)), new Double(items.get(3))));
				arr.add(sp);
			}

			// continue with other line
			while ((line = reader.readLine()) != null) {
				items = Arrays.asList(line.split("\\s*,\\s*"));
				if(items.size() != 4){
					break;
				}
				Staypoint sp = new Staypoint();
				sp.setId(getID(items.get(0)));
				sp.setTime(items.get(1));
				sp.setLatlng(new Coordinate(new Double(items.get(2)), new Double(items.get(3))));
				arr.add(sp);
			}
		}
		return arr;
	}
}
