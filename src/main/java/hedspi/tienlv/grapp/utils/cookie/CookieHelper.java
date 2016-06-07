package hedspi.tienlv.grapp.utils.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CookieHelper(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void addCookie(String name, String value) {
		// save user id into cookie
		Cookie cookie = null;
		if (request.getCookies() != null) {
			for (Cookie c : request.getCookies()) {
				if (c != null && c.getName().equals(name)) {
					cookie = c;
					break;
				}
			}
		}

		if (cookie == null) {
			cookie = new Cookie(name, value);
		}
		cookie.setValue(value);
		cookie.setMaxAge(1000 * 60 * 60 * 24); // 1day
		response.addCookie(cookie);
	}

	public Cookie getCookie(String name) {
		Cookie cookie = null;
		if (request.getCookies() != null) {
			for (Cookie c : request.getCookies()) {
				if (c != null && c.getName().equals(name)) {
					cookie = c;
					break;
				}
			}
		}

		return cookie;
	}

}
