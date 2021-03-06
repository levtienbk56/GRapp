package hedspi.tienlv.grapp.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan.AlgoPrefixSpan;
import ca.pfv.spmf.input.sequence_database_list_integers.SequenceDatabase;
import hedspi.tienlv.grapp.model.GPSPoint;
import hedspi.tienlv.grapp.model.Itemset;
import hedspi.tienlv.grapp.model.Sequence;
import hedspi.tienlv.grapp.model.Staypoint;
import hedspi.tienlv.grapp.model.StaypointTag;

@Service
public class MainService {
	public static final Logger logger = LogManager.getLogger(MainService.class);

	public void process(File file, String userID) {
		/*******************************************************************/
		/*** init variable **************************************************/
		/*******************************************************************/
		// init list
		List<GPSPoint> gpsPoints = new ArrayList<GPSPoint>();
		List<Staypoint> staypoints = new ArrayList<Staypoint>();
		List<StaypointTag> staypointTags = new ArrayList<StaypointTag>();
		List<Sequence> sequences = new ArrayList<Sequence>();

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

		// folder nearpoint
		File nearDir = new File(spdir + "/nearpoint");
		if (!nearDir.exists()) {
			nearDir.mkdirs();
		}
		// file staypoint
		File spFile = new File(spdir + "/" + userID + ".txt");
		File nearFile = new File(nearDir + "/" + userID + ".txt");

		// if exist, do nothing, then exit.
		// else, calcute staypoint, then write to file.
		if (spFile.exists()) {
			return;
		}
		try {
			staypoints = spService.extractStayPoints(gpsPoints, 30, 1200);
			spService.writeFile(staypoints, spFile);
			spService.writeNearPointToFile(staypoints, nearFile);
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
			// dddd
			List<String> tags = gtService.getTags(sp.getLatlng());
			if (tags.isEmpty()) {
				continue;
			}
			Itemset itemset = new Itemset(tags);

			StaypointTag spt = new StaypointTag();
			spt.setId(sp.getId());
			spt.setLatlng(sp.getLatlng());
			spt.setTime(sp.getTime());
			spt.setTags(itemset);
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
		// file sequence
		File sqFile = new File(sqdir + "/" + userID + ".txt");
		if (sqFile.exists()) {
			return;
		}

		sequences = spmService.createSequence(staypointTags);
		spmService.writeSequencesToFile(sqFile, sequences);

		/*******************************************************************/
		/*** Sequential Pattern Mining *************************************/
		/*******************************************************************/
		// folder pattern
		File pdir = new File(uDir + "/pattern");
		if (!pdir.exists()) {
			pdir.mkdirs();
		}
		// file pattern
		File pFile = new File(pdir + "/" + userID + ".txt");
		if (pFile.exists()) {
			return;
		}
		SequenceDatabase sequenceDatabase = new SequenceDatabase();
		try {
			// load sequence
			sequenceDatabase.loadFile(sqFile.getAbsolutePath());

			// run SPM algorithms
			AlgoPrefixSpan algo = new AlgoPrefixSpan();
			int minsup = spmService.estimateMinsup(sequences);
			int maxlength = spmService.estimateMaxLength(sequences);
			algo.setMaximumPatternLength(maxlength);
			algo.setShowSequenceIdentifiers(false);
			algo.runAlgorithm(sequenceDatabase, pFile.getAbsolutePath(), minsup);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
