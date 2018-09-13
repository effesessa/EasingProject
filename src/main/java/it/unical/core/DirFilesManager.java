package it.unical.core;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

public class DirFilesManager
{

	public static final int SUBMITTED_FILE = 0;

	public static final int TEST_CASE_FILE = 1;

	private final File randomDirectory;

	private File submittedFile;

	private File testCaseFile;

	private String nameArchive;

	public DirFilesManager()
	{
		final String dirRandom = RandomStringUtils.randomAlphanumeric(8);
		randomDirectory = new File(workingDirSeparator() + dirRandom);
		randomDirectory.mkdir();
	}

	public File convert(MultipartFile multipartFile)
	{
		submittedFile = new File(randomDirectory.getAbsolutePath() + separator() + multipartFile.getOriginalFilename());
		try
		{
			submittedFile.createNewFile();
			if (submittedFile != null)
				multipartFile.transferTo(submittedFile);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		return submittedFile;
	}

	public File createTestCaseFile(String fileName)
	{
		testCaseFile = new File(randomDirectory.getAbsolutePath() + separator() + fileName);
		try
		{
			testCaseFile.createNewFile();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		return testCaseFile;
	}

	public String getPathArchive(String nameArchive)
	{
		this.nameArchive = nameArchive;
		final File file = new File(randomDirectory.getAbsolutePath() + separator() + nameArchive);
		if (!file.exists())
			file.mkdir();
		return file.getAbsolutePath();
	}

	public String getPathArchiveAndFile(String extractedFileName)
	{
		return nameArchive + separator() + extractedFileName;
	}

	public String getPathRandomDirectory()
	{
		return randomDirectory.getAbsolutePath();
	}

	public File getRandomDirectory()
	{
		return randomDirectory;
	}

	public File getSubmittedFile()
	{
		return submittedFile;
	}

	public File getTestCaseFile()
	{
		return testCaseFile;
	}

	public String nameRandomDir()
	{
		return randomDirectory.getName();
	}

	public String separator()
	{
		return System.getProperty("file.separator");
	}

	public String separatorAndNameRandomDir()
	{
		return separator() + randomDirectory.getName();
	}

	private String workingDirSeparator()
	{
		return System.getProperty(Engine.WORKING_DIRECTORY) + separator();
	}

}
