package it.unical.core;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.WebApplicationContext;

import it.unical.dao.SubmitDAO;
import it.unical.dao.TeamDAO;
import it.unical.entities.Problem;
import it.unical.entities.Submit;
import it.unical.entities.Team;
import it.unical.forms.SubmitForm;
import it.unical.utils.FFileUtils;
import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public class SubmissionHandler
{

	public static final int MAX_SUBMISSION_SAVED = 5;

	private static void checkAndRemove(Problem problem, SubmitDAO submitDAO, Team team)
	{
		final List<Submit> submits = submitDAO.getAllSubmitByProblemAndTeam(problem.getId_problem(), team.getId());
		if (submits.size() == 5)
			submitDAO.delete(submits.get(submits.size() - 1));
	}

	public static void save(WebApplicationContext context, Problem problem, SubmitForm submitDTO, Verdict verdict,
			DirFilesManager dirFilesManager)
	{
		final File submittedFile = dirFilesManager.getSubmittedFile();
		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		final Team team = teamDAO.getByName(submitDTO.getTeam());
		checkAndRemove(problem, submitDAO, team);
		final Submit submit = new Submit();
		submit.setIdTeam(team);
		submit.setProblem(problem);
		submit.setInfo(verdict.getStatus());
		if (verdict.getErrorText() != null && !verdict.getErrorText().equals(""))
			submit.setError(verdict.getErrorText());
		if (verdict.getTestCaseFailed() != null && !verdict.getTestCaseFailed().equals(""))
			submit.setTestCaseFailed(verdict.getTestCaseFailed());
		if (verdict.getExecutionTime() != null)
			submit.setScore(verdict.getExecutionTime());
		submit.setDate(DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now()));
		final byte submittedFileInBytes[] = FFileUtils.readFileToByteArray(submittedFile);
		submit.setSolution(submittedFileInBytes);
		submit.setType(StringUtils.getExtension(submittedFile.getName()));
		submitDAO.create(submit);
		FileUtils.deleteQuietly(dirFilesManager.getRandomDirectory());
	}

}
