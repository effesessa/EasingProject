package it.unical.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unical.core.Engine;
import it.unical.core.SubmissionHandler;
import it.unical.core.strategy.TypeContext;
import it.unical.dao.ContestDAO;
import it.unical.dao.MembershipDAO;
import it.unical.dao.ProblemDAO;
import it.unical.dao.SubmitDAO;
import it.unical.dao.TagDAO;
import it.unical.dao.TeamDAO;
import it.unical.dao.UserDAO;
import it.unical.entities.Contest;
import it.unical.entities.Membership;
import it.unical.entities.Problem;
import it.unical.entities.Submit;
import it.unical.entities.Tag;
import it.unical.entities.Team;
import it.unical.entities.User;
import it.unical.forms.AddProblemForm;
import it.unical.forms.SubmitForm;
import it.unical.utils.Judge;
import it.unical.utils.SessionUtils;
import it.unical.utils.Status;
import it.unical.utils.StringUtils;

@Controller
public class ProblemController
{
	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	@Autowired
	private WebApplicationContext context;

	@RequestMapping(value = "/addProblem", method = RequestMethod.POST)
	public String addProblem(HttpSession session, @ModelAttribute AddProblemForm problemForm, Model model)
			throws IOException
	{
		if (problemForm.isShow_testcase() == false)
			logger.info("NO ACCESS");
		else
			logger.info("ACCESS");
		logger.info("==============");
		logger.info(problemForm.getProblemTags());
		final String[] tags = problemForm.getProblemTags().split(",");
		logger.info("==============");
		for (final String string : tags)
			logger.info(string);

		final TypeContext typeContext = TypeContext.getInstance();
		typeContext.setStrategy(problemForm.getTestcase().getOriginalFilename());
		final Problem problem = typeContext.prepareToSave(problemForm);
		if (typeContext.getStatus() == Status.SUCCESS)
		{
			final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
			final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
			final Contest contest = contestDAO.getContestByName(problemForm.getContestName());
			problem.setId_contest(contest);
			problemDAO.create(problem);

			final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
			for (final String tag : tags)
			{
				final Tag t = new Tag();
				t.setProblem(problem);
				t.setValue(tag);
				tagDAO.create(t);
			}
		}
		else
			System.out.println("TODO redirect with popup errore" + typeContext.getStatus());
		return "redirect:/";
	}

	@RequestMapping(value = "/myProblems", method = RequestMethod.GET)
	public String addProblem(HttpSession session, Model model) throws IOException
	{
		setAccountAttribute(session, model);
		final User user = (User) model.asMap().get("user");

		if (user == null || !user.isProfessor())
			return "redirect:/";

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");

		// final List<String> professorContests =
		// contestDAO.getContestsNamesByProfessor(user.getId());
		final List<Contest> professorContests = contestDAO.getContestsByProfessor(user.getId());
		final List<Problem> problemsByProfessor = problemDAO.getProblemsByProfessor(user.getId());

		model.addAttribute("contests", professorContests);
		model.addAttribute("problems", problemsByProfessor);
		return "myproblems";
	}

	/*
	 * @RequestMapping(value = "/addProblem", method = RequestMethod.POST)
	 * public String addProblem(HttpSession session, @ModelAttribute
	 * AddProblemForm problemForm, Model model) throws IOException { // TODO
	 * Controllare che non esista un altro Problema dello stesso // Contest con
	 * lo stesso nome setAccountAttribute(session, model); final ProblemDAO
	 * problemDAO = (ProblemDAO) context.getBean("problemDAO"); final Problem
	 * problem = new Problem(); final ContestDAO contestDAO = (ContestDAO)
	 * context.getBean("contestDAO"); final Contest contest =
	 * contestDAO.getContestByName(problemForm.getContestName());
	 *
	 * switch (Integer.parseInt(problemForm.getType())) { case 1: {
	 *
	 * String pathTest = problemForm.getPathTest(); String pathSol =
	 * problemForm.getPathSol();
	 *
	 * pathTest = pathTest.replace("file:///", "/"); pathSol =
	 * pathSol.replace("file:///", "/");
	 *
	 * final File file1 = new File(pathTest.trim()); final byte[] fileData1 =
	 * new byte[(int) file1.length()]; final File file2 = new File(pathSol);
	 * byte[] fileData2 = new byte[(int) file2.length()]; try { final
	 * FileInputStream fileInputStream1 = new FileInputStream(file1);
	 * fileInputStream1.read(fileData1); fileInputStream1.close(); } catch
	 * (final Exception e) { e.printStackTrace(); return "redirect:/"; }
	 *
	 * try { final FileInputStream fileInputStream2 = new
	 * FileInputStream(file2); fileInputStream2.read(fileData2);
	 * fileInputStream2.close(); } catch (final Exception e) { fileData2 = null;
	 * e.printStackTrace();
	 *
	 * }
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); //problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setSol(fileData2); problem.setTest(fileData1);
	 * problemDAO.create(problem); logger.info("inserito"); return "redirect:/";
	 * } case 2: { String pathTest = problemForm.getPathZip();
	 *
	 * pathTest = pathTest.replace("file:///", "/");
	 *
	 * final File file1 = new File(pathTest.trim()); final byte[] fileData1 =
	 * new byte[(int) file1.length()]; try { final FileInputStream
	 * fileInputStream1 = new FileInputStream(file1);
	 * fileInputStream1.read(fileData1); fileInputStream1.close(); } catch
	 * (final Exception e) { e.printStackTrace(); return "redirect:/"; }
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setTest(fileData1); problemDAO.create(problem);
	 *
	 * return "redirect:/"; } case 3: { String pathTest =
	 * problemForm.getPathAlgorithm();
	 *
	 * pathTest = pathTest.replace("file:///", "/"); final File file1 = new
	 * File(pathTest.trim()); final byte[] fileData1 = new byte[(int)
	 * file1.length()];
	 *
	 * try { final FileInputStream fileInputStream1 = new
	 * FileInputStream(file1); fileInputStream1.read(fileData1);
	 * fileInputStream1.close(); } catch (final Exception e) {
	 * e.printStackTrace(); return "redirect:/"; }
	 *
	 * pathTest = pathTest.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", "");
	 *
	 * String result = judge.compile("java", "", pathTest);
	 *
	 * result = judge.execute("java", "", 1000, pathTest);
	 *
	 * final byte[] solution = result.getBytes();
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setSol(solution); problemDAO.create(problem);
	 *
	 * return "redirect:/"; }
	 *
	 * case 4: {
	 *
	 * String pathTest = problemForm.getPathAlgorithm();
	 *
	 * pathTest = pathTest.replace("file:///", "/"); final File file1 = new
	 * File(pathTest.trim()); final byte[] fileData1 = new byte[(int)
	 * file1.length()];
	 *
	 * try { final FileInputStream fileInputStream1 = new
	 * FileInputStream(file1); fileInputStream1.read(fileData1);
	 * fileInputStream1.close(); } catch (final Exception e) {
	 * e.printStackTrace(); return "redirect:/"; }
	 *
	 * pathTest = pathTest.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", ""); final String domain =
	 * problemForm.getDomain();
	 *
	 * String test = null;
	 *
	 * if (domain.equals("Array Integer")) { final TestCase testcase = new
	 * ArrayTest(); test = testcase.generate(); }
	 *
	 * String result = judge.compile("java", "", pathTest);
	 *
	 * result = judge.execute("java", test, 1000, pathTest);
	 * logger.info(result); if (result.equals("RUN_ERROR")) return "redirect:/";
	 * final byte[] solution = result.getBytes();
	 *
	 * System.out.println(result);
	 *
	 * problem.setName(problemForm.getName());
	 * problem.setType(problemForm.getType()); problem.setId_contest(contest);
	 * problem.setJury(contest.getJury()); problem.setTimelimit((float) 1000.0);
	 * problem.setTest(test.getBytes()); problem.setSol(solution);
	 * problemDAO.create(problem); } } return "redirect:/"; }
	 */

	@RequestMapping(value = "/createProblem", method = RequestMethod.GET)
	public void addProblem(@RequestParam String req, HttpSession session, Model model, HttpServletResponse response)
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		final ObjectMapper mapper = new ObjectMapper();
		final int userID = SessionUtils.getUserIdFromSessionOrNull(session);
		if (SessionUtils.isLoggedIn(session) && req.equals("contests"))
		{
			final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
			final List<String> contests = contestDAO.getContestsNamesByProfessor(userID);
			try
			{
				mapper.writeValue(response.getOutputStream(), contests);
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (SessionUtils.isLoggedIn(session) && req.equals("tags"))
		{
			final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
			final List<String> tags = tagDAO.getAllTagValues();
			try
			{
				mapper.writeValue(response.getOutputStream(), tags);
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (SessionUtils.isLoggedIn(session) && req.equals("popularTags"))
		{
			final TagDAO tagDAO = (TagDAO) context.getBean("tagDAO");
			final List<String> tags = tagDAO.getMostPopularTags();
			try
			{
				mapper.writeValue(response.getOutputStream(), tags);
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirm(@RequestParam String teamname, @RequestParam String problemid, @RequestParam String path,
			HttpSession session, Model model)
	{
		setAccountAttribute(session, model);

		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");

		final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO");
		final Team team = teamDAO.getByName(teamname);

		Submit submit = submitDAO.getAllSubmitByProblemAndTeamFake(Integer.parseInt(problemid), team.getId());

		final File fileSolution = new File(path);
		final byte[] fileData = new byte[(int) fileSolution.length()];

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final Problem problem = problemDAO.get(Integer.parseInt(problemid));

		if (submit != null)
			submitDAO.delete(submit);
		final LocalDate localDate = LocalDate.now();

		submit = new Submit();
		submit.setIdTeam(team);
		submit.setProblem(problem);
		submit.setInfo(problem.getName());
		// set the score eventually here
		submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate));
		submit.setSolution(fileData);

		submitDAO.create(submit);
		return "redirect:/";

	}

	@RequestMapping(value = "/problem", method = RequestMethod.POST)
	public String editOrDeleteProblem(@RequestParam String op, @RequestParam int id, HttpSession session, Model model)
	{
		if (op.equals("deleteProblem"))
		{
			final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
			final Problem problem = problemDAO.get(id);
			final Integer userID = SessionUtils.getUserIdFromSessionOrNull(session);
			if (userID != null && userID.equals(problem.getJury().getProfessor().getId()))
				problemDAO.delete(problem);
		}
		else
		{
			// Controllare che l'utente sia collegato, sia un Prof e che sia il
			// Leader della Giuria del Problema (l'if di sopra più o meno)
			// TODO Prendere form e modificare il Problema
		}
		return "myproblems";
	}

	private ArrayList<String> executeZip(Team team, byte[] data, String pathSol)
	{

		final Judge judge = new Judge("java", team.getName());
		///////// SCOMPATTO L'ARCHIVIO CHE SI TROVA IN DATA//////
		InputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;
		boolean found = false;
		final ArrayList<String> info = new ArrayList<String>();
		try
		{
			fis = new ByteArrayInputStream(data);
			zipIs = new ZipInputStream(new BufferedInputStream(fis));
			int cont = 0;
			int contWrong = 0;

			while ((zEntry = zipIs.getNextEntry()) != null)
			{
				cont++;
				System.out.println(zEntry.getName());
				final byte[] test = new byte[(int) zEntry.getSize()];
				zipIs.read(test, 0, test.length);
				final String strTestCase = new String(test, StandardCharsets.UTF_8);
				zEntry = zipIs.getNextEntry();
				System.out.println(zEntry.getName());
				final byte[] sol = new byte[(int) zEntry.getSize()];
				zipIs.read(sol);
				final String strSol = new String(sol, StandardCharsets.UTF_8);
				String result = judge.compile("java", team.getName(), pathSol);

				///////////////// Judge every file in zip///////////////
				if (result.equals("COMPILE_SUCCESS"))
				{
					// info.add("Compilation result: "+result);
					result = judge.execute("java", strTestCase, 1000, pathSol);

					if (result.contains("RUN_ERROR") || result.equals("TLE"))
						info.add("execution result: " + result);
					//// controllare l'esecuzione/////
					final String match = judge.match(result, strSol);
					if (!match.equals("RIGHT"))
					{
						contWrong++;
						found = true;
						info.add("Mismatch with: " + zEntry.getName());
					}
				}

			}
			zipIs.close();
			if (!found)
			{
				logger.info("corretto");
				info.add("No mismatch found");
				info.add("All tests passed!!");

				return info;
			}
			else
			{
				logger.info("errato");
				info.add("Test Failed: " + contWrong + "/" + cont);
				return info;
			}

		}
		catch (final FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String newsubmit(@ModelAttribute SubmitForm submitForm, HttpSession session, Model model) throws IOException
	{
		setAccountAttribute(session, model);
		System.out.println("********************submit*********************");

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final Problem problem = problemDAO.get(submitForm.getIdProblem());
		System.out.println(submitForm.getIdProblem());
		System.out.println(submitForm.getTeam());
		System.out.println(submitForm.getSolution().getOriginalFilename());
		final TypeContext typeContext = TypeContext.getInstance();
		typeContext.setStrategy(Engine.BASE_NAME_INPUT + Engine.DOT + problem.getType());
		final String status = typeContext.submit(problem, submitForm);
		System.out.println(status);
		SubmissionHandler.save(context, problem, submitForm, status);
		System.out.println("********************submit*********************");
		return "redirect:/";
	}

	// Vista di un Problema
	// Vista di origine non utilizzata
	// Lista dei problemi già integrata nella Vista dei Contest
	@RequestMapping(value = "/problem", method = RequestMethod.GET)
	public String problemMainView(@RequestParam String id, HttpSession session, Model model)
	{
		setAccountAttribute(session, model);

		final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
		final Problem problem = problemDAO.get(Integer.parseInt(id));

		final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
		final List<Submit> submits = submitDAO.getAllSubmitByProblem(problem.getId_problem());

		final ContestDAO contestDAO = (ContestDAO) context.getBean("contestDAO");
		final Contest contest = contestDAO.get(problem.getId_contest().getIdcontest());

		final MembershipDAO membershipDAO = (MembershipDAO) context.getBean("membershipDAO");
		final List<Membership> memberships = membershipDAO
				.getTeamByStudent(SessionUtils.getUserIdFromSessionOrNull(session));

		model.addAttribute("memberships", memberships);
		model.addAttribute("problem", problem);
		model.addAttribute("submits", submits);
		model.addAttribute("contest", contest);

		return "problemview";

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

	// Submit soluzione
	/*
	 * @RequestMapping(value = "/submit", method = RequestMethod.POST) public
	 * String submit(@RequestParam String problemId, HttpSession
	 * session, @RequestParam("team") String team1,
	 *
	 * @RequestParam String path, Model model) throws IOException {
	 * setAccountAttribute(session, model);
	 *
	 * final SubmitDAO submitDAO = (SubmitDAO) context.getBean("submitDAO");
	 *
	 * final TeamDAO teamDAO = (TeamDAO) context.getBean("teamDAO"); final Team
	 * team = teamDAO.getByName(team1);
	 *
	 * final ProblemDAO problemDAO = (ProblemDAO) context.getBean("problemDAO");
	 * final Problem problem = problemDAO.get(Integer.parseInt(problemId));
	 *
	 * Submit submit =
	 * submitDAO.getAllSubmitByProblemAndTeamFake(Integer.parseInt(problemId),
	 * team.getId()); switch (Integer.parseInt(problem.getType())) { case 1: {
	 * String pathSol = path; final byte[] data = problem.getTest(); final
	 * byte[] data2 = problem.getSol();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", team.getName());
	 *
	 * String result = judge.compile("java", team.getName(), pathSol);
	 *
	 * final String strTestCase = new String(data, StandardCharsets.UTF_8);
	 * final String strSolution = new String(data2, StandardCharsets.UTF_8);
	 *
	 * if (result.equals("COMPILE_SUCCESS")) { result = judge.execute("java",
	 * strTestCase, 1000, pathSol);
	 *
	 * final String match = judge.match(result, strSolution); if
	 * (match.equals("RIGHT")) { final LocalDate localDate = LocalDate.now();
	 * logger.info("corretto"); if (submit != null) { submitDAO.delete(submit);
	 * submit.setIdTeam(team); submit.setProblem(problem);
	 * submit.setInfo(problem.getName());
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); // set the score eventually here submit.setSolution(fileData);
	 * submitDAO.create(submit); return "redirect:/"; } else { submit = new
	 * Submit(); submit.setIdTeam(team); submit.setProblem(problem);
	 * submit.setInfo(problem.getName()); // set the score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "redirect:/"; } } else { logger.info("errato"); return "redirect:/"; } }
	 * else return "redirect:/"; }
	 *
	 * case 2: { String pathSol = path; final byte[] data = problem.getTest();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final ArrayList<String> info = executeZip(team, data, pathSol);
	 * model.addAttribute("problem", problem); model.addAttribute("infos",
	 * info); model.addAttribute("team", team); model.addAttribute("path",
	 * path);
	 *
	 * return "addProblemConfirmation"; }
	 *
	 * case 3: { final LocalDate localDate = LocalDate.now(); String pathSol =
	 * path; final byte[] data2 = problem.getSol();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", team.getName());
	 *
	 * String result = judge.compile("java", team.getName(), pathSol);
	 *
	 * final String strSolution = new String(data2, StandardCharsets.UTF_8);
	 *
	 * if (result.equals("COMPILE_SUCCESS")) { result = judge.execute("java",
	 * "", 1000, pathSol);
	 *
	 * final String match = judge.match(result, strSolution); if
	 * (match.equals("RIGHT")) { logger.info("corretto"); if (submit != null) {
	 * submitDAO.delete(submit); submit.setIdTeam(team);
	 * submit.setProblem(problem); submit.setInfo(problem.getName()); // set the
	 * score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "problemview"; } else { submit = new Submit(); submit.setIdTeam(team);
	 * submit.setProblem(problem); submit.setInfo(problem.getName()); // set the
	 * score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "problemview"; } } else { logger.info("errato"); return "redirect:/"; } }
	 * else return "redirect:/"; }
	 *
	 * case 4: { final LocalDate localDate = LocalDate.now(); String pathSol =
	 * path; final byte[] data = problem.getTest(); final byte[] data2 =
	 * problem.getSol();
	 *
	 * pathSol = pathSol.replace("file:///", "/");
	 *
	 * final File fileSolution = new File(pathSol); final byte[] fileData = new
	 * byte[(int) fileSolution.length()];
	 *
	 * pathSol = pathSol.replace("/Main.java", "");
	 *
	 * final Judge judge = new Judge("java", team.getName());
	 *
	 * String result = judge.compile("java", team.getName(), pathSol);
	 *
	 * final String strTestCase = new String(data, StandardCharsets.UTF_8);
	 * System.out.println("Gli passo questo test case: " + strTestCase); final
	 * String strSolution = new String(data2, StandardCharsets.UTF_8);
	 *
	 * if (result.equals("COMPILE_SUCCESS")) { result = judge.execute("java",
	 * strTestCase, 1000, pathSol);
	 *
	 * final String match = judge.match(result, strSolution); if
	 * (match.equals("RIGHT")) { logger.info("corretto"); if (submit != null) {
	 * submitDAO.delete(submit); submit.setIdTeam(team);
	 * submit.setProblem(problem); submit.setInfo(problem.getName());
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); // set the score eventually here submit.setSolution(fileData);
	 * submitDAO.create(submit); return "redirect:/"; } else { submit = new
	 * Submit(); submit.setIdTeam(team); submit.setProblem(problem);
	 * submit.setInfo(problem.getName()); // set the score eventually here
	 * submit.setDate(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate)
	 * ); submit.setSolution(fileData); submitDAO.create(submit); return
	 * "redirect:/"; } } else { logger.info("errato"); return "redirect:/"; } }
	 * else return "redirect:/"; }
	 *
	 * } return "redirect:/";
	 *
	 * }
	 */
}