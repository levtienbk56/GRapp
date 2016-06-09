package hedspi.tienlv.grapp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hedspi.tienlv.grapp.model.Sequence;
import hedspi.tienlv.grapp.model.SequentialPattern;
import hedspi.tienlv.grapp.model.StaypointTag;

public class SPMService {

	// TODO: processSPM
	public List<SequentialPattern> processSPM(List<Sequence> sequences) {
		return null;
	}

	// TODO: createSequence
	public List<Sequence> createSequence(List<StaypointTag> spts) {
		List<Sequence> sequences = new ArrayList<Sequence>();
		
		for (StaypointTag spt : spts) {
			//TODO: change list tags in StaypointTag to List<integer>
		}
		return null;
	}

	// TODO: loadSequenceFromFile
	public List<Sequence> loadSequenceFromFile(File file) {
		return null;
	}

	// TODO: writeSequenceToFile
	public List<SequentialPattern> loadPatternFromFile(File file) {
		return null;
	}

	// TODO: writeSequenceToFile
	public void writeSequenceToFile(String path) {

	}

	// TODO: writePatternToFile
	public void writePatternToFile(String path) {

	}
}
