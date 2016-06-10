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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hedspi.tienlv.grapp.model.Sequence;
import hedspi.tienlv.grapp.model.Staypoint;
import hedspi.tienlv.grapp.model.StaypointTag;
import hedspi.tienlv.grapp.service.GeotagService;
import hedspi.tienlv.grapp.service.MainService;
import hedspi.tienlv.grapp.service.StaypointService;
import hedspi.tienlv.grapp.utils.cookie.CookieHelper;

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
				MainService mainService = new MainService();
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
		File uploadRootDir = new File(uploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		/*** file uploaded ***/
		File uFile = new File(uploadRootPath + "/" + cookie.getValue() + ".txt");
		if (!uFile.exists() || uFile.isDirectory()) {
			return staypoints;
		}

		/*** check if filename is not Integer type ***/
		try {
			Integer.parseInt(cookie.getValue());
		} catch (Exception ex) {
			ex.printStackTrace();
			return staypoints;
		}

		/*** save staypoint into file ***/
		// folder staypoint
		File spdir = new File(uploadRootPath + "/staypoint");
		if (!spdir.exists()) {
			spdir.mkdirs();
		}
		// file staypoint
		File spFile = new File(spdir + "/" + cookie.getValue() + ".txt");
		/***
		 * if exist, read file and return client. else, calcute staypoint, then
		 * write to file
		 **/
		if (spFile.exists()) {
			return staypoints;
		}

		try {
			/*** read upload file and load gps point data ***/

			/*** extract staypoint ***/

			System.out.println("Staypoint extract: " + staypoints.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return staypoints;
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
		File sptDir = new File(uploadRootPath + "/staypointTag");
		if (!sptDir.exists()) {
			sptDir.mkdirs();
		}
		File sptFile = new File(sptDir + "/" + cookie.getValue() + ".txt");
		if (sptFile.exists()) {
			// load and return
			return spTags;
		}

		/*** not exist, calcute and save to file, then return back client ***/
		// folder staypoint
		File spFile = new File(uploadRootPath + "/staypoint/" + cookie.getValue() + ".txt");
		if (!spFile.exists()) {
			return spTags;
		}

		StaypointService spService = new StaypointService();
		try {
			List<Staypoint> staypoints = spService.extractFromFile(spFile);
			GeotagService geotagService = new GeotagService();
			for (Staypoint sp : staypoints) {
				StaypointTag spt = new StaypointTag();
				spt.setId(sp.getId());
				spt.setLatlng(sp.getLatlng());
				spt.setTime(sp.getTime());

				// geotag
				List<String> tags = geotagService.getTags(sp.getLatlng());
				// spt.setTags(tags);
				// spTags.add(spt);
			}
			// write file
			// geotagService.writeToFile(spTags, sptFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return spTags;
		}

		return spTags;
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
		// folder staypoint
		File spFile = new File(uploadRootPath + "/geotag/" + cookie.getValue() + ".txt");
		if (!spFile.exists()) {
			return sequences;
		}

		// StaypointTagFileExtractor extractor = new
		// StaypointTagFileExtractor();
		// try {
		// List<StaypointTag> spts =
		// extractor.extractFromTxtFile(spFile.getAbsolutePath());
		// for (StaypointTag spt : spts) {
		// // TODO: tao sequence ~ date
		// String date = spt.getTime().split(" ")[0];
		// //
		// }
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return sequences;
	}

	@RequestMapping(value = "/sequence-pattern", method = RequestMethod.POST)
	public @ResponseBody List<Staypoint> getSequencePattern(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}
}
