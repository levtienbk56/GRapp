package hedspi.tienlv.grapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import hedspi.tienlv.grapp.model.Coordinate;
import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.model.Staypoint;
import hedspi.tienlv.grapp.utils.file.MyFile;

@Service
public class StaypointService {
	/**
	 * file format <id>,<date time>,<longitude>,<latitude>
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public List<Staypoint> extractFromFile(File file) throws Exception {
		String filePath = file.getAbsolutePath();
		List<Staypoint> list = new ArrayList<Staypoint>();
		BufferedReader reader = MyFile.readFile(filePath);
		if (reader == null) {
			return list;
		}
		String line;

		// check first lind for header
		line = reader.readLine();
		if (line != null) {
			List<String> items = Arrays.asList(line.split("\\s*,\\s*"));

			// remove header
			if (!items.get(0).equals("name") && !items.get(0).equals("id")) {
				if (items.size() == 4) {
					Staypoint sp = new Staypoint();
					sp.setId(getID(items.get(0)));
					sp.setTime(items.get(1));
					sp.setLatlng(new Coordinate(new Double(items.get(2)), new Double(items.get(3))));
					list.add(sp);
				}
			}

			// continue with other line
			while ((line = reader.readLine()) != null) {
				items = Arrays.asList(line.split("\\s*,\\s*"));
				if (items.size() != 4) {
					continue;
				}
				Staypoint sp = new Staypoint();
				sp.setId(getID(items.get(0)));
				sp.setTime(items.get(1));
				sp.setLatlng(new Coordinate(new Double(items.get(2)), new Double(items.get(3))));
				list.add(sp);
			}
		}
		return list;
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

	public void writeFile(List<Staypoint> staypoints, File file) throws Exception {
		String string = "id,date time,latitude,longtitude\n";
		MyFile.cleanFile(file);
		MyFile.writeToFile(file, string);
		for (Staypoint sp : staypoints) {
			String str = sp.getId() + "," + sp.getTime() + "," + sp.getLatlng().getLat() + "," + sp.getLatlng().getLng()
					+ "\n";
			MyFile.writeToFile(file, str);
		}
	}

	/**
	 *
	 * @param gpsPoints
	 *            list of GPSPoint objects
	 * @return list of StayPoint
	 */
	public List<Staypoint> extractStayPoints(List<GPSPoint> gpsPoints, double distThresh, double timeThresh) {
		int i = 0, j, k = 0, token, pointNum;
		double dist;
		long deltaTime;
		GPSPoint pi, pj;

		// list of stay point
		List<Staypoint> SP = new ArrayList<Staypoint>();
		if (gpsPoints.size() <= 0) {
			return SP;
		}

		// insert one point at last array
		gpsPoints.add(new GPSPoint(gpsPoints.get(0).getId(), 0.0, 0.0, "2999-01-01 00:00:00"));
		pointNum = gpsPoints.size();

		while (i < pointNum - 1) {
			pi = gpsPoints.get(i);
			j = i + 1;
			token = 0;
			while (j < pointNum) {
				pj = gpsPoints.get(j);
				dist = Coordinate.distance(pi.getLatlng(), pj.getLatlng());

				if (dist > distThresh) {
					// tính khoảng thời gian của SP
					deltaTime = GPSPoint.interval(pi.getTime(), pj.getTime());
					if (deltaTime > timeThresh) {
						int l;
						Staypoint S = new Staypoint();
						S.setId(k);
						for (l = i; l < j; l++) {
							// @see contains()
							if (!S.getArr().contains(gpsPoints.get(l))) {
								S.getArr().add(gpsPoints.get(l));
							}
						}
						S.setTime(pi.getTime());

						// tính trọng tâm của SP
						S.computeAvgCoordinate();
						// thêm SP này vào list kết quả
						SP.add(S);
						i = j;
						token = 1;
						k++; // id of staypoint
					}
					break;
				}
				j++;
			}
			if (token != 1) {
				i++;
			}
		}
		return SP;
	}

	public void writeNearPointToFile(List<Staypoint> staypoints, File file) throws Exception {
		int i, j;
		for (i = 0; i < staypoints.size(); i++) {
			Staypoint sp = staypoints.get(i);
			List<GPSPoint> arr = sp.getArr();
			for (j = 0; j < arr.size(); j++) {
				GPSPoint p = arr.get(j);
				String str = i + "," + p.getTime() + "," + p.getLatlng().getLat() + "," + p.getLatlng().getLng() + "\n";
				MyFile.writeToFile(file, str);
			}
		}
	}

	public void extractNearPointFromFile(List<Staypoint> staypoints, File nearFile) throws Exception {
		String filePath = nearFile.getAbsolutePath();
		BufferedReader reader = MyFile.readFile(filePath);
		if (reader == null) {
			return;
		}
		String line;
		List<String> items;
		// continue with other line
		while ((line = reader.readLine()) != null) {
			items = Arrays.asList(line.split("\\s*,\\s*"));
			int id = getID(items.get(0));
			if (items.size() != 4) {
				break;
			}
			GPSPoint p = new GPSPoint();
			p.setId(id);
			p.setTime(items.get(1));
			p.setLatlng(new Coordinate(new Double(items.get(2)), new Double(items.get(3))));
			for (Staypoint a : staypoints) {
				if (a.getId() == id) {
					a.getArr().add(p);
					break;
				}
			}
		}
	}
}
