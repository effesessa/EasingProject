package it.unical.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import it.unical.dao.ContestDAO;
import it.unical.dao.QuizDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.Quiz;
import it.unical.entities.User;
import it.unical.forms.AddQuizForm;
import it.unical.utils.SessionUtils;

@Controller
public class QuizController {

	@Autowired
	private WebApplicationContext context;

	private void _setAccountAttribute(final HttpSession session, final Model model) {
		if (SessionUtils.isUser(session)) {
			final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
			final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
			model.addAttribute("user", user);
			model.addAttribute("typeSession", "Account");
			model.addAttribute("userLogged", true);
		} else
			model.addAttribute("typeSession", "Login");
	}
	
	@RequestMapping(value="/addQuiz",  method = RequestMethod.POST)
	public String addQuiz(final HttpSession session, @ModelAttribute final AddQuizForm addQuizForm, final Model model) {
		_setAccountAttribute(session, model);
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Contest contest = contestDAO.getContestByName(addQuizForm.getContestName());
		final Quiz quiz = new Quiz();
		quiz.setName(addQuizForm.getName());
		quiz.setContest(contest);
		quiz.setPoints(addQuizForm.getPoints());
		quizDAO.create(quiz);
		return "redirect:/";
	}

}