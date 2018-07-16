package it.unical.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import it.unical.core.Engine;

/**
 * @author Fabrizio
 */

public class FFileUtils {
	
	public static void deleteDirectory(File directory) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					FileUtils.deleteDirectory(directory);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static void deleteFile(File file) {
		FileUtils.deleteQuietly(file);
	}
	
	public static File createNewFile(String name) {
		File file = new File(System.getProperty(Engine.WORKING_DIRECTORY) + name);
		try {
			file.createNewFile();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public static File createNewFile(String fileName, String directory) {
		File file = new File(directory + System.getProperty("file.separator") + fileName);
		try {
			file.createNewFile();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public static boolean writeByteArrayToFile(File file, byte data[]) {
		try {
			FileUtils.writeByteArrayToFile(file,data);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static byte[] readFileToByteArray(File file) {
		byte bytes[] = null;
		try {
			bytes = FileUtils.readFileToByteArray(file);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static File makeRandomDirectory() {
		File file = new File(System.getProperty(Engine.WORKING_DIRECTORY) + System.getProperty("file.separator") + RandomStringUtils.randomAlphanumeric(8));
		file.mkdir();
		return file;
	}
}
