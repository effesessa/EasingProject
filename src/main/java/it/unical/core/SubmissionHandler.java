package it.unical.core;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.web.context.WebApplicationContext;
import it.unical.dao.SubmitDAO;
import it.unical.dao.TeamDAO;
import it.unical.entities.Problem;
import it.unical.entities.Submit;
import it.unical.entities.Team;
import it.unical.forms.SubmitForm;
import it.unical.utils.FFileUtils;

/**
 * @author Fabrizio
 */

public class SubmissionHandler {
	
	public static final int MAX_SUBMISSION_SAVED = 5;
	
	public static void save(WebApplicationContext context, Problem problem, SubmitForm submitDTO, Verdict verdict) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				File submittedFile = new File(System.getProperty(Engine.WORKING_DIRECTORY) 
						+ submitDTO.getSolution().getOriginalFilename());
				SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
				TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
				Team team = teamDAO.getByName(submitDTO.getTeam());
				checkAndRemove(problem, submitDAO, team);
				Submit submit = new Submit();
				submit.setIdTeam(team);
				submit.setProblem(problem);
				submit.setInfo(verdict.getStatus());
				if(verdict.getExecutionTime() != null)
					submit.setScore(verdict.getExecutionTime());
				submit.setDate(DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now()));
				byte submittedFileInBytes[] = FFileUtils.readFileToByteArray(submittedFile);
				submit.setSolution(submittedFileInBytes);
				submitDAO.create(submit);
			}
		}).start();
	}
	
	private static void checkAndRemove(Problem problem, SubmitDAO submitDAO, Team team) {
		List<Submit> submits = submitDAO.getAllSubmitByProblemAndTeam(problem.getId_problem(), team.getId());
		if(submits.size() == 5)
			submitDAO.delete(submits.get(submits.size()-1));
	}

}
