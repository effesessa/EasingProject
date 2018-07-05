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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import it.unical.dao.ContestDAO;
import it.unical.dao.MembershipDAO;
import it.unical.dao.PartecipationDAO;
import it.unical.dao.SubmitDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.Membership;
import it.unical.entities.Partecipation;
import it.unical.entities.Submit;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.AddTeamForm;
import it.unical.forms.LogInForm;
import it.unical.utils.SessionUtils;

@Controller
public class TeamController
{

	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

	@Autowired
	private WebApplicationContext context;

	@RequestMapping(value = "/addTeam", method = RequestMethod.POST)
	public String AddTeam(HttpSession session, Model model, @ModelAttribute("addTeamForm") AddTeamForm form)
	{

		setAccountAttribute(session, model);
		final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		final User user = userDAO.get(SessionUtils.getUserIdFromSessionOrNull(session));
		final User user1 = user;
		User user2 = null;
		User user3 = null;

		if (form.getMember2() != "")
			user2 = userDAO.get(Integer.parseInt(form.getMember2()));
		if (form.getMember3() != "")
			user3 = userDAO.get(Integer.parseInt(form.getMember3()));

		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		Team team = teamDAO.getByName(form.getName());

		// TODO Da gestire meglio, com'Ë ora potrei mettere tre volte la stessa
		// matricola, o inserirla in posizioni differenti dalla prima creando
		// NullPointerExcep.
		// Si potrebbe omettere un input nel form, la matricola di chi crea il
		// team verr‡ implicitamente inserita nel team.
		if (user2 == null && user3 == null)
		{
			logger.info("errore, no members selected");
			return "redirect:/createteam";
		}
		else if ((form.getMember2() != "" && user2 == null) || (form.getMember3() != "" && user3 == null))
		{
			logger.info("errore, matricole non valide");
			return "redirect:/createteam";
		}

		else if (form.getName() == "" || team != null)
		{
			logger.info("errore, nome team errato o gi√† in uso");
			return "redirect:/createteam";
		}
		else if (user2 == null && user3 == null)
		{
			logger.info("current2: " + user1.getId());
			team = new Team();
			team.setName(form.getName());
			teamDAO.create(team);
			final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
			final Membership membership = new Membership();
			membership.setTeam(team);
			membership.setUser(user1);
			membershipDAO.create(membership);
		}
		else if (user3 == null)
		{
			team = new Team();
			team.setName(form.getName());
			teamDAO.create(team);
			final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
			final Membership membership = new Membership();
			membership.setTeam(team);
			membership.setUser(user1);
			membershipDAO.create(membership);
			membership.setUser(user2);
			membershipDAO.create(membership);
		}
		else
		{
			team = new Team();
			team.setName(form.getName());
			teamDAO.create(team);
			final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
			final Membership membership = new Membership();
			membership.setTeam(team);
			membership.setUser(user1);
			membershipDAO.create(membership);
			membership.setUser(user2);
			membershipDAO.create(membership);
			membership.setUser(user3);
			membershipDAO.create(membership);
		}

		logger.info("TEAM CREATO CON SUCCESSO");
		return "redirect:/createteam";

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

	@RequestMapping(value = "/teamview", method = RequestMethod.GET)
	public String subscribeContest(@RequestParam String name, HttpSession session, Model model)
	{

		setAccountAttribute(session, model);
		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		final Team team = teamDAO.getByName(name);
		final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
		final List<Membership> students = membershipDAO.getStudentsByTeam(team.getId());
		final PartecipationDAO partecipationDAO = (PartecipationDAO) context.getBean("partecipationDAO");
		final List<Partecipation> contests = partecipationDAO.getContestByTeam(team.getId());
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final ArrayList<Contest> contest = new ArrayList<Contest>(contests.size());
		for (int i = 0; i < contests.size(); i++)
			contest.add(contestDAO.get(contests.get(i).getId()));

		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		final List<Submit> submits = submitDAO.getAllSubmitByTeam(team.getId());

		model.addAttribute("students", students);
		model.addAttribute("submits", submits);
		model.addAttribute("contests", contest);
		model.addAttribute("team", team);
		// TODO Vista mancante
		return "/team";
	}

	// Visualizza i Team
	@RequestMapping(value = "/createteam", method = RequestMethod.GET)
	public String teamMainView(HttpSession session, Model model)
	{

		final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
		final List<Membership> memberships = membershipDAO
				.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));
		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		final ArrayList<Team> teams = new ArrayList<Team>(memberships.size());
		for (int i = 0; i < memberships.size(); i++)
			teams.add(teamDAO.get(memberships.get(i).getTeam().getId()));
		setAccountAttribute(session, model);
		model.addAttribute("teams", teams);
		return "teamviews";

	}
}