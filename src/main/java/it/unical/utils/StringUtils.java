package it.unical.utils;

import java.io.File;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import it.unical.core.Engine;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

/**
 * @author Fabrizio
 */

public class StringUtils {
	
	public static final String UTF8_BOM = "\uFEFF";
	
	public static final Pattern DIACRITICS_AND_FRIENDS = Pattern.compile(
            "[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");
	
	public static String checkAndRemoveUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1);
        }
        return s;
    }
	
	public static String stripDiacritics(String str) {
	    str = Normalizer.normalize(str, Normalizer.Form.NFD);
	    str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
	    return str;
	}
	
	public static String capitalize(String file) {
		file.toLowerCase();
		return file.substring(0, 1).toUpperCase() + file.substring(1);
	}
	
	public static String getExtension(String file) {
		return FilenameUtils.getExtension(file);
	}
	
	public static String getBaseName(String file) {
		return FilenameUtils.getBaseName(file);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean compatible(MultipartFile multipartFile, String oldType) {
		String newType = StringUtils.getExtension(multipartFile.getOriginalFilename());
		if(newType.equals(oldType))
			return true;
		if(newType.equals(TypeFileExtension.ZIP)) {
			File file = MultipartFileUtils.convert(multipartFile);
			boolean test = true;
			try {
				ZipFile zipFile = new ZipFile(file);
				List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
				for (int i = 0; i < fileHeaderList.size(); i++) {
					FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
					if(!StringUtils.getExtension(fileHeader.getFileName()).equals(oldType)) {
						test = false;
						break;
					}
				}
				FFileUtils.deleteFile(file);
				return test;
			} 
			catch (ZipException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
