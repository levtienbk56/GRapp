package hedspi.tienlv.grapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import hedspi.tienlv.grapp.common.Constraint;
import hedspi.tienlv.grapp.model.Coordinate;
import hedspi.tienlv.grapp.model.StaypointTag;
import hedspi.tienlv.grapp.model.placeapi.NearBySearchResult;
import hedspi.tienlv.grapp.model.placeapi.Result;
import hedspi.tienlv.grapp.utils.file.MyFile;
import hedspi.tienlv.grapp.utils.http.ClientRequest;

public class GeotagService {
	public List<String> getTags(Coordinate point) {
		List<String> tags = new ArrayList<String>();

		int radius = 20;
		List<Result> results = new ArrayList<Result>();
		while (radius < 100) {
			results = requestNearbyPlace(point, radius);
			if (results.size() > 0) {
				break;
			} else {
				radius += 20;
			}
		}

		for (Result result : results) {
			List<String> types = result.getTypes();

			for (String type : types) {
				if (Result.listTypes.contains(type) && !type.equals("establishment") && !type.equals("atm")) {
					// chỉ add vào list nếu item đó chưa có
					if (!tags.contains(type)) {
						tags.add(type);
					}
				}
			}
		}

		Collections.sort(tags);
		return tags;
	}

	/**
	 * request nearby place base on coordinate and radius
	 *
	 * @param point:
	 *            gps coordinate point
	 * @param radius:
	 *            limit ranger
	 * @return list of results (Result object)
	 */
	private List<Result> requestNearbyPlace(Coordinate point, int radius) {
		List<Result> results = new ArrayList<Result>();
		List<Result> r = new ArrayList<Result>();
		try {
			String url;
			Gson gson = new Gson();
			ClientRequest client = new ClientRequest();
			url = createRequestURL(point.getLat(), point.getLng(), radius);
			String json = client.request(url);
			NearBySearchResult obj = gson.fromJson(json, NearBySearchResult.class);
			results = obj.getResults();
			for (Result x : results) {
				if (Result.listTypes.contains(x.getTypes().get(0))) {
					r.add(x);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}

	private static String createRequestURL(double lat, double lng, int radius) {
		return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng + "&radius="
				+ radius + "&key=" + Constraint.API_KEY;
	}

	public void writeToFile(List<StaypointTag> spTags, String pathFile) throws Exception {
		String string = "id,date time,latitude,longtitude,tags\n";
		MyFile.cleanFile(pathFile);
		MyFile.writeToFile(pathFile, string);
		for (StaypointTag sp : spTags) {
			String str = sp.getId() + "," + sp.getTime() + "," + sp.getLatlng().getLat() + "," + sp.getLatlng().getLng()
					+ "," + sp.getTagsIntegerToString() + "\n";
			MyFile.writeToFile(pathFile, str);
		}
	}

}