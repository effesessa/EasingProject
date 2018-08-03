package it.unical.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.core.Engine;
import it.unical.dao.ContestDAO;
import it.unical.dao.JuryMemberDAO;
import it.unical.dao.MembershipDAO;
import it.unical.dao.PartecipationDAO;
import it.unical.dao.ProblemDAO;
import it.unical.dao.QuizDAO;
import it.unical.dao.RegistrationDAO;
import it.unical.dao.SubjectDAO;
import it.unical.dao.SubmitDAO;
import it.unical.dao.SubmitQuizDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Answer;
import it.unical.entities.Contest;
import it.unical.entities.JuryMember;
import it.unical.entities.Membership;
import it.unical.entities.Partecipation;
import it.unical.entities.Problem;
import it.unical.entities.ProblemConstraint;
import it.unical.entities.Question;
import it.unical.entities.Quiz;
import it.unical.entities.QuizConstraint;
import it.unical.entities.Subject;
import it.unical.entities.Submit;
import it.unical.entities.SubmitQuiz;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.SubscribeForm;
import it.unical.utils.SessionUtils;
import it.unical.utils.Status;
import it.unical.utils.TypeFileExtension;

@Controller
public class ContestController
{

	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

	@Autowired
	private WebApplicationContext context;

	@RequestMapping(value = "/createContest", method = RequestMethod.GET)
	public void addContest(@RequestParam String req, HttpSession session, Model model, HttpServletResponse response)
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		final ObjectMapper mapper = new ObjectMapper();
		final int userID = SessionUtils.getUserIdFromSessionOrNull(session);
		if (SessionUtils.isLoggedIn(session) && req.equals("subjects"))
		{
			final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
			final List<Subject> subjects = subjectDAO.getAllSubjectFromProfessor(userID);
			final Map<Integer, String> subjs = new HashMap<>();
			for (final Subject s : subjects)
				subjs.put(s.getSubjectId().getId_subject(), s.getName());
			try
			{
				mapper.writeValue(response.getOutputStream(), subjs);
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}
		else if (SessionUtils.isLoggedIn(session) && req.equals("juries"))
		{
			final JuryMemberDAO juryDAO = (JuryMemberDAO) context.getBean("jurymemberDAO");
			final List<JuryMember> juries = juryDAO.getJurysFromProfessor(userID);
			final List<Integer> juriesNames = new ArrayList<>();
			for (final JuryMember j : juries)
				juriesNames.add(j.getId());
			try
			{
				mapper.writeValue(response.getOutputStream(), juriesNames);
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// Visualizza il Contest e la lista dei Problemi
	@RequestMapping(value = "/contest", method = RequestMethod.GET)
	public String contestMainView(@RequestParam String name, HttpSession session, Model model)
	{
		final User user = (User) model.asMap().get("user");
		if (user == null || user.isProfessor() || name == null)
			return "redirect:/";

		setAccountAttribute(session, model);
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Contest contest = contestDAO.getContestByName(name);
		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");
		final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
		final List<Membership> memberships = membershipDAO
				.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));
		final PartecipationDAO partecipationDAO = (PartecipationDAO) context.getBean("partecipationDAO");
		final SubmitQuizDAO submitQuizDAO = (SubmitQuizDAO) context.getBean("submitQuizDAO");
		final ArrayList<Team> teams = new ArrayList<>(memberships.size());
		for (int i = 0; i < memberships.size(); i++)
			teams.add(memberships.get(i).getTeam());

		if (contest.isExam())
		{
			boolean registered = false;
			for (final Team team : teams)
				if (partecipationDAO.getTeamPartecipation(team.getId(), contest.getIdcontest()) != null)
					registered = true;
			if (!registered)
				return "redirect:/";
		}

		final List<Submit> submitsByAllJoinedTeams = new LinkedList<>();
		for (final Team team : teams)
		{
			final List<Submit> submitByTeam = submitDAO.getAllSubmitByTeam(team.getId());
			submitsByAllJoinedTeams.addAll(submitByTeam);
		}

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final List<Problem> problems = problemDAO.getProblemOfAContest(contest.getIdcontest());
		for (final Problem p : problems)
			p.setDescription(p.getDescription().replaceAll(System.getProperty("line.separator"), "<br />"));
		final List<Quiz> quizzes = quizDAO.getAllQuizByContest(contest.getIdcontest());
		
		
		/**
		 * 
		 */
		//init boolean array
		final List<Boolean> quizzesToShow = new ArrayList<>(Arrays.asList(new Boolean[quizzes.size()]));
		Collections.fill(quizzesToShow, Boolean.TRUE);
		final List<Boolean> problemsToShow = new ArrayList<>(Arrays.asList(new Boolean[problems.size()]));
		Collections.fill(problemsToShow, Boolean.TRUE);
		//init constraint of a contest
		Contest contestFetch = contestDAO.getFetchJoinConstraints(contest.getIdcontest());
		
		//if contest has constraints for its quizzes
		if (!contest.getQuizConstraints().isEmpty()) {
			int i = 0;
			for (final Quiz quiz : quizzes) {
				boolean passed = true;
				int totalScore = 0;
				int correct = 0;
				for (final QuizConstraint quizConstraint : contestFetch.getQuizConstraints()) {
					if (quizConstraint.getQuiz().getId().equals(quiz.getId())) {
						for (final Quiz quiz2 : quizzes) {
							if (!quiz2.getId().equals(quiz.getId())) {
								final SubmitQuiz submitQuiz = submitQuizDAO.getByTeamAndQuiz(teams.get(0), quiz2);
								if (submitQuiz != null)
									totalScore+=submitQuiz.getTotalScore();
							}
						}
						for (final Problem problem : problems) {
							final List<Submit> submits = submitDAO.getAllSubmitByProblemAndTeam(problem.getId_problem(), teams.get(0).getId());
							for (final Submit submit : submits) {
								if (submit.getInfo().equals(Status.CORRECT)) {
									correct++;
									break;
								}
							}
						}
						if (totalScore < quizConstraint.getMinPoints() || correct < quizConstraint.getMinCorrects())
							passed = false;
						break;
					}
				}
				quizzesToShow.set(i, passed);
				i++;
			}
		}
		//if contest has constraints for its problems
				if (!contest.getProblemConstraints().isEmpty()) {
					int i = 0;
					for (final Problem problem : problems) {
						boolean passed = true;
						int totalScore = 0;
						int correct = 0;
						for (final ProblemConstraint problemConstraint : contestFetch.getProblemConstraints()) {
							if (problemConstraint.getProblem().getId_problem().equals(problem.getId_problem())) {
								for (final Problem problem2 : problems) {
									if (!problem2.getId_problem().equals(problem.getId_problem())) {
										final List<Submit> submits = submitDAO.getAllSubmitByProblemAndTeam(problem2.getId_problem(), teams.get(0).getId());
										for (final Submit submit : submits) {
											if (submit.getInfo().equals(Status.CORRECT)) {
												correct++;
												break;
											}
										}
									}
								}
								for (final Quiz quiz : quizzes) {
									final SubmitQuiz submitQuiz = submitQuizDAO.getByTeamAndQuiz(teams.get(0), quiz);
									if (submitQuiz != null)
										totalScore+=submitQuiz.getTotalScore();
								}
								if (totalScore < problemConstraint.getMinPoints() || correct < problemConstraint.getMinCorrects())
									passed = false;
								break;
							}
						}
						problemsToShow.set(i, passed);
						i++;
					}
				}
		/**
		 * 
		 */
		// debugQuiz(quizs);
		model.addAttribute("quizzes", quizzes);
		model.addAttribute("submits", submitsByAllJoinedTeams);
		model.addAttribute("teams", teams);
		if (!problems.isEmpty())
			model.addAttribute("problems", problems);
		else
			model.addAttribute("problems", "");
		model.addAttribute("contest", contest.getIdcontest());
		model.addAttribute("quizzesToShow", quizzesToShow);
		model.addAttribute("problemsToShow", problemsToShow);
		return "contest";
	}

	// Iscrizione a un Contest
	@RequestMapping(value = "/subscribeContest", method = RequestMethod.POST)
	public String contestSignUp(HttpSession session, @ModelAttribute SubscribeForm subscribeForm, Model model)
	{
		// controlli del team (team già iscritto e membri iscritti al corso
		// oppure è professore)
		setAccountAttribute(session, model);

		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		final Team team = teamDAO.getByName(subscribeForm.getTeam());
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Contest contest = contestDAO.get(Integer.parseInt(subscribeForm.getContest()));
		final PartecipationDAO partecipationDAO = (PartecipationDAO) context.getBean("partecipationDAO");
		Partecipation partecipation = partecipationDAO.getTeamPartecipation(team.getId(), contest.getIdcontest());
		final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
		final Subject subject = subjectDAO.get(contest.getSubject().getSubjectId().getId_subject());
		final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");

		if (contest.isExam())
		{
			final List<Membership> memberships = membershipDAO
					.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));
			final ArrayList<Team> teams = new ArrayList<>();
			for (int i = 0; i < memberships.size(); i++)
				teams.add(memberships.get(i).getTeam());

			if (!subscribeForm.getPassword().equals(contest.getPassword()))
			{
				logger.info("Password errata");
				return "redirect:/";
			}
			for (final Team t : teams)
			{
				final Partecipation p = partecipationDAO.getTeamPartecipation(t.getId(), contest.getIdcontest());
				if (p != null)
				{
					logger.info("Un Team di cui fai parte � gi� iscritto a quest'Esame");
					return "redirect:/";
				}
			}

		}

		if (partecipation != null)
		{
			logger.info("il team è già iscritto");

			model.addAttribute("name", contest.getName());
			return "redirect:/contest";
		}
		else
		{
			// controllo se i membri sono iscritti al corso e se il prof ha
			// stabilito tale regola!
			final List<Membership> members = membershipDAO.getStudentsByTeam(team.getId());
			final RegistrationDAO registrationDAO = (RegistrationDAO) context.getBean("registrationDAO");
			for (int i = 0; i < members.size(); i++)
				if (registrationDAO.getRegistration(members.get(i).getUser().getId(), subject.getSubjectId()) == null)
				{
					logger.info("non tutti i membri sono iscritti al corso");
					return "redirect:/contest?name=" + subject.getName();
				}
			// tutti i membri sono iscritti quindi possono registrarsi
			partecipation = new Partecipation();
			partecipation.setContest(contest);
			partecipation.setTeam(team);
			partecipationDAO.create(partecipation);
			return "redirect:/";
		}
	}

	public void debugQuiz(List<Quiz> quizs)
	{
		System.out.println("**************************debug quiz**************************");
		for (final Quiz quiz : quizs)
		{
			System.out.println("Quiz:" + quiz.getName());
			for (final Question question : quiz.getQuestions())
			{
				System.out.println("Question:" + question.getText());
				for (final Answer answer : question.getAnswers())
					System.out.println("Answer:" + answer.getText());
			}
			System.out.println("__________________________________________________________");
		}
		System.out.println("**************************debug quiz**************************");
	}

	@RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
	public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response)
	{
		// response.setContentType("application/pdf");
		try
		{
			// get your file as InputStream
			// InputStream is = ...;
			// copy it to response's OutputStream
			final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
			final Problem problem = problemDAO.get(Integer.valueOf(fileName));
			// final ByteArrayInputStream is = new
			// ByteArrayInputStream(problem.getDownload());
			// org.apache.commons.io.IOUtils.copy(is,
			// response.getOutputStream());
			// response.flushBuffer();

			// response.getOutputStream().write(problem.getDownload());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=" + problem.getName());
			final byte[] data = problem.getDownload();
			response.setContentLength(data.length);

			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		}
		catch (final IOException ex)
		{
			logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
			throw new RuntimeException("IOError writing file to output stream");
		}

	}

	@RequestMapping(value = "/testCase/output/{id_problem}", method = RequestMethod.GET)
	public void getSolution(@PathVariable("id_problem") String id_problem, HttpServletResponse response,
			HttpSession session)
	{
		if (SessionUtils.isLoggedIn(session))
			try
			{
				final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
				final Problem problem = problemDAO.get(Integer.valueOf(id_problem));
				final String mimeType = TypeFileExtension.getMimeType(problem.getType());
				response.setContentType(mimeType);
				response.setHeader("Content-disposition",
						"attachment; filename=" + Engine.BASE_NAME_OUTPUT + Engine.DOT + problem.getType());
				final byte[] data = problem.getSol();
				response.setContentLength(data.length);
				response.getOutputStream().write(data);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			catch (final IOException ex)
			{
				logger.info("Error writing file to output stream. Filename was '{}'", id_problem, ex);
				throw new RuntimeException("IOError writing file to output stream");
			}
	}

	@RequestMapping(value = "/testCase/input/{id_problem}", method = RequestMethod.GET)
	public void getTestCase(@PathVariable("id_problem") String id_problem, HttpServletResponse response,
			HttpSession session)
	{
		if (SessionUtils.isLoggedIn(session))
			try
			{
				final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
				final Problem problem = problemDAO.get(Integer.valueOf(id_problem));
				final String mimeType = TypeFileExtension.getMimeType(problem.getType());
				response.setContentType(mimeType);
				response.setHeader("Content-disposition",
						"attachment; filename=" + Engine.BASE_NAME_INPUT + Engine.DOT + problem.getType());
				final byte[] data = problem.getTest();
				response.setContentLength(data.length);
				response.getOutputStream().write(data);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			catch (final IOException ex)
			{
				logger.info("Error writing file to output stream. Filename was '{}'", id_problem, ex);
				throw new RuntimeException("IOError writing file to output stream");
			}
	}

	@RequestMapping(value = "/manageExam", method = RequestMethod.GET)
	public String manageExam(final HttpSession session, @RequestParam int contestID, final Model model)
	{
		setAccountAttribute(session, model);
		final User user = (User) model.asMap().get("user");
		if (user == null || !user.isProfessor())
			return "redirect:/";

		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final QuizDAO quizDAO = (QuizDAO) context.getBean("quizDAO");

		final Contest contest = contestDAO.get(contestID);
		final List<Problem> problems = problemDAO.getProblemOfAContest(contestID);
		final List<Quiz> quizzes = quizDAO.getAllQuizByContest(contestID);
		model.addAttribute("contest", contest);
		model.addAttribute("problems", problems);
		model.addAttribute("quizzes", quizzes);

		return "manageExam";
	}

	@RequestMapping(value = "/myContests", method = RequestMethod.GET)
	public String myContests(final HttpSession session, final Model model)
	{
		setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final SubjectDAO subjectDAO = (SubjectDAO) context.getBean("subjectDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		if (user != null && user.isProfessor())
		{
			final Map<Subject, List<Contest>> subjectsMap = new HashMap<>();
			final List<Subject> subjects = subjectDAO.getAllSubjectFromProfessor(user.getId());
			for (final Subject subject : subjects)
			{
				final List<Contest> contests = contestDAO.getContestBySubject(subject.getSubjectId().getId_subject(),
						Integer.valueOf(subject.getSubjectId().getYear()));
				subjectsMap.put(subject, contests);
			}
			model.addAttribute("subjectsMap", subjectsMap);
			return "myContests";
		}
		return "redirect:/";
	}

	private void setAccountAttribute(HttpSession session, Model model)
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
}