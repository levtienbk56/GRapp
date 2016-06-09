package hedspi.tienlv.grapp.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Lev Tien
 */
public class MyFile {

	/**
	 * read buffer content in a file
	 *
	 * @param pathFile
	 *            path of file
	 * @return buffer of content. you can extract: String line; while ((line =
	 *         reader.readLine()) != null){}
	 */
	public static BufferedReader readFile(String pathFile) throws Exception {
		InputStream in = null;
		BufferedReader reader = null;
		try {
			Path file = Paths.get(pathFile);
			in = Files.newInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(in);
			reader = new BufferedReader(inputStreamReader);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new Exception("error read file: " + pathFile);
		}
		return reader;
	}

	/**
	 * write string of content to the file
	 *
	 * @param pathFile
	 *            path of file
	 * @param content
	 *            string of content
	 */
	public static void writeToFile(File file, String content) throws Exception {
		try {
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// append to file
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void cleanFile(File file) throws IOException {
		FileWriter fileOut = new FileWriter(file);
		fileOut.write("");
		fileOut.close();
	}

	public static String getTypeOfFile(String path) {
		// check file type
		List<String> types = Arrays.asList(path.split("\\."));
		System.out.println(path);
		String typeFile = types.get(types.size() - 1);
		return typeFile;
	}

	public static String getFileName(String path) {
		// check file type
		List<String> temp = Arrays.asList(path.split("\\\\"));
		String fullName = temp.get(temp.size() - 1);
		temp = Arrays.asList(fullName.split("\\."));
		String name = temp.get(0);
		return name;
	}

	public static void createFolder(String name) throws Exception {
		try {
			File f = new File(name);
			if (!f.exists()) {
				f.mkdir();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("error create folder:" + name);
		}
	}

}
