package it.unical.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

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
}
