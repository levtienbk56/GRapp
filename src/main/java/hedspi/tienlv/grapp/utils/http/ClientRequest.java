package hedspi.tienlv.grapp.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientRequest {

	private final String USER_AGENT = "Mozilla/5.0";

	/**
	 * get Json result from Geocoder service
	 *
	 * @param link
	 * @return an String of json result
	 */
	public String request(String link) {
		String results = "";
		try {
			URL url = new URL(link);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// optional default is GET
			conn.setRequestMethod("GET");

			// add request header
			conn.setRequestProperty("User-Agent", USER_AGENT);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			StringBuilder out = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				out.append(line);
			}

			results = out.toString();
			conn.disconnect();

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return results;
	}
}
