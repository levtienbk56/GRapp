package hedspi.tienlv.grapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hedspi.tienlv.grapp.common.Constraint;
import hedspi.tienlv.grapp.model.Coordinate;
import hedspi.tienlv.grapp.model.Itemset;
import hedspi.tienlv.grapp.model.StaypointTag;
import hedspi.tienlv.grapp.model.placeapi.NearBySearchResult;
import hedspi.tienlv.grapp.model.placeapi.Result;
import hedspi.tienlv.grapp.utils.file.MyFile;
import hedspi.tienlv.grapp.utils.http.ClientRequest;

@Service
public class GeotagService {
	/**
	 * request google api to retrive location's tags
	 * 
	 * @param point
	 * @return
	 */
	public List<String> getTags(Coordinate point) {
		List<String> tags = new ArrayList<String>();

		int radius = 20;
		List<Result> results = new ArrayList<Result>();
		while (radius <= 100) {
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
	 * @param point
	 * @param radius
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

	/**
	 * 
	 * @param spTags
	 * @param pathFile
	 * @throws Exception
	 */
	public void writeToFile(List<StaypointTag> spTags, File file) throws Exception {
		String string = "id,date time,latitude,longtitude,tags\n";
		MyFile.cleanFile(file);
		MyFile.writeToFile(file, string);
		for (StaypointTag sp : spTags) {
			String str = sp.getId() + "," + sp.getTime() + "," + sp.getLatlng().getLat() + "," + sp.getLatlng().getLng()
					+ "," + sp.getTagsToString() + "\n";
			MyFile.writeToFile(file, str);
		}
	}

	public List<StaypointTag> loadStaypointTagsFromFile(File file) throws Exception {
		String filePath = file.getAbsolutePath();
		List<StaypointTag> list = new ArrayList<StaypointTag>();
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
				if (items.size() != 5) {
					return list;
				}
				StaypointTag spt = new StaypointTag();
				spt.setId(getID(items.get(0)));
				spt.setTime(items.get(1));
				spt.setLatlng(new Coordinate(new Double(items.get(2)), new Double(items.get(3))));

				// get string tags
				String[] tags = items.get(4).trim().split(" ");
				Itemset its = new Itemset(Arrays.asList(tags));
				spt.setTags(its);
				list.add(spt);
			}

			// continue with other line
			while ((line = reader.readLine()) != null) {
				items = Arrays.asList(line.split("\\s*,\\s*"));
				if (items.size() != 5) {
					return list;
				}
				StaypointTag spt = new StaypointTag();
				spt.setId(getID(items.get(0)));
				spt.setTime(items.get(1));
				spt.setLatlng(new Coordinate(new Double(items.get(2)), new Double(items.get(3))));

				// load int tag
				String[] tags = items.get(4).trim().split(" ");
				Itemset its = new Itemset();
				for (String tag : tags) {
					if (tag != null && tag != "" && tag != " ") {
						try {
							its.addTag(Integer.parseInt(tag));
						} catch (Exception e) {
						}
					}
				}
				spt.setTags(its);
				list.add(spt);
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

}
