package hedspi.tienlv.grapp.service;

import java.util.ArrayList;
import java.util.List;

import hedspi.tienlv.grapp.model.Coordinate;
import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.model.Staypoint;
import hedspi.tienlv.grapp.utils.file.MyFile;

public class StaypointService {

	public void writeFile(List<Staypoint> staypoints, String pathFile) throws Exception {
		String string = "id,date time,latitude,longtitude\n";
		MyFile.cleanFile(pathFile);
		MyFile.writeToFile(pathFile, string);
		for (Staypoint sp : staypoints) {
			String str = sp.getId() + "," + sp.getTime() + "," + sp.getLatlng().getLat() + "," + sp.getLatlng().getLng()
					+ "\n";
			MyFile.writeToFile(pathFile, str);
		}
	}

	/**
	 *
	 * @param arrLog
	 *            list of GPSPoint objects
	 * @return list of StayPoint
	 */
	public List<Staypoint> extractStayPoints(List<GPSPoint> arrLog, double distThresh, double timeThresh) {
		int i = 0, j, token, pointNum;
		double dist;
		long deltaTime;
		GPSPoint pi, pj;

		// list of stay point
		List<Staypoint> SP = new ArrayList<Staypoint>();
		if (arrLog.size() <= 0) {
			return SP;
		}

		// insert one point at last array
		arrLog.add(new GPSPoint(arrLog.get(0).getId(), 0.0, 0.0, "2999-01-01 00:00:00"));
		pointNum = arrLog.size();

		while (i < pointNum - 1) {
			pi = arrLog.get(i);
			j = i + 1;
			token = 0;
			while (j < pointNum) {
				pj = arrLog.get(j);
				dist = Coordinate.distance(pi.getLatlng(), pj.getLatlng());

				if (dist > distThresh) {
					// tính khoảng thời gian của SP
					deltaTime = GPSPoint.interval(pi.getTime(), pj.getTime());
					if (deltaTime > timeThresh) {
						int l;
						Staypoint S = new Staypoint();
						for (l = i; l < j; l++) {
							// @see contains()
							if (!S.getArr().contains(arrLog.get(l))) {
								S.getArr().add(arrLog.get(l));
							}
						}
						S.setTime(pi.getTime());

						// tính trọng tâm của SP
						S.computeAvgCoordinate();
						// thêm SP này vào list kết quả
						SP.add(S);
						i = j;
						token = 1;
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
}
