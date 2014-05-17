package main.java.info.jtrac.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public abstract class FileUtil {
	/**
	 * 
	 * @param path as String
	 * <li>example : "/tmp/uploads/"</li>
	 * @param filename as String
	 * @param mimeType as String
	 * @return
	 */
	public static OutputStream createOutputStream(String path, String filename, String mimeType) {
		/*
		 * Create upload stream
		 */
		FileOutputStream fos = null;
		try {
			// Open the file for writing.
			File file = new File( path + filename);
			fos = new FileOutputStream(file);
		} catch (final java.io.FileNotFoundException e) {
			return null;
		}
		return fos;
	}
}
