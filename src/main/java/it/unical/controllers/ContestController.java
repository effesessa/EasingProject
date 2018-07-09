package it.unical.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import it.unical.dao.RegistrationDAO;
import it.unical.dao.SubjectDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.JuryMember;
import it.unical.entities.Membership;
import it.unical.entities.Partecipation;
import it.unical.entities.Problem;
import it.unical.entities.Subject;
import it.unical.entities.SubjectId;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.SubscribeForm;
import it.unical.utils.SessionUtils;
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
	public void contestgetFile(@PathVariable("file_name") String fileName, HttpServletResponse response)
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

		}
		catch (final IOException ex)
		{
			logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
			throw new RuntimeException("IOError writing file to output stream");
		}

	}

	
	@RequestMapping(value = "/files/{id_problem}", method = RequestMethod.GET)
	public void getTestCase(@PathVariable("id_problem") String id_problem, HttpServletResponse response) {
		try
		{
			final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
			final Problem problem = problemDAO.get(Integer.valueOf(id_problem));
			if(problem.getType().equals(TypeFileExtension.ZIP))
				response.setContentType("application/zip");
			else
				response.setContentType("text/plain");
			response.setHeader("Content-disposition", 
					"attachment; filename=" + Engine.BASE_NAME + Engine.DOT + problem.getType());
			final byte[] data = problem.getTest();
			response.setContentLength(data.length);
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
		}
		catch (IOException ex) {
			logger.info("Error writing file to output stream. Filename was '{}'", id_problem, ex);
			throw new RuntimeException("IOError writing file to output stream");
		}
	}
	
	// Visualizza il Contest e la lista dei Problemi
	@RequestMapping(value = "/contest", method = RequestMethod.GET)
	public String contestMainView(@RequestParam String name, HttpSession session, Model model)
	{
		// TODO Reindirizzare in caso di name empty o di Professore
		setAccountAttribute(session, model);
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Contest contest = contestDAO.getContestByName(name);

		final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
		final List<Membership> memberships = membershipDAO
				.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));
		final ArrayList<Team> teams = new ArrayList<Team>(memberships.size());
		for (int i = 0; i < memberships.size(); i++)
			teams.add(memberships.get(i).getTeam());
		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final List<Problem> problems = problemDAO.getProblemOfAContest(contest.getIdcontest());
		for (final Problem p : problems)
			p.setDescription(p.getDescription().replaceAll("\r\n", "<br />"));
		logger.info("contesttttt " + problems.size());
		model.addAttribute("teams", teams);
		if (!problems.isEmpty())
			model.addAttribute("problems", problems);
		else
			model.addAttribute("problems", "");
		model.addAttribute("contest", contest.getIdcontest());
		return "contest";

	}

	// Iscrizione a un Contest
	// Vista di origine mai utilizzata
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
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
			final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
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
			return "iscritto";
		}
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