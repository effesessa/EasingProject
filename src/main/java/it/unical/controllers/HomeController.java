package it.unical.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		// controllo se il corso esiste gi√†
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));

		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Contest contest = new Contest();
		final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
		final Subject subject = subjectDAO.get(Integer.parseInt(addContestForm.getSubjectId()));
		final JuryDAO juryDAO = (JuryDAO) context.getBean("juryDAO");
		final Jury jury = juryDAO.get(Integer.parseInt(addContestForm.getJury()));
		// TODO Effettuare controlli (esistenza Subject/Jury)
		// Controllare struttura DB di Contest: perchÈ FK idcontest-id_jury?

		contest.setName(addContestForm.getName());
		contest.setRestriction(1);
		contest.setSubject(subject);
		contest.setJury(jury);
		contest.setDeadline(
				"" + addContestForm.getYear() + "/" + addContestForm.getMonth() + "/" + addContestForm.getDay());
		logger.info(contest.getSubject().getSubjectId().getId_subject() + " : " + contest.getJury().getId_jury());
		contestDAO.create(contest);
		return "redirect:/";

	}

	// Creazione Subject
	@RequestMapping(value = "/addSubject", method = RequestMethod.POST)
	public String addSubject(@ModelAttribute("addSubjectForm") AddSubjectForm form, HttpSession session, Model model)
	{
		setAccountAttribute(session, model);

		// controllo se il corso esiste gi√†
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		if (form.getName() != "")
		{
			// TODO Effettuare controlli (Subject con quell'Anno)
			final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
			final Subject subject = new Subject();
			final SubjectId subjectId = new SubjectId();
			subjectId.setId_subject(Integer.parseInt(form.getId()));
			subjectId.setYear(form.getYear());
			subject.setSubjectId(subjectId);
			subject.setName(form.getName());
			subject.setPassword(form.getPassword());
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

		// carica attributi utente se √® studente
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
																								// pi√π
																								// submit

			// TODO Possono esserci ripetizioni se uno Studente fa
			// parte di pi˘ Team che partecipano a un Contest, a meno che non si
			// imponga all'iscrizione che uno Studente possa far parte di un
			// solo Team per Contest
			final PartecipationDAO partecipationDAO = (PartecipationDAO) context.getBean("partecipationDAO");
			final ArrayList<Partecipation> contests = new ArrayList<>();
			for (int i = 0; i < teams.size(); i++)
				for (int j = 0; j < partecipationDAO.getContestByTeam(teams.get(i).getId()).size(); j++)
					contests.add(partecipationDAO.getContestByTeam(teams.get(i).getId()).get(j));// sono
																									// pi√π
																									// submit

			model.addAttribute(MODEL_ATTRIBUTE_USER, user);
			// Storico personale
			model.addAttribute("teams", teams);
			model.addAttribute("subjects", subjects);
			model.addAttribute("submits", submits);
			model.addAttribute("contests", contests);
		}
		// carica attributi utente se √® professore
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
		final List<User> result = new ArrayList<User>();
		final List<Subject> result2 = new ArrayList<Subject>();
		final List<Contest> result3 = new ArrayList<Contest>();
		final List<Team> result4 = new ArrayList<Team>();
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

	// Migliorare, cosÏ trova solo con il nome completo
	@RequestMapping(value = "/searchProblem", method = RequestMethod.POST)
	public String searchProblem(@ModelAttribute SearchForm form, HttpSession session, Model model)
	{
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		Contest contest;
		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final List<Problem> problems = problemDAO.getByName(form.getWord());
		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		List<Submit> submit;
		final Map<String, List<Submit>> submits = new HashMap<String, List<Submit>>();

		for (int i = 0; i < problems.size(); i++)
		{
			submit = submitDAO.getAllSubmitByProblem(problems.get(i).getId_problem());
			for (final Submit s : submit)
				logger.info(s.getInfo());
			contest = contestDAO.get(problems.get(i).getId_contest().getIdcontest());
			submits.put(contest.getName(), submit);
			System.out.println(submit.size());
		}
		setAccountAttribute(session, model);
		model.addAttribute("submits", submits);
		model.addAttribute("word", form.getWord());
		// for (Iterator it = submits.keySet().iterator(); it.hasNext();)
		// {
		// Submit submit2 = (Submit) it.next();
		//
		// }
		System.out.println(submits.size());
		for (final String s : submits.keySet())
		{
			logger.info("ENTRATO");
			final List<Submit> ls = submits.get(s);
			System.out.println(ls.size());
			for (final Submit s2 : ls)
				logger.info(s2.getInfo());
		}
		System.out.println("=========");
		for (final Map.Entry<String, List<Submit>> entry : submits.entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue().toString());
			for (final Submit s : entry.getValue())
				System.out.println(s.getInfo());
		}

		return "submitResults";
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
	// TODO Hashing password, controlli (email\matricola gi‡ esistente),
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