package hedspi.tienlv.grapp.utils.model;

public class FileExtractor {
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
