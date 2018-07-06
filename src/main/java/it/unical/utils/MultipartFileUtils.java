package it.unical.utils;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Fabrizio
 */

public class MultipartFileUtils {
	
	public static File convert(MultipartFile multipartFile) {
		File file = FFileUtils.createNewFile(multipartFile.getOriginalFilename());
		if(file != null) {
			try {
				multipartFile.transferTo(file);
			} 
			catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
