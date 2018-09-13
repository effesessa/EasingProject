package it.unical.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import it.unical.dao.ContestDAO;
import it.unical.dao.RegistrationDAO;
import it.unical.dao.SubjectDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.Registration;
import it.unical.entities.Subject;
import it.unical.entities.User;
import it.unical.forms.SubjectPasswordForm;
import it.unical.utils.SessionUtils;

@Controller
public class SubjectController
{

	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

	@Autowired
	private WebApplicationContext context;

	private void setAccountAttribute(HttpSession session, Model model)
	{
		if (SessionUtils.isUser(session))
		{
			final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
			final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
			model.addAttribute("user", user);
			model.addAttribute("typeSession", "Account");
		}
		else
			model.addAttribute("typeSession", "Login");
	}

	// Visualizza la Subject richiesta e i suoi Contest
	@RequestMapping(value = "/subject", method = RequestMethod.GET)
	public String subjectMainView(@RequestParam String name, HttpSession session, Model model)
	{

		final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
		final Subject subject = subjectDAO.get(name);
		setAccountAttribute(session, model);
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final List<Contest> contests = contestDAO.getContestBySubject(subject.getSubjectId().getId_subject(),
				Integer.parseInt(subject.getSubjectId().getYear()));

		model.addAttribute("contests", contests);
		model.addAttribute("name", name);
		return "subjectview";

	}

	// Iscrizione a una Subject se non già iscritto
	@RequestMapping(value = "/signUpSubject", method = RequestMethod.POST)
	public String subjectSignUp(HttpSession session, @RequestParam String name,
			@ModelAttribute("subjectPasswordForm") SubjectPasswordForm form, Model model)
	{
		if (!SessionUtils.isUser(session))
			return null;
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
		final Subject subject = subjectDAO.get(name);
		final RegistrationDAO registrationDAO = (RegistrationDAO) context.getBean("registrationDAO");
		Registration registration = registrationDAO.getRegistration(user.getId(), subject.getSubjectId());
		setAccountAttribute(session, model);
		if (subject.getPassword().equals(form.getpassword()))
			if (registration == null)
			{
				registration = new Registration();
				registration.setSubject(subject);
				registration.setUser(user);
				registrationDAO.create(registration);
				logger.info("Registrazione effettuata con successo");
				return "redirect:/";
			}
			else
			{
				logger.info("Risulti già registrato a questo Corso");
				return "redirect:/";
			}

		logger.info("Password non corretta");
		return "redirect:/";

	}
}
