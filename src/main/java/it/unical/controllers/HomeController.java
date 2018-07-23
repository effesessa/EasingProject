package it.unical.controllers;

import java.util.ArrayList;
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
import org.springframework.web.context.WebApplicationContext;

import it.unical.dao.ContestDAO;
import it.unical.dao.JuryDAO;
import it.unical.dao.JuryMemberDAO;
import it.unical.dao.MembershipDAO;
import it.unical.dao.PartecipationDAO;
import it.unical.dao.ProblemDAO;
import it.unical.dao.RegistrationDAO;
import it.unical.dao.SubjectDAO;
import it.unical.dao.SubmitDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.Jury;
import it.unical.entities.JuryMember;
import it.unical.entities.Membership;
import it.unical.entities.Partecipation;
import it.unical.entities.Problem;
import it.unical.entities.Registration;
import it.unical.entities.Subject;
import it.unical.entities.SubjectId;
import it.unical.entities.Submit;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.AddContestForm;
import it.unical.forms.AddSubjectForm;
import it.unical.forms.SearchForm;
import it.unical.forms.SignInForm;
import it.unical.utils.SessionUtils;

@Controller
public class HomeController
{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String MODEL_ATTRIBUTE_USER = "user";

	@Autowired
	private WebApplicationContext context;

	// Creazione Contest
	@RequestMapping(value = "/addContest", method = RequestMethod.POST)
	public String addContest(@ModelAttribute AddContestForm addContestForm, /*
																			 * @RequestParam
																			 * String
																			 * restriction
																			 */
			HttpSession session, Model model)
	{
		setAccountAttribute(session, model);
		// controllo se il corso esiste già
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));

		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Contest contest = new Contest();
		final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
		final Subject subject = subjectDAO.get(Integer.parseInt(addContestForm.getSubjectId()));
		final JuryDAO juryDAO = (JuryDAO) context.getBean("juryDAO");
		final Jury jury = juryDAO.get(Integer.parseInt(addContestForm.getJury()));
		// TODO Effettuare controlli (esistenza Subject/Jury)
		// Controllare struttura DB di Contest: perch� FK idcontest-id_jury?

		contest.setName(addContestForm.getName());
		contest.setRestriction(1);
		contest.setSubject(subject);
		contest.setJury(jury);
		contest.setDeadline(addContestForm.getDeadline());
		contestDAO.create(contest);
		return "redirect:/";

	}

	// Creazione Subject
	@RequestMapping(value = "/addSubject", method = RequestMethod.POST)
	public String addSubject(@ModelAttribute("addSubjectForm") AddSubjectForm form, HttpSession session, Model model)
	{
		setAccountAttribute(session, model);

		// controllo se il corso esiste già
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		if (form.getName() != "")
		{
			// TODO Effettuare controlli (Subject con quell'Anno)
			final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
			final Subject subject = new Subject();
			final SubjectId subjectId = new SubjectId();
			subjectId.setId_subject((int) (subjectDAO.getLastID() + 1));
			subjectId.setYear(form.getYear());
			subject.setSubjectId(subjectId);
			subject.setName(form.getName());
			subject.setPassword(form.getPassword());
			subject.setUrl(form.getUrl());
			subject.setId_professor(user);
			subjectDAO.create(subject);

			return "redirect:/";
		}
		else
			return "redirect:/";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session)
	{
		logger.info("Home page requested.");

		if (SessionUtils.isUser(session))
		{
			populateUserModel(session, model);
			return "homeUser";
		}
		return "login";
	}

	private void populateUserModel(HttpSession session, Model model)
	{
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));

		// carica attributi utente se è studente
		if (!user.isProfessor())
		{
			final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
			final List<Membership> memberships = membershipDAO
					.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));
			final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
			final ArrayList<Team> teams = new ArrayList<>();
			for (int i = 0; i < memberships.size(); i++)
				teams.add(teamDAO.get(memberships.get(i).getTeam().getId()));

			final RegistrationDAO registrationDAO = (RegistrationDAO) context.getBean("registrationDAO");
			final List<Registration> registrations = registrationDAO
					.getRegistrationByStudent(SessionUtils.getUserIdFromSessionOrNull(session));
			final ArrayList<String> subjects = new ArrayList<>();
			for (int i = 0; i < registrations.size(); i++)
				subjects.add(registrations.get(i).getSubject().getName());

			final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
			final ArrayList<Submit> submits = new ArrayList<>();
			for (int i = 0; i < teams.size(); i++)
				for (int j = 0; j < submitDAO.getAllSubmitByTeam(teams.get(i).getId()).size(); j++)
					submits.add(submitDAO.getAllSubmitByTeam(teams.get(i).getId()).get(j));// sono
																							// più
																							// submit

			// TODO Possono esserci ripetizioni se uno Studente fa
			// parte di pi� Team che partecipano a un Contest, a meno che non si
			// imponga all'iscrizione che uno Studente possa far parte di un
			// solo Team per Contest
			final PartecipationDAO partecipationDAO = (PartecipationDAO) context.getBean("partecipationDAO");
			final ArrayList<Partecipation> contests = new ArrayList<>();
			for (int i = 0; i < teams.size(); i++)
				for (int j = 0; j < partecipationDAO.getContestByTeam(teams.get(i).getId()).size(); j++)
					contests.add(partecipationDAO.getContestByTeam(teams.get(i).getId()).get(j));// sono
																									// più
																									// submit

			model.addAttribute(MODEL_ATTRIBUTE_USER, user);
			// Storico personale
			model.addAttribute("teams", teams);
			model.addAttribute("subjects", subjects);
			model.addAttribute("submits", submits);
			model.addAttribute("contests", contests);
		}
		// carica attributi utente se è professore
		else
		{
			final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
			final List<Subject> subjects = subjectDAO.getAllSubjectFromProfessor(user.getId());

			final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
			final ArrayList<Contest> contests = new ArrayList<>();
			for (int i = 0; i < subjects.size(); i++)
			{
				final int size = contestDAO.getContestBySubject(subjects.get(i).getSubjectId().getId_subject(),
						Integer.parseInt(subjects.get(i).getSubjectId().getYear())).size();
				for (int j = 0; j < size; j++)
					contests.add(contestDAO.getContestBySubject(subjects.get(i).getSubjectId().getId_subject(),
							Integer.parseInt(subjects.get(i).getSubjectId().getYear())).get(j));
			}

			final JuryMemberDAO juryMemberDAO = (JuryMemberDAO) context.getBean("jurymemberDAO");
			final List<JuryMember> juries = juryMemberDAO.getJurysFromProfessor(user.getId());
			final ArrayList<Contest> contestJury = new ArrayList<>();
			logger.info("contest con giuria   " + juries.size());
			for (int i = 0; i < juries.size(); i++)
				for (int j = 0; j < contestDAO.getContestByJury(juries.get(i).getJury().getId_jury()).size(); j++)
				{
					contestJury.add(contestDAO.getContestByJury(juries.get(i).getJury().getId_jury()).get(j));
					logger.info(contestDAO.getContestByJury(juries.get(i).getJury().getId_jury()).get(j).getName());
				}
			logger.info("Juries size: " + juries.size());
			logger.info("CJ size: " + contestJury.size());
			model.addAttribute(MODEL_ATTRIBUTE_USER, user);
			model.addAttribute("subjects", subjects);
			model.addAttribute("contests", contests);
			model.addAttribute("contestjuries", contestJury);
		}

	}

	// research back-end
	// Fare query direttamente con la ricerca potrebbe essere meglio?
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@ModelAttribute SearchForm form, HttpSession session, Model model)
	{
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final List<User> users = userDAO.getAll();
		final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
		final List<Subject> subjects = subjectDAO.getAll();
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final List<Contest> contests = contestDAO.getAll();
		final List<User> result = new ArrayList<>();
		final List<Subject> result2 = new ArrayList<>();
		final List<Contest> result3 = new ArrayList<>();
		final List<Team> result4 = new ArrayList<>();
		final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
		final List<Membership> memberships = membershipDAO
				.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));
		final String searchText = form.getWord();
		boolean isNumber = false;
		if (searchText.matches("-?\\d+"))
			isNumber = true;

		for (int i = 0; i < users.size(); i++)
			if (users.get(i).getId().toString().contains(searchText))
				result.add(users.get(i));
		for (int j = 0; j < subjects.size(); j++)
			if (subjects.get(j).getName().toLowerCase().contains(searchText.toLowerCase())
					|| isNumber && subjects.get(j).getSubjectId().getId_subject() == Integer.parseInt(searchText))
				result2.add(subjects.get(j));

		for (int k = 0; k < contests.size(); k++)
			if (contests.get(k).getName().toLowerCase().contains(searchText.toLowerCase())
					|| isNumber && contests.get(k).getIdcontest() == Integer.parseInt(searchText))
				result3.add(contests.get(k));
		for (final Membership membership : memberships)
			result4.add(membership.getTeam());

		setAccountAttribute(session, model);
		model.addAttribute("UserResult", result);
		model.addAttribute("SubjectResult", result2);
		model.addAttribute("ContestResult", result3);
		model.addAttribute("TeamResult", result4);

		return "searchResults";
	}

	@RequestMapping(value = "/searchProblem", method = RequestMethod.POST)
	public String searchProblem(@ModelAttribute SearchForm form, HttpSession session, Model model)
	{
		setAccountAttribute(session, model);
		final User user = (User) model.asMap().get("user");
		if (user == null || !user.isProfessor())
			return "redirect:/";

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final List<Problem> problems = problemDAO.getAllProblemsByTagOrLikeName(form.getWord());
		System.out.println("word:" + form.getWord());
		System.out.println("getAllProblemsByLikeTagOrLikeName:" + problems.size());
		for (final Problem problem : problems)
			System.out.println(problem.getName());
		model.addAttribute("problems", problems);
		return "searchProblems";
	}

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

	// Iscrizione
	// TODO Hashing password, controlli (email\matricola gi� esistente),
	// conferma email (?)
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signIn(@ModelAttribute SignInForm form, HttpSession session, Model model)
	{
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = new User(Integer.parseInt(form.getId()), form.getName(), form.getSurname(),
				form.getPassword(), form.getEmail(), false);
		userDAO.create(user);
		return "redirect:/";
	}

}