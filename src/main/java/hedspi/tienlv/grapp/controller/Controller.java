package hedspi.tienlv.grapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hedspi.tienlv.grapp.function.staypoint.StayPointExtractor;
import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.model.StayPoint;
import hedspi.tienlv.grapp.utils.cookie.CookieHelper;
import hedspi.tienlv.grapp.utils.model.GPSPointExtractor;

@org.springframework.stereotype.Controller
public class Controller {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() {
		return "pages/home";
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String staypointPage() {
		return "pages/staypoint";
	}

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String handleUploadAndProcess(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) {

		/*** folder upload ***/
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File uploadRootDir = new File(uploadRootPath);
		System.out.println("uploaded folder:" + uploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		/*** file uploaded ***/
		File mFile = new File(uploadRootPath + "/" + file.getOriginalFilename());

		CookieHelper cookieHelper = new CookieHelper(request, response);
		cookieHelper.addCookie("user_id", FilenameUtils.getBaseName(mFile.getName()));

		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(mFile));
				FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "redirect:/process";
	}

	@RequestMapping(value = "/staypoint", method = RequestMethod.POST)
	public @ResponseBody List<StayPoint> getStaypoint(HttpServletRequest request, HttpServletResponse response) {
		List<StayPoint> staypoints = new ArrayList<StayPoint>();

		CookieHelper ch = new CookieHelper(request, response);
		Cookie cookie = ch.getCookie("user_id");

		/*** folder upload ***/
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File uploadRootDir = new File(uploadRootPath);
		System.out.println("uploaded folder:" + uploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		/*** file uploaded ***/
		File mFile = new File(uploadRootPath + "/" + cookie.getValue() + ".txt");

		System.out.println("path: " + mFile.getAbsolutePath());
		try {
			/*** read file and load gps point data ***/
			List<GPSPoint> points = new GPSPointExtractor().extractFromFile(mFile.getAbsolutePath());
			/*** extract staypoint ***/
			staypoints = new StayPointExtractor(30, 1200).extractStayPoints(points);
			System.out.println("Staypoint extract: " + staypoints.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return staypoints;
	}

	private List<StayPoint> sp(String path) {
		List<StayPoint> staypoints = new ArrayList<StayPoint>();

		try {
			/*** read file and load gps point data ***/
			List<GPSPoint> points = new GPSPointExtractor().extractFromFile(path);
			/*** extract staypoint ***/
			staypoints = new StayPointExtractor(30, 1200).extractStayPoints(points);
			System.out.println("Staypoint extract: " + staypoints.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staypoints;
	}

}
