package it.unical.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import it.unical.dao.ContestDAO;
import it.unical.dao.ProblemConstraintDAO;
import it.unical.dao.ProblemDAO;
import it.unical.dao.QuizConstraintDAO;
import it.unical.dao.QuizDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.ProblemConstraint;
import it.unical.entities.QuizConstraint;
import it.unical.entities.User;
import it.unical.forms.ManageExamForm;
import it.unical.utils.SessionUtils;

@Controller
public class ExamController
{

	@Autowired
	private WebApplicationContext context;

	private void _setAccountAttribute(HttpSession session, Model model)
	{
		if (SessionUtils.isUser(session))
		{
			final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
			final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
			model.addAttribute("user", user);
			model.addAttribute("typeSession", "Account");
			model.addAttribute("userLogged", true);
		}
		else
			model.addAttribute("typeSession", "Login");
	}

	@RequestMapping(value = "/manageExam", method = RequestMethod.POST)
	public String manageExam(@ModelAttribute ManageExamForm form, final HttpSession session, final Model model)
	{
		_setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final ProblemConstraintDAO problemConstraintDAO = (ProblemConstraintDAO) context
				.getBean("problemConstraintDAO");
		final QuizConstraintDAO quizConstraintDAO = (QuizConstraintDAO) context.getBean("quizConstraintDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");

		final Contest contest = contestDAO.get(form.getContestID());

		final Integer userID = SessionUtils.getUserIdFromSessionOrNull(session);
		if (userID != null && userDAO.get(userID).isProfessor())
			for (final Map.Entry<String, Integer> entry : form.getMinProblems().entrySet())
			{
				// TODO Evitare di creare il Constraint se minProblems e
				// minPoints sono 0
				// TODO Eliminare i vecchi Constraint di quel Contest per
				// evitare duplicazioni
				final String key = entry.getKey();
				switch (key.substring(0, 1))
				{
				case "q":
					final QuizConstraint quizConstraint = new QuizConstraint();
					quizConstraint.setContest(contest);
					quizConstraint.setMinCorrects(entry.getValue());
					quizConstraint.setMinPoints(form.getMinPoints().get(key));
					quizConstraint.setQuiz(quizDAO.get(Integer.parseInt(key.substring(1, key.length()))));
					quizConstraintDAO.create(quizConstraint);
					// contest.getQuizConstraints().add(quizConstraint);
					break;
				case "p":
					final ProblemConstraint problemConstraint = new ProblemConstraint();
					problemConstraint.setContest(contest);
					problemConstraint.setMinCorrects(entry.getValue());
					problemConstraint.setMinPoints(form.getMinPoints().get(key));
					problemConstraint.setProblem(problemDAO.get(Integer.parseInt(key.substring(1, key.length()))));
					problemConstraintDAO.create(problemConstraint);
					// contest.getProblemConstraints().add(problemConstraint);
					break;
				default:
					break;
				}
			}
		// contestDAO.update(contest);
		return "redirect:/";
	}

	@RequestMapping(value = "/examVisibility", method = RequestMethod.POST)
	public void toggleVisibility(@RequestParam Integer contestID, final HttpSession session, final Model model,
			HttpServletResponse response)
	{
		_setAccountAttribute(session, model);
		final User user = (User) model.asMap().get("user");

		if (user != null && user.isProfessor())
		{
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
			final Contest contest = contestDAO.get(contestID);
			contest.setVisible(!contest.isVisible());
			contestDAO.update(contest);

		}
	}
}
