package hedspi.tienlv.grapp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.model.Sequence;
import hedspi.tienlv.grapp.model.SequentialPattern;
import hedspi.tienlv.grapp.model.Staypoint;
import hedspi.tienlv.grapp.model.StaypointTag;

public class MainService {
	public void process(File file, String userID) {
		/*******************************************************************/
		/*** init variable **************************************************/
		/*******************************************************************/
		// init list
		List<GPSPoint> gpsPoints = new ArrayList<GPSPoint>();
		List<Staypoint> staypoints = new ArrayList<Staypoint>();
		List<StaypointTag> staypointTags = new ArrayList<StaypointTag>();
		List<Sequence> sequences = new ArrayList<Sequence>();
		List<SequentialPattern> sequentialPatterns = new ArrayList<SequentialPattern>();

		// init service
		GPSPointService gpsPointService = new GPSPointService();
		StaypointService spService = new StaypointService();
		GeotagService gtService = new GeotagService();
		SPMService spmService = new SPMService();

		// resource directory
		File uDir = file.getParentFile();

		/*******************************************************************/
		/*** read upload file and load gps point data **********************/
		/*******************************************************************/
		try {
			gpsPoints = gpsPointService.extractPointFromFile(file);
			System.out.println("GPS point extract: " + gpsPoints.size());
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		/*******************************************************************/
		/*** staypoint *****************************************************/
		/*******************************************************************/
		// folder staypoint
		File spdir = new File(uDir + "/staypoint");
		if (!spdir.exists()) {
			spdir.mkdirs();
		}
		// file staypoint
		File spFile = new File(spdir + "/" + userID + ".txt");

		// if exist, do nothing, then exit.
		// else, calcute staypoint, then write to file.
		if (spFile.exists()) {
			return;
		}
		try {
			staypoints = spService.extractStayPoints(gpsPoints, 30, 1200);
			spService.writeFile(staypoints, spFile);
			System.out.println("Staypoint extract: " + staypoints.size());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		/*******************************************************************/
		/*** geotag ********************************************************/
		/*******************************************************************/
		// folder staypointtag
		File sptdir = new File(uDir + "/staypointTag");
		if (!sptdir.exists()) {
			sptdir.mkdirs();
		}
		// file staypointtag
		File sptFile = new File(sptdir + "/" + userID + ".txt");
		if (sptFile.exists()) {
			return;
		}
		for (Staypoint sp : staypoints) {
			List<String> tags = gtService.getTags(sp.getLatlng());

			StaypointTag spt = new StaypointTag();
			spt.setId(sp.getId());
			spt.setLatlng(sp.getLatlng());
			spt.setTime(sp.getTime());
			spt.setTags(tags);
			staypointTags.add(spt);
		}
		try {
			// write to file
			gtService.writeToFile(staypointTags, sptFile);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		/*******************************************************************/
		/*** create sequence ***********************************************/
		/*******************************************************************/
		// folder sequence
		File sqdir = new File(uDir + "/sequence");
		if (!sqdir.exists()) {
			sqdir.mkdirs();
		}
		// file staypointtag
		File sqFile = new File(sqdir + "/" + userID + ".txt");
		
		// TODO: create sequence and save to file
		/*******************************************************************/
		/*** Sequential Pattern Mining *************************************/
		/*******************************************************************/
	}
}
