package hedspi.tienlv.grapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hedspi.tienlv.grapp.model.Sequence;
import hedspi.tienlv.grapp.model.SequentialPattern;
import hedspi.tienlv.grapp.model.Staypoint;
import hedspi.tienlv.grapp.model.StaypointTag;
import hedspi.tienlv.grapp.service.GeotagService;
import hedspi.tienlv.grapp.service.MainService;
import hedspi.tienlv.grapp.service.SPMService;
import hedspi.tienlv.grapp.service.StaypointService;
import hedspi.tienlv.grapp.utils.cookie.CookieHelper;

@org.springframework.stereotype.Controller
public class Controller {
	public static final Logger logger = LogManager.getLogger(Controller.class);

	@Autowired
	StaypointService spService;
	@Autowired
	GeotagService gtservice;
	@Autowired
	SPMService spmService;
	@Autowired
	MainService mainService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() {
		return "pages/home";
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String staypointPage() {
		return "pages/process";
	}

	/**
	 * handle upload file
	 * 
	 * @param request
	 *            cookie usable
	 * @param response
	 *            cookie usable
	 * @param file
	 *            upload file
	 * @return redirect to /process page
	 */
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

		if (!file.isEmpty()) {
			try {
				// save user id into Cookie
				String userID = FilenameUtils.getBaseName(mFile.getName());
				CookieHelper cookieHelper = new CookieHelper(request, response);
				cookieHelper.addCookie("user_id", userID);

				// save file to uplaod folder
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(mFile));
				FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();

				// main process
				mainService = new MainService();
				mainService.process(mFile, userID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/process";
	}

	/**
	 * Ajax request, get staypoint
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/staypoint", method = RequestMethod.POST)
	public @ResponseBody List<Staypoint> getStaypoint(HttpServletRequest request, HttpServletResponse response) {
		List<Staypoint> staypoints = new ArrayList<Staypoint>();

		CookieHelper ch = new CookieHelper(request, response);
		Cookie cookie = ch.getCookie("user_id");
		if (cookie == null) {
			return staypoints;
		}

		/*** folder upload ***/
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		// file staypoint
		File spFile = new File(uploadRootPath + "/staypoint/" + cookie.getValue() + ".txt");
		/***
		 * if exist, read file and return client. else, calcute staypoint, then
		 * write to file
		 **/
		if (!spFile.exists()) {
			return staypoints;
		}

		try {
			/*** read file and load staypoint data ***/
			staypoints = spService.extractFromFile(spFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return logger.exit(staypoints);
	}

	@RequestMapping(value = "/geotag", method = RequestMethod.POST)
	public @ResponseBody List<StaypointTag> getGeotag(HttpServletRequest request, HttpServletResponse response) {
		List<StaypointTag> spTags = new LinkedList<StaypointTag>();

		CookieHelper ch = new CookieHelper(request, response);
		Cookie cookie = ch.getCookie("user_id");
		if (cookie == null) {
			return spTags;
		}

		/*** check if exist, just load data from file then return to client ***/
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File sptFile = new File(uploadRootPath + "/staypointTag/" + cookie.getValue() + ".txt");
		if (!sptFile.exists()) {
			return spTags;
		}

		gtservice = new GeotagService();
		try {
			spTags = gtservice.loadStaypointTagsFromFile(sptFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logger.exit(spTags);
	}

	@RequestMapping(value = "/sequence", method = RequestMethod.POST)
	public @ResponseBody List<Sequence> getSequence(HttpServletRequest request, HttpServletResponse response) {
		List<Sequence> sequences = new ArrayList<Sequence>();

		CookieHelper ch = new CookieHelper(request, response);
		Cookie cookie = ch.getCookie("user_id");
		if (cookie == null) {
			return sequences;
		}

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		// folder sequence
		File sqFile = new File(uploadRootPath + "/sequence/" + cookie.getValue() + ".txt");
		if (!sqFile.exists()) {
			return sequences;
		}

		try {
			sequences = spmService.loadSequenceFromFile(sqFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return logger.exit(sequences);
	}

	@RequestMapping(value = "/pattern", method = RequestMethod.POST)
	public @ResponseBody List<SequentialPattern> getSequencePattern(HttpServletRequest request,
			HttpServletResponse response) {
		List<SequentialPattern> patterns = new ArrayList<SequentialPattern>();

		CookieHelper ch = new CookieHelper(request, response);
		Cookie cookie = ch.getCookie("user_id");
		if (cookie == null) {
			return patterns;
		}

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		// folder sequence pattern
		File pFile = new File(uploadRootPath + "/pattern/" + cookie.getValue() + ".txt");
		logger.debug("patternFile", pFile.getAbsolutePath());
		if (!pFile.exists()) {
			return patterns;
		}

		try {
			patterns = spmService.loadPatternFromFile(pFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return logger.exit(patterns);
	}
}
